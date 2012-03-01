package me.vurt.nms.core.data;

import java.io.Serializable;

/**
 * 要在服务器和客户端之间通过消息中间件传递的对象，发送消息时将数据对象直接转换为二进制流，所以Data内部包含的所有对象也必须实现Serializable接口
 * @author Vurt
 *
 */
public interface Data  extends Serializable{

}
