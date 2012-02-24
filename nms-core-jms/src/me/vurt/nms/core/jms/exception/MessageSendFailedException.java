package me.vurt.nms.core.jms.exception;

/**
 * 发送消息失败
 * @author yanyl
 *
 */
public class MessageSendFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5118163431584496247L;
	
	public MessageSendFailedException(){
		super();
	}
	
	public MessageSendFailedException(String msg){
		super(msg);
	}
	
	public MessageSendFailedException(String msg,Throwable throwable){
		super(msg,throwable);
	}

}
