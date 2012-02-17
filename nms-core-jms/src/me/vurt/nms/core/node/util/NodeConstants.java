package me.vurt.nms.core.node.util;

/**
 * @author yanyl
 *
 */
public interface NodeConstants {
	/**
	 * 节点类型属性名，节点类型是程序启动时的参数确定的，-Dnms.node.type=client|server
	 */
	String NODE_TYPE_PROPERTY="nms.node.type";
			
	/**
	 * 节点类型，服务端
	 */
	String NODE_TYPE_SERVER="server";
	
	/**
	 * 节点类型，客户端
	 */
	String NODE_TYPE_CLIENT="client";
			
}
