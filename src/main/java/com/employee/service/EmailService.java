package com.employee.service;

import com.employee.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNewEmployeeEmail(String managerEmail, Employee newEmployee) {
        String subject = "New Employee Added";
        String message = newEmployee.getEmployeeName() + " will now work under you. Mobile number is " +
                newEmployee.getPhoneNumber() + " and email is " + newEmployee.getEmail();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(managerEmail);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}
