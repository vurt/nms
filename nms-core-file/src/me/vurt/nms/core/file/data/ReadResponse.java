package me.vurt.nms.core.file.data;

import java.io.InputStream;

public interface ReadResponse {
	
	/**
	 * 读取文件流
	 */
	InputStream getInputStream();
}
