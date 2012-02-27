package me.vurt.nms.core.node.server.dao;

import me.vurt.nms.core.node.Node;

import org.nutz.dao.Dao;
import org.nutz.service.NameEntityService;

/**
 * @author yanyl
 *
 */
public class NodeService extends NameEntityService<Node> {
	@SuppressWarnings("unchecked")
	/**
	 * 初始化节点Service
	 * @param dao 
	 * @param clazz 节点接口实现类
	 */
	public NodeService(Dao dao,Class<? extends Node> clazz){
		super(dao, (Class<Node>)clazz);
	}
	
	/**
	 * 向数据库插入数据
	 * @param node
	 */
	public void insert(Node node){
		dao().insert(node);
	}
	
}
