package br.com.gpmendes7.services;

import br.com.gpmendes7.config.EmailConfig;
import br.com.gpmendes7.data.dto.request.EmailRequestDTO;
import br.com.gpmendes7.mail.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private EmailConfig emailConfigs;

    public void sendSimpleEmail(EmailRequestDTO emailRequest) {
        emailSender
                .to(emailRequest.getTo())
                .withSubject(emailRequest.getSubject())
                .withMessage(emailRequest.getSubject())
                .send(emailConfigs);
    }

}
