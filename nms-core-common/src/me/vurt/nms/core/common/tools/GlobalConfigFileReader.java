package me.vurt.nms.core.common.tools;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yanyl
 * 
 */
public class GlobalConfigFileReader {
	public static final Logger LOGGER = LoggerFactory
			.getLogger(GlobalConfigFileReader.class);

	/**
	 * 全局配置文件的文件夹名称
	 */
	public static final String CONFIG_DIRECTORY_NAME = "conf";

	private static File CONFIG_DIRECTORY = null;

	public static void init() {
		String workingDir = System.getProperty("user.dir");
		String configDir = workingDir + File.separator + CONFIG_DIRECTORY_NAME;

		CONFIG_DIRECTORY = new File(configDir);
		if (!(CONFIG_DIRECTORY.exists() && CONFIG_DIRECTORY.isDirectory())) {
			LOGGER.error("没有找到全局配置文件目录，当期望的全局配置文件目录路径:"
					+ CONFIG_DIRECTORY.getAbsolutePath());
			CONFIG_DIRECTORY = null;
		} else {
			LOGGER.info("全局配置文件目录初始化成功");
		}
	}

	/**
	 * 获取全局设置文件的URL，全局设置文件是指放在{user.dir}/conf中的文件
	 * 
	 * @param path
	 *            配置文件相对于{user.dir}/conf的路径，不能以分隔符开头
	 * @return 配置文件，如果不存在则返回null
	 */
	public static File getConfigFile(String path) {
		File file = new File(CONFIG_DIRECTORY, path);
		if (file.exists() && file.isFile()) {
			LOGGER.debug("找到了配置文件:" + file.getAbsolutePath());
			return file;
		}
		return null;
	}
}
