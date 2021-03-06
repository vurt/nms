package me.vurt.nms.core.data;

import java.util.Map;

/**
 * 响应消息
 * @author yanyl
 *
 */
public interface Response extends Data{
	/**
	 * 响应是否为空，为空(返回true)则不发送响应
	 * @return
	 */
	boolean isEmpty();
	
	/**
	 * 添加错误信息
	 * @param handlerID
	 * @param error
	 */
	void addError(String handlerID,String error);
	
	/**
	 * 获取响应中的错误信息
	 * @return 响应错误信息Map，Key是{@link MessageHandler#getId() 消息处理器ID}，Value是错误信息
	 */
	Map<String, String> getErrors();
	
	/**
	 * 添加多条响应数据
	 * @param respones
	 */
	void addResponses(Map<String, Object> responses);
	
	/**
	 * 添加单条响应
	 * @param key 单条响应结果的Key，任何字符串皆可，如果多个处理器返回相同key的响应，则只有一条会被发送回请求端
	 * @param value 响应数据
	 */
	void addResponse(String key,Object value);
	
	/**
	 * 获取所有响应数据
	 * @return 响应数据Map
	 */
	Map<String, Object> getResponses();
	
	/**
	 * 获取响应数据
	 * @param key
	 * @return
	 */
	Object getResponse(String key);
}
