package me.vurt.nms.core.sync.tool;

import org.apache.commons.io.monitor.FileAlterationObserver;


/**
 * @author yanyl
 * 
 */
public class FolderMonitorUtil {
	/**
	 * 创建文件夹观察者，
	 * @param path
	 * @return
	 */
	public static FileAlterationObserver createFileAlterationObserver(String path) {
		FileAlterationObserver observer = new FileAlterationObserver(path);
		observer.addListener(new FileListener());
		return observer;
	}
}
