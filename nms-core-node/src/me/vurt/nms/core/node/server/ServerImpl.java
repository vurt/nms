package me.vurt.nms.core.node.server;

import java.util.Collection;

import me.vurt.nms.core.common.tools.ConfigReader;
import me.vurt.nms.core.common.tools.IPParser;
import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.Server;
import me.vurt.nms.core.node.exception.InvalidNodeTypeException;
import me.vurt.nms.core.node.server.dao.DAOUtil;
import me.vurt.nms.core.node.server.dao.NodeService;

public class ServerImpl implements Server {
	
	private static Server instance;
	
	public static Server getInstance(){
		if(ConfigReader.isClient()){
			throw new InvalidNodeTypeException("客户端节点不能获取服务器实例");
		}
		if(instance==null){
			instance=new ServerImpl();
		}
		return instance;
	}
	
	private NodeService service;
	
	private ServerImpl(){
		service=DAOUtil.getNodeService();
	}

	@Override
	public Collection<Node> getAllNodes() {
		return service.getAllNodes();
	}

	@Override
	public Collection<Node> getNodes(int pageNumber, int pageSize) {
		return service.getNodes(pageNumber, pageSize);
		
	}

	@Override
	public String getGroup() {
		return "";
	}

	@Override
	public String getId() {
		return "Server";
	}

	@Override
	public String getHost() {
		return IPParser.getIP();
	}

	@Override
	public String getKey() {
		return null;
	}

}
