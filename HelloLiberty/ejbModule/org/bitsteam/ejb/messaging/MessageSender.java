package org.bitsteam.ejb.messaging;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.bitsteam.ejb.messaging.view.MessageSenderLocal;

/**
 * Session Bean implementation class MessageSender
 * This class is called by the REST Web Service MessageSenderService to drop a message into 
 * the local liberty queue (run by liberty messaging engine)
 */
@Stateless
@Local(MessageSenderLocal.class)
@LocalBean
@Path("/")
public class MessageSender implements MessageSenderLocal {

    public MessageSender() {}
    
	@GET
	@Path("/dropMessage")
    public String sendMessage(String messageType){
    	if(messageType == "TEST"){
    		
    	  //dropTestMessage();
    	} // else if another message type...
    	
    	return "Message dropped!";
    }
    
    public void dropTestMessage() {
    	
    	
    	//boolean result = dropInQueue("Test Message that was dropped!!!","jms/libertyQCF", "jms/libertyQue");
    }
    	
    
    public boolean dropInQueue(String message, String queueConnectionFactoryName, String queueName){
    	QueueConnection queueConnection = null;
    	QueueSession queueSession = null;
    	QueueSender queueSender = null;
    	int i = 0;
    	
    	try {
    		Context context = new InitialContext();
    		QueueConnectionFactory qcf = (QueueConnectionFactory)context.lookup(queueConnectionFactoryName);
    		Queue queue = (Queue)context.lookup(queueName);
    		
    		queueConnection = qcf.createQueueConnection();
    		queueConnection.start();
    		
    		queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    		queueSender = queueSession.createSender(queue);
    		TextMessage textMessage = queueSession.createTextMessage(message);
    		queueSender.send(textMessage);
    		i++;
    		
    		String msg = "Queue Message Sent - QCF: " + queueConnectionFactoryName + ", Queue: " + queueName;
    		System.out.println(msg);
    		return true;
    		
    	} catch(Throwable t) {
    		t.printStackTrace();
    		String msg = "Exception thrown while dropping message in Queue - QCF: " + queueConnectionFactoryName +
    				", Queue: " + queueName;
    		System.out.println(msg);
    		return false;
    	} finally {
    		try {
    			if(queueSender != null) {
    				queueSender.close();
    			}
    			if(queueSession != null) {
    				queueSession.close();
    			}
    			if(queueConnection != null) {
    				queueConnection.close();
    			}
    		} catch(Throwable t) {
    		}
    	
    	}
    }

}