package me.vurt.nms.core.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import me.vurt.nms.core.ApplicationContextHolder;
import me.vurt.nms.core.jms.exception.InvalidJMSConfigException;
import me.vurt.nms.core.jms.impl.NMSMessageListenerAdapter;
import me.vurt.nms.core.jms.impl.StaticMessageListener;
import me.vurt.nms.core.jms.util.MessageListenerCache;
import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.client.ClientNode;
import me.vurt.nms.core.node.util.BeanConstants;
import me.vurt.nms.core.node.util.GlobalConfigReader;
import me.vurt.nms.core.node.util.NodeConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.util.Assert;

/**
 * NMS JMS工具工厂<BR>
 * 如果该工厂的调用者是客户端节点，那么创建出来的所有消息监听器都只接受发送给当前节点的消息，该消息选择器无法修改
 * (即，消息头中{@link NodeConstants#PROPERTY_NODE_GROUP}和{@link NodeConstants#PROPERTY_NODE_ID}属性值与当前节点匹配)
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

	/**
	 * 新建消息生产者实例，需要手动设置JmsTemplate后才能使用
	 * 
	 * @return
	 */
	public static MessageProducer createProducer() {
		MessageProducer producer = (MessageProducer) ApplicationContextHolder
				.getBean(BeanConstants.PRODUCER_BEAN);
		if (producer == null)
			throw new InvalidJMSConfigException("jms producer配置错误");
		return producer;
	}

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
		initMessageSelector(listenerContainer);

		MessageListener listener = new StaticMessageListener();
		listener.setDestination(destination);

		MessageListenerAdapter adapter = new NMSMessageListenerAdapter(listener);
		adapter.setDefaultListenerMethod(MessageListener.MESSAGE_HANDLE_MOTHED_NAME);
		adapter.setDefaultResponseDestination(responseDestination);

		listenerContainer.setMessageListener(adapter);
		MessageListenerCache.put(listenerContainer, listener);
		return listenerContainer;
	}
	
	/**
	 * 构建只接收发送给当前节点消息的选择器的表达式
	 */
	public static String getMessageSelector(){
		if(!GlobalConfigReader.isClient()){
			LOGGER.warn("当前节点不是客户端，不需要过滤信息");
			 return "";
		}
		String messageSelector ="\'"+ NodeConstants.PROPERTY_NODE_GROUP+"\'" + " = "
				+ Node.CURRENT.getGroup() + " AND \'" + NodeConstants.PROPERTY_NODE_ID+"\'" + " = "
				+ Node.CURRENT.getId();
		LOGGER.debug("当前节点的MessageSelector="+messageSelector);
		return messageSelector;
	}

	/**
	 * 初始化监听容器的消息选择器<BR>
	 * 客户端中监听所有非私有Destination的监听容器默认只接受发给当前节点的消息
	 * (即消息头中nms.node.group和nms.node.id属性与当前节点匹配的消息)
	 * 
	 * @param listenerContainer
	 */
	private static void initMessageSelector(
			DefaultMessageListenerContainer listenerContainer) {
		// 当前节点的类型是客户端
		if (GlobalConfigReader.isClient()) {
			listenerContainer.setMessageSelector(getMessageSelector());
		}
	}
}
