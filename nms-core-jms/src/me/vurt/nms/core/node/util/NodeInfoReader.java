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
public class NodeInfoReader {
	private static PropertiesManager configManager=new PropertiesManager(GlobalConfigFileReader.getConfigFile(NodeConstants.CONFIG_FILE_PATH).toURI());
	
	/**
	 * 判断当前节点是不是客户端，是不是客户端是系统属性nms.node.type决定的，只有该属性值为client时认为是客户端
	 * @return 当前节点是否客户端
	 */
	public static boolean isClient(){
		String type=System.getProperty(NodeConstants.SYS_PROPERTY_NODE_TYPE);
		return NodeConstants.NODE_TYPE_CLIENT.equals(type);
	}
	
	/**
	 * 当前节点是否已注册
	 * @return
	 */
	public static boolean isRegisted(){
		return false;
	}
	
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
	 * 获取当前节点所属的分组名，如果配置文件中没配该属性则抛出运行时异常
	 * @return 分组名
	 */
	public static String getNodeGroup(){
		String group= configManager.read(NodeConstants.PROPERTY_NODE_GROUP);
		if(group==null){
			throw new RuntimeException("节点没有配置"+NodeConstants.PROPERTY_NODE_GROUP+"属性，无法获取");
		}
		return group;
	}
	
	/**
	 * 获取当前节点的ID，如果配置文件中没配该属性则抛出运行时异常
	 * @return
	 */
	public static String getNodeID(){
		String id= configManager.read(NodeConstants.PROPERTY_NODE_ID);
		if(id==null){
			throw new RuntimeException("节点没有配置"+NodeConstants.PROPERTY_NODE_ID+"属性，无法获取");
		}
		return id;
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
