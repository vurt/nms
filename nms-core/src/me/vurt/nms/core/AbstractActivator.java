package me.vurt.nms.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractActivator implements BundleActivator {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractActivator.class);

	private static BundleContext context;

	private static Luncher luncher ;

	/**
	 * @return bundleContext的唯一实例
	 */
	public static BundleContext getContext() {
		return context;
	}
	
	public AbstractActivator(){
		luncher=getLuncher();
	}
	
	/**
	 * 获取当前bundle的Luncher实例
	 * @return
	 */
	protected abstract Luncher getLuncher();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;
		doStart();
		luncher.start();
		LOGGER.debug(bundleContext.getBundle().getSymbolicName() + "已启动");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		luncher.stop();
		doStop();
		LOGGER.debug(bundleContext.getBundle().getSymbolicName() + "已关闭");
	}
	
	/**
	 * 启动插件，在{@link Luncher}启动前调用
	 */
	public abstract void doStart();
	
	/**
	 * 关闭插件，在{@link Luncher}关闭后调用
	 */
	public abstract void doStop();

}
