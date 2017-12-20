package br.ufpi.easii.iscool.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@RequestScoped
public class EmailUtil {
	private String subject;
	private String message;
	private String receiver;
	private Session session;
	private Properties properties;
	private MimeMessage mimeMessage;
	
	public EmailUtil(String subject, String menssage, String receiver) throws IOException{
		this.subject = subject;
		this.message = menssage;
		this.receiver = receiver;
		
		properties = new Properties(); 
		InputStream in = EmailUtil.class.getResourceAsStream("/mail.properties");  
		properties.load(in); 
		in.close(); 

		session = Session.getInstance(properties,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(properties.getProperty("mail.user"), properties.getProperty("mail.password"));
				}
			});
	}
	
	public EmailUtil() throws IOException {
		this.subject = "";
		this.message = "";
		this.receiver = "";
		properties = new Properties(); 
		InputStream in = EmailUtil.class.getResourceAsStream("/mail.properties");  
		properties.load(in); 
		in.close(); 

		session = Session.getInstance(properties,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(properties.getProperty("mail.user"), properties.getProperty("mail.password"));
				}
			});
	}
	
	public void send(String subject, String message, String receiver) throws AddressException, MessagingException{	
		setMessage(message);
		setSubject(subject);
		setReceiver(receiver);		
		
		mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(properties.getProperty("mail.user")));
		
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(this.receiver));
		mimeMessage.setContent(this.message, "text/html");
		mimeMessage.setSubject(this.subject);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Transport.send(mimeMessage);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void send() throws AddressException, MessagingException{	
		mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(properties.getProperty("mail.user")));
		
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(this.receiver));
		mimeMessage.setContent(this.message, "text/html");
		mimeMessage.setSubject(this.subject);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Transport.send(mimeMessage);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param menssage the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}