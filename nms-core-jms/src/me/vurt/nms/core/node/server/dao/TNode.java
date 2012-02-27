package me.vurt.nms.core.node.server.dao;

import me.vurt.nms.core.node.Node;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 节点信息表映射的数据对象POJO
 * @author Vurt
 *
 */
@Table("node")
public class TNode implements Node{

	/**
	 * 节点ID，主键，唯一
	 */
	@Column("node_id")
	@Name(casesensitive=false)
	private String id;
	
	/**
	 * 节点所在分组名称
	 */
	@Column("node_group")
	private String group;
	
	/**
	 * 节点的访问地址，随节点心跳传递回来
	 */
	@Column
	private String host;
	
	/**
	 * 节点注册成功时，服务器分配给节点的唯一的key，用于节点请求连接时验证
	 */
	@Column("node_key")
	private String key;
	
	/**
	 * 最后一次心跳的时间
	 */
	@Column
	private String lastHeartBeat;
	
	public TNode(){}
	
	public TNode(String group,String id,String host,String key){
		setGroup(group);
		setId(id);
		setHost(host);
		setKey(key);
	}
	
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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
	
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
