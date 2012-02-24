package me.vurt.nms.core.jms;

import javax.jms.Destination;

import me.vurt.nms.core.data.Response;
import me.vurt.nms.core.jms.exception.MessageReceiveFailedException;
import me.vurt.nms.core.jms.exception.MessageSendFailedException;

import org.springframework.jms.core.JmsTemplate;

/**
 * 消息发送者
 * @author yanyl
 * 
 */
public interface MessageProducer {
	/**
	 * 设置jmsTemplate
	 * @param jmsTemplate
	 */
	void setJmsTemplate(JmsTemplate jmsTemplate);
	
	/**
	 * 发送消息至默认{@link Destination 目的地}，默认目的地在jmsTemplate中指定，如果不手动指定JmsTemplate，则无法使用该方法
	 * 
	 * @param message 消息内容
	 * @throws MessageSendFailedException 消息发送失败
	 */
	void send(Object message) throws MessageSendFailedException;

	/**
	 * 发送消息
	 * @param destination 目的地
	 * @param message 消息内容
	 * @throws MessageSendFailedException 消息发送失败
	 */
	void send(Destination destination, Object message) throws MessageSendFailedException;
	
	/**
	 * 发送消息，并阻塞线程直至收到响应
	 * @param sendTo 信息要发送到的目的地
	 * @param receiveFrom 接收响应信息的目的地，该信息会被自动设置到发送的消息的JMS Reply-To属性中
	 * @param message 消息内容
	 * @return 响应信息
	 * @throws MessageSendFailedException 消息发送失败
	 * @throws MessageReceiveFailedException 响应消息接收失败
	 */
	Response sendAndReceive(Destination sendTo,Destination receiveFrom,Object message) throws MessageSendFailedException,MessageReceiveFailedException;
}
