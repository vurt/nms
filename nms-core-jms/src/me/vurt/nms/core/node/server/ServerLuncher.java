package me.vurt.nms.core.node.server;

import javax.jms.Destination;

import me.vurt.nms.core.ApplicationContextHolder;
import me.vurt.nms.core.jms.JMSFactory;
import me.vurt.nms.core.jms.MessageListener;
import me.vurt.nms.core.node.AbstractNodeLuncher;
import me.vurt.nms.core.node.util.BeanConstants;

/**
 * @author yanyl
 * 
 */
public class ServerLuncher extends AbstractNodeLuncher {
	private MessageListener heartBeatListener;
	private MessageListener registrationListener;

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#start()
	 */
	@Override
	protected void start() {
		heartBeatListener=JMSFactory.getMessageListener((Destination)ApplicationContextHolder.getBean(BeanConstants.HEART_BEAT_QUEUE_BEAN));
		heartBeatListener.addMessageHandler(new HeartBeatHandler());
		heartBeatListener.start();
		
		registrationListener=JMSFactory.getMessageListener((Destination)ApplicationContextHolder.getBean(BeanConstants.REGISTRATION_QUEUE_BEAN));
		registrationListener.addMessageHandler(new RegistrationHandler());
		registrationListener.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#stop()
	 */
	@Override
	protected void stop() {
		heartBeatListener.stop();
	}

}
