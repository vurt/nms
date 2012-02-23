package me.vurt.nms.core.node.util;

import me.vurt.nms.core.common.properties.PropertiesManager;
import me.vurt.nms.core.common.tools.GlobalConfigFileReader;

/**
 * 全局配置文件的读取类
 * @author Vurt
 *
 */
public class GlobalConfigReader {
	private static PropertiesManager configManager=new PropertiesManager(GlobalConfigFileReader.getConfigFile(NodeConstants.CONFIG_FILE_PATH).toURI());
	
	/**
	 * 获取客户端节点的属性文件管理器
	 * @return
	 */
	public static PropertiesManager getPropertiesManager(){
		return configManager;
	}
	
	public static boolean debugMode(){
		String debug=configManager.read(NodeConstants.PROPERTY_DEBUG_MODE);
		return Boolean.valueOf(debug);
	}
	
	/**
	 * 判断当前节点是不是客户端，是不是客户端是系统属性nms.node.type决定的，只有该属性值为client时认为是客户端
	 * @return 当前节点是否客户端
	 */
	public static boolean isClient(){
		String type=System.getProperty(NodeConstants.SYS_PROPERTY_NODE_TYPE);
		return NodeConstants.NODE_TYPE_CLIENT.equals(type);
	}
}
