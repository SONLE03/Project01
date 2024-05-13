package com.clothing.MailService.service;

import com.clothing.MailService.dto.response.OrderEventResponse;
import com.clothing.MailService.dto.response.SendOtpResponse;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailServiceImp implements MailService{
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    @Override
    public void sendOrder(OrderEventResponse order) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(order.getCustomerResponse().getEmail());
            helper.setSubject("Hóa đơn bán hàng: " + order.getOrderId());

            Context context = new Context();
            context.setVariable("order", order);
            String htmlContent = templateEngine.process("invoiceTemplate", context);

            helper.setText(htmlContent, true); // Đặt nội dung là HTML

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendOtp(SendOtpResponse sendOtpResponse) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(sendOtpResponse.getTo());
        simpleMailMessage.setSubject(sendOtpResponse.getSubject());
        simpleMailMessage.setText(sendOtpResponse.getText());
        javaMailSender.send(simpleMailMessage);
    }
}
