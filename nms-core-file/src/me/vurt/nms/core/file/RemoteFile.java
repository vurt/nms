package me.vurt.nms.core.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import me.vurt.nms.core.file.data.FolderContent;
import me.vurt.nms.core.file.exception.InvalidFilePathException;

import org.apache.commons.lang.StringUtils;

/**
 * 远程文件，通过所在节点和文件路径可以访问远程文件并对其进行读写
 * 
 * @author Vurt
 * 
 */
public class RemoteFile implements NFile {

	/**
	 * 文件所在节点id
	 */
	private String node;

	/**
	 * 根目录名称
	 */
	private FolderContent root;

	/**
	 * 文件访问路径
	 */
	private String path;

	/**
	 * 远程文件
	 * 
	 * @param node
	 *            节点名称
	 * @param rootFolder
	 *            所属根目录
	 * @param path
	 *            文件路径
	 */
	public RemoteFile(String node, FolderContent rootFolder, String path) {
		if (!path.startsWith("" + SEPARATOR_CHAR)) {
			throw new InvalidFilePathException("无效的文件路径，远程文件路径必须以\'"
					+ SEPARATOR_CHAR + "\'开头");
		}
		this.node = node;
		this.root = rootFolder;
		this.path = path;
	}

	@Override
	public boolean isDirectory() {
		return StringUtils.isEmpty(root.getFileContents().get(path));
	}

	@Override
	public boolean isFile() {
		return !isDirectory();
	}

	@Override
	public boolean delete() {
		if (root.isLocal()) {
			return root.getFile(path).delete();
		} else {
			// TODO:远程删除
		}
		return false;
	}

	@Override
	public InputStream readFile() throws FileNotFoundException {
		if (root.isLocal()) {
			new FileInputStream(root.getFile(path));
		} else {
			// TODO:远程读取
		}
		return null;
	}

	@Override
	public void writeFile(InputStream is) throws IOException {
		if (root.isLocal()) {
			FileOutputStream os = new FileOutputStream(root.getFile(path));
			byte[] temp = new byte[1024];
			int num = 0;
			while ((num = is.read(temp)) != -1) {
				os.write(temp, 0, num);
			}
		} else {
			// TODO:远程修改
		}
	}

	@Override
	public String[] list() {
		if (isFile()) {
			return null;
		}
		// TODO：目前只能遍历根目录，还要大量indexOf操作，效率怎么样？
		return null;
	}

	@Override
	public String[] list(FilenameFilter filter) {
		if (isFile()) {
			return null;
		}
		return null;
	}

	/**
	 * @return 文件所属根目录
	 */
	public FolderContent getRootFolder() {
		return root;
	}

	/**
	 * 目录分隔符
	 */
	public static final char SEPARATOR_CHAR = '/';

	@Override
	public String getParent() {
		if (path.endsWith("" + SEPARATOR_CHAR)) {
			path = path.substring(0, path.length() - 1);
		}
		int index = path.lastIndexOf(SEPARATOR_CHAR);
		if (index == -1) {
			return null;
		}
		return path.substring(0, index);
	}

	@Override
	public NFile getParentNFile() {
		return new RemoteFile(node, root, getParent());
	}

}
