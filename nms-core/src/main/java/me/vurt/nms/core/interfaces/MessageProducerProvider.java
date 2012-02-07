package me.vurt.nms.core.interfaces;

import javax.jms.MessageProducer;

/**
 * 消息制造者提供器
 * @author yanyl
 *
 */
public interface MessageProducerProvider {
	MessageProducer getMessageProducer();
}
