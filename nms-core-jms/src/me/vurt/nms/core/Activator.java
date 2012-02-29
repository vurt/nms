package me.vurt.nms.core;

import me.vurt.nms.core.node.AbstractLuncher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Activator.class);
	
	private static BundleContext context;

	/**
	 * @return bundleContext的唯一实例
	 */
	public static BundleContext getContext(){
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		context=bundleContext;
		AbstractLuncher.INSTANCE.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		AbstractLuncher.INSTANCE.stop();
	}

}
