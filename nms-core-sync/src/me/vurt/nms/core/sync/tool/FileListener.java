package me.vurt.nms.core.sync.tool;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yanyl
 *
 */
public class FileListener implements FileAlterationListener {
	private static final Logger LOGGER=LoggerFactory.getLogger(FileListener.class);

	/* (non-Javadoc)
	 * @see org.apache.commons.io.monitor.FileAlterationListener#onStart(org.apache.commons.io.monitor.FileAlterationObserver)
	 */
	@Override
	public void onStart(FileAlterationObserver observer) {
		//检查开始
		LOGGER.debug("检查开始");
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.io.monitor.FileAlterationListener#onDirectoryCreate(java.io.File)
	 */
	@Override
	public void onDirectoryCreate(File directory) {
		LOGGER.debug("新建了一个文件夹"+directory.getAbsolutePath());
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.io.monitor.FileAlterationListener#onDirectoryChange(java.io.File)
	 */
	@Override
	public void onDirectoryChange(File directory) {
		LOGGER.debug("修改了一个文件夹"+directory.getAbsolutePath());
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.io.monitor.FileAlterationListener#onDirectoryDelete(java.io.File)
	 */
	@Override
	public void onDirectoryDelete(File directory) {
		LOGGER.debug("删除了一个文件夹"+directory.getAbsolutePath());
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.io.monitor.FileAlterationListener#onFileCreate(java.io.File)
	 */
	@Override
	public void onFileCreate(File file) {
		LOGGER.debug("新建了一个文件"+file.getAbsolutePath());
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.io.monitor.FileAlterationListener#onFileChange(java.io.File)
	 */
	@Override
	public void onFileChange(File file) {
		LOGGER.debug("修改了一个文件"+file.getAbsolutePath());
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.io.monitor.FileAlterationListener#onFileDelete(java.io.File)
	 */
	@Override
	public void onFileDelete(File file) {
		LOGGER.debug("删除了一个文件"+file.getAbsolutePath());
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.io.monitor.FileAlterationListener#onStop(org.apache.commons.io.monitor.FileAlterationObserver)
	 */
	@Override
	public void onStop(FileAlterationObserver observer) {
		//一次检查结束
		LOGGER.debug("检查结束");
	}

}
