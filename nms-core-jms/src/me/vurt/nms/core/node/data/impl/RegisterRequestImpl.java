package me.vurt.nms.core.node.data.impl;

import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.data.RegisterRequest;

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
	/**
	 * 初始化注册请求
	 * @param node 要注册的节点
	 */
	public RegisterRequestImpl(Node node){
		this.id=node.getId();
		this.group=node.getGroup();
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
}
