package me.vurt.nms.core.jms;

import java.io.File;

import javax.jms.Destination;

import me.vurt.nms.core.data.Response;
import me.vurt.nms.core.exception.InvalidConfigException;
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
	 * 设置消息存活时间
	 * @param time
	 */
	void setTimeToLive(long time) ;

	/**
	 * 设置消息是否持久化
	 * 
	 * @param deliveryMode
	 */
	void setDeliveryPersistent(boolean deliveryPersistent) ;
	
	/**
	 *	接受响应的超时时间
	 * @param time
	 */
	void setReceiveTimeout(long timeout);
	
	/**
	 * 发送消息至默认{@link Destination 目的地}，消息可以是可序列化的普通对象或{@link File 文件}，默认目的地在jmsTemplate中指定，如果不手动指定JmsTemplate，则无法使用该方法
	 * 
	 * @param message 消息内容，如果是{@link File}类型，发送该文件，如果是其他类型则直接发送该对象
	 * @throws MessageSendFailedException 消息发送失败
	 * @throws InvalidConfigException 发送{@link File 文件}但没有配置文件服务器
	 */
	void send(Object message) throws MessageSendFailedException;

	/**
	 * 发送消息，消息可以是可序列化的普通对象或{@link File 文件}
	 * @param destination 目的地
	 * @param message 消息内容，如果是{@link File}类型，发送该文件，如果是其他类型则直接发送该对象
	 * @throws MessageSendFailedException 消息发送失败
	 * @throws InvalidConfigException 发送{@link File 文件}但没有配置文件服务器
	 */
	void send(Destination destination, Object message) throws MessageSendFailedException;
	
	/**
	 * 发送消息，并阻塞线程直至收到响应或超时，消息可以是可序列化的普通对象或{@link File 文件}
	 * @param sendTo 信息要发送到的目的地
	 * @param receiveFrom 接收响应信息的目的地，该信息会被自动设置到发送的消息的JMS Reply-To属性中
	 * @param message 消息内容，如果是{@link File}类型，发送该文件，如果是其他类型则直接发送该对象
	 * @return 响应信息，如果接受响应超时则返回null
	 * @throws MessageSendFailedException 消息发送失败
	 * @throws MessageReceiveFailedException 响应消息接收失败
	 * @throws InvalidConfigException 发送{@link File 文件}但没有配置文件服务器
	 * @see #setReceiveTimeout(long)
	 */
	Response sendAndReceive(Destination sendTo,Destination receiveFrom,Object message) throws MessageSendFailedException,MessageReceiveFailedException;
}
