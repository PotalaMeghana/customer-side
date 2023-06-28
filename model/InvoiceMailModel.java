package eStoreProduct.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

public class InvoiceMailModel {
	public static void sendEmail(HttpServletRequest request, HttpServletResponse response,
			eStoreProduct.model.admin.entities.orderModel ordermodel,String email) throws Exception {
		// Set up model attributes with the variables for JSP replacements

		// Resolve JSP view
		/*
		 * InternalResourceViewResolver viewResolver = new
		 * InternalResourceViewResolver(); viewResolver.setPrefix("/WEB-INF/views/");
		 * viewResolver.setSuffix(".jsp"); String viewName = "invoice";
		 * request.setAttribute("order", ordermodel); // Render JSP RequestDispatcher
		 * requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/" + viewName
		 * + ".jsp"); StringWriter stringWriter = new StringWriter();
		 * HttpServletResponseWrapper responseWrapper = new
		 * HttpServletResponseWrapper(response) {
		 * 
		 * @Override public PrintWriter getWriter() throws IOException { return new
		 * PrintWriter(stringWriter); } }; requestDispatcher.include(request,
		 * responseWrapper); String renderedHtml = stringWriter.toString();
		 * 
		 * OutputStream file = new FileOutputStream(new File("Invoice.pdf")); Document
		 * document = new Document(); PdfWriter.getInstance(document, file);
		 * document.open(); HTMLWorker htmlWorker = new HTMLWorker(document);
		 * htmlWorker.parse(new StringReader(renderedHtml)); document.close();
		 * file.close();
		 */
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        String viewName = "invoice";
        request.setAttribute("ordermodel", ordermodel);
        
        // Render JSP
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/" + viewName + ".jsp");
        StringWriter stringWriter = new StringWriter();
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response) {
            @Override
            public PrintWriter getWriter() throws IOException {
                return new PrintWriter(stringWriter);
            }
        };
        requestDispatcher.include(request, responseWrapper);
        String renderedHtml = stringWriter.toString();
        
        OutputStream file = new FileOutputStream(new File("Invoice.pdf"));
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(renderedHtml, file, converterProperties);

		Properties p = new Properties();
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.auth", true);
		p.put("mail.smtp.starttls.enable", true);
		p.put("mail.smtp.port", "587");
		Session s = Session.getInstance(p, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("potalameghana2@gmail.com", "juad solc doyd jjcm");
			}
		});
		try {
			MimeMessage mm = new MimeMessage(s);
			mm.setFrom(new InternetAddress("potalameghana2@gmail.com"));
			mm.setRecipient(Message.RecipientType.TO, new InternetAddress("sangamreddyajay@gmail.com"));
			mm.setSubject("Ticket Confirmation Details");
			// mm.setContent(renderedHtml, "text/html");
			mm.setContent("This is ticket email...........\n", "text/html");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("This is message body");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			String filename = "Invoice.pdf";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			mm.setContent(multipart);

			Transport.send(mm);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Send the email with the rendered HTML

	}

}