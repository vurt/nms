package me.vurt.nms.core.ogsi;

import me.vurt.nms.core.exception.OSGIFrameworkStoppedExcpetion;
import me.vurt.nms.core.ogsi.util.AutoProcessor;
import me.vurt.nms.core.ogsi.util.OSGiUtil;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;

/**
 * @author yanyl
 * 
 */
public class NMSCoreThread extends Thread {
	public static final Logger LOGGER = Logger.getLogger(NMSCoreThread.class);

	private static Framework framework = null;

	private boolean isStopped = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// Print welcome banner.
		LOGGER.info("framework is starting");
		try {
			framework = OSGiUtil.getFrameworkFactory().newFramework(null);
			framework.init();
			AutoProcessor.process(null, framework.getBundleContext());
			framework.start();
		} catch (Exception ex) {
			LOGGER.error("Could not create framework: " + ex.getMessage(), ex);
			System.exit(-1);
		}
		isStopped = false;
	}

	/**
	 * block util felix osgi framework is started
	 */
	private void blockUtilStarted() {
		if (isStopped) {
			throw new OSGIFrameworkStoppedExcpetion();
		}
		long start = System.currentTimeMillis();
		while (framework == null || framework.getState() != Framework.ACTIVE) {
			try {
				synchronized (this) {
					this.wait(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("wait for start:" + (end - start) + "ms");
	}

	public BundleContext getBundleContext() {
		blockUtilStarted();
		return framework.getBundleContext();
	}

	/**
	 * Returns the installed bundles, must be invoke while framework is running
	 * 
	 * @return
	 */
	public Bundle[] getInstalledBundles() {
		// Use the system bundle activator to gain external
		// access to the set of installed bundles.
		return getBundleContext().getBundles();
	}

	public void shutdownApplication() {
		// Shut down the felix framework when stopping the
		// host application.
		try {
			framework.stop();
			framework.waitForStop(0);
			isStopped = true;
		} catch (BundleException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
}
