package me.vurt.nms.core.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	public void start(BundleContext context) throws Exception {
		initLog();
//		initSystemProperites();
	}

	/**
	 * 
	 */
	private void initSystemProperites() {
		File globalConfig = GlobalConfigFileReader
				.getConfigFile("nms-config.properties");
		
		if(globalConfig!=null){
			importProperitesToSystem(globalConfig);
			System.out.println("全局配置文件读取完成");
		}else{
			System.err.println("全局配置文件不存在");
		}
	}
	
	/**
	 * @param realFile
	 */
	private static void importProperitesToSystem(File realFile) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(realFile));
			System.out.println("将配置文件"+realFile.getAbsolutePath()+"导入到系统参数中");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			//文件格式不符合properties标准
		}
	}

	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void initLog() throws FileNotFoundException, IOException {
		File logConfig = GlobalConfigFileReader
				.getConfigFile("log4j.properties");

		if (logConfig != null) {
			InputStream is = new FileInputStream(logConfig);
			Properties props = new Properties();
			props.load(is);

			PropertyConfigurator.configure(props);

			System.out.println("日志初始化完成");
		} else {
			System.err.println("没有找到日志文件");
		}
	}

	public void stop(BundleContext context) throws Exception {
		LogManager.shutdown();
	}

}
