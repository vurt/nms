package me.vurt.nms.core.jms;

import me.vurt.nms.core.ApplicationContextHolder;
import me.vurt.nms.core.jms.util.JMSUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * NMS JMS工具工厂
 * 
 * @author yanyl
 * 
 */
public class JMSFactory {
	public static final Logger LOGGER = LoggerFactory
			.getLogger(JMSFactory.class);

	/**
	 * 连接工程bean名称
	 */
	public static final String CONNECTION_FACTORY_BEAN_NAME = "connectionFactory";

	// /**
	// * 新建消息生产者实例
	// *
	// * @return
	// */
	// public static MessageProducerImpl createProducer() {
	// MessageProducerImpl producer = (MessageProducerImpl) context
	// .getBean("producer");
	// if (producer == null)
	// throw new InvalidJMSConfigException("jms producer配置错误");
	// return producer;
	// }

	/**
	 * 获取消息监听器
	 * 
	 * @param containerID
	 *            Spring配置文件中定义的监听容器的bean id
	 * @return 容器中的消息监听器
	 */
	public static MessageListener getMessageListener(String containerID) {
		DefaultMessageListenerContainer listenerContainer = ApplicationContextHolder
				.getBean(containerID,
						DefaultMessageListenerContainer.class);

		MessageListener listener = JMSUtil.getInnerListener(listenerContainer);
		return listener;
	}

	// public static DefaultMessageListenerContainer createListenerContainer(
	// Destination destination) {
	// Assert.notNull(destination, "destination不允许为空");
	// DefaultMessageListenerContainer listenerContainer = new
	// DefaultMessageListenerContainer();
	// listenerContainer.setConnectionFactory((ConnectionFactory) context
	// .getBean(CONNECTION_FACTORY_BEAN_NAME));
	// listenerContainer.setDestination(destination);
	//
	// MessageListenerAdapter2 adapter = new MessageListenerAdapter2(
	// new MessageListener());
	// adapter.setDefaultListenerMethod(MessageListener.MESSAGE_HANDLE_MOTHED_NAME);
	// listenerContainer.setMessageListener(adapter);
	//
	// return listenerContainer;
	// }
}
