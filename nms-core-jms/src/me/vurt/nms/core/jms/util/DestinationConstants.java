package me.vurt.nms.core.jms.util;

/**
 * Spring中定义的系统bean的名称常量
 * @author yanyl
 *
 */
public interface DestinationConstants {
	/**
	 * 传递心跳的Queue的名称
	 */
	String HEART_BEAT_QUEUE="HeartBeat";
	
	/**
	 * 注册使用的Queue的名称
	 */
	String REGISTRATION_QUEUE="Registration";
	
	/**
	 * 响应客户端注册消息使用的Queue的名称
	 */
	String REGISTRATION_RESPONSE_QUEUE="registrationResponseQueue";
	
	/**
	 * 服务器广播消息的Topic的Name
	 */
	String TOPIC="NMSTopic";
}
