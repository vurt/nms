package me.vurt.nms.core;


/**
 * 配置的读取类
 * @author Vurt
 *
 */
public interface CoreService {
	
	boolean debugMode();
	
	/**
	 * 读取心跳间隔时间
	 * @return
	 */
	long getHeartBeatRepeatInterval();
	
	/**
	 * 判断当前节点是不是客户端，是不是客户端是系统属性nms.node.type决定的，只有该属性值为client时认为是客户端
	 * @return 当前节点是否客户端
	 */
	boolean isClient();
	
	/**
	 * 读取配置项
	 * @param proeprtyName 配置属性名称
	 * @return 属性值，没有改属性则返回null
	 */
	String getConfig(String proeprtyName);
}
