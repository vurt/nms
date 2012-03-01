package me.vurt.nms.core.node.data;

import me.vurt.nms.core.data.Data;


/**
 * 节点连接请求，初始化时默认包含了当前节点的信息
 * @author yanyl
 *
 */
public interface RegisterRequest extends Data{
	
	/**
	 * @return 节点ID
	 */
	public String getId() ;

	/**
	 * @return 节点分组
	 */
	public String getGroup();
	
	/**
	 * @return 节点访问地址
	 */
	public String getHost();
	
	/**
	 * 节点第一次向服务器发送连接请求时，该属性为空，如注册成功，
	 * 则服务器会给节点分配一个唯一的key，以后节点每次申请连接服务器时都需要把该key传回服务器进行验证
	 * @return 服务器分配给当前节点的key
	 */
	public String getKey();
}
