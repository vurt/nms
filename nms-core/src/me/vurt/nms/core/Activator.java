package me.vurt.nms.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import me.vurt.nms.core.common.tools.GlobalConfigFileReader;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		context=bundleContext;
		initLog();
		//新建StartFlag的实例便可激活服务
		new CoreServiceProvider();
	}

	private void initLog() throws IOException {
		File logConfig = GlobalConfigFileReader
				.getConfigFile("log4j.properties");

		if (logConfig != null) {
			InputStream is = new FileInputStream(logConfig);

			Properties props = new Properties();
			props.load(is);

			PropertyConfigurator.configure(props);
		} else {
			System.err.println("没有找到日志文件");
		}
	}

	public void stop(BundleContext context) throws Exception {
		context=null;
		LogManager.shutdown();
	}

}
