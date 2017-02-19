package com.xinwei.taskmanager.services.util;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xinwei.taskmanager.model.dto.MailContext;

import ch.qos.logback.classic.Logger;

import java.util.Properties;

public class JavaMail {
	public Element getMailConfig() {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		Element element = null;
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse("taskmanager-config.xml");
			NodeList nodeList = document.getElementsByTagName("web_server");
			Node node = nodeList.item(0);
			element = (Element) node;
		} catch (Exception e) {
		}
		return element;
	}

	public void sendMail(MailContext mailContext) throws MessagingException {
		JavaMailSender mailSender = new JavaMailSenderImpl();
		//SimpleMailMessage mailMessage = new SimpleMailMessage();

		((JavaMailSenderImpl) mailSender).setHost("172.31.2.3");
		((JavaMailSenderImpl) mailSender).setUsername("zhangfang");
		((JavaMailSenderImpl) mailSender).setPassword("zhangfang");
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.port", "25");
		properties.put("mail.smtp.socketFactory.fallback", "false");
		((JavaMailSenderImpl) mailSender).setJavaMailProperties(properties);

		//Multiple Email format
		MimeMessage mailMessage = ((JavaMailSenderImpl)mailSender).createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
		
		String cc[] = new String[mailContext.getCc().size()];
		int count01 = 0;
		for (String ccString : mailContext.getCc()) {
			cc[count01++] = ccString;
		}
		String to[] = new String[mailContext.getTo().size()];
		int count02 = 0;
		for (String toWhom : mailContext.getTo()) {
			to[count02++] = toWhom;
		}
		helper.setFrom(mailContext.getFrom());
		helper.setCc(cc);
		helper.setSubject(mailContext.getSubject());
		helper.setText(mailContext.getHtml(),true);
		helper.setTo(to);
		((JavaMailSender)mailSender).send(mailMessage);
	}
}
