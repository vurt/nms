package me.vurt.nms.core.node.data.impl;

import java.util.HashMap;
import java.util.Map;

import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.data.HeartBeat;

/**
 * 
 * @author yanyl
 *
 */
public class HeartBeatImpl implements HeartBeat{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5059493683441434795L;
	
	/**
	 * 创建(发送)该心跳的时间
	 */
	private String heartBeatTime;
	
	/**
	 * 扩展数据，
	 */
	private Map<String, String> data=new HashMap<String, String>();
	
	private String group;
	
	private String id;
	
	private String ip;
	
	public HeartBeatImpl(Node node){
		group=node.getGroup();
		id=node.getId();
		ip=node.getHost();
	}
	
	public String getIp(){
		return ip;
	}

	/**
	 * @param heartBeatTime the heartBeatTime to set
	 */
	public void setHeartBeatTime(String heartBeatTime) {
		this.heartBeatTime = heartBeatTime;
	}

	public String getGroup() {
		return group;
	}

	public String getId() {
		return id;
	}

	public String getHeartBeatTime() {
		return heartBeatTime;
	}

	public Map<String, String> getData() {
		return data;
	}
	
	/**
	 * 将数据添加到心跳的扩展数据Map中去，如果对应key的value已存在则直接覆盖
	 * @param otherData 要添加的数据
	 */
	public void addExtendData(Map<String, String> otherData){
		data.putAll(otherData);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HeartBeatImpl [ip=" + getIp() + ", group=" + getGroup() + ", id=" + getId()
				+ ", heartBeatTime=" + heartBeatTime + ", data=" + data.toString() + "]";
	}
}

