package me.vurt.nms.core.node.server;

import java.sql.SQLException;

import javax.jms.Destination;

import me.vurt.nms.core.ApplicationContextHolder;
import me.vurt.nms.core.jms.JMSFactory;
import me.vurt.nms.core.jms.MessageListener;
import me.vurt.nms.core.jms.impl.StaticMessageListener;
import me.vurt.nms.core.node.AbstractNodeLuncher;
import me.vurt.nms.core.node.client.ClientNode;
import me.vurt.nms.core.node.server.handler.HeartBeatHandler;
import me.vurt.nms.core.node.server.handler.RegistrationHandler;
import me.vurt.nms.core.node.util.BeanConstants;
import me.vurt.nms.core.node.util.GlobalConfigReader;

import org.h2.tools.Server;

/**
 * 服务端启动器
 * 
 * @author yanyl
 * 
 */
public class ServerLuncher extends AbstractNodeLuncher {
	private MessageListener heartBeatListener;
	private MessageListener registrationListener;
	private Server h2Server;

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#start()
	 */
	@Override
	protected void start() {
		if (GlobalConfigReader.debugMode()) {
			try {
				h2Server = Server.createWebServer().start();
			} catch (SQLException e) {
				LOGGER.error("H2数据库启动失败", e);
			}
		}
		
		heartBeatListener = JMSFactory
				.getMessageListener((Destination) ApplicationContextHolder
						.getBean(BeanConstants.HEART_BEAT_QUEUE_BEAN));
		if (heartBeatListener instanceof StaticMessageListener) {
			((StaticMessageListener) heartBeatListener)
					.addMessageHandler(new HeartBeatHandler());
		}
		heartBeatListener.start();

		registrationListener = JMSFactory
				.getMessageListener(
						(Destination) ApplicationContextHolder
								.getBean(BeanConstants.REGISTRATION_QUEUE_BEAN),
						(Destination) ApplicationContextHolder
								.getBean(BeanConstants.REGISTRATION_RESPONSE_QUEUE_BEAN));
		if (registrationListener instanceof StaticMessageListener) {
			((StaticMessageListener) registrationListener)
					.addMessageHandler(new RegistrationHandler());
		}
		registrationListener.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#stop()
	 */
	@Override
	protected void stop() {
		if (heartBeatListener != null) {
			heartBeatListener.stop();
		}
		if (registrationListener != null) {
			registrationListener.stop();
		}
		if (h2Server != null) {
			h2Server.stop();
		}
	}

}
