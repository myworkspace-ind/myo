/**
 * Licensed to MKS Group under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * MKS Group licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.sakaiproject.myworkspace.common;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmailSender {
	@Setter @Getter
	private String hostName;
	
	@Setter @Getter
	private int port;
	
	@Setter @Getter
	private boolean ssl;
	
	@Setter @Getter
	private String username;
	
	@Setter @Getter
	private String password;
	
	public EmailSender(String hostName) {
		this.hostName = hostName;
	}
	
	public String sendSimpleText(int port, String username, String password, boolean ssl, 
										String from, String to, String subject, String msg) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(hostName);
		email.setSmtpPort(port);
		email.setAuthenticator(new DefaultAuthenticator(username, password));
		email.setSSLOnConnect(ssl);
		email.setFrom(from);
		email.setSubject(subject);
		email.setMsg(msg);
		email.addTo(to);
		
		return email.send();
	}
	
	public String sendWithAttachments(String toAddr, String toName, String fromAddr, String fromName, String subject, String msg, EmailAttachment attachment) throws EmailException {
		// Create the attachment
//		  EmailAttachment attachment = new EmailAttachment();
//		  attachment.setPath("mypictures/john.jpg");
//		  attachment.setDisposition(EmailAttachment.ATTACHMENT);
//		  attachment.setDescription("Picture of John");
//		  attachment.setName("John");
		
//		You can also use EmailAttachment to reference any valid URL for files that you do not have locally. When the message is sent, the file will be downloaded and attached to the message automatically.
//
//		The next example shows how we could have sent the apache logo to John instead.
//		  // Create the attachment
//		  EmailAttachment attachment = new EmailAttachment();
//		  attachment.setURL(new URL("http://www.apache.org/images/asf_logo_wide.gif"));
//		  attachment.setDisposition(EmailAttachment.ATTACHMENT);
//		  attachment.setDescription("Apache logo");
//		  attachment.setName("Apache logo");
		  
		  // Create the email message
		  MultiPartEmail email = new MultiPartEmail();
		  email.setHostName(hostName);
		  email.addTo(toAddr, toName); //"jdoe@somewhere.org", "John Doe");
		  email.setFrom(fromAddr, fromName);
		  email.setSubject(subject);
		  email.setMsg(msg);

		  // add the attachment
		  email.attach(attachment);

		  // send the email
		  return email.send();
	}
	
	public String sendHTML(String toAddr, String toName, String fromAddr, String fromName, String subject, URL imageURL, String imageName, String htmMsg) throws EmailException, MalformedURLException {
		// Create the email message
		  HtmlEmail email = new HtmlEmail();
		  email.setHostName(hostName);
		  email.addTo(toAddr, toName);
		  email.setFrom(fromAddr, fromName);
		  email.setSubject(subject);
		  
		  // embed the image and get the content id
		  // URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
		  String cid = email.embed(imageURL, imageName);
		  
		  // set the html message
		  // email.setHtmlMsg("<html>The apache logo - <img src=\"cid:"+cid+"\"></html>");
		  email.setHtmlMsg(htmMsg);

		  // set the alternative message
		  // email.setTextMsg("Your email client does not support HTML messages");

		  // send the email
		  return email.send();
	}

	public String sendHTMLWithImage(String htmlEmailTemplate, String toAddr, String toName, String fromAddr, String fromName, String subject, URL imageURL, String imageName, String htmMsg) throws EmailException, MalformedURLException {
		  // load your HTML email template
		  // String htmlEmailTemplate = ".... <img src=\"http://www.apache.org/images/feather.gif\"> ....";

		  // define you base URL to resolve relative resource locations
		  URL url = new URL("http://www.apache.org");

		  // create the email message
		  ImageHtmlEmail email = new ImageHtmlEmail();
		  email.setDataSourceResolver(new DataSourceUrlResolver(url));
		  email.setHostName(hostName);
		  email.addTo(toAddr, toName);
		  email.setFrom(fromAddr, fromName);
		  email.setSubject(subject);
		  
		  // set the html message
		  email.setHtmlMsg(htmlEmailTemplate);

		  // set the alternative message
		  email.setTextMsg("Your email client does not support HTML messages");

		  // send the email
		  return email.send();
	}
}
