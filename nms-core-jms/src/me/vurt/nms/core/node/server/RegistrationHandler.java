package me.vurt.nms.core.node.server;

import me.vurt.nms.core.jms.MessageHandler;

public class RegistrationHandler implements MessageHandler{

	@Override
	public String getId() {
		return "registrationHandler";
	}

	@Override
	public void handle(Object msg) {
		LOGGER.debug("处理了一条注册申请");
	}

}
