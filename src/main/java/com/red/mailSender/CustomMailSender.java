package com.red.mailSender;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.red.security.entity.CodeConfirm;
import com.red.security.entity.User;
import com.red.security.service.CodeConfirmService;

@Service
public class CustomMailSender {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private CodeConfirmService codeService;
	
	public void sendCodeConfirm(CodeConfirm cc) {
		User user = cc.getUser();
		String codeConfirm = cc.getCode();
		mailSender.send(constructConfirmRegistrationEmail(user.getEmail(), codeConfirm));
		
	}
	
	private static SimpleMailMessage constructConfirmRegistrationEmail(String mail, String confirmCode) {
		
		String message = "Código de confirmación:\n\n\t" + confirmCode;
		
		String subject = "ElaisNet - Código de confirmación.";
		
		return constructEmail(subject, message, mail);
	}
	
	private static SimpleMailMessage constructEmail(String subject, String body, String mail) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		
		email.setSubject(subject);
		
		email.setText(body);
		
		email.setTo(mail);
		
		return email;
	}
}
