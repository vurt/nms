package me.vurt.nms.core.common.properties;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性文件管理器
 * @author yanyl
 * 
 */
//TODO:写入的属性有多余的转义符(比如":"变成"\:")，不影响使用，但是编辑属性文件时看着不舒服
public class PropertiesManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesManager.class);

	private URL fileUrl;

	private Properties properties;

	private boolean needReload = true;

	/**
	 * 属性管理器构造函数
	 * 
	 * @param filePath
	 *            属性文件路径，从classpath中读取
	 */
	public PropertiesManager(String filePath) {
		fileUrl = this.getClass().getClassLoader().getResource(filePath);
	}
	
	/**
	 * 属性管理器构造函数
	 * @param uri 属性文件URI
	 */
	public PropertiesManager(URI uri){
		try {
			fileUrl=uri.toURL();
		} catch (MalformedURLException e) {
			LOGGER.error("无法读取属性文件，URI无效:"+uri.toString());
		}
	}

	private Properties load() {
		// 使用修改过的Properties，避免写入属性后格式和注释丢失的问题
		properties = new SafeProperties();
		InputStream ips = null;
		try {
			ips = new BufferedInputStream(fileUrl.openStream());
			properties.load(ips);
		} catch (FileNotFoundException e) {
			LOGGER.error("没有找到属性文件:" + fileUrl.toString());
		} catch (IOException e) {
			LOGGER.error("属性文件读取失败");
		} finally {
			try {
				ips.close();
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error("输入关闭出错");
			}
		}
		// 每次读取后把需要重载置为false
		needReload = false;
		return properties;
	}

	private boolean flush() {
		boolean flag = false;
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(fileUrl.getFile());
			properties.store(fos, null);
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				LOGGER.error("输出流无法关闭");
			}
		}
		// 每次写入文件后把需要重载置为true
		needReload = true;
		return flag;
	}

	/**
	 * 将属性写入文件
	 * 
	 * @param map
	 *            一次写入多个值
	 * @return
	 */
	public boolean write(Map<String, String> map) {
		load();
		for (String key : map.keySet()) {
			properties.setProperty(key, map.get(key));
		}
		return flush();
	}

	/**
	 * 将属性写入文件
	 * @param key 
	 * @param value
	 * @return
	 */
	public boolean write(String key, String value) {
		load();
		properties.setProperty(key, value);
		return flush();
	}

	/**
	 * 获取对应属性的值
	 * 
	 * @param key
	 *            属性名
	 */
	public String read(String key) {
		// 如果属性文件有写入过则读取值之前先重载
		if (properties == null || needReload) {
			load();
		}
		return properties.getProperty(key);
	}
}
