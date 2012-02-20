package me.vurt.nms.core.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import me.vurt.nms.core.ApplicationContextHolder;
import me.vurt.nms.core.jms.util.MessageListenerCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.util.Assert;

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
	 * @param destination
	 *            要监听的目标
	 * @return 消息监听器，默认新建出来时是未启动状态
	 */
	public static MessageListener getMessageListener(Destination destination) {
		MessageListener listener = MessageListenerCache
				.getMessageListener(destination);
		if (listener == null) {
			createListenerContainer(destination);
			return MessageListenerCache.getMessageListener(destination);
		}
		return listener;
	}

	/**
	 * 获取消息监听器
	 * 
	 * @param destination
	 *            要监听的目标
	 * @param responseDestination
	 *            默认响应目标，只有在接受到的Message的JMS Reply-To属性为空时才有效，只有在新建时设置有效
	 * @return 消息监听器，默认新建出来时是未启动状态
	 */
	public static MessageListener getMessageListener(Destination destination,
			Destination responseDestination) {
		MessageListener listener = MessageListenerCache
				.getMessageListener(destination);
		if (listener == null) {
			createListenerContainer(destination, responseDestination);
			return MessageListenerCache.getMessageListener(destination);
		}
		return listener;
	}

	/**
	 * 获取Spring中定义的连接工厂
	 * 
	 * @return
	 */
	private static ConnectionFactory getConnectionFactory() {
		return (ConnectionFactory) ApplicationContextHolder
				.getBean(CONNECTION_FACTORY_BEAN_NAME);
	}

	/**
	 * 创建监听指定目的地的监听容器，并初始化一个{@link MessageListener 监听器}，将它们根据目标缓存到
	 * {@link MessageListenerCache}中去。 该监听容器中的{@link MessageListenerAdapter}
	 * 没有默认响应目标，要设置默认响应目标，请使用
	 * {@link #createListenerContainer(Destination, Destination)}
	 * 
	 * @param destination
	 *            要监听的目标
	 * @return 监听容器，创建时默认不启动
	 */
	public static DefaultMessageListenerContainer createListenerContainer(
			Destination destination) {
		return createListenerContainer(destination, null);
	}

	/**
	 * 创建监听指定目的地的监听容器，并初始化一个{@link MessageListener 监听器}，将它们根据目标缓存到
	 * {@link MessageListenerCache}中去。
	 * 
	 * @param destination
	 *            要监听的目标
	 * @param responseDestination
	 *            默认响应目标，只有在接受到的Message的JMS Reply-To属性为空时才有效
	 * @return 监听容器，创建时默认不启动
	 */
	public static DefaultMessageListenerContainer createListenerContainer(
			Destination destination, Destination responseDestination) {
		Assert.notNull(destination, "destination不允许为空");
		DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
		listenerContainer.setConnectionFactory(getConnectionFactory());
		listenerContainer.setDestination(destination);

		MessageListener listener = new MessageListener();
		listener.setDestination(destination);

		MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
		adapter.setDefaultListenerMethod(MessageListener.MESSAGE_HANDLE_MOTHED_NAME);
		adapter.setDefaultResponseDestination(responseDestination);

		listenerContainer.setMessageListener(adapter);
		MessageListenerCache.put(listenerContainer, listener);
		return listenerContainer;
	}
}
