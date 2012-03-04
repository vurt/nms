package me.vurt.nms.core.node;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 服务端节点
 * @author Vurt
 *
 */
public interface Server extends Node{
	public static final Logger LOGGER=LoggerFactory.getLogger(Server.class);
	
	/**
	 * 获取所有节点
	 * @return
	 */
	Collection<Node> getAllNodes();
	
	/**
	 * 分页查询节点
	 * @param pageNumber 第几页，从1开始
	 * @param pageSize 每页最多有多少条记录
	 * @return
	 */
	Collection<Node> getNodes(int pageNumber,int pageSize);
	
}
