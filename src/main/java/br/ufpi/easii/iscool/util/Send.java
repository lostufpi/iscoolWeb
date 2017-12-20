package br.ufpi.easii.iscool.util;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.mail.EmailException;

public class Send {    
	public static void main(String[] args) throws EmailException, IOException {
		EmailUtil email = new EmailUtil();	
		try {
			System.out.println("Tentou enviar o email");
			email.send("asdada", "asdadas", "gust4von0x@gmail.com");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}