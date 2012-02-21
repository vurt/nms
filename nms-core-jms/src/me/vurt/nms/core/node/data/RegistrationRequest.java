package me.vurt.nms.core.node.data;

/**
 * 节点注册请求
 * @author yanyl
 *
 */
public class RegistrationRequest {
	/**
	 * 节点ID
	 */
	private String id;
	
	/**
	 * 节点分组
	 */
	private String group;

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
