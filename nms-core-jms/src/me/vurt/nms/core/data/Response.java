package me.vurt.nms.core.data;

import java.util.Map;

import me.vurt.nms.core.jms.MessageHandler;

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
	 * 添加一条响应数据
	 * @param handlerID 处理器ID
	 * @param response 响应数据
	 */
	void addResponse(String handlerID,Object response);
	/**
	 * 获取响应数据
	 * @return 响应数据Map，Key是{@link MessageHandler#getId() 消息处理器ID}，Value具体响应数据
	 */
	Map<String, Object> getResponses();
}
