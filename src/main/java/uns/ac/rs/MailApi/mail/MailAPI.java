package uns.ac.rs.MailApi.mail;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.mail.util.BASE64DecoderStream;

import uns.ac.rs.MailApi.dto.BackendContactDTO;
import uns.ac.rs.MailApi.dto.MessageDTO;
import uns.ac.rs.MailApi.entity.*;
import uns.ac.rs.MailApi.service.FolderService;

@Component
public class MailAPI {
	
	private final String TMP_PATH = Objects.requireNonNull(getClass()
            .getClassLoader()
            .getResource(""))
            .getPath().substring(1)
            + "tmp" + File.separator;
	
	@Autowired
	private FolderService folderService;
	
	public boolean sendMessage(Message message) {

        String EMAIL_TO = message.getTo();
        String EMAIL_CC = message.getCc();
        String EMAIL_BCC = message.getBcc();
        String EMAIL_FROM = message.getFrom();
 
        Account account = message.getAccount();
        String username = account.getUsername();
        String password = account.getPassword();
        
        // Parametri za mail server
        Properties properties = new Properties();
//        properties.put("mail.smtp.socketFactory.port", account.getSmtpPort());
//        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.smtp.host", account.getSmtpAddress());
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.port", account.getSmtpPort());
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
       
 
        Session session = Session.getDefaultInstance(properties, null);
        session.setDebug(true);
        
        try {
            MimeMessage APImessage = new MimeMessage(session);
 
            APImessage.setFrom(new InternetAddress(EMAIL_FROM));
            APImessage.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
            APImessage.setRecipients(javax.mail.Message.RecipientType.CC, InternetAddress.parse(EMAIL_CC));
            APImessage.setRecipients(javax.mail.Message.RecipientType.BCC, InternetAddress.parse(EMAIL_BCC));
            APImessage.setSubject(message.getSubject());
         
            
            // Postavljanje sadrzaja poruke i attachmenta
            Multipart multipart = new MimeMultipart();

            // Postavljanje teksta
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(message.getContent(), "text/plain");
            
            multipart.addBodyPart(textPart);

            // Postavljanje attachmenta
            for (Attachment attachment : message.getAttachments()) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                
                attachmentPart.setContent(Base64.decodeBase64(attachment.getData()), attachment.getMimeType());
                attachmentPart.setFileName(attachment.getName());
                
                multipart.addBodyPart(attachmentPart);
            }

            APImessage.setContent(multipart);

            //Transport.send(APImessage);
            
            Transport transport = session.getTransport("smtp");
            
    		// Enter your correct gmail UserID and Password
    		// if you have 2FA enabled then provide App Specific Password
    		transport.connect("smtp.gmail.com", account.getUsername()+"@gmail.com", account.getPassword());
    		transport.sendMessage(APImessage, APImessage.getAllRecipients());
    		transport.close();
    	
           
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return false;
	}
	
	/**
	 * Pulls new e-mails using JavaMailAPI and returns them
	 * as a List. You can do further operations to those mails.
	 * 
	 * @param Account that is going to be synchronized.
	 * @return List of new e-mails.
	 */
	
	public List<Message> loadMessages(Account account) {
		
		ArrayList<Message> messages = new ArrayList<Message>();
	
		Store store = null;
		javax.mail.Folder APIinbox = null;
		
        try {
            Properties properties = new Properties();
            properties.put("mail.imap.host", account.getInServerAddress());
            properties.put("mail.imap.port", account.getInServerPort());
            properties.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.imap.socketFactory.fallback", "false");
            properties.setProperty("mail.imap.socketFactory.port", String.valueOf(account.getInServerPort()));
            
           Session session = Session.getDefaultInstance(properties, null);
//           

            store = session.getStore("imaps");
            store.connect("imap.googlemail.com", 993, account.getUsername(), account.getPassword());
            
            APIinbox = store.getFolder("inbox");
            APIinbox.open(javax.mail.Folder.READ_ONLY);
            
            Folder inbox = folderService.findInbox(account);
            
            javax.mail.Message[] APImessages = APIinbox.getMessages();
 
            for (javax.mail.Message APImessage : APImessages) {
            	if (account.getLastSyncTime() == null || APImessage.getSentDate().after(account.getLastSyncTime())) {
		            Message message = new Message();
		            
		            message.setFrom(APImessage.getFrom()[0].toString());
		
		            Address[] APIto = APImessage.getRecipients(RecipientType.TO);
		            if (APIto != null) {
			            List<BackendContactDTO> to = new ArrayList<BackendContactDTO>();
			            for (Address address : APIto) {
			            	to.add(new BackendContactDTO(address.toString()));
			            }
			            message.setTo(MessageDTO.contactsToString(to));
		            } else {
		            	message.setTo(null);
		            }
		            
		            Address[] APIcc = APImessage.getRecipients(RecipientType.CC);
		            if (APIcc != null) {
			            List<BackendContactDTO> cc = new ArrayList<BackendContactDTO>();
			            for (Address address : APIcc) {
			            	cc.add(new BackendContactDTO(address.toString()));
			            }
			            message.setCc(MessageDTO.contactsToString(cc));
		            } else {
		            	message.setCc(null);
		            }
		            
		            Address[] APIbcc = APImessage.getRecipients(RecipientType.BCC);
		            if (APIbcc != null) {
			            List<BackendContactDTO> bcc = new ArrayList<BackendContactDTO>();
			            for (Address address : APIbcc) {
			            	bcc.add(new BackendContactDTO(address.toString()));
			            }
			            message.setBcc(MessageDTO.contactsToString(bcc));
		            } else {
		            	message.setBcc(null);
		            }
		            
		            Date timestamp = APImessage.getSentDate();
		            message.setDateTime(new Timestamp(timestamp.getTime()));
		            
		            message.setSubject(APImessage.getSubject());
		            
		            //setMessageContent(APImessage, message);
		            
		            message.setTags(new ArrayList<Tag>());
		            
		            message.setFolder(inbox);
		            message.setAccount(account);
		            message.setUnread(true);
		            
		            
		            ArrayList<Attachment> attachments = new ArrayList<Attachment>();
		            
		            MimeMultipart multipart = (MimeMultipart) APImessage.getContent();
		            
		            ArrayList<BodyPart> parts = extractParts(multipart);

		            for (int i = 0; i < parts.size(); i++) {
		                MimeBodyPart bodyPart = (MimeBodyPart) parts.get(i);

		                byte[] bytes;
		                
		                if (bodyPart.isMimeType("text/plain")) {
		                	message.setContent((String) bodyPart.getContent());
		                	continue;
		                } else if (bodyPart.isMimeType("image/*")) {
		                	BASE64DecoderStream base64DecoderStream = (BASE64DecoderStream) bodyPart.getContent();
		                    bytes = IOUtils.toByteArray(base64DecoderStream);
		                } else {
		                	InputStream is = bodyPart.getInputStream();
			                bytes = IOUtils.toByteArray(is);		                	
		                }
		                		                
		                Attachment attachment = new Attachment();
		                attachment.setName(bodyPart.getFileName());
		                attachment.setData(Base64.encodeBase64String(bytes));
		                // TODO: Srediti ovaj MimeType.
		                attachment.setMimeType("image/*");
		                attachment.setMessage(message);
		                
		                attachments.add(attachment);
		            }
		            
		            message.setAttachments(attachments);
		            
		            messages.add(message);
            	}
	        }
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
        	try {
        		APIinbox.close();
        	} catch (Exception ex) {
        		ex.printStackTrace();
        	}
        	
        	try {
        		store.close();
        	} catch (Exception ex) {
        		ex.printStackTrace();
        	}
        }
        
        return messages;
	}
	
	/**
	 * @deprecated Initially used to transfer data from API mail to our entity.
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void setMessageContent(javax.mail.Message APImessage, Message message) {
		try {
	        if (APImessage.isMimeType("text/plain"))
	        	message.setContent((String) APImessage.getContent());
	        else if (APImessage.isMimeType("multipart/*")) {
	        	
	        	List<Attachment> messageAttachments = message.getAttachments();
	        	
	        	Multipart multipart = (Multipart) APImessage.getContent();
	        	
	        	StringBuilder textContent = new StringBuilder();
	        	for (int i = 0; i < multipart.getCount(); i++) {
	        		BodyPart part = multipart.getBodyPart(i);
	        		
	        		if (part.isMimeType("text/plain")) {
	    	        	textContent.append((String) part.getContent());
	        		} else if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
	    	        	Attachment attachment = new Attachment();
	    	        	
	    	        	MimeBodyPart bodyPart = (MimeBodyPart) part;
	    	        	
	    	        	String fileName = bodyPart.getFileName();

	    	        	bodyPart.saveFile(TMP_PATH + File.separator + fileName);

                        byte[] bFile = Files.readAllBytes(new File(TMP_PATH + fileName).toPath());
                        String sFile = Base64.encodeBase64String(bFile);

                        attachment.setName(fileName);
                        attachment.setData(sFile);
                        attachment.setMimeType(bodyPart.getContentType().split(";")[0]);

	    	            attachment.setMessage(message);

	    	            messageAttachments.add(attachment);
	    	        }
	        	}

	        	message.setContent(textContent.toString());
	        }
	        	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Pomocna metoda za izvlacenje attachmenta iz API-jeve poruke
	private ArrayList<BodyPart> extractParts(MimeMultipart multipart) {
		ArrayList<BodyPart> parts = new ArrayList<BodyPart>();
		
		try {
			for (int i = 0; i < multipart.getCount(); i++) {
	            MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
	            if (bodyPart.isMimeType("text/plain")) {
	            	parts.add(bodyPart);
	            } else if (bodyPart.isMimeType("image/*")) {
	            	parts.add(bodyPart);
	            } else if (bodyPart.isMimeType("multipart/*")) {
	            	for (BodyPart part : extractParts((MimeMultipart)bodyPart.getContent())) {
	            		parts.add(part);
	            	}
	            }
	        }
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return parts;
	}
}
