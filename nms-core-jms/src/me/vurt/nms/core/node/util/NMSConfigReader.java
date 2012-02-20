package me.vurt.nms.core.node.util;

import me.vurt.nms.core.common.properties.PropertiesManager;
import me.vurt.nms.core.common.tools.GlobalConfigFileReader;
import me.vurt.nms.core.exception.UnsupportedNodeType;
import me.vurt.nms.core.node.NodeType;

/**
 * 全局配置文件读取帮助类
 * @author yanyl
 *
 */
public class NMSConfigReader {
	private static PropertiesManager configManager=new PropertiesManager(GlobalConfigFileReader.getConfigFile(NodeConstants.CONFIG_FILE_PATH).toURI());
	
	/**
	 * 获取系统的属性文件管理器
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
	 * 获取当前节点所属的分组名
	 * @return
	 */
	public static String getNodeGroup(){
		return configManager.read(NodeConstants.PROPERTY_NODE_GROUP);
	}
	
	/**
	 * 获取当前节点的ID
	 * @return
	 */
	public static String getNodeID(){
		return configManager.read(NodeConstants.PROPERTY_NODE_ID);
	}
	
	/**
	 * 当前节点的节点类型
	 * @return
	 */
	public static NodeType getNodeType(){
		String type=System.getProperty(NodeConstants.SYS_PROPERTY_NODE_TYPE);
		if(NodeConstants.NODE_TYPE_SERVER.equals(type)){
			return NodeType.Sever;
		}else if(NodeConstants.NODE_TYPE_CLIENT.equals(type)){
			return NodeType.Client;
		}
		throw new UnsupportedNodeType(type);
	}
}
