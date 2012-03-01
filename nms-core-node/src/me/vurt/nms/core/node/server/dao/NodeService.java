package me.vurt.nms.core.node.server.dao;

import java.util.Collection;

import me.vurt.nms.core.node.Node;

import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
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
	
	/**
	 * 获取所有节点
	 * @return
	 */
	public Collection<Node> getAllNodes(){
		return query(null, null);
	}
	
	
	/**
	 * 分页查询节点
	 * @param pageNumber 第几页，从1开始
	 * @param pageSize 每页最多有多少天记录
	 * @return
	 */
	public Collection<Node> getNodes(int pageNumber,int pageSize){
		Pager pager=dao().createPager(pageNumber, pageSize);
		return query(null, pager);
	}
	
}
