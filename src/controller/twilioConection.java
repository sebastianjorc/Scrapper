package controller;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class twilioConection implements autentificationData{

	public twilioConection() {
		send("sms de prueba por Sebastián");
	}
	
	public twilioConection(String text) {
		send(text);
	}

	public static void send(String bodyMessage){	
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message.creator(
				new PhoneNumber(numeroReceptor),
				new PhoneNumber(numeroEmisor), 
				bodyMessage
				).create();
	}
	
	public void estados () {		
		ResourceSet <Message> messages = Message.reader().read();
		for (Message m : messages) {
		    System.out.println(m.getSid() + " : " + m.getStatus());
		}
	}

}
