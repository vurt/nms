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
	 * 节点注册成功时，服务器会给节点分配一个唯一的key，
	 * 以后节点每次节点申请连接服务器时都需要把该key传回服务器进行验证
	 * @return 服务器分配给节点的key
	 */
	String getKey();
	
}
