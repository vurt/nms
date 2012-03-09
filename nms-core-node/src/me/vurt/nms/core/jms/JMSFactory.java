package me.vurt.nms.core.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import me.vurt.nms.core.Constants;
import me.vurt.nms.core.common.properties.PropertiesManager;
import me.vurt.nms.core.common.tools.ConfigReader;
import me.vurt.nms.core.exception.InvalidConfigException;
import me.vurt.nms.core.jms.impl.MessageProducerImpl;
import me.vurt.nms.core.jms.impl.NMSMessageListenerAdapter;
import me.vurt.nms.core.jms.util.FileMessageConverter;
import me.vurt.nms.core.jms.util.MessageListenerCache;
import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.util.NodeConstants;
import me.vurt.nms.core.node.util.PropertyNameUtil;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.util.Assert;

/**
 * 通讯接口工厂<BR>
 * 
 * @author yanyl
 * 
 */
public class JMSFactory {
	public static final Logger LOGGER = LoggerFactory
			.getLogger(JMSFactory.class);
	private static PropertiesManager globalConfig = ConfigReader
			.getConfigFileManager();

	private static ConnectionFactory connectionFactory;

	/**
	 * 获取连接到消息中间件代理的连接工厂
	 * 
	 * @return
	 */
	private static final ConnectionFactory getConnectionFactory() {
		if (connectionFactory == null) {
			String url = globalConfig.read(Constants.CONFIG_BROKER_URL);
			if (StringUtils.isEmpty(url)) {
				throw new InvalidConfigException(Constants.CONFIG_BROKER_URL
						+ "属性不能为空");
			}
			url=attachUploadUrl(url);
			connectionFactory = new PooledConnectionFactory(
					new ActiveMQConnectionFactory(url));
		}
		return connectionFactory;
	}

	/**
	 * 如果配置了文件服务器，则将文件服务器的路径添加到brokerUrl后面
	 * 
	 * @param brokerUrl
	 *            源brokerUrl
	 * @return 新的brokerUrl
	 */
	private static String attachUploadUrl(String brokerUrl) {
		String result = brokerUrl;
		String ftpUrl = globalConfig.read(Constants.CONFIG_FTP_URL);
		if (!StringUtils.isEmpty(ftpUrl)) {
			String username = globalConfig.read(Constants.CONFIG_FTP_USERNAME);
			StringBuilder ftpUrlBuilder = new StringBuilder("ftp://");
			if (!StringUtils.isEmpty(username)) {
				ftpUrlBuilder.append(username);
				ftpUrlBuilder.append(":");
				String password = globalConfig
						.read(Constants.CONFIG_FTP_PASSWORD);
				if (StringUtils.isEmpty(password)) {
					throw new InvalidConfigException(
							Constants.CONFIG_FTP_PASSWORD + "不能为空");
				}
				ftpUrlBuilder.append(password);
			}
			ftpUrlBuilder.append(ftpUrl);
			result = brokerUrl + "?jms.blobTransferPolicy.uploadUrl="
					+ ftpUrlBuilder.toString();
		}else{
			LOGGER.warn("没有配置文件服务器信息，将不能使用消息中间件传输文件");
		}
		return result;
	}

	/**
	 * 创建队列Destination
	 * 
	 * @param queue
	 *            队列名称
	 */
	public static Destination createQueueDestination(String queue) {
		return new ActiveMQQueue(queue);
	}

	/**
	 * @param topic
	 *            topic名称
	 */
	public static Destination createTopicDestination(String topic) {
		return new ActiveMQTempTopic(topic);
	}

	/**
	 * 新建消息生产者实例
	 * 
	 * @return
	 */
	public static MessageProducer createProducer() {
		return new MessageProducerImpl();
	}

	/**
	 * 新建JmsTemplate，DefaultDestination为空，支持发送文件，QOS值(deliveryMode, priority, timeToLive)在发送消息时有
	 */
	public static JmsTemplate createJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(JMSFactory.getConnectionFactory());
		jmsTemplate.setMessageConverter(new FileMessageConverter());
		jmsTemplate.setExplicitQosEnabled(true);
		return jmsTemplate;
	}

	/**
	 * 获取消息监听器, 如果该方法的调用者运行在客户端节点，那么创建出来的所有消息监听器都只接受发送给当前节点的消息，该消息选择器无法修改
	 * (即，消息头中 {@link NodeConstants#PROPERTY_NODE_GROUP}和
	 * {@link NodeConstants#PROPERTY_NODE_ID}属性值与当前节点匹配)
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
	 * @deprecated 响应信息应该在发送端设置，而不是接收端
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
	public static String getMessageSelector() {
		if (!ConfigReader.isClient()) {
			LOGGER.warn("当前节点不是客户端，不需要过滤信息");
			return "";
		}
		String messageSelector = PropertyNameUtil
				.toJMSName(NodeConstants.PROPERTY_NODE_GROUP)
				+ " = \'"
				+ Node.CURRENT.getGroup()
				+ "\' AND "
				+ PropertyNameUtil.toJMSName(NodeConstants.PROPERTY_NODE_ID)
				+ " = \'" + Node.CURRENT.getId() + "\'";
		LOGGER.debug("当前节点的MessageSelector=" + messageSelector);
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
		if (ConfigReader.isClient()) {
			listenerContainer.setMessageSelector(getMessageSelector());
		}
	}
}
