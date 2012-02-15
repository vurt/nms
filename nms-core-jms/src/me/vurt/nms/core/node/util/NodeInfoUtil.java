package me.vurt.nms.core.node.util;

import me.vurt.nms.core.common.properties.PropertiesManager;
import me.vurt.nms.core.common.tools.GlobalConfigFileReader;

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
}
