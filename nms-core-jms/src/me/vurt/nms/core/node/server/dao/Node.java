package me.vurt.nms.core.node.server.dao;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 节点数据POJO
 * @author Vurt
 *
 */
@Table("node")
public class Node {

	/**
	 * 节点ID，主键，唯一
	 */
	@Column
	@Name(casesensitive=false)
	private String id;
	
	/**
	 * 节点所在分组名称
	 */
	@Column
	private String group;
	
	/**
	 * 节点的ip地址，随节点心跳传递回来
	 */
	@Column
	private String ip;
	
	/**
	 * 最后一次心跳的时间
	 */
	@Column
	private String lastHeartBeat;
	
	/**
	 * 节点描述
	 */
	@Column
	private String desc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLastHeartBeat() {
		return lastHeartBeat;
	}

	public void setLastHeartBeat(String lastHeartBeat) {
		this.lastHeartBeat = lastHeartBeat;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
