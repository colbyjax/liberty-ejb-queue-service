package org.bitsteam.rest;

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
import javax.ws.rs.PathParam;

/**
 * Session Bean implementation class MessageSender
 * This class is called by the REST Web Service MessageSenderService to drop a message into 
 * the local liberty queue (run by liberty messaging engine)
 */
@Stateless(mappedName="ejb/MessageSenderService", name="MessageSenderService")
@Path("/rest")
public class MessageSenderService  {

    public MessageSenderService() {}
    
	@GET
	@Path("/dropMessage/{id}")
	// http://localhost:9080/HelloLibertyWeb/rest/dropMessage/1
    
	/**
	 * Will handle the incoming request and pass the request to the appropriate method
	 * based on the parameter passed.
	 * @param id
	 * @return
	 */
	public String handleMessage(@PathParam("id") int id){
    	String result = null;
		if(id == 1) {
    		dropTestMessage();
    		result = "Message Dropped!";
    	} else {
    		result = "No Message Dropped.";
    	}
    	
    	return result;
    }
    
    public void dropTestMessage() {
    	dropInQueue("Test Message that was dropped!!!","jms/libertyQCF", "jms/libertyQue");
    }
    	
    
    public boolean dropInQueue(String message, String queueConnectionFactoryName, String queueName){
    	QueueConnection queueConnection = null;
    	QueueSession queueSession = null;
    	QueueSender queueSender = null;
    	
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
    		
    		String msg = "[MessageSender] - Message successfully dropped on queue: " + message;
    		System.out.println(msg);
    		return true;
    		
    	} catch(Throwable t) {
    		t.printStackTrace();
    		String msg = "[MessageSender] - Exception thrown while dropping message in Queue - QCF: " + queueConnectionFactoryName +
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
