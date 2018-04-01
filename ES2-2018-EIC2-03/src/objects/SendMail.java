package objects;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {

	private String mailFROM;
	private String mailTO;
	private String subject;
	private String text;
	private char[] pass;

	public SendMail(String mailfrom, char[] password, String mailto) {
		this.mailFROM = mailfrom;
		this.mailTO = mailto;
		pass = password;
	}

	public String getMailfrom() {
		return mailFROM;
	}

	public String getMailto() {
		return mailTO;
	}

	public void setSubject(String s) {
		subject = s;
	}

	public void setContent(String c) {
		text = c;
	}

	private String fromCharArray(char[] arr) {
		String s = "";
		for (int i = 0; i < arr.length; i++) {
			s += arr[i];
		}
		return s;
	}

	public void send() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailFROM, fromCharArray(pass));
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailFROM));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTO));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
			System.out.println("E-mail succesfully sent to " + mailTO);
		} catch (MessagingException e) {
		}
	}

}