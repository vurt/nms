package me.vurt.nms.core.node.server.handler;

import me.vurt.nms.core.jms.MessageHandler;

public class RegistrationHandler implements MessageHandler{

	@Override
	public String getId() {
		return "registrationHandler";
	}

	@Override
	public Object handle(Object msg) {
		LOGGER.debug("处理了一条注册申请");
		return null;
	}

}
