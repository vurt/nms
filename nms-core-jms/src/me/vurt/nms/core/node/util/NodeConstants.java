package me.vurt.nms.core.node.util;

/**
 * @author yanyl
 *
 */
public interface NodeConstants {
	
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
	 * 系统属性名:节点类型，设置方式:启动时 -Dnms.node.type=client|server
	 * 
	 */
	String SYS_PROPERTY_NODE_TYPE="nms.node.type";
	
	/**
	 * 属性名:节点验证key，程序自动维护的值，请勿手动修改
	 */
	String PROPERTY_NODE_KEY="sys.nms.node.key";
	
	/**
	 * 属性名:节点分组
	 */
	String PROPERTY_NODE_GROUP="nms.node.group";
	
	/**
	 * 属性名:节点ID
	 */
	String PROPERTY_NODE_ID="nms.node.id";
	
	/**
	 * 属性名:调试模式
	 */
	String PROPERTY_DEBUG_MODE="debug";
	
	/**
	 * 属性名：数据库连接驱动名称
	 */
	String PROPERTY_DB_DRIVER="nms.server.db.driver";
	
	/**
	 * 属性名：数据库url
	 */
	String PROPERTY_DB_URL="nms.server.db.url";
	
	/**
	 * 属性名：数据库用户名
	 */
	String PROPERTY_DB_USERNAME="nms.server.db.username";

	/**
	 * 属性名：数据库密码
	 */
	String PROPERTY_DB_PASSWORD="nms.server.db.password";
	
	/**
	 * 响应信息中"节点Key"的key
	 */
	String RESPONSE_NODE_KEY="node_key";

	/**
	 * 节点类型，服务端
	 */
	String NODE_TYPE_SERVER="server";
	
	/**
	 * 节点类型，客户端
	 */
	String NODE_TYPE_CLIENT="client";
	
	/**
	 * 系统使用的标准日期格式
	 */
	String DATE_FORMAT_PATTERN="yyyy-MM-dd hh:mm:ss";
			
}
