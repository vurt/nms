package me.vurt.nms.core.common.tools;

import me.vurt.nms.core.Constants;
import me.vurt.nms.core.common.properties.PropertiesManager;

public class ConfigReader{
	
	private static PropertiesManager configManager=new PropertiesManager(GlobalConfigFileReader.getConfigFile(Constants.CONFIG_FILE_PATH).toURI());
	
	/**
	 * 获取客户端节点的属性文件管理器
	 * @return
	 */
	public static PropertiesManager getConfigFileManager(){
		return configManager;
	}
	
	
	public static  boolean debugMode(){
		String debug=configManager.read(Constants.CONFIG_DEBUG_MODE);
		return Boolean.valueOf(debug);
	}
	
	/**
	 * 读取心跳间隔时间
	 * @return
	 */
	public static long getHeartBeatRepeatInterval(){
		String interval=configManager.read(Constants.CONFIG_HEARTBEAT_REPEAT_INTERVAL);
		return Long.valueOf(interval);
	}
	
	/**
	 * 判断当前节点是不是客户端，是不是客户端是系统属性nms.node.type决定的，只有该属性值为client时认为是客户端
	 * @return 当前节点是否客户端
	 */
	public static boolean isClient(){
		String type=System.getProperty(Constants.SYS_PROPERTY_NODE_TYPE);
		return Constants.NODE_TYPE_CLIENT.equals(type);
	}

	/**
	 * 读取配置项
	 * @param proeprtyName 配置属性名称
	 * @return 属性值，没有改属性则返回null
	 */
	public static String getConfig(String proeprtyName) {
		return configManager.read(proeprtyName);
	}

}
