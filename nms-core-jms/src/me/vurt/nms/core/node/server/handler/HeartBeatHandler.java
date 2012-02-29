package me.vurt.nms.core.node.server.handler;

import java.util.Map;

import me.vurt.nms.core.data.HeartBeat;
import me.vurt.nms.core.jms.MessageHandler;
import me.vurt.nms.core.node.server.dao.DAOUtil;
import me.vurt.nms.core.node.server.dao.NodeService;
import me.vurt.nms.core.node.server.dao.TNode;

/**
 * @author yanyl
 * 
 */
public class HeartBeatHandler implements MessageHandler {
	public static final String ID = "heartBeatHandler";

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.jms.MessageHandler#getId()
	 */
	@Override
	public String getId() {
		return ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.jms.MessageHandler#handle(java.lang.Object)
	 */
	@Override
	public Map<String, Object> handle(Object msg) {
		if (msg instanceof HeartBeat) {
			HeartBeat heartBeat = (HeartBeat) msg;

			NodeService service = DAOUtil.getNodeService();
			TNode node=(TNode)service.fetch(heartBeat.getId());
			node.setLastHeartBeat(heartBeat.getHeartBeatTime());
			node.setHost(heartBeat.getIp());
			service.dao().update(node);
		}
		return null;
	}

}
