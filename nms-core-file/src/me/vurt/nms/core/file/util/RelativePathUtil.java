package me.vurt.nms.core.file.util;

import java.io.File;

/**
 * 相对路径计算工具类
 * @author yanyl
 * 
 */
public class RelativePathUtil {
	/**
	 * 获取文件相对路径，该文件必须被包含在根目录中(层次不限)
	 * 
	 * @param root
	 *            相对路径的根
	 * @param file
	 *            要计算相对路径的文件
	 * @return 相对路径，如文件没有被包含在根目录中则返回null
	 */
	public static String parseRelativePath(File root, File file) {
		String rootPath = root.getAbsolutePath();
		String filePath = file.getAbsolutePath();
		int index=filePath.indexOf(rootPath);
		//文件没有在根目录中
		if ( index== -1) {
			return null;
		}
		String relativePath=filePath.substring(index+rootPath.length());
		return relativePath;
	}
}
