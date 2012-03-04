package me.vurt.nms.core.node.client;

import me.vurt.nms.core.common.properties.PropertiesManager;
import me.vurt.nms.core.common.tools.ConfigReader;
import me.vurt.nms.core.common.tools.IPParser;
import me.vurt.nms.core.node.Client;
import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.exception.InvalidNodeTypeException;
import me.vurt.nms.core.node.util.NodeConstants;

/**
 * 全局配置文件读取帮助类，也是节点在客户端的实现类
 * @author yanyl
 *
 */
public class ClientImpl implements Client{
	private static PropertiesManager configManager=ConfigReader.getConfigFileManager();
	
	private static Client instance;
	
	public static Client getInstance(){
		if(!ConfigReader.isClient()){
			throw new InvalidNodeTypeException("服务器节点不能获取客户端实例");
		}
		if(instance==null){
			instance=new ClientImpl();
		}
		return instance;
	}
	
	private ClientImpl(){
		
	}
	
	/**
	 * 获取当前机器的外网IP
	 * @return
	 */
	public String getHost(){
		return IPParser.getIP();
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

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.node.Node#getKey()
	 */
	@Override
	public String getKey() {
		return configManager.read(NodeConstants.PROPERTY_NODE_KEY);
	}
	
	/**
	 * 设置节点认证Key，请勿手动调用
	 * @param key
	 */
	public void setKey(String key){
		configManager.write(NodeConstants.PROPERTY_NODE_KEY, key);
	}
}
