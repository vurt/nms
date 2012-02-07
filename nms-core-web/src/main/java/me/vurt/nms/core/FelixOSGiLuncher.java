package me.vurt.nms.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * Felix OSGi¿ò¼ÜÆô¶¯Æ÷
 * @author yanyl
 *
 */
public class FelixOSGiLuncher implements ServletContextListener {
	public static final Logger LOGGER=Logger.getLogger(FelixOSGiLuncher.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info("Felix OSGi framework is started!");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.info("Felix OSGi framework is stoped!");
	}

}
