package me.vurt.nms.core.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.event.OsgiBundleApplicationContextEvent;
import org.springframework.osgi.context.event.OsgiBundleApplicationContextListener;
import org.springframework.osgi.context.event.OsgiBundleContextRefreshedEvent;

/**
 * 节点的启动器
 * 
 * @author yanyl
 * 
 */
public abstract class AbstractNodeLuncher implements OsgiBundleApplicationContextListener {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractNodeLuncher.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.osgi.context.event.OsgiBundleApplicationContextListener
	 * #onOsgiApplicationEvent(org.springframework.osgi.context.event.
	 * OsgiBundleApplicationContextEvent)
	 */
	@Override
	public void onOsgiApplicationEvent(OsgiBundleApplicationContextEvent event) {
		if (event instanceof OsgiBundleContextRefreshedEvent) {
			LOGGER.info("插件[" + event.getBundle().getSymbolicName()
					+ "]的ApplicationContext创建完成，开始启动处理线程");
			start();
		}else{
			//OsgiBundleContextFailedEvent|OsgiBundleContextClosedEvent
			stop();
		}
	}
	
	/**
	 * 启动所有需要在初始化时启动的处理线程
	 */
	protected abstract void start();
	
	/**
	 * 关闭所有已启动的处理线程
	 */
	protected abstract void stop();

}
