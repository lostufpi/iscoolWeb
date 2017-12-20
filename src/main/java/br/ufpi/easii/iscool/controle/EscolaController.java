package br.ufpi.easii.iscool.controle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.ufpi.easii.iscool.dao.EscolaDao;
import br.ufpi.easii.iscool.dao.RedeEscolarDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.DadosCadastrais;
import br.ufpi.easii.iscool.entidade.Escola;
import br.ufpi.easii.iscool.entidade.GestorEscolar;
import br.ufpi.easii.iscool.entidade.RedeEscolar;
import br.ufpi.easii.iscool.sessao.GestorDeRedeLogado;
import br.ufpi.easii.iscool.sessao.GestorEscolarLogado;
import br.ufpi.easii.iscool.util.EmailUtil;
import br.ufpi.easii.iscool.util.GeradorDeSenha;

@Controller
@Named
public class EscolaController {

	private EscolaDao escolas;
	private UsuarioDao usuarios;
	private EmailUtil emailUtil;
	private RedeEscolarDao redes;
	private GestorDeRedeLogado gestorDeRedeLogado;
	private GestorEscolarLogado gestorEscolarLogado;

	private Result result;
	private Validator validator;

	protected EscolaController(){
		this(null, null, null, null, null, null, null, null);
	}

	@Inject
	public EscolaController(EscolaDao escolas, RedeEscolarDao redes, Result result, UsuarioDao usuarios, Validator validator, EmailUtil emailUtil, GestorDeRedeLogado gestorDeRedeLogado, GestorEscolarLogado gestorEscolarLogado){
		this.escolas = escolas;
		this.redes = redes;
		this.result = result;
		this.usuarios = usuarios;
		this.validator = validator;
		this.emailUtil = emailUtil;
		this.gestorDeRedeLogado = gestorDeRedeLogado;
		this.gestorEscolarLogado = gestorEscolarLogado;
	}
	
	@Get("criarContaEscolar")
	public void criarConta(){
	}

	@Get
	public void inserirEscola(){
		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
	}

	@Get
	public void telaPrincipal(){
		validator.check(gestorEscolarLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
	}
	
	@Post
	public void criarConta(final Escola escola, final GestorEscolar gestor, String confirmaSenha){
		
		//Valida se todos os campos obrigatórios referentes as informações da escola estão preenchidos
		validator.check(escola.getNome() != null && escola.getIdentificadorNaRede() != null, new SimpleMessage("erro", "Preencha todos os campos do formulário!"));
		validator.onErrorRedirectTo(EscolaController.class).criarConta();
		
		//Valida se todos os campos obrigatórios referenste as informeções do gestor da escola estão preenchidos
		validator.check(gestor.getNome() != null && gestor.getEmail() != null && gestor.getSenha() != null, new SimpleMessage("erro", "Preencha todos os campos do formulário!"));
		validator.onErrorRedirectTo(EscolaController.class).criarConta();
		
		//Valida se já existe um usuário com o mesmo email do novo gestor
		validator.check(usuarios.pesquisarUsuarioPorEmail(gestor) == false, new SimpleMessage("erro", "Usuário já existente!"));
		validator.onErrorRedirectTo(EscolaController.class).criarConta();
		
		//Valida se já existe uma escola com mesmo CNPJ ou Razão Social da nova escola
		validator.check(escolas.pesquisarEscolaPorCnpjOuRazaoSocial(escola.getDadosCadastrais().getCnpj(), escola.getDadosCadastrais().getRazaoSocial()) == false, new SimpleMessage("erro", "Escola com mesmo CNPJ ou Razão Social já cadastrada!"));
		validator.onErrorRedirectTo(EscolaController.class).criarConta();
		
		//Valida se já existe uma escola com as mesmas caracteristicas da mesma escola
		validator.check(escolas.pesquisarEscolaExistente(escola.getIdentificadorNaRede(), escola.getNome()) == false, new SimpleMessage("erro", "Escola já existente no banco de dados!"));
		validator.onErrorRedirectTo(EscolaController.class).criarConta();
		
		//Valida se as senha e confirmação de senha coincidem
		validator.check(gestor.getSenha().equals(confirmaSenha), new SimpleMessage("erro", "As senhas não coincidem!"));
		validator.onErrorRedirectTo(EscolaController.class).criarConta();
		
		//Valida se a senha contem entre 6 e 30 caracteres
		validator.check(gestor.getSenha().length() >= 6 && gestor.getSenha().length() < 30, new SimpleMessage("erro", "A senha deve conter entre 6 e 30 caracteres!"));
		validator.onErrorRedirectTo(EscolaController.class).criarConta();
		String hashSenha = GeradorDeSenha.generateHash(gestor.getSenha());
		escola.setGestorEscolar(gestor);
		gestor.setEscola(escola);
		gestor.setAluno(false);
		gestor.setProfessor(false);
		gestor.setSenha(hashSenha);
		usuarios.inserirUsuario(gestor);
		escolas.inserirEscolar(escola);
		
		String mensagem = "Senhor " + gestor.getNome() + " ,você foi cadastrado com sucesso! <br>"
				+ "Bem-Vindo ao IsCool. <br>Seu login é: " + gestor.getEmail() +
				"<br>Sua senha é: " + gestor.getSenha();
		
		try {
			emailUtil.send("[IsCool]", mensagem, gestor.getEmail());
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.include("mensagem", "Escola cadastrada com sucesso!");
		result.redirectTo(RedeEscolarController.class).iscool();
	}
	
	@Post
	public void inserirEscola(Escola escola, final GestorEscolar gestor, long idDaRede){

		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		validator.check(usuarios.pesquisarUsuarioPorEmail(gestor) == false, new SimpleMessage("erro", "Usuário já existente!"));
		validator.onErrorUsePageOf(EscolaController.class).inserirEscola();

		String senha = GeradorDeSenha.geradorDeSenha();
		String hashSenha = GeradorDeSenha.generateHash(senha);
		RedeEscolar r = redes.pesquisarRedeEscolarPorId(idDaRede);

		gestor.setEscola(escola);
		gestor.setSenha(hashSenha);
		gestor.setAluno(false);
		gestor.setProfessor(false);

		escola.setGestorEscolar(gestor);
		escola.setRedeEscolar(r);

		if(escolas.pesquisarEscolaExistente(escola.getIdentificadorNaRede(), escola.getNome(), escola.getRedeEscolar().getId()) == false){
			usuarios.inserirUsuario(gestor);
			escolas.inserirEscolar(escola);

			String mensagem = "Senhor(a)" + gestor.getNome() + " você foi cadastrado com sucesso! <br>"
					+ "Bem-Vindo ao IsCool. <br>Seu login é: " + gestor.getEmail() +
					"Sua senha é: " + senha;

			try {
				emailUtil.send("Bem Vindo Ao IsCool", mensagem, gestor.getEmail());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			result.include("feedback", "Escola cadastrada com sucesso!");
			result.redirectTo(RedeEscolarController.class).telaPrincipal();
		}else{
			validator.add(new SimpleMessage("feedback", "Escola já existente no banco de dados!"));
			validator.onErrorUsePageOf(EscolaController.class).inserirEscola();
		}
	}

	@Post
	public void importarArquivoCSV(UploadedFile uploadedFile, long idDaRede) {

		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		if(uploadedFile != null){

			String nomeDoArquivo = uploadedFile.getFileName();
			String extencao = null;

			for(int i = 0;i < nomeDoArquivo.length(); i++){
				if(nomeDoArquivo.charAt(i) == '.'){
					extencao = nomeDoArquivo.substring(i, nomeDoArquivo.length());
					System.out.println("Extenção do arquivo: " + extencao);
				}
			}

			if(extencao.equals(".csv")){
				InputStream inputStream = uploadedFile.getFile();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

				ArrayList<Escola> escolasJaCadastradas = new ArrayList<Escola>();
				ArrayList<Escola> escolasCadastradas = new ArrayList<Escola>();
				
				DadosCadastrais dadosCadastrais = new DadosCadastrais();

				String linha = null;

				try{
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
					while((linha = bufferedReader.readLine()) != null){

						String informacao = null;

						int inicio = 0;
						int controle = 0;

						Escola escola = new Escola();

						GestorEscolar gestor = new GestorEscolar();

						String senha = GeradorDeSenha.geradorDeSenha();

						System.out.println("Tamanho da linha: " + linha.length());

						for(int i = 0;i < linha.length(); i++){
							if(linha.charAt(i) == ';'){
								informacao = linha.substring(inicio, i);
								inicio = i + 1;
								if(controle == 0){
									System.out.println("Identificador na rede: " + informacao);
									escola.setIdentificadorNaRede(informacao);
								}						
								if(controle == 1){
									System.out.println("Nome da escola: " + informacao);
									escola.setNome(informacao);
								}
								if(controle == 2){
									System.out.println("Nome do gestor: " + informacao);
									gestor.setNome(informacao);
								}
								if(controle == 3){
									System.out.println("Razão Social: " + informacao);
									dadosCadastrais.setRazaoSocial(informacao);
									escola.setDadosCadastrais(dadosCadastrais);
								}
								if(controle == 4){
									System.out.println("Cnpj: " + informacao);
									dadosCadastrais.setCnpj(informacao);
									escola.setDadosCadastrais(dadosCadastrais);
								}
								controle = controle + 1;
							}
							if(linha.length()-1 == i){
								informacao = linha.substring(inicio, i + 1);
								System.out.println("Email do gestor: " + informacao);
								gestor.setEmail(informacao);
							}
						}
						RedeEscolar rede = redes.pesquisarRedeEscolarPorId(idDaRede);

						gestor.setEscola(escola);
						gestor.setSenha(senha);
						gestor.setAluno(false);
						gestor.setProfessor(false);
						
						escola.setGestorEscolar(gestor);
						escola.setRedeEscolar(rede);

						if(usuarios.pesquisarUsuarioPorEmail(gestor) ==  false){
							if(escolas.pesquisarEscolaExistente(escola.getIdentificadorNaRede(), escola.getNome(), escola.getRedeEscolar().getId()) == false){
								System.out.println("Entrou para tentar cadastrar a escola");
								usuarios.inserirUsuario(gestor);
								escolas.inserirEscolar(escola);

								escolasCadastradas.add(escola);

							}else{

								escolasJaCadastradas.add(escola);

							}
						}
					}
					result.include("escolasJaCadastradas", escolasJaCadastradas);
					result.include("escolasCadastradas", escolasCadastradas);
					result.redirectTo(RedeEscolarController.class).telaPrincipal();
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}else{
				System.out.println("É necessário entrar com um arquivo do tipo .csv para fazer a importação!");
				validator.add(new SimpleMessage("feedback", "É necessário entrar com um arquivo do tipo .csv para fazer a importação!"));
				validator.onErrorUsePageOf(EscolaController.class).inserirEscola();
			}
		}else{
			System.out.println("É necessário entrar com o arquivo para fazer a importação!");
			validator.add(new SimpleMessage("feedback", "É necessário entrar com o arquivo para fazer a importação!"));
			validator.onErrorUsePageOf(EscolaController.class).inserirEscola();
		}
	}

	@Delete("escola/{id}")
	public void excluirEscola(long id){

		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Escola escola = escolas.pesquisarEscolaPorId(id);
		
		validator.check(gestorDeRedeLogado.getId() == escola.getRedeEscolar().getGestorDeRede().getId(), new SimpleMessage("erro", "Você não possui permissão para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		validator.check(escola != null, new SimpleMessage("erro", "Esta escola não existe!"));
		validator.onErrorUsePageOf(RedeEscolarController.class).telaPrincipal();
		
		validator.check(escola.getRedeEscolar().getGestorDeRede().getId() == gestorDeRedeLogado.getId(), new SimpleMessage("erro", "Você não possui permissão para acessar esse recurso!"));
		validator.onErrorUsePageOf(RedeEscolarController.class).telaPrincipal();
		
		escolas.excluirEscola(escola);
		
		result.include("feedback", "Escola excluida com sucesso!");
		result.redirectTo(RedeEscolarController.class).listarEscolas(escola.getRedeEscolar().getId());
	}

	@Get
	public Escola atualizarEscola(long id){
		
		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Escola escola = escolas.pesquisarEscolaPorId(id);
		
		validator.check(escola != null, new SimpleMessage("erro", "Esta escola não existe"));
		validator.onErrorUsePageOf(RedeEscolarController.class).listarEscolas(gestorDeRedeLogado.getRede().getId());
		
		validator.check(escola.getRedeEscolar().getGestorDeRede().getId() == gestorDeRedeLogado.getId(), new SimpleMessage("erro", "Você não possui permissão para acessar esse recurso!"));
		validator.onErrorUsePageOf(RedeEscolarController.class).telaPrincipal();
		
		return escola; 
	}

	@Post
	public void atualizarEscola(Escola escola, long idDaEscola){
		
		validator.check(gestorDeRedeLogado.isLogado(), new SimpleMessage("erro", "Você precisa está autenticado para acessar esse recurso!"));
		validator.onErrorRedirectTo(UsuarioController.class).login();
		
		Escola e = escolas.pesquisarEscolaPorId(idDaEscola);
		
		e.getGestorEscolar().setNome(escola.getGestorEscolar().getNome());
		e.getGestorEscolar().setEmail(escola.getGestorEscolar().getEmail());
		
		e.getDadosCadastrais().setTelefone(escola.getDadosCadastrais().getTelefone());
		
		e.getEndereco().setLogradouro(escola.getEndereco().getLogradouro());
		e.getEndereco().setEndereco(escola.getEndereco().getEndereco());
		e.getEndereco().setNumero(escola.getEndereco().getNumero());
		e.getEndereco().setBairro(escola.getEndereco().getBairro());
		e.getEndereco().setCidade(escola.getEndereco().getCidade());
		e.getEndereco().setEstado(escola.getEndereco().getEstado());
		
		escolas.atualizarEscola(e);

		if(escolas.pesquisarEscolaExistente(escola.getIdentificadorNaRede(), escola.getNome(), e.getRedeEscolar().getId()) == false || escolas.pesquisarEscolaPorCnpjOuRazaoSocial(escola.getDadosCadastrais().getCnpj(), escola.getDadosCadastrais().getRazaoSocial()) == false){
				System.out.println("Entrou no caralho da atualização");
				e.setIdentificadorNaRede(escola.getIdentificadorNaRede());
				e.setNome(escola.getNome());
				escolas.atualizarEscola(e);
				result.include("feedback", "Escola atualizada com sucesso!");
				result.redirectTo(RedeEscolarController.class).listarEscolas(e.getRedeEscolar().getId());
		}else{
			result.include("feedback", "Já existe uma escola na rede com esses dados!");
			result.redirectTo(RedeEscolarController.class).listarEscolas(e.getRedeEscolar().getId());
		}
	}
}