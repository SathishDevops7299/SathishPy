package ijp.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String to, String subject, String body) throws MessagingException {
	    MimeMessage message =  javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    helper.setSubject(subject);
	    helper.setTo(to);
	    helper.setText(body, true);
	    helper.setFrom("sshah@arigs.com");
	    javaMailSender.send(message);
	}
}
