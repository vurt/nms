package me.vurt.nms.core.jms.data;

import java.io.InputStream;

import org.springframework.jms.core.JmsTemplate;

import me.vurt.nms.core.data.Response;
import me.vurt.nms.core.jms.util.FileMessageConverter;

/**
 * 大对象响应
 * * <BR>
 * 大对象响应与其他类型的响应不同，该对象本身不通过消息中间件传输，只是为了统一MessageListener响应普通对象和文件时的操作方式。
 * 每个大对象响应只能包含一个流，并且在通过JMS传输时也只发送一个流，其他数据(包括错误信息)都不能传输
 * <BR>
 * 该类型的响应必须配合使用{@link FileMessageConverter}的{@link JmsTemplate}使用
 * @author yanyl
 * @see FileMessageConverter
 *
 */
public interface BlobResponse extends Response{
	/**
	 * 设置响应数据的输入流，创建时使用
	 * @param is
	 */
	void setInputStream(InputStream is);
	
	/**
	 * 读取大对象
	 * @return 可读取大对象的输入流
	 */
	InputStream getInputStream();
}
