package me.vurt.nms.core.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map.Entry;

import me.vurt.nms.core.file.data.RootFolder;
import me.vurt.nms.core.file.util.DigestTool.DigestAlgorithm;

import org.apache.commons.io.DirectoryWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根目录遍历器，遍历过程中会计算根目录中所有的文件内容信息
 * 
 * 
 * @author Vurt
 * 
 */
public class RootFolderWalker extends DirectoryWalker<Entry<String, String>> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RootFolderWalker.class);

	private RootFolder rootFolder;
	private File root;
	private Collection<Entry<String, String>> contents;
	private DigestTool digestTool;

	public RootFolderWalker(RootFolder rootFolder) {
		this.rootFolder = rootFolder;
		root = new File(rootFolder.getAbsolutePath());
		// 使用map到Collection接口的适配器，在遍历过程中直接将结果写入RootFolder中，节约内存
		contents = new MapCollectionAdapter<String, String>(
				rootFolder.getFileContents());
		// TODO 遍历大文件夹时速度很慢，因为要计算每个文件的MD5，能优化么
		rootFolder.getFileContents().clear();
		digestTool = DigestTool.getInstance(DigestAlgorithm.MD5);
	}

	/**
	 * 开始遍历，遍历时会自动填充{@link RootFolder}的contents信息，开始后无法取消，除非发生异常<BR>
	 * 如果文件夹中东西很多那么会很慢
	 */
	public void start() {
		try {
			LOGGER.debug("开始计算根目录[" + rootFolder.getName() + "]的内容信息");
			long start = System.currentTimeMillis();
			walk(root, contents);
			// 删除根目录
			rootFolder.removeContent("");
			long end = System.currentTimeMillis();
			LOGGER.debug("根目录[" + rootFolder.getName() + "]内容信息计算完成，耗时:"
					+ (end - start) + "ms，根目录中子文件和文件夹总个数:"
					+ rootFolder.getFileContents().size());
		} catch (IOException e) {
			LOGGER.error("遍历根目录时出现异常，根目录地址:" + rootFolder);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.io.DirectoryWalker#handleFile(java.io.File, int,
	 * java.util.Collection)
	 */
	@Override
	protected void handleFile(File file, int depth,
			Collection<Entry<String, String>> results) throws IOException {
		String md5 = digestTool.digestHex(new FileInputStream(file));
		results.add(new FileInfo(
				RelativePathUtil.parseRelativePath(root, file), md5));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.io.DirectoryWalker#handleDirectory(java.io.File,
	 * int, java.util.Collection)
	 */
	@Override
	protected boolean handleDirectory(File directory, int depth,
			Collection<Entry<String, String>> results) throws IOException {
		results.add(new FileInfo(RelativePathUtil.parseRelativePath(root,
				directory), ""));
		return true;
	}

	/**
	 * 文件信息
	 * 
	 * @author yanyl
	 */
	static class FileInfo implements Entry<String, String> {
		private String path;

		private String md5;

		public FileInfo(String path, String md5) {
			this.path = path;
			this.md5 = md5;
		}

		/**
		 * 文件相对路径
		 */
		@Override
		public String getKey() {
			return path;
		}

		/**
		 * 文件摘要，如果是文件夹则摘要为空字符串""
		 */
		@Override
		public String getValue() {
			return md5;
		}

		@Override
		public String setValue(String value) {
			this.md5 = value;
			return md5;
		}

	}
}
