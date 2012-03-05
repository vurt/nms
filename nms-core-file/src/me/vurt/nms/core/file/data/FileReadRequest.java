package me.vurt.nms.core.file.data;

import me.vurt.nms.core.data.Data;

/**
 * 读远程文件的请求
 * @author Vurt
 *
 */
public class FileReadRequest implements Data {

	/**
	 * 
	 */
	private static final long serialVersionUID = 232158272440522144L;
	
	private String path;
	
	private boolean readOnly;
	
	/**
	 * 要读取的文件路径
	 * @param path 远程文件的访问路径(相对路径)
	 * 
	 */
	public FileReadRequest(String path){
		this.path=path;
	}
	
}
