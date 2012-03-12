package me.vurt.nms.core.jms;


/**
 * 消息处理器动态从OSGi已注册服务中获取的消息监听器
 * @author yanyl
 *
 */
public abstract class DynamicMessageListener extends MessageListener{

	//TODO:未实现动态注册处理器的消息处理器
//	/* (non-Javadoc)
//	 * @see me.vurt.nms.core.jms.MessageListener#getValidHandlers()
//	 */
//	@Override
//	protected Collection<MessageHandler> getValidHandlers() {
//		try {
//			handlers.clear();
//			//TODO:应监听服务注册，实时更新Map
//			Collection<ServiceReference<MessageHandler>> refs = Activator
//					.getContext().getServiceReferences(MessageHandler.class,
//							getFilter());
//			LOGGER.debug("发现了"+refs.size()+"个处理服务，过滤表达式:"+getFilter());
//			for (ServiceReference<MessageHandler> ref : refs) {
//				MessageHandler handler = Activator.getContext().getService(ref);
//				handlers.put(handler.getId(), handler);
//			}
//		} catch (InvalidSyntaxException e) {
//			LOGGER.error("过滤表达式语法错误",e);
//		}
//		return handlers.values();
//	}
	
	/**
	 * 获取服务过滤表式
	 * 
	 * @return OSGi服务过滤表达式
	 */
	protected abstract String getFilter() ;

}
