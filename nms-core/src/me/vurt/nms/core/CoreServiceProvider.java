package me.vurt.nms.core;

import me.vurt.nms.core.common.tools.ConfigReader;

/**
 * 插件服务组件
 * 
 * @author Vurt
 * 
 */
public class CoreServiceProvider implements StartFlag, CoreService {

	@Override
	public boolean debugMode() {
		return ConfigReader.debugMode();
	}

	@Override
	public long getHeartBeatRepeatInterval() {
		return ConfigReader.getHeartBeatRepeatInterval();
	}

	@Override
	public boolean isClient() {
		return ConfigReader.isClient();
	}

	@Override
	public String getConfig(String proeprtyName) {
		return ConfigReader.getConfig(proeprtyName);
	}
}
