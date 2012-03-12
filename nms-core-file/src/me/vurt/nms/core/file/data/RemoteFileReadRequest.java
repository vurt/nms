package me.vurt.nms.core.file.data;

import me.vurt.nms.core.data.Data;

/**
 * 读远程文件的请求
 * @author Vurt
 *
 */
public class RemoteFileReadRequest implements Data {

	/**
	 * 
	 */
	private static final long serialVersionUID = 232158272440522144L;
	
	private String root;
	
	private String path;
	
	/**
	 * 读取远程文件的请求
	 * @param root 根目录
	 * @param path 访问相对路径
	 * 
	 */
	public RemoteFileReadRequest(String root,String path){
		this.root=root;
		this.path=path;
	}

	/**
	 * @return 根目录名称
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * @return 文件相对路径
	 */
	public String getPath() {
		return path;
	}
}
