package me.vurt.nms.core.node.util;

import me.vurt.nms.core.common.properties.PropertiesManager;
import me.vurt.nms.core.common.tools.GlobalConfigFileReader;
import me.vurt.nms.core.exception.UnsupportedNodeType;
import me.vurt.nms.core.node.NodeType;

/**
 * @author yanyl
 *
 */
public class NodeInfoUtil {
	private static PropertiesManager NodePropertiesManager=new PropertiesManager(GlobalConfigFileReader.getConfigFile("nms-Config.properties").toURI());
	
	/**
	 * 获取当前节点所属的分组名
	 * @return
	 */
	public static String getNodeGroup(){
		return NodePropertiesManager.read("nms.node.group");
	}
	
	/**
	 * 获取当前节点的ID
	 * @return
	 */
	public static String getNodeID(){
		return NodePropertiesManager.read("nms.node.id");
	}
	
	/**
	 * 当前节点的节点类型
	 * @return
	 */
	public static NodeType getNodeType(){
		String type=System.getProperty(NodeConstants.NODE_TYPE_PROPERTY);
		if(NodeConstants.NODE_TYPE_SERVER.equals(type)){
			return NodeType.Sever;
		}else if(NodeConstants.NODE_TYPE_CLIENT.equals(type)){
			return NodeType.Client;
		}
		throw new UnsupportedNodeType(type);
	}
}
