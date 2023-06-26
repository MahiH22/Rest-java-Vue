package com.ceuflix.app.controller.mail;

import com.ceuflix.app.domain.entidadesjpa.Cuenta;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class MailSenderService {
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public MailSenderService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void verificarCorreo(String toMail,String subject, Cuenta cuenta) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setTo(toMail);
        helper.setSubject(subject);

        Context context = new Context();
        context.setVariable("nombrePersona",String.format("%s %s",cuenta.getPersona().getNombre(),cuenta.getPersona().getApellido()));
        context.setVariable("usuario",cuenta.getUsername());
        context.setVariable("correo",cuenta.getCorreo());
        context.setVariable("link",String.format("http://localhost:8080/verificar/%s",cuenta.getIdCuenta()));
        String htmlContent = templateEngine.process("mail",context);
        helper.setText(htmlContent,true);

        mailSender.send(message);
        System.out.println("un correo ha sido enviado");
    }
}
