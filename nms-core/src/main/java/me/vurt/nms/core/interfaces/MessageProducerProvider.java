package me.vurt.nms.core.interfaces;

import javax.jms.MessageProducer;

/**
 * ��Ϣ�������ṩ��
 * @author yanyl
 *
 */
public interface MessageProducerProvider {
	MessageProducer getMessageProducer();
}
