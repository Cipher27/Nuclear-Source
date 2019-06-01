package com.rs.game.player.content;



import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.rs.game.player.Player;
import com.rs.utils.Logger;
import com.sun.mail.smtp.SMTPTransport;

/**
 *  
 * @author paolo
 * If you want to use this dowload this ;
 * link: https://console.cloud.google.com/appengine/settings?project=thatscape-1329&authuser=1
  Thatscape@gmail.com
 */

public class EmailManager {
	
	private Player player;
	//text that is at the end of every message.
	private String endMessage = " This is an automatic system, responding on this won't do anything. If you want to contact the staff please use the forums or in-game chat.";
	//email information
	private String email = "Thatscape@gmail.Com";
	private String password = "Runescape132";
	
	public EmailManager(Player player) {
		this.player = player;
	}
	
	/**
	  * sends a mail with the message you want it to send
	  *
	  */
	public  void sendCustomMail(Player player, String message,String subject,String targetEmail){
		        final String sendTo = targetEmail;
		        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";			
			//    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
				Properties props = System.getProperties();
				props.setProperty("mail.smtps.host", "smtp.gmail.com");
				props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
				props.setProperty("mail.smtp.socketFactory.fallback", "false");
				props.setProperty("mail.smtp.port", "465");
				props.setProperty("mail.smtp.socketFactory.port", "465");
				props.setProperty("mail.smtps.auth", "true");
				props.put("mail.smtps.quitwait", "false");
				Session session = Session.getDefaultInstance(props, null);
				SMTPTransport t;
				try {
					Message msg = new MimeMessage(session);
				    msg.setFrom(new InternetAddress(email, "Thatscape server"));
					msg.addRecipient(Message.RecipientType.TO,new InternetAddress(sendTo , "sendTo "));
				    msg.setSubject(subject);
					msg.setText(message);
					t = (SMTPTransport) session.getTransport("smtps");
					t.connect("smtp.gmail.com", email, password);
					t.sendMessage(msg, msg.getAllRecipients());
				    t.close();
				   } catch (NoSuchProviderException e1) {
						e1.printStackTrace();
						player.getPackets().sendGameMessage("Email couldn't be sent.. Try again in 30 seconds.");
						Logger.log("Email Error", "NoSuchProviderException");
					} catch(MessagingException e) {
						e.printStackTrace();
						player.getPackets().sendGameMessage("Email couldn't be sent.. Try again in 30 seconds.");
						Logger.log("Email Error", "MessagingException");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						player.getPackets().sendGameMessage("Email couldn't be sent.. Try again in 30 seconds.");
						Logger.log("Email Error", "UnsupportedEncodingException");
					}
			
	}
	/**
	 * 
	 * Sends a email with the security code
	 */
	public  void sendSecurityEmail(Player player){
		if (player.hasEnteredEmail == false)
			return;
		        final String sendTo = player.getEmailAdress();
				player.getPackets().sendGameMessage("A mail has been send to your mail, give to code to the player manager.");
		        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";			
			//    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
				Properties props = System.getProperties();
				props.setProperty("mail.smtps.host", "smtp.gmail.com");
				props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
				props.setProperty("mail.smtp.socketFactory.fallback", "false");
				props.setProperty("mail.smtp.port", "465");
				props.setProperty("mail.smtp.socketFactory.port", "465");
				props.setProperty("mail.smtps.auth", "true");
				props.put("mail.smtps.quitwait", "false");
				Session session = Session.getDefaultInstance(props, null);
				SMTPTransport t;
				try {
					Message msg = new MimeMessage(session);
				    msg.setFrom(new InternetAddress(email, "Thatscape server"));
					msg.addRecipient(Message.RecipientType.TO,new InternetAddress(sendTo , "sendTo "));
				    msg.setSubject("Thatscape email confirmation.");
					msg.setText("Your confirmation code is : "+player.emailSecurityCode+"  Thatscape team. This is an automatic system, responding on this won't do anything. If you want to contact the owner please use the forums.");
					t = (SMTPTransport) session.getTransport("smtps");
					t.connect("smtp.gmail.com", email, password);
					t.sendMessage(msg, msg.getAllRecipients());
				    t.close();
				   } catch (NoSuchProviderException e1) {
						e1.printStackTrace();
						player.getPackets().sendGameMessage("Email couldn't be sent.. Try again in 30 seconds.");
						Logger.log("Email Error", "NoSuchProviderException");
					} catch(MessagingException e) {
						e.printStackTrace();
						player.getPackets().sendGameMessage("Email couldn't be sent.. Try again in 30 seconds.");
						Logger.log("Email Error", "MessagingException");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						player.getPackets().sendGameMessage("Email couldn't be sent.. Try again in 30 seconds.");
						Logger.log("Email Error", "UnsupportedEncodingException");
					}
			
	}
    /**
	  * checks if the input code is the same as the code from the player.
	  **/
	public  void validateCode(String code, Player player) {
		if (code.equals(player.emailSecurityCode)) {
			player.setEmailAdress(player.getTemporaryEmail());
			player.hasEmailRecovery = true;
			player.sm("You have successfully linked your email to this account");
		} else {
			player.sm("You have entered the wrong code, please try again. Or resent the mail.");
		}
	}
	/**
	 * send a mail whenever a account logs in with another IP
	 */
	public void sendIpWarning(){
		//player.getEmailManager().sendCustomMail(player, "We have recorded a new ip login on your account, Ip:  "+endMessage,"Thatscape ip login",""+player.getEmailAdress());
		return;
	}
	
	/**
	  generates the code for the player
	  **
	  */
	
	protected String generateCode(Player player) {
        String POSSIBLECHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        while (code.length() < 11) {
            int index = (int) (rnd.nextFloat() * POSSIBLECHARS.length());
            code.append(POSSIBLECHARS.charAt(index));
        }
        player.emailSecurityCode = code.toString();
        return player.emailSecurityCode;

    }
	/**
	 * To check if the player actually types a real email adress
	 * @param email
	 * @return
	 */
	public static boolean isValidEmailAddress(String email,Player player) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
			  player.sm("The email address that you've entered was incorrect.");
		      result = false;
		   }
		   player.setTemporaryEmail(email);
		   player.setEmailAdress(email);
		   player.hasEnteredEmail = true;
		   return result;
		}


}