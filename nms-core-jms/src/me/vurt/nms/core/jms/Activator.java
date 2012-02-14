package me.vurt.nms.core.jms;

import me.vurt.nms.core.jms.test.ProducerTest;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class Activator implements BundleActivator {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Activator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		LOGGER.info("nms-core-jms is started!");
//		ProducerTest.main(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		LOGGER.info("nms-core-jms is stoped!");
	}

}
