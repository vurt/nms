package me.vurt.nms.core.jms.impl;

import javax.jms.JMSException;
import javax.jms.Message;

import me.vurt.nms.core.jms.MessageListener;

import org.springframework.jms.listener.adapter.MessageListenerAdapter;

/**
 * 消息监听器适配器，继承自{@link MessageListenerAdapter}
 * 
 * @author yanyl
 * 
 */
public class NMSMessageListenerAdapter extends MessageListenerAdapter {
	
	public NMSMessageListenerAdapter(Object delegate){
		super(delegate);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.jms.listener.adapter.MessageListenerAdapter#postProcessResponse(javax.jms.Message, javax.jms.Message)
	 */
	@Override
	protected void postProcessResponse(Message request, Message response)
			throws JMSException {
		super.postProcessResponse(request, response);

		if(getDelegate() instanceof MessageListener){
			MessageListener listener=(MessageListener)getDelegate();
			listener.postProcessResponse(request, response);
		}
	}
}
