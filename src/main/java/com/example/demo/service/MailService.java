package com.example.demo.service;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
@Service
public class MailService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private SpringTemplateEngine templateEngine;
	public void sendEmail(String to,String subject, String body) {
		try {

		    MimeMessage message = javaMailSender.createMimeMessage();
		    MimeMessageHelper helper = new MimeMessageHelper(message, 
		    		StandardCharsets.UTF_8.name());
		    
//		    Context context = new Context();
//		    context.setVariable("data", body); 
//		    String html = templateEngine.process("hi.html", context);
		    
		    helper.setTo(to);
		    helper.setText(body, true);
		    helper.setSubject(subject);
		    helper.setFrom("ducphuong170498@gmail.com");
		    javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
//		    logger.error("Email sent with error: " + e.getMessage());
		}
	}
}
