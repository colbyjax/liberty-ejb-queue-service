package org.bitsteam.ejb.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: HelloMDB
 * 
 * Message Driven Bean that consumes all messages from libertyQue
 * (Messages can be dropped by invoking RESTFUL web service
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "jms/libertyQue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "jms/libertyQue")
public class HelloMDB implements MessageListener {

    public HelloMDB() {}
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
       System.out.println("[HelloMDB] - Message Received!");
       
       TextMessage textMessage = (TextMessage) message;
       
       try{
    	   handleMessage(textMessage);
       } catch (Exception e) {
    	   System.out.println("[HelloMDB] - Exception thrown while handling message: " + e.getMessage());
       }
        
    }
    
    /**
     * Not really needed to breakout for this small test, but as more messages needed to 
     * be handled, this is a good skeleton framework
     * @param textMessage
     * @throws JMSException
     */
    public void handleMessage(TextMessage textMessage) throws JMSException {
    	if(textMessage.getText() != null) {
    		String messageText = textMessage.getText().trim();
    		System.out.println("[HelloMDB] - Message Text: " + messageText);
    	}
    }

}
