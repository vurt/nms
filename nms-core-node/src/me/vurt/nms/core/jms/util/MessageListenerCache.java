package me.vurt.nms.core.jms.util;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;

import me.vurt.nms.core.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.util.Assert;

/**
 * 监听容器和监听器的缓存
 * @author yanyl
 * 
 */
public class MessageListenerCache {
	private static final Logger LOGGER=LoggerFactory.getLogger(MessageListenerCache.class);
	/**
	 * 储存目的地和监听器容器的Map
	 */
	private static Map<Destination, DefaultMessageListenerContainer> containerMap = new HashMap<Destination, DefaultMessageListenerContainer>();

	/**
	 * 储存目的地和监听器的Map
	 */
	private static Map<Destination, MessageListener> listenerMap = new HashMap<Destination, MessageListener>();

	/**
	 * 是否存在该目的地的监听器
	 * 
	 * @param destination
	 * @return
	 */
	public static boolean hasListener(Destination destination) {
		return containerMap.containsKey(destination);
	}

	/**
	 * 获取已缓存的消息监听器
	 * 
	 * @param destination
	 * @return
	 */
	public static MessageListener getMessageListener(Destination destination) {
		if (hasListener(destination)) {
			LOGGER.debug("从缓存读取"+destination.toString()+"的监听器");
			return listenerMap.get(destination);
		}
		return null;
	}

	/**
	 * 储存消息监听器容器
	 * 
	 * @param container
	 */
	public static void put(DefaultMessageListenerContainer container,MessageListener listener) {
		Assert.notNull(container,"缓存的监听容器不能为空");
		Assert.notNull(listener,"缓存的监听器不能为空");
		containerMap.put(container.getDestination(), container);
		listenerMap.put(container.getDestination(), listener);
		LOGGER.debug("将目标地址:"+container.getDestination().toString()+"的监听器放入缓存");
	}

	public static void startContainer(Destination destination) {
		if (hasListener(destination)) {
			DefaultMessageListenerContainer container=containerMap.get(destination);
			container.initialize();
			container.start();
		}
	}

	/**
	 * 关闭对指定目的地的监听器
	 * 
	 * @param destination
	 *            目的地
	 */
	public static void stopContainer(Destination destination) {
		if (hasListener(destination)) {
			containerMap.get(destination).stop();
		}
	}

}
