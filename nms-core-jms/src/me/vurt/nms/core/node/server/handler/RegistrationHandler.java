package me.vurt.nms.core.node.server.handler;

import me.vurt.nms.core.jms.MessageHandler;

public class RegistrationHandler implements MessageHandler{

	@Override
	public String getId() {
		return "registrationHandler";
	}

	@Override
	public Object handle(Object msg) {
		return null;
	}

}
