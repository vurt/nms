package me.vurt.nms.core.jms.impl;

import java.util.Collection;

import me.vurt.nms.core.jms.MessageHandler;
import me.vurt.nms.core.jms.MessageListener;

/**
 * 消息处理器为静态(手动添加)的消息监听器
 * @author yanyl
 * 
 */
public class StaticMessageListener extends MessageListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.jms.MessageListener#getValidHandlers()
	 */
	@Override
	protected Collection<MessageHandler> getValidHandlers() {
			return handlers.values();
	}
	
	/* (non-Javadoc)
	 * @see me.vurt.nms.core.jms.MessageListener#addMessageHandler(me.vurt.nms.core.jms.MessageHandler)
	 */
	@Override
	public void addMessageHandler(MessageHandler handler) {
		super.addMessageHandler(handler);
	}
	
	/* (non-Javadoc)
	 * @see me.vurt.nms.core.jms.MessageListener#removeHandler(java.lang.String)
	 */
	@Override
	public void removeHandler(String id) {
		super.removeHandler(id);
	}
}
