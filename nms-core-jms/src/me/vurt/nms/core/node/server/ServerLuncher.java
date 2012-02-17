package me.vurt.nms.core.node.server;

import me.vurt.nms.core.ApplicationContextHolder;
import me.vurt.nms.core.jms.MessageListener;
import me.vurt.nms.core.jms.util.JMSUtil;
import me.vurt.nms.core.node.AbstractNodeLuncher;
import me.vurt.nms.core.node.util.BeanConstants;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * @author yanyl
 * 
 */
public class ServerLuncher extends AbstractNodeLuncher {
	private DefaultMessageListenerContainer heartbeatListenerContainer;
	private MessageListener heartBeatListener;

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#start()
	 */
	@Override
	protected void start() {
		heartbeatListenerContainer=ApplicationContextHolder.getBean(
				BeanConstants.Server.HEARTBEAT_LISTENER_CONTAINER_BEAN,
				DefaultMessageListenerContainer.class);
		heartBeatListener=JMSUtil.getInnerListener(heartbeatListenerContainer);
		heartBeatListener.setDestination(heartbeatListenerContainer.getDestination());
		heartBeatListener.addMessageHandler(new HeartBeatHandler());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#stop()
	 */
	@Override
	protected void stop() {
		heartbeatListenerContainer.stop();
		heartBeatListener.stop();
	}

}
