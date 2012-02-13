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
 * NMS JMS���߹���
 * 
 * @author yanyl
 * 
 */
public class JMSFactory {
	private static final ApplicationContext context = new FileSystemXmlApplicationContext(
			GlobalConfigFileReader.getConfigFile("spring-jms.xml")
					.getAbsolutePath());

	/**
	 * ��Ϣ������bean����
	 */
	public static final String PRODUCER_BEAN_NAME = "producer";
	/**
	 * ��Ϣ������bean����
	 */
	public static final String LISTENER_CONTAINER_BEAN_NAME = "listenerContainer";
	/**
	 * ���ӹ���bean����
	 */
	public static final String CONNECTION_FACTORY_BEAN_NAME = "connectionFactory";

	/**
	 * �½���Ϣ������ʵ��
	 * 
	 * @return
	 */
	public static MessageProducerImpl createProducer() {
		MessageProducerImpl producer = (MessageProducerImpl) context
				.getBean("producer");
		if (producer == null)
			throw new InvalidJMSConfigException("jms producer���ô���");
		return producer;
	}

	/**
	 * ��ȡ��Ϣ��������һ���������ÿ��Ŀ�ĵ�ֻ��һ��MessageListener�������Ŀ�ĵػ�û�б�ļ��������������½�֮������У���ֱ�ӷ������е�
	 * ��
	 * 
	 * @param destination
	 *            Ҫ������Ŀ�ĵأ����Ϊnull���Ǽ���spring-jms.xml�ж����defaultDestination
	 * @return �����ض�Destination����Ϣ������
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
		Assert.notNull(destination, "destination������Ϊ��");
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
