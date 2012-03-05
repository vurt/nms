package me.vurt.nms.core.file;

import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;

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
	 * 文件访问路径
	 */
	private String path;

	public RemoteFile(String node, String path) {
		this.node = node;
		this.path = path;
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public boolean isFile() {
		return true;
	}

	@Override
	public boolean delete() {
		return false;
	}

	@Override
	public InputStream readFile() throws FileNotFoundException {
		return null;
	}

	@Override
	public void writeFile(InputStream os) {

	}

	@Override
	public String[] list() {
		if (isFile()) {
			return null;
		}
		return null;
	}

	@Override
	public String[] list(FilenameFilter filter) {
		if (isFile()) {
			return null;
		}
		return null;
	}

	public static final char separatorChar = '/';

	@Override
	public String getParent() {
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		int index = path.lastIndexOf(separatorChar);
		if (index == -1) {
			return null;
		}
		return path.substring(0, index);
	}

	@Override
	public NFile getParentNFile() {
		return new RemoteFile(node, getParent());
	}

}
