package me.vurt.nms.core.node.server.handler;

import me.vurt.nms.core.data.RegisterRequest;
import me.vurt.nms.core.jms.MessageHandler;
import me.vurt.nms.core.node.server.exception.BadRequestException;

public class RegistrationHandler implements MessageHandler{

	@Override
	public String getId() {
		return "registrationHandler";
	}

	@Override
	public Object handle(Object msg) {
		if(!(msg instanceof RegisterRequest)){
			throw new BadRequestException("错误的注册请求类型："+msg.getClass().getName());
		}
		return  null;
	}

}
