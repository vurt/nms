package me.vurt.nms.core;

/**
 * NMS常量
 * @author yanyl
 *
 */
public interface Constants {
	
	/**
	 * NMS全局配置文件路径
	 */
	String CONFIG_FILE_PATH="nms-Config.properties";
	/**
	 * 配置属性名:消息中间件代理URL
	 */
	String CONFIG_BROKER_URL="mq.broker.url";
	/**
	 * 配置属性名:客户端心跳间隔时间
	 */
	String CONFIG_HEARTBEAT_REPEAT_INTERVAL="client.heartBeat.repeatInterval";
	
	/**
	 * 配置属性名:调试模式
	 */
	String CONFIG_DEBUG_MODE="debug";
		
	/**
	 * 系统属性名:节点类型，设置方式:启动时 -Dnms.node.type=client|server
	 * 
	 */
	String SYS_PROPERTY_NODE_TYPE="nms.node.type";
	
	/**
	 * 节点类型，服务端
	 */
	String NODE_TYPE_SERVER="server";
	
	/**
	 * 节点类型，客户端
	 */
	String NODE_TYPE_CLIENT="client";
	
			
}
