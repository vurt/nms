package me.vurt.nms.core.file.data;

import me.vurt.nms.core.data.Data;

/**
 * 读远程文件的请求
 * @author Vurt
 *
 */
public class RemoteFileRequest implements Data {

	/**
	 * 
	 */
	private static final long serialVersionUID = 232158272440522144L;
	
	private String root;
	
	private String path;
	
	private boolean readOnly;
	
	/**
	 * 读取远程文件的请求
	 * @param root 根目录
	 * @param path 访问相对路径
	 * 
	 */
	public RemoteFileRequest(String root,String path){
		this.root=root;
		this.path=path;
	}
	
}
