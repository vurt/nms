package me.vurt.nms.core.node.data.impl;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.vurt.nms.core.node.data.HeartBeat;
import me.vurt.nms.core.node.util.NodeInfoUtil;

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
	 * 节点IP地址
	 */
	private String ip;
	/**
	 * 节点分组
	 */
	private String group;
	/**
	 * 节点ID
	 */
	private String id;
	
	/**
	 * 创建(发送)该心跳的时间
	 */
	private String heartBeatTime;
	
	/**
	 * 扩展数据，
	 */
	private Map<String, String> data;
	
	private static final DateFormat DATE_FORMAT=DateFormat.getDateInstance();
	
	public HeartBeatImpl(){
		data=new HashMap<String, String>();
		heartBeatTime=DATE_FORMAT.format(new Date());
		group=NodeInfoUtil.getNodeGroup();
		id=NodeInfoUtil.getNodeID();
	}
	
	public String getIP(){
		//TODO:准确的外网IP
		return ip;
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
		return "HeartBeatImpl [ip=" + ip + ", group=" + group + ", id=" + id
				+ ", heartBeatTime=" + heartBeatTime + ", data=" + data.toString() + "]";
	}
	
	
}

