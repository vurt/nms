package me.vurt.nms.core.data;

import java.util.Map;

/**
 * 心跳信息接口
 * @author yanyl
 * 
 */
public interface HeartBeat extends Data {
	
	/**
	 * @return 节点IP地址
	 */
	public String getIp();
	
	/**
	 * @return 节点所属分组
	 */
	public String getGroup();

	/**
	 * @return 节点ID
	 */
	public String getId();

	/**
	 * @return 心跳信息发送的时间
	 */
	public String getHeartBeatTime();

	/**
	 * @return 随心跳发送回来的扩展数据
	 */
	public Map<String, String> getData();
}
