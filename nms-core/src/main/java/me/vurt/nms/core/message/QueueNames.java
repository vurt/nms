package me.vurt.nms.core.message;

/**
 * 节点管理系统客户端和服务端通讯时使用的消息队列名称
 * @author yanyl
 *
 */
public interface QueueNames {
	/**
	 * 客户端向服务端发送心跳信息时使用的消息队列的名称，
	 */
	String HEARTBEAT="heartBeat";
}
