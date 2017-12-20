package br.ufpi.easii.iscool.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;

import com.google.gson.JsonObject;

public class NotificacaoUtil{
	
	public static void montarNotificacao(String mensagem, String titulo, String token, String codigoImagem) throws JSONException{
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

	private static String getStringFromInputStream(InputStream inputStream){
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
	
	public static void enviarNotificacao(String mensagem, String token, String titulo, String codigoImagem){
		montarNotificacao(mensagem, titulo, token, codigoImagem);
	}
}