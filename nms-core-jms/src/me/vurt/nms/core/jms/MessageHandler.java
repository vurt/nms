package me.vurt.nms.core.jms;

/**
 * @author yanyl
 *
 */
public interface MessageHandler {
	/**
	 * ���ش�����ID
	 * @return
	 */
	String getId();
	/**
	 * ������Ϣ
	 * @param msg
	 */
	void handle(Object msg);
}
