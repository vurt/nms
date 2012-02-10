package me.vurt.nms.core.jms.util;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;

import me.vurt.nms.core.jms.MessageListener;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * @author yanyl
 * 
 */
public class MessageListenerCache {
	/**
	 * ����Ŀ�ĵغͼ�������Map
	 */
	private static Map<Destination, DefaultMessageListenerContainer> listenerMap = new HashMap<Destination, DefaultMessageListenerContainer>();

	/**
	 * �Ƿ���ڸ�Ŀ�ĵصļ�����
	 * 
	 * @param destination
	 * @return
	 */
	public static boolean hasListener(Destination destination) {
		return listenerMap.containsKey(destination);
	}

	/**
	 * ��ȡ�ѻ������Ϣ������
	 * @param destination
	 * @return
	 */
	public static MessageListener getCachedMessageListener(
			Destination destination) {
		return JMSUtil.getInnerListener(listenerMap.get(destination));
	}
	
	/**
	 * ������Ϣ����������
	 * @param container
	 */
	public static void put(DefaultMessageListenerContainer container){
		listenerMap.put(container.getDestination(), container);
	}
	
	/**
	 * �رն�ָ��Ŀ�ĵصļ�����
	 * @param destination Ŀ�ĵ�
	 */
	public static void stopContainer(Destination destination){
		if(hasListener(destination)){
			getCachedMessageListener(destination).stop();
		}
	}

}
