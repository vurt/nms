package me.vurt.nms.core.node.server;

import java.sql.SQLException;

import javax.jms.DeliveryMode;

import me.vurt.nms.core.Luncher;
import me.vurt.nms.core.common.tools.ConfigReader;
import me.vurt.nms.core.jms.JMSFactory;
import me.vurt.nms.core.jms.MessageListener;
import me.vurt.nms.core.jms.util.DestinationConstants;
import me.vurt.nms.core.node.data.impl.RegisterResponseImpl;
import me.vurt.nms.core.node.server.dao.DAOUtil;
import me.vurt.nms.core.node.server.dao.TNode;
import me.vurt.nms.core.node.server.handler.HeartBeatHandler;
import me.vurt.nms.core.node.server.handler.RegistrationHandler;

import org.h2.tools.Server;

/**
 * 服务端启动器
 * 
 * @author yanyl
 * 
 */
public class ServerLuncher implements Luncher {
	private MessageListener heartBeatListener;
	private MessageListener registrationListener;
	private Server h2Server;

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#start()
	 */
	@Override
	public void start() {
		LOGGER.debug("[bundle:core-node]-服务端启动");
		if (ConfigReader.debugMode()) {
			try {
				h2Server = Server.createWebServer().start();
				DAOUtil.getServerDao().create(TNode.class, false);
			} catch (SQLException e) {
				LOGGER.error("H2数据库启动失败", e);
			}
		}

		heartBeatListener = JMSFactory.getMessageListener(JMSFactory
				.createQueueDestination(DestinationConstants.HEART_BEAT_QUEUE));
		heartBeatListener.addMessageHandler(new HeartBeatHandler());
		heartBeatListener.start();

		registrationListener = JMSFactory
				.getMessageListener(JMSFactory
						.createQueueDestination(DestinationConstants.REGISTRATION_QUEUE));
		registrationListener.setResponseType(RegisterResponseImpl.class);
		// 注册响应消息以非持久化的方式发送，存活时间5秒，避免某些节点发送注册请求后没来得及处理响应就掉线导致注册响应Queue堵塞
		registrationListener.setResponseTimeToLive(5000);
		registrationListener
				.setResponseDeliveryMode(DeliveryMode.NON_PERSISTENT);
		registrationListener	.addMessageHandler(new RegistrationHandler());
		registrationListener.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#stop()
	 */
	@Override
	public void stop() {
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
