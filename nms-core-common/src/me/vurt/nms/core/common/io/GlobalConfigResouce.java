package me.vurt.nms.core.common.io;

import java.io.File;

import me.vurt.nms.core.common.tools.GlobalConfigFileReader;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * 全局配置文件的{@link Resource}接口实现类
 * 
 * @author yanyl
 * 
 */
public class GlobalConfigResouce extends FileSystemResource {

	/**
	 * 创建NMS配置文件资源
	 * 
	 * @param path
	 *            全局配置文件的相对路径
	 */
	public GlobalConfigResouce(String path) {
		super(getRealPath(path));
	}

	/**
	 * 获取真实的NMS配置文件地址
	 * 
	 * @param path
	 *            全局配置文件的相对路径
	 * @return 配置文件的绝对路径
	 */
	private static String getRealPath(String path) {
		File file = new File(path);
		if (file.exists()) {
			return path;
		} else {
			File realFile = GlobalConfigFileReader.getConfigFile(path);
			if (realFile != null && realFile.exists() && realFile.isFile()) {
				return realFile.getAbsolutePath();
			}
			// 如果没找到，则返回原路径
			return path;
		}
	}

}
