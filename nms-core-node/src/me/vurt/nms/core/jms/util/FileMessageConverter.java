package me.vurt.nms.core.jms.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import me.vurt.nms.core.jms.data.BlobResponse;
import me.vurt.nms.core.jms.data.DefaultBlobResponse;

import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.SimpleMessageConverter;

/**
 * 
 * 可以将{@link File}和{@link InputStream}类型的数据转换成{@link BlobMessage}
 * 发送的消息转换器，对其他类型对象的处理与 {@link SimpleMessageConverter}一致
 * 
 * <pre>
 * 发送时{@link File}和{@link InputStream}将会被转换成{@link BlobMessage}
 * 接收时{@link BlobMessage}将会被转换成{@link InputStream}
 * </pre>
 * 
 * @author Vurt
 * 
 */
public class FileMessageConverter extends SimpleMessageConverter {
	@Override
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		if (object instanceof File) {
			return createMessageForFile((File) object, session);
		} else if (object instanceof InputStream) {
			return createMessageForInputStream((InputStream) object, session);
		} else {
			return super.toMessage(object, session);
		}
	}

	@Override
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		if (message instanceof BlobMessage) {
			return extractFileFromMessage((BlobMessage) message);
		} else {
			return super.fromMessage(message);
		}
	}

	/**
	 * 将要发送的文件转成{@link BlobMessage}
	 * @param file 要发送的文件
	 * @param session
	 * @return
	 * @throws JMSException
	 */
	protected Message createMessageForFile(File file, Session session)
			throws JMSException {
		if (session instanceof ActiveMQSession) {
			ActiveMQSession mqSession = (ActiveMQSession) session;
			return mqSession.createBlobMessage(file);
		} else {
			throw new MessageConversionException(
					"错误的Session类型，只支持通过ActiveMQSession发送文件");
		}
	}

	/**
	 * 将要发送的流转换成{@link BlobMessage}
	 * 
	 * @param is 输入流
	 * @param session
	 * @return {@link BlobMessage}
	 * @throws JMSException
	 */
	protected Message createMessageForInputStream(InputStream is,
			Session session) throws JMSException {
		if (session instanceof ActiveMQSession) {
			ActiveMQSession mqSession = (ActiveMQSession) session;
			return mqSession.createBlobMessage(is);
		} else {
			throw new MessageConversionException(
					"错误的Session类型，只支持通过ActiveMQSession发送文件");
		}
	}

	/**
	 * 将接收到的BlobMessage转换为BlobResponse
	 */
	protected Object extractFileFromMessage(BlobMessage message)
			throws JMSException {
		try {
			BlobResponse response = new DefaultBlobResponse();
			response.setInputStream(message.getInputStream());
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
