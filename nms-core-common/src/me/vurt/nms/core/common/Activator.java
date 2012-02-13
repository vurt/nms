package me.vurt.nms.core.common;

import java.io.InputStream;
import java.util.Properties;

import me.vurt.nms.core.common.tools.GlobalConfigFileReader;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext context) throws Exception {
		InputStream is=Activator.class.getResourceAsStream("/conf/log4j.properties");
		if(is!=null){
			Properties props = new Properties();
			props.load(is);

			PropertyConfigurator.configure(props);
			
			Logger logger=LoggerFactory.getLogger(Activator.class);
			logger.info("logger started!");
		}else{
			System.err.println("没有找到日志文件");
		}
		GlobalConfigFileReader.init();
	}

	public void stop(BundleContext context) throws Exception {
		LogManager.shutdown();
	}

}
