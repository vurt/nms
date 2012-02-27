package me.vurt.nms.core.node.server.dao;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.vurt.nms.core.node.Node;

/**
 * @author yanyl
 * 
 */
public class NodeFactory {
	public static final Logger LOGGER=LoggerFactory.getLogger(NodeFactory.class);
	
	/**
	 * 创建一个节点，并自动为其分配一个唯一的{@link Node#getKey() Key}
	 * 
	 * @param id 节点id
	 * @param group 节点所属分组
	 * @param host 节点访问地址
	 * @return 节点对象
	 */
	public static Node createNode(String id, String group, String host) {
		String key = UUID.randomUUID().toString();
		LOGGER.debug("创建新节点"+id+"，为其自动分配了key:"+key);
		return new TNode(group, id, host, key);
	}
}
