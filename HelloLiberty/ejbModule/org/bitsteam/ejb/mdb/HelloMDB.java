package org.bitsteam.ejb.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.BytesMessage;
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
				propertyName = "destination", propertyValue = "jms/aeiQue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "jms/aeiQue")
public class HelloMDB implements MessageListener {

    public HelloMDB() {}
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
       System.out.println("[HelloMDB] - Message Received!");
       
       // What type of message is it?
       if(message instanceof TextMessage) {
    	   TextMessage textMessage = (TextMessage) message;
           try{
        	   handleTextMessage(textMessage);
           } catch (Exception e) {
        	   System.out.println("[HelloMDB] - Exception thrown while handling message: " + e.getMessage());
           }
       } else if (message instanceof BytesMessage) {
    	   BytesMessage bm=(BytesMessage)message;
	       	       
    	   try{
        	   handleBytesMessage(bm);
           } catch (Exception e) {
        	   System.out.println("[HelloMDB] - Exception thrown while handling message: " + e.getMessage());
           }

       }
        
    }
    
    /**
     * Not really needed to breakout for this small test, but as more messages needed to 
     * be handled, this is a good skeleton framework
     * @param textMessage
     * @throws JMSException
     */
    public void handleTextMessage(TextMessage textMessage) throws JMSException {
    	if(textMessage.getText() != null) {
    		String messageText = textMessage.getText().trim();
    		System.out.println("[HelloMDB] - Message Text: " + messageText);
    	}
    }
    
    public void handleBytesMessage(BytesMessage byteMessage) throws JMSException {
    	byte data[]=new byte[(int)byteMessage.getBodyLength()];
    	String msg = byteMessage.readUTF();
	    System.out.println(msg);
    }

}
