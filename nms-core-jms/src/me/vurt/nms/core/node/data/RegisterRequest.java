package me.vurt.nms.core.node.data;

import java.io.Serializable;

import me.vurt.nms.core.node.util.NodeInfoReader;

/**
 * 节点注册请求，初始化时默认包含了当前节点的信息
 * @author yanyl
 *
 */
public class RegisterRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6882852395914960576L;

	/**
	 * 节点ID
	 */
	private String id=NodeInfoReader.getNodeID();
	
	/**
	 * 节点分组
	 */
	private String group=NodeInfoReader.getNodeGroup();
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}
}
