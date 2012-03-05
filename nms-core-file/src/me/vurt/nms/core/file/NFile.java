package me.vurt.nms.core.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;

/**
 * 节点管理系统中的文件
 * 
 * @author Vurt
 * 
 */
public interface NFile {
	/**
	 * 是否是文件夹
	 * 
	 * @return
	 */
	boolean isDirectory();

	/**
	 * 是否是文件
	 * 
	 * @return
	 */
	boolean isFile();

	/**
	 * 删除文件
	 * 
	 * @return
	 */
	boolean delete();
	
	/**
	 * 上级文件路径
	 */
	String getParent();
	
	/**
	 * 上级文件对象
	 */
	NFile getParentNFile();

	/**
	 * @see {@link File#list()}
	 */
	public String[] list();

	/**
	 * @see {@link File#list(FilenameFilter)}
	 */
	public String[] list(FilenameFilter filter);

	/**
	 * 读取文件内容
	 */
	InputStream readFile() throws FileNotFoundException;

	/**
	 * 写文件，用输入流中的数据完全覆盖原文件中的数据
	 * 
	 * @param is
	 *            读取文件内容的输入流
	 */
	void writeFile(InputStream is);
}
