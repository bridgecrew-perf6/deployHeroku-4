package com.company.service;

import com.company.dto.EmailDto;
import com.company.entity.EmailEntity;
import com.company.exception.AppBadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.LinkedList;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailRepository repository;

    public void send(String toEmail,String title,String content){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setSubject(title);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setText(content, true);
            create(toEmail,title,content);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new AppBadRequestException(e.getMessage());
        }
    }

    public  void create(String toEmail, String title, String content){
        EmailEntity entity = new EmailEntity();
        entity.setToEmail(toEmail);
        entity.setContent(content);
        entity.setTitle(title);
        repository.save(entity);
    }

    public List<EmailDto> getList(PageRequest of) {
        List<EmailDto> list = new LinkedList<>();
        repository.findAll(of).forEach(emailEntity -> {
            list.add(toDo(emailEntity));
        });
        return list;
    }
    private EmailDto toDo(EmailEntity emailEntity) {
        EmailDto dto = new EmailDto();
        dto.setToEmail(emailEntity.getToEmail());
        dto.setContent(emailEntity.getContent());
        dto.setTitle(emailEntity.getTitle());
        dto.setId(emailEntity.getId());

        return dto;
    }

    public String delete(Integer id) {
        repository.findById(id).orElseThrow(() ->{
            throw new ItemNotFoundException("Item not found");
        });
        repository.deleteById(id);
        return "Success";
    }
}
