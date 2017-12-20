package br.ufpi.easii.iscool.controle;

import java.util.List;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.ufpi.easii.iscool.dao.EscolaDao;
import br.ufpi.easii.iscool.dao.RedeEscolarDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Escola;
import br.ufpi.easii.iscool.entidade.GestorDeRede;
import br.ufpi.easii.iscool.entidade.RedeEscolar;
import br.ufpi.easii.iscool.sessao.GestorDeRedeLogado;
import br.ufpi.easii.iscool.util.EmailUtil;
import br.ufpi.easii.iscool.util.GeradorDeSenha;

@Controller
public class RedeEscolarController {

	private RedeEscolarDao redes;
	private UsuarioDao usuarios;
	private EscolaDao escolas;
	private EmailUtil emailUtil;
	private GestorDeRedeLogado gestorDeRedeLogado;
	private Result result;
	private Validator validator;

	protected RedeEscolarController(){
		this(null, null, null, null, null, null, null);
	}

	@Inject
	public RedeEscolarController(RedeEscolarDao redes, Result result, UsuarioDao usuarios, Validator validator, EmailUtil emailUtil, EscolaDao escolas, GestorDeRedeLogado gestorDeRedeLogado){
		this.redes = redes;
		this.result = result;
		this.usuarios = usuarios;
		this.validator = validator;
		this.emailUtil = emailUtil;
		this.escolas = escolas;
		this.gestorDeRedeLogado = gestorDeRedeLogado;
	}

	@Get("/")
	public void iscool(){
		System.out.println("Entrou na pagina inicial");
	}

	@Get("inserirRedeEscolar")
	public void inserirRedeEscolar(){
	}

	@Get
	public void telaPrincipal(){
		
		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você está desconectado! Logue no sistema para acessar o recurso."));
		validator.onErrorUsePageOf(UsuarioController.class).login();
		
		RedeEscolar redeEscolar = redes.pesquisarRedeEscolarPorId(gestorDeRedeLogado.getRede().getId());
		
		result.include("numeroDeEscolas", redeEscolar.getEscolas().size());
	}

	@Post
	public void inserirRedeEscolar(RedeEscolar rede, final GestorDeRede gestor, String confirmaSenha){
		
		validator.check(gestor.getNome() != null && gestor.getEmail() != null && gestor.getSenha() != null, new SimpleMessage("erro", "Preencha todos os campos do formulário!"));
		validator.onErrorRedirectTo(RedeEscolarController.class).inserirRedeEscolar();
		
		validator.check(rede.getNome() != null, new SimpleMessage("erro", "Preencha todos os campos do formulário!"));
		validator.onErrorRedirectTo(RedeEscolarController.class).inserirRedeEscolar();
		
		validator.check(usuarios.pesquisarUsuarioPorEmail(gestor) == false, new SimpleMessage("erro", "Usuário já existente!"));
		validator.onErrorRedirectTo(RedeEscolarController.class).inserirRedeEscolar();
		
		validator.check(redes.pesquisarRedePorCnpjOuRazaoSocial(rede.getDadosCadastrais().getCnpj(), rede.getDadosCadastrais().getRazaoSocial()) == false, new SimpleMessage("erro", "Rede com mesmo CNPJ ou Razão Social já existente no banco de dados!"));
		validator.onErrorRedirectTo(RedeEscolarController.class).inserirRedeEscolar();
		
		validator.check(gestor.getSenha().length() >= 6 && gestor.getSenha().length() < 30, new SimpleMessage("erro", "A senha deve conter entre 6 e 30 dígitos!"));
		validator.onErrorRedirectTo(RedeEscolarController.class).inserirRedeEscolar();
		
		validator.check(gestor.getSenha().equals(confirmaSenha), new SimpleMessage("erro", "As senhas não coincidem!"));
		validator.onErrorRedirectTo(RedeEscolarController.class).inserirRedeEscolar();

		String hashSenha = GeradorDeSenha.generateHash(gestor.getSenha());
		gestor.setSenha(hashSenha);
		gestor.setRedeEscolar(rede);
		rede.setGestorDeRede(gestor);
		usuarios.inserirUsuario(gestor);
		redes.inserirRedeEscolar(rede);

		String mensagem = "Senhor " + gestor.getNome() + " sua conta foi criada com sucesso!<br>" + "Sua senha é: ";

		try {
			emailUtil.send("Licença do IsCool", mensagem, gestor.getEmail());
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result.include("feedback", "Rede escolar cadastrada com sucesso!");
		result.redirectTo(RedeEscolarController.class).iscool();
	}

	@Get
	public List<Escola> listarEscolas(long idDaRede){
		
		String alerta = null;
		
		RedeEscolar redeEscolar = redes.pesquisarRedeEscolarPorId(idDaRede);
		
		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();

		validator.check(redeEscolar != null, new SimpleMessage("erro", "Você não possui permissão para acessar esse recurso!"));
		validator.onErrorUsePageOf(RedeEscolarController.class).telaPrincipal();

		validator.check(gestorDeRedeLogado.getId() == redeEscolar.getGestorDeRede().getId(), new SimpleMessage("erro", "Você não possui permissão para acessar esse recurso!"));
		validator.onErrorUsePageOf(RedeEscolarController.class).telaPrincipal();
		
		List<Escola> escolasLista = escolas.listarEscolasPorIdDaRede(idDaRede);
		if(escolasLista.size() == 0)
			alerta = "Você ainda não possui escolas cadastradas";
		
		result.include("alert", alerta);
		return escolasLista;
	}
}