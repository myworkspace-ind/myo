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

package org.sakaiproject.myo.controller;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.HtmlEmail;
import org.sakaiproject.myo.service.OrgService;
import org.sakaiproject.myo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SubmitController extends BaseController {
 
	@Autowired
	UserService userService;
	
	@Autowired
	OrgService orgService;
	
	
	
	/**
	  * This method is called when binding the HTTP parameter to bean (or model).
	  * 
	  * @param binder
    */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	     // Sample init of Custom Editor
	//     Class<List<ItemKine>> collectionType = (Class<List<ItemKine>>)(Class<?>)List.class;
	//     PropertyEditor orderNoteEditor = new MotionRuleEditor(collectionType);
	//     binder.registerCustomEditor((Class<List<ItemKine>>)(Class<?>)List.class, orderNoteEditor);
	 }

	 
	/**
	 * Simply selects the home view to render by returning its name.
     * @return 
	 */
	@RequestMapping(value = {"submit_test"}, method = RequestMethod.GET)
	public String displayTestSubmitPage(HttpServletRequest request, HttpSession httpSession) {

		initSession(request, httpSession);
//		List<OkrUser> allUsers = userService.findAll();
//		int len = (allUsers != null) ? allUsers.size(): 0;
//		log.info("Number of users: " + len);
//		mav.addObject("users", allUsers);
//		mav.addObject("orgs", orgService.findAll());
		return "submit_test";
	}
	
	/**
	  * This method handle sending Email when submit a form with recipient Email and its content.
	  * This form is used for example/testing before use real manager_email in database.
	  * 
	  * @param from: who send email and have key password from Gmail.
	  * @param password: key password of application.
   */
	@PostMapping(value="/submit_test/send")
	@ResponseBody
    public String sendEmail(@RequestParam("recipientEmail") String recipientEmail, @RequestParam("emailContent") String emailContent) {
        
		final String from = "nhbthach@gmail.com";
        final String password = "jzst hwrb kcas krzr";
        
        // This used for check log
        System.out.println("Recipient: "+recipientEmail);
        System.out.println("Email: "+ emailContent);
        
        String templateContent = "\n"+
        		"<!DOCTYPE html>\r \n"
				+ "<html>\r\n"
				+ "<body> \r\n"
				+ "\r\n"
				+ "<h1>Click <a href=\"https://www.vnexpress.net\">here</a> to redirect to content of user's OKR. </h1>\n"
				+ "\r\n"
				+ "</body> \r\n"
				+ "</html>";
        
        try {
        	// Configure email 
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setAuthentication(from, password);
			email.setStartTLSEnabled(true);
			
			// Configure address and content
			email.setFrom(from);
			email.addTo(recipientEmail);
			email.setSubject("Test about send email. Number:"+ System.currentTimeMillis());
			email.setHtmlMsg(emailContent + templateContent);
			
			email.send();
			System.out.println("Email sent successfully!");
			return "home";
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("Failed to submit");
			e.printStackTrace();
			
			return "Failed to submit";
		}
       
        /* ------------- Configuration -------------- */
        // Initialize attributes for props - configuration
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        // props.put("mail.smtp.ssl.protocols", "TLSv1.2");
//
//        
//        // Create Authenticator, thru this function => get authenticator to sign-in email
//		Authenticator auth = new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				// TODO Auto-generated method stub
//				return new PasswordAuthentication(from, password);
//			}
//			
//		};
//		
//		// Add session 
//		Session session = Session.getInstance(props, auth);
//        
//        // Add new one message
//        MimeMessage message = new MimeMessage(session);
//        
//        /* --------------- Send Email --------------- */
//        try {
//        	// Add header
//        	message.addHeader("Content-type", "text/HTML; charset=UTF-8"); 
//        	
//        	//message.setFrom(from);
//        	InternetAddress fromAddr = new InternetAddress(from);
//            message.setFrom(fromAddr);
//            
//            // Setting Recipient and Type of sending (TO). Other types: CC, BCC.
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
//            
//            /*Content of an email*/
//            // 1. Subject of mail
//            message.setSubject("Test about send email. Number: " + System.currentTimeMillis());  
//            // 2. Sent Date
//            message.setSentDate(new Date());
//            // 3. Content of mail
//            message.setText(emailContent);
//            
//            // Set HTML to support attach link inside the content in real business.
            
        	/*
			message.setContent("<!DOCTYPE html>\r \n"
					+ "<html>\r\n"
					+ "<body> \r\n"
					+ "\r\n"
					+ "<h1>click <a href=\"https://www.vnexpress.net\">here</a> to redirect to content of user's OKR </h1>\n"
					+ "\r\n"
					+ "</body> \r\n"
					+ "</html>", "text/html");
            */
//            
//            Transport.send(message);
//            
//            System.out.println("Email sent successfully!");
//  
//            return "Successfully";
//            
//        } catch (Exception e) {
//        	
//        	System.out.println("Failed to send email");
//        	
//        	e.printStackTrace();
//        	
//        	return "Failed to submit";
//			
//        }
    }
}



