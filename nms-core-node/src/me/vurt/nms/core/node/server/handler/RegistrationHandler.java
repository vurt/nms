package me.vurt.nms.core.node.server.handler;

import java.util.HashMap;
import java.util.Map;

import me.vurt.nms.core.jms.MessageHandler;
import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.data.RegisterRequest;
import me.vurt.nms.core.node.exception.BadRequestException;
import me.vurt.nms.core.node.exception.NodeAlreadyExistException;
import me.vurt.nms.core.node.server.dao.DAOUtil;
import me.vurt.nms.core.node.server.dao.NodeFactory;
import me.vurt.nms.core.node.server.dao.NodeService;
import me.vurt.nms.core.node.util.NodeConstants;

public class RegistrationHandler implements MessageHandler<RegisterRequest> {

	@Override
	public String getId() {
		return "registrationHandler";
	}

	@Override
	public Map<String, Object> handle(RegisterRequest msg)
			throws NodeAlreadyExistException, BadRequestException {
		if (!(msg instanceof RegisterRequest)) {
			throw new BadRequestException("错误的注册请求类型："
					+ msg.getClass().getName());
		}
		RegisterRequest request = (RegisterRequest) msg;
		NodeService service = DAOUtil.getNodeService();
		Node node = service.fetch(request.getId());
		if (node != null) {
			if (!node.getKey().equals(request.getKey())) {
				LOGGER.debug("数据库中节点" + node.getId() + "的key是" + node.getKey()
						+ "，连接请求的key是" + request.getKey());
				// 一个ID的节点只允许注册一次
				throw new NodeAlreadyExistException(request.getId());
			}
			// TODO:成功连接后要做一些处理？
		} else {
			// 第一次连接
			node = NodeFactory.createNode(request.getId(), request.getGroup(),
					request.getHost());
			service.insert(node);
		}
		// 节点每次连接，服务器都会将Key送回去，下次连接将拿本次连接返回的key来验证，以便日后有可能需要修改key的生成规则
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(NodeConstants.RESPONSE_NODE_KEY, node.getKey());
		return response;
	}

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.jms.MessageHandler#getMessageType()
	 */
	@Override
	public Class<RegisterRequest> getMessageType() {
		return RegisterRequest.class;
	}

}
