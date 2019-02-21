package com.azxx.picture.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${email.emailFrom}")
    private String emailFrom;

    /**
     *
     * @param emailTo 邮件发送地址
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleEmail(String emailTo,String subject,String content){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailFrom);
        message.setTo(emailTo);

        //同时发送邮件给多人
//        String[] adds = {"mr.xuzheng@outlook.com","xuzheng_13@qq.com"};
//        message.setTo(adds);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("email send successful!");
        } catch (Exception e) {
            logger.info("email send failure!",e);
        }
    }

    /**
     *  发送HTML邮件
     * @param emailTo  要发送的邮件地址
     * @param subject 主题
     * @param content 内容
     */
    public void sendHtmlEmail(String emailTo,String subject,String content){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(emailFrom);
            helper.setTo(emailTo);
            helper.setSubject(subject);
            helper.setText(content,true);

            mailSender.send(message);
            logger.info("email send successful!");
        } catch (MessagingException e) {
            logger.info("email send failure!",e);
        }
    }

    /**
     *  发送带附件的邮件
     * @param to 要发送的邮件地址
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件地址
     */
    public void sendAttachmentsEmail(String to, String subject, String content, String filePath) {

        //创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            // true表示这个邮件是有附件的
            helper.setText(content, true);

            //创建文件系统资源
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            //添加附件
            helper.addAttachment(fileName, file);

            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }

    /**
     *  发送嵌入静态资源的邮件
     * @param to 要发送的邮件地址
     * @param subject 主题
     * @param content 内容
     * @param rscPath 资源路径
     * @param rscId 资源ID
     */
    public void sendInlineResourceEmail(String to, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));

            //添加内联资源，一个id对应一个资源，最终通过id来找到该资源
            helper.addInline(rscId, res);//添加多个图片可以使用多条 <img src='cid:" + rscId + "' > 和 helper.addInline(rscId, res) 来实现

            mailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }

    }
}
