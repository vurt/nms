package me.vurt.nms.core.node;

import me.vurt.nms.core.node.client.ClientNode;

/**
 * 节点
 * @author Vurt
 *
 */
public interface Node {
	/**
	 * 获取当前节点(只有客户端有效)
	 */
	public static Node CURRENT=new ClientNode();
	
	/**
	 * @return 节点分组
	 */
	String getGroup();
	
	/**
	 * @return 节点id
	 */
	String getId();
	
	/**
	 * @return 节点访问地址
	 */
	String getHost();
	
	/**
	 * @return 当前节点是否是已注册状态
	 */
	boolean isRegisted();

}
