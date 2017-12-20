package br.ufpi.easii.iscool.android.controle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONException;
import org.jsoup.Jsoup;

import com.google.gson.JsonObject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.ufpi.easii.iscool.dao.DisciplinaDao;
import br.ufpi.easii.iscool.dao.ProvaDao;
import br.ufpi.easii.iscool.dao.UsuarioDao;
import br.ufpi.easii.iscool.entidade.Disciplina;
import br.ufpi.easii.iscool.entidade.Prova;
import br.ufpi.easii.iscool.entidade.Questao;
import br.ufpi.easii.iscool.entidade.Usuario;


@Controller
public class MensagemController {
	private UsuarioDao usuarioDao;
	private DisciplinaDao disciplinaDao;
	private ProvaDao provaDao;

	public MensagemController(){
		this(null, null, null);
	}

	@Inject
	protected MensagemController(UsuarioDao usuarioDao, DisciplinaDao disciplinaDao, ProvaDao provaDao){
		this.usuarioDao = usuarioDao;
		this.disciplinaDao = disciplinaDao;
		this.provaDao = provaDao;
	}

	public void montarMensagem(String mensagem, String idUser, String  idDisciplina, String nomeUser, String horaMsg, String token, String nomeDisciplina) throws JSONException{

		try{
			JsonObject configuracoes = new JsonObject();
			JsonObject dados = new JsonObject();

			dados.addProperty("chave", "0");
			dados.addProperty("nomeDoUser", nomeUser);
			dados.addProperty("idDoUser", idUser);
			dados.addProperty("mensagem", mensagem);
			dados.addProperty("idDisciplina", idDisciplina);
			dados.addProperty("hora", horaMsg);
			dados.addProperty("nomeDisciplina", nomeDisciplina);

			configuracoes.addProperty("to", token);
			configuracoes.add("data", dados);

			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 

			conn.setRequestProperty("Authorization", "key=" + "AIzaSyDxPOm1LP3pYIWXvL4yBlBmFIkG21aFvDM");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			OutputStream outPutStream = conn.getOutputStream();
			outPutStream.write(configuracoes.toString().getBytes("UTF-8"));

			InputStream inputStream = conn.getInputStream();
			String resp = getStringFromInputStream(inputStream);
			System.out.println("Resp: " + resp);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void montarNotificacao(String mensagem, String titulo, String token, String codigoImagem) throws JSONException{

		try{
			JsonObject configuracoes = new JsonObject();
			JsonObject dados = new JsonObject();

			dados.addProperty("chave", "1");
			dados.addProperty("mensagem", mensagem);
			dados.addProperty("titulo", titulo);
			dados.addProperty("codigo", codigoImagem);

			configuracoes.addProperty("to", token);
			configuracoes.add("data", dados);

			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 

			conn.setRequestProperty("Authorization", "key=" + "AIzaSyDxPOm1LP3pYIWXvL4yBlBmFIkG21aFvDM");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			OutputStream outPutStream = conn.getOutputStream();
			outPutStream.write(configuracoes.toString().getBytes("UTF-8"));

			InputStream inputStream = conn.getInputStream();
			String resp = getStringFromInputStream(inputStream);
			System.out.println("Resp: " + resp);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private String getStringFromInputStream(InputStream inputStream){
		BufferedReader bufferedReader = null;
		StringBuilder stringBuilder = new StringBuilder();
		String line;

		try{
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			while((line = bufferedReader.readLine()) != null){
				stringBuilder.append(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bufferedReader != null){
				try{
					bufferedReader.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return stringBuilder.toString();
	}

	@Post
	public void enviarMensagem(String mensagem, String idUser, String idDisciplina, String nomeUser, String horaMsg) throws JSONException, UnsupportedEncodingException{
		try{
			Disciplina disciplina = disciplinaDao.pesquisarDisciplinaPorId(Long.parseLong(idDisciplina));
			List<Usuario> usuarios = usuarioDao.listarAlunosPorTurma(disciplina.getTurma().getId());

			for(Usuario u : usuarios){
				if(u.getToken() != null && u.getToken() != "")
					montarMensagem(mensagem, idUser, idDisciplina, nomeUser, horaMsg, u.getToken(), disciplina.getNome());
			}

			montarMensagem(mensagem, idUser, idDisciplina, nomeUser, horaMsg, disciplina.getProfessor().token, disciplina.getNome());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void enviarNotificacao(String mensagem, String titulo, String token, String codigoImagem){
		montarNotificacao(mensagem, titulo, token, codigoImagem);
	}

	@Get
	public void enviarMensagem(){

	}
	
	@Get("/mensagem/explicacao/{idProva}")
	public void enviarExplicacoes(long idProva){
		Prova prova = provaDao.pesquisarProvaPorId(idProva);
		int contador = 0;
		for(Questao q : prova.getGabarito().getQuestoes()){
			Date data = new Date();
			if(q.getNumeroDeRespostas() > q.getNumeroDeAcertos()){
				if(contador == 0){
					String mensagem = "Olá, notamos um grande número de erros nas seguintes questões, por esse motivo"
							+ " resolvemos enviar a explicação cadastrada pelo professor!";
					try {
						enviarMensagem(mensagem, "0", ""+prova.getDisciplina().getId(), "Explicação", "" + data.getTime());
					} catch (JSONException | UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				try {
					String exp = "Questão: " + q.getDescricao() + "\n";
					exp = exp + "a) " + q.getPrimeiraAlternativa() + "\n";
					exp = exp + "b) " + q.getSegundaAlternativa() + "\n";
					exp = exp + "c) " + q.getTerceiraAlternativa() + "\n";
					exp = exp + "d) " + q.getQuartaAlternativa() + "\n";
					exp = exp + "e) " + q.getQuintaAlternativa() + "\n";
					exp = exp + "Explicação: " + q.getExplicacao();
					
					String html = Jsoup.parse(exp).text();
					
					enviarMensagem(html, "0", ""+prova.getDisciplina().getId(), "Explicação", "" + data.getTime());
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				contador++;
			}
		}
	}
}