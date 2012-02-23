package me.vurt.nms.core.node.client;

import me.vurt.nms.core.common.properties.PropertiesManager;
import me.vurt.nms.core.common.tools.GlobalConfigFileReader;
import me.vurt.nms.core.common.tools.IPParser;
import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.util.GlobalConfigReader;
import me.vurt.nms.core.node.util.NodeConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 全局配置文件读取帮助类，也是节点在客户端的实现类
 * @author yanyl
 *
 */
public class ClientNode implements Node{
	private static final Logger LOGGER=LoggerFactory.getLogger(ClientNode.class);
	private static PropertiesManager configManager=GlobalConfigReader.getPropertiesManager();
	
	/**
	 * 获取当前机器的外网IP
	 * @return
	 */
	public String getHost(){
		return IPParser.getIP();
	}
	
	/**
	 * 当前节点是否已注册
	 * @return
	 */
	public boolean isRegisted(){
		LOGGER.info("当前节点未注册");
		return false;
	}
	
	/**
	 * 获取当前节点所属的分组名，如果配置文件中没配该属性则抛出运行时异常
	 * @return 分组名
	 */
	public String getGroup(){
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
	public String getId(){
		String id= configManager.read(NodeConstants.PROPERTY_NODE_ID);
		if(id==null){
			throw new RuntimeException("节点没有配置"+NodeConstants.PROPERTY_NODE_ID+"属性，无法获取");
		}
		return id;
	}
}
