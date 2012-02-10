package me.vurt.nms.core.jms.impl;

import org.springframework.jms.listener.adapter.MessageListenerAdapter;

/**
 * 消息监听器适配器，继承自{@link MessageListenerAdapter}，仅为开放其getDelegate()方法
 * 
 * @author yanyl
 * 
 */
public class MessageListenerAdapter2 extends MessageListenerAdapter {
	public MessageListenerAdapter2() {
		super();
	}

	public MessageListenerAdapter2(Object delegate) {
		super(delegate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.jms.listener.adapter.MessageListenerAdapter#getDelegate
	 * ()
	 */
	@Override
	public Object getDelegate() {
		return super.getDelegate();
	}
}
