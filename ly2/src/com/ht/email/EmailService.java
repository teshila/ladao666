package com.ht.email;

import java.util.List;

public interface EmailService {

	void sendingEmail(String toEmail,String title,String contentTemplate);

	void sendEmail(MailModel mail);

}
