package me.vurt.nms.core.jms;

import javax.jms.Destination;

import me.vurt.nms.core.jms.exception.InvalidJMSConfigException;
import me.vurt.nms.core.jms.impl.MessageProducerImpl;
import me.vurt.nms.core.jms.util.EmptyDestination;
import me.vurt.nms.core.jms.util.JMSUtil;
import me.vurt.nms.core.jms.util.MessageListenerCache;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * NMS JMS工具工厂
 * 
 * @author yanyl
 * 
 */
public class JMSFactory {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext(
			"conf/spring-jms.xml");

	/**
	 * 消息生产者bean名称
	 */
	public static final String PRODUCER_BEAN_NAME = "producer";
	/**
	 * 消息监听器bean名称
	 */
	public static final String LISTENER_CONTAINER_BEAN_NAME = "listenerContainer";

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
			DefaultMessageListenerContainer listenerContainer = (DefaultMessageListenerContainer) context
					.getBean(LISTENER_CONTAINER_BEAN_NAME);
			if (listenerContainer == null)
				throw new InvalidJMSConfigException("messageListener配置错误");

			if (!EmptyDestination.INSTANCE.equals(destination))
				listenerContainer.setDestination(destination);

			MessageListener listener = JMSUtil
					.getInnerListener(listenerContainer);
			listener.setDestination(listenerContainer.getDestination());
			
			MessageListenerCache.put(listenerContainer);
			
			return listener;
		}
	}
}
