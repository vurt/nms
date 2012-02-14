package me.vurt.nms.core.jms;

/**
 * @author yanyl
 *
 */
public interface MessageHandler {
	/**
	 * 返回处理器ID
	 * @return
	 */
	String getId();
	/**
	 * 处理消息
	 * @param msg
	 */
	void handle(Object msg);
}
