package me.vurt.nms.core.jms;

import javax.jms.Destination;

/**
 * @author yanyl
 * 
 */
public interface MessageProducer {
	/**
	 * 发送消息至默认{@link Destination 目的地}
	 * 
	 * @param message 消息内容
	 */
	void send(Object message);

	/**
	 * 发送消息
	 * @param destination 目的地
	 * @param message 消息内容
	 */
	void send(Destination destination, Object message);
}
