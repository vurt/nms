package me.vurt.nms.core.node.data;

import java.io.Serializable;

import org.springframework.jms.support.converter.SimpleMessageConverter;

/**
 * 要在服务器和客户端之间通过消息中间件传递的对象，发送消息时使用的是{@link SimpleMessageConverter}将对象转为Message的，
 * 默认实现是将数据对象直接转换为二进制流而不是json之类的，所以Data内部包含的所有对象也必须实现Serializable接口，而不是直接通过各种get方法来适配任意数据
 * @author Vurt
 *
 */
public interface Data  extends Serializable{

}
