package me.vurt.nms.core.file.io;

import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import me.vurt.nms.core.data.Response;
import me.vurt.nms.core.file.Constants;
import me.vurt.nms.core.jms.MessageHandler;
import me.vurt.nms.core.jms.MessageListener;
import me.vurt.nms.core.jms.data.BlobResponse;
import me.vurt.nms.core.jms.data.DefaultBlobResponse;

/**
 * 监听读取文件请求的消息监听器，发送普通字符串消息响应的方式与其他消息监听器一致，但是对文件响应有特殊要求， 文件响应的要求如下：<BR>
 * 1.处理器返回的Map中Key等于{@link Constants#RESPONSE_FILE_KEY};<BR>
 * 2.处理器返回的Map中Value是{@link InputStream 流};<BR>
 * 如果有多个处理器的返回值中有符合以上要求的键值对，那么只会使用第一个返回的值，同时会打印警告信息。<BR>
 * 
 * @author yanyl
 * 
 */
public class FileRequestMessageListener extends MessageListener {

	public FileRequestMessageListener() {
		super();
		setResponseType(DefaultBlobResponse.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * me.vurt.nms.core.jms.MessageListener#doMessageReceived(java.lang.Object,
	 * me.vurt.nms.core.data.Response)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void doMessageReceived(Object msg, Response response) {
		// 是否已有文件响应
		boolean fileHasSet = false;
		for (MessageHandler handler : getValidHandlers(msg)) {
			try {
				Map<String, Object> result = handler.handle(msg);
				if (result != null) {
					if (result.containsKey(Constants.RESPONSE_FILE_KEY)) {
						fileHasSet = initResponseFile(response, fileHasSet,
								handler.getId(), result);
					} else {
						response.addResponses(result);
					}
				}
			} catch (Exception e) {
				LOGGER.error("处理消息时发生异常，handlerID:" + handler.getId(), e);
				response.addError(handler.getId(), e.getMessage());
			}
		}
	}

	/**
	 * 将响应的文件添加到响应对象中去
	 * 
	 * @param response
	 *            最后的响应对象
	 * @param fileHasSet
	 *            文件是不是已经被添加过了
	 * @param handlerId
	 *            处理器ID
	 * @param result
	 *            处理器返回的响应结果集
	 * @return
	 */
	private boolean initResponseFile(Response response, boolean fileHasSet,
			String handlerId, Map<String, Object> result) {
		for (Entry<String, Object> entry : result.entrySet()) {
			if (Constants.RESPONSE_FILE_KEY.equals(entry.getKey())) {
				// 如果类型不对则直接忽略
				if (!(entry.getValue() instanceof InputStream)) {
					LOGGER.warn("读取到Key为" + Constants.RESPONSE_FILE_KEY
							+ "的响应数据，但是Value类型不是InputStream，直接忽略，来自处理器："
							+ handlerId);
				} else {
					if (fileHasSet) {
						LOGGER.warn("已有消息处理器返回了需要的文件，处理器" + handlerId
								+ "返回的响应文件将被忽略");
					} else {
						((BlobResponse) response)
								.setInputStream((InputStream) entry.getValue());
						LOGGER.debug("处理器" + handlerId + "返回了需要的文件!");
						fileHasSet = true;
					}
				}
			} else {
				response.addResponse(entry.getKey(), entry.getValue());
			}
		}
		return fileHasSet;
	}
}
