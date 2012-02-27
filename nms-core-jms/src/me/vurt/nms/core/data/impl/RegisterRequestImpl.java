package me.vurt.nms.core.data.impl;

import me.vurt.nms.core.data.RegisterRequest;
import me.vurt.nms.core.node.Node;

/**
 * 节点注册请求，初始化时默认包含了当前节点的信息
 * @author yanyl
 *
 */
public class RegisterRequestImpl implements RegisterRequest{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6882852395914960576L;
	
	private String id;
	
	private String group;
	
	private String host;
	
	private String key;
	/**
	 * 初始化注册请求
	 * @param node 要注册的节点
	 */
	public RegisterRequestImpl(Node node){
		this.id=node.getId();
		this.group=node.getGroup();
		this.host=node.getHost();
		this.key=node.getKey();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.data.RegisterRequest#getHost()
	 */
	@Override
	public String getHost() {
		return host;
	}

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.data.RegisterRequest#getKey()
	 */
	@Override
	public String getKey() {
		return key;
	}
}
