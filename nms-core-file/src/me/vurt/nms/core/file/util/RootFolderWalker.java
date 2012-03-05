package me.vurt.nms.core.file.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import me.vurt.nms.core.file.data.RootFolder;
import me.vurt.nms.core.file.util.RootFolderWalker.FileInfo;

import org.apache.commons.io.DirectoryWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Vurt
 * 
 */
public class RootFolderWalker extends DirectoryWalker<FileInfo> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RootFolderWalker.class);

	private RootFolder rootFolder;
	private File root;
	private Collection<FileInfo> contents;

	public RootFolderWalker(RootFolder rootFolder) {
		this.rootFolder = rootFolder;
		root = new File(rootFolder.getAbsolutePath());
		contents = new ArrayList<FileInfo>();
	}

	/**
	 * 开始遍历，遍历时会自动填充{@link RootFolder}的contents信息，开始后无法取消，除非发生异常
	 */
	public void start() {
		try {
			walk(root, contents);
			for (FileInfo info : contents) {
				rootFolder.addContent(info.getKey(), info.getValue());
			}
		} catch (IOException e) {
			LOGGER.error("遍历根目录时出现异常，根目录地址:" + rootFolder);
		}
	}

	static class FileInfo implements Entry<String, String> {
		private String path;

		private String md5;

		public FileInfo(String path, String md5) {
			this.path = path;
			this.md5 = md5;
		}

		@Override
		public String getKey() {
			return path;
		}

		@Override
		public String getValue() {
			// TODO Auto-generated method stub
			return md5;
		}

		@Override
		public String setValue(String value) {
			this.md5 = value;
			return md5;
		}

	}
}
