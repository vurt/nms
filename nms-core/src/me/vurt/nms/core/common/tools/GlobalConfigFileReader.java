package me.vurt.nms.core.common.tools;

import java.io.File;

/**
 * @author yanyl
 * 
 */
public class GlobalConfigFileReader {
	/**
	 * 全局配置文件的文件夹名称
	 */
	public static final String CONFIG_DIRECTORY_NAME = "conf";

	private static File CONFIG_DIRECTORY = null;
	
	static{
		init();
	}

	public static void init() {
		String workingDir = System.getProperty("user.dir");
		String configDir = workingDir + File.separator + CONFIG_DIRECTORY_NAME;

		CONFIG_DIRECTORY = new File(configDir);
		if (!(CONFIG_DIRECTORY.exists() && CONFIG_DIRECTORY.isDirectory())) {
			System.err.println("没有找到全局配置文件目录，当期望的全局配置文件目录路径:"
					+ CONFIG_DIRECTORY.getAbsolutePath());
			CONFIG_DIRECTORY = null;
		} else {
			System.out.println("全局配置文件目录初始化成功");
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
			return file;
		}
		return null;
	}
}
