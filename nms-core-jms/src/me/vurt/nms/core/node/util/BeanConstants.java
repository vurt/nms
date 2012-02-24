package me.vurt.nms.core.node.util;

/**
 * Spring中定义的系统bean的名称常量
 * @author yanyl
 *
 */
public interface BeanConstants {
	/**
	 * 连接工厂的Bean ID
	 */
	String CONNECTION_FACTORY="jmsFactory";
	/**
	 * 通用消息生产者
	 */
	String PRODUCER_BEAN="producer";
	/**
	 * 传递心跳的Queue的名称
	 */
	String HEART_BEAT_QUEUE_NAME="HeartBeat";
	/**
	 * 传递心跳的Queue的Destination的Bean ID
	 */
	String HEART_BEAT_QUEUE_BEAN="heartBeatQueue";
	
	/**
	 * 注册使用的Queue的名称
	 */
	String REGISTRATION_QUEUE_NAME="Registration";
	
	/**
	 * 注册使用的Queue的Destination的Bean ID
	 */
	String REGISTRATION_QUEUE_BEAN="registrationQueue";
	
	
	/**
	 * 响应客户端注册消息使用的Queue的名称
	 */
	String REGISTRATION_RESPONSE_QUEUE_NAME="registrationResponseQueue";
	
	/**
	 * 响应客户端注册消息使用的Queue的Destination的Bean ID
	 */
	String REGISTRATION_RESPONSE_QUEUE_BEAN="registrationResponseQueue";
	
	/**
	 * 服务器广播消息的Topic的Name
	 */
	String TOPIC_NAME="NMSTopic";
	
	/**
	 * 服务器广播消息的Topic的Destination的Bean ID
	 */
	String TOPIC_BEAN="nmsTopic";
	
	/**
	 * 客户端节点使用的常量
	 * @author yanyl
	 *
	 */
	interface Client{
		/**
		 * 心跳任务启动器
		 */
		String HEARTBEAT_SCHEDULER="heartBeatScheduler";
	}
	
	/**
	 * 服务器节点的常量
	 * @author yanyl
	 *
	 */
	interface Server{
		/**
		 * 广播消息生产者Bean ID
		 */
		String TOPIC_PRODUCER_BEAN="topicProducer";
		
		/**
		 * 心跳监听容器的Bean ID
		 */
		String HEARTBEAT_LISTENER_CONTAINER_BEAN="heartBeatListenerContainer";
		
		/**
		 * 注册监听容器的Bean ID
		 */
		String REGISTRATION_LISTENER_CONTAINER_BEAN="registrationListenerContainer";
	}
}
