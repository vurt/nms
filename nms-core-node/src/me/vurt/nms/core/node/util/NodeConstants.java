package me.vurt.nms.core.node.util;

/**
 * @author yanyl
 *
 */
public interface NodeConstants {
	
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
	String PROPERTY_DB_DRIVER="server.node.db.driver";
	
	/**
	 * 属性名：数据库url
	 */
	String PROPERTY_DB_URL="server.node.db.url";
	
	/**
	 * 属性名：数据库用户名
	 */
	String PROPERTY_DB_USERNAME="server.node.db.username";

	/**
	 * 属性名：数据库密码
	 */
	String PROPERTY_DB_PASSWORD="server.node.db.password";
	
	/**
	 * 响应信息中"节点Key"的key
	 */
	String RESPONSE_NODE_KEY="node_key";
	
	/**
	 * 系统使用的标准日期格式
	 */
	String DATE_FORMAT_PATTERN="yyyy-MM-dd hh:mm:ss";
			
}
