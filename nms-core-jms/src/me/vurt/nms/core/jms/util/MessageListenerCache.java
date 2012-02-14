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
	 * 储存目的地和监听器的Map
	 */
	private static Map<Destination, DefaultMessageListenerContainer> listenerMap = new HashMap<Destination, DefaultMessageListenerContainer>();

	/**
	 * 是否存在该目的地的监听器
	 * 
	 * @param destination
	 * @return
	 */
	public static boolean hasListener(Destination destination) {
		return listenerMap.containsKey(destination);
	}

	/**
	 * 获取已缓存的消息监听器
	 * @param destination
	 * @return
	 */
	public static MessageListener getCachedMessageListener(
			Destination destination) {
		return JMSUtil.getInnerListener(listenerMap.get(destination));
	}
	
	/**
	 * 储存消息监听器容器
	 * @param container
	 */
	public static void put(DefaultMessageListenerContainer container){
		listenerMap.put(container.getDestination(), container);
	}
	
	/**
	 * 关闭对指定目的地的监听器
	 * @param destination 目的地
	 */
	public static void stopContainer(Destination destination){
		if(hasListener(destination)){
			getCachedMessageListener(destination).stop();
		}
	}

}
