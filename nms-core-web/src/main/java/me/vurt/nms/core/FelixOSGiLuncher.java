package me.vurt.nms.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import me.vurt.nms.core.osgi.FelixOSGiFrameworkThread;

import org.apache.log4j.Logger;

/**
 * Felix OSGi¿ò¼ÜÆô¶¯Æ÷
 * @author yanyl
 *
 */
public class FelixOSGiLuncher implements ServletContextListener {
	public static final Logger LOGGER=Logger.getLogger(FelixOSGiLuncher.class);
	
	private FelixOSGiFrameworkThread frameworkThread=null;
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		frameworkThread=new FelixOSGiFrameworkThread(null);
		frameworkThread.start();
		LOGGER.info("Felix OSGi framework is started!");
		frameworkThread.stopFramework();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		frameworkThread.stopFramework();
		LOGGER.info("Felix OSGi framework is stoped!");
	}

}
