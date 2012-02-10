package me.vurt.nms.core.jms;

import javax.jms.Destination;

/**
 * @author yanyl
 * 
 */
public interface MessageProducer {
	/**
	 * ������Ϣ��Ĭ��{@link Destination Ŀ�ĵ�}
	 * 
	 * @param message ��Ϣ����
	 */
	void send(Object message);

	/**
	 * ������Ϣ
	 * @param destination Ŀ�ĵ�
	 * @param message ��Ϣ����
	 */
	void send(Destination destination, Object message);
}
