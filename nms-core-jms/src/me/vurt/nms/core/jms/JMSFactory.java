package me.vurt.nms.core.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import me.vurt.nms.core.common.tools.GlobalConfigFileReader;
import me.vurt.nms.core.jms.exception.InvalidJMSConfigException;
import me.vurt.nms.core.jms.impl.MessageListenerAdapter2;
import me.vurt.nms.core.jms.impl.MessageProducerImpl;
import me.vurt.nms.core.jms.util.EmptyDestination;
import me.vurt.nms.core.jms.util.JMSUtil;
import me.vurt.nms.core.jms.util.MessageListenerCache;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.util.Assert;

/**
 * NMS JMS工具工厂
 * 
 * @author yanyl
 * 
 */
public class JMSFactory {
	private static final ApplicationContext context = new FileSystemXmlApplicationContext(
			GlobalConfigFileReader.getConfigFile("spring-jms.xml")
					.getAbsolutePath());

	/**
	 * 消息生产者bean名称
	 */
	public static final String PRODUCER_BEAN_NAME = "producer";
	/**
	 * 消息监听器bean名称
	 */
	public static final String LISTENER_CONTAINER_BEAN_NAME = "listenerContainer";
	/**
	 * 连接工程bean名称
	 */
	public static final String CONNECTION_FACTORY_BEAN_NAME = "connectionFactory";

	/**
	 * 新建消息生产者实例
	 * 
	 * @return
	 */
	public static MessageProducerImpl createProducer() {
		MessageProducerImpl producer = (MessageProducerImpl) context
				.getBean("producer");
		if (producer == null)
			throw new InvalidJMSConfigException("jms producer配置错误");
		return producer;
	}

	/**
	 * 获取消息监听器，一个虚拟机中每个目的地只有一个MessageListener，即如果目的地还没有别的监听器监听，则新建之，如果有，则直接返回已有的
	 * 。
	 * 
	 * @param destination
	 *            要监听的目的地，如果为null则是监听spring-jms.xml中定义的defaultDestination
	 * @return 监听特定Destination的消息监听器
	 */
	public static MessageListener getMessageListener(Destination destination) {
		if (destination == null) {
			destination = EmptyDestination.INSTANCE;
		}
		if (MessageListenerCache.hasListener(destination)) {
			return MessageListenerCache.getCachedMessageListener(destination);
		} else {
			if (EmptyDestination.INSTANCE.equals(destination)) {
				destination = (Destination) context
						.getBean("defaultDestination");
			}

			DefaultMessageListenerContainer listenerContainer = createListenerContainer(destination);

			MessageListener listener = JMSUtil
					.getInnerListener(listenerContainer);

			MessageListenerCache.put(listenerContainer);

			return listener;
		}
	}

	public static DefaultMessageListenerContainer createListenerContainer(
			Destination destination) {
		Assert.notNull(destination, "destination不允许为空");
		DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
		listenerContainer.setConnectionFactory((ConnectionFactory) context
				.getBean(CONNECTION_FACTORY_BEAN_NAME));
		listenerContainer.setDestination(destination);

		MessageListenerAdapter2 adapter = new MessageListenerAdapter2(
				new MessageListener());
		adapter.setDefaultListenerMethod(MessageListener.MESSAGE_HANDLE_MOTHED_NAME);
		listenerContainer.setMessageListener(adapter);

		return listenerContainer;
	}
}
