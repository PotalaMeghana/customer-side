package eStoreProduct.externalServices;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import eStoreProduct.DAO.admin.EmailConfigDAO;
import eStoreProduct.model.admin.entities.EmailConfigModel;

public class emailSend {
	private EmailConfigDAO emailConfig;
	public emailSend(EmailConfigDAO emailConfig)
	{
		this.emailConfig=emailConfig;
	}
	public static String generateOTP() {
		int length = 6;
		StringBuilder otp = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			otp.append(random.nextInt(10));
		}

		return otp.toString();
	}

	public String sendEmail(String email1) {
		EmailConfigModel emailConfigModel=emailConfig.getEmail();
        String adminEmail=emailConfigModel.getEmail();
        String adminPassword=emailConfigModel.getPwd();
		
		// Get form data from the request
		String name = "Akshaya";
		String email = email1;
		String otp = emailSend.generateOTP();
		String subject = "Reset Password";
		String message = "Our Otp is" + otp;

		// Set up email properties
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		// Set up email session

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(adminEmail, adminPassword);
			}
		});

		try {
			// Create a new message
			Message emailMessage = new MimeMessage(session);

			// Set the sender and recipient addresses
			emailMessage.setFrom(new InternetAddress(adminEmail));
			emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

			// Set the email subject and body
			emailMessage.setSubject(subject);
			emailMessage.setText("Name: " + name + "\n\nMessage: " + message);
			Transport.send(emailMessage);

			// Return a success message to the user

		} catch (MessagingException e) {
			// Return an error message to the user
			System.out.println("error");
		}
		return otp;
	}

}
