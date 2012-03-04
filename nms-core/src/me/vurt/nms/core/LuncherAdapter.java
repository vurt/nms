package me.vurt.nms.core;

import me.vurt.nms.core.common.tools.ConfigReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动器适配器，根据节点类型启动不同的启动器实例
 * 
 * @author yanyl
 * 
 */
public abstract class LuncherAdapter implements Luncher {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(LuncherAdapter.class);

	private Luncher luncher;

	public LuncherAdapter() {
		try {
			if (ConfigReader.isClient()) {
				luncher = getClientLuncherType().newInstance();
			} else {
				luncher = getServerLuncherType().newInstance();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return 服务器启动器的实现类
	 */
	protected abstract Class<? extends Luncher> getServerLuncherType();

	/**
	 * @return 客户端启动器的实现类
	 */
	protected abstract Class<? extends Luncher> getClientLuncherType();

	public void restart() {
		luncher.stop();
		luncher.start();
	}

	public void start() {
		luncher.start();
	}

	public void stop() {
		luncher.stop();
	}

}
