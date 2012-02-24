package me.vurt.nms.core.data;

import java.util.Map;

/**
 * 心跳信息扩展接口，如果想要在节点心跳信息中添加自定义的数据，只要将一个实现了该接口的对象的实例注册为服务即可
 * @author yanyl
 *
 */
public interface HeartBeatExtender {
	/**
	 * 获取所有要附加到心跳信息中传回服务器的数据，每次心跳时都会被调用
	 * @return 所有要附加到心跳信息中传回服务器的数据
	 */
	public Map<String, String> getExtendData();
}
