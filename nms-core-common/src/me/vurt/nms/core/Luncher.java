package me.vurt.nms.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动器接口，在bundle加载后启动所有处理逻辑的接口
 * @author yanyl
 *
 */
public interface Luncher {
	public static final Logger LOGGER = LoggerFactory
			.getLogger(Luncher.class);

	/**
	 * 启动所有需要在初始化时启动的处理线程
	 */
	void start();
	
	/**
	 * 关闭所有已启动的处理线程
	 */
	void stop();
}
