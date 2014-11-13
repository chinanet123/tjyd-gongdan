package com.chinaops.web.ydgd.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class EmailUtil {
	public static void sendEmail(String title, String ticketId, String customerName, String receiveTime){
		Properties props = new Properties();
		props.setProperty("mail.host", "mail.china-ops.com");
		props.setProperty("mail.smtp.auth", "true");
		Authenticator authenticator = new Authenticator(){
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("ningtao.ma","123456");
			}
		};
		Session session = Session.getDefaultInstance(props, authenticator);
		session.setDebug(true);
		
		Message message = new MimeMessage(session);  //
		
		try {
			message.setFrom(new InternetAddress("ningtao.ma@china-ops.com"));
			message.setRecipients(RecipientType.TO,InternetAddress.parse("ningtao.ma@china-ops.com,liqiang.zhang@china-ops.com"));
			//message.setRecipients(RecipientType.TO,InternetAddress.parse("wen.li@china-ops.com,yanyan.zhang@china-ops.com"));
			message.setSubject(title);
			String content = "客户" + customerName + "在" + receiveTime + "时请求" + title + "操作，工单ID号：" + ticketId + "。";
			message.setContent(content,"text/html;charset=UTF-8");
			
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
