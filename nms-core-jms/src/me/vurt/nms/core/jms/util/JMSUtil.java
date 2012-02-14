package me.vurt.nms.core.jms.util;

import me.vurt.nms.core.jms.MessageListener;
import me.vurt.nms.core.jms.impl.MessageListenerAdapter2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * @author yanyl
 * 
 */
public class JMSUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JMSUtil.class);

	public static MessageListener getInnerListener(
			DefaultMessageListenerContainer container) {
		Object temp = container.getMessageListener();
		if (temp instanceof MessageListenerAdapter2) {
			return (MessageListener) ((MessageListenerAdapter2) temp)
					.getDelegate();
		} else {
			LOGGER.error("不支持的监听器类型");
			return null;
		}
	}
}
