package me.vurt.nms.core.jms.impl;

import org.springframework.jms.listener.adapter.MessageListenerAdapter;

/**
 * ��Ϣ���������������̳���{@link MessageListenerAdapter}����Ϊ������getDelegate()����
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
