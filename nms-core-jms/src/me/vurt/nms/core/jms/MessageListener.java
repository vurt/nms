package me.vurt.nms.core.jms;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.jms.Destination;

import me.vurt.nms.core.jms.util.MessageListenerCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yanyl
 * 
 */
public class MessageListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageListener.class);

	private static int counter = 0;

	/**
	 * ���������
	 */
	private int index = 0;

	public MessageListener() {
		addCounter();
		index = counter;
	}

	/**
	 * �������½�����������
	 */
	private synchronized void addCounter() {
		counter++;
	}

	private Destination destination = null;

	/**
	 * ���ü�����Ŀ�ĵأ�������ȡ���ֶ����ò������޸ļ�������
	 * 
	 * @param destination
	 */
	public void setDestination(Destination destination) {
		if (this.destination != null) {
			LOGGER.error("��֧���޸ļ���Ŀ��");
		} else {
			this.destination = destination;
			LOGGER.info(index + " - ��ʼ����" + destination.toString());
		}
	}

	/**
	 * ��Ϣ������Map��Key�Ǵ�����id��Value�Ǵ�����ʵ��
	 */
	private Map<String, MessageHandler> handlers = new LinkedHashMap<String, MessageHandler>();
	
	/**
	 * ��Ϣ�������ķ�����
	 */
	public static final String MESSAGE_HANDLE_MOTHED_NAME="messageReceived";

	public void messageReceived(Object msg) {
		LOGGER.debug("Received one msg:" + msg.toString());
		if (handlers.size() == 0) {
			LOGGER.warn("û�������κδ�����");
		}
		for (MessageHandler handler : handlers.values()) {
			handler.handle(msg);
		}
	}

	/**
	 * ��Ӵ�����
	 * 
	 * @param handler
	 */
	public void addMessageHandler(MessageHandler handler) {
		handlers.put(handler.getId(), handler);
	}

	/**
	 * ɾ��������
	 * 
	 * @param id
	 *            ������id
	 */
	public void removeHandler(String id) {
		if (handlers.containsKey(id)) {
			handlers.remove(id);
		}
	}

	/**
	 * �رռ�����
	 */
	public void stop() {
		MessageListenerCache.stopContainer(destination);
		LOGGER.info(index + " - ��ֹͣ����:" + destination.toString());
	}
}
