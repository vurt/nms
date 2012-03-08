package me.vurt.nms.core.file.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.vurt.nms.core.data.Data;
import me.vurt.nms.core.file.NMSFileSystem;
import me.vurt.nms.core.file.exception.CannotReloadException;
import me.vurt.nms.core.file.util.RootFolderWalker;
import me.vurt.nms.core.node.Node;

/**
 * NMS文件系统的根目录，保存文件夹下所有文件和子文件夹(层次不限)的信息，信息包括：相对路径、文件内容的MD5(如果是文件)<BR>
 * 该类型的对象可以通过网络发送
 * 
 * @author Vurt
 * 
 */
public class RootFolder implements Data {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4924162401212362067L;
	/**
	 * 根目录在文件系统中的绝对路径，不作为两个FolderInfo相等的条件
	 */
	private String absolutePath;
	/**
	 * 根目录名，文件夹在NMS系统内的名称，可以与文件系统中的名称不同，但在每个{@link Node 节点}中必须唯一
	 */
	private String name;
	/**
	 * 所属节点ID
	 */
	private String node;

	private Map<String, String> fileContents;

	/**
	 * 根目录构造函数，不要手动创建，使用{@link NMSFileSystem#createInstance(File, String)}
	 * 来创建，可以保证根目录的有效性和唯一性
	 * 
	 * @param rootFolder
	 *            根目录{@link File 文件}
	 * @param name
	 *            根目录名称
	 */
	public RootFolder(String node, File rootFolder, String name) {
		this.node = node;
		this.absolutePath = rootFolder.getAbsolutePath();
		this.name = name;
		this.fileContents = new HashMap<String, String>();
	}

	/**
	 * 根目录是不是在当前节点创建的
	 * 
	 * @return
	 */
	public boolean isLocal() {
		return Node.CURRENT.getId().equals(node);
	}

	/**
	 * 目录在文件系统中的绝对路径
	 */
	public String getAbsolutePath() {
		return absolutePath;
	}

	/**
	 * 根目录名，文件夹在NMS系统内的名称，可以与文件系统中的名称不同，但在每个{@link Node 节点}中必须唯一
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	private boolean initialized = false;

	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * 根目录下所有文件和文件夹的信息，KEY是文件相对路径(相对于当前根目录)，Value是文件的MD5，如果没有MD5信息，则表示它是一个文件夹
	 * 
	 * @return 包含根目录下所有子文件和文件夹信息的Map
	 */
	public Map<String, String> getFileContents() {
		if (!isInitialized()) {
			throw new RuntimeException("根节点没有被初始化过，无法查看文件内容");
		}
		// TODO：能不能在确保key唯一性的情况下把key缩短，因为RootFolder要在网络中传输的，节省内存，也节省网络资源
		return fileContents;
	}

	/**
	 * 获取根目录下的文件的{@link File}对象
	 * 
	 * @param path
	 *            文件相对路径
	 * @return 不存在则返回null
	 * @throws RuntimeException
	 *             如果根目录不是在创建它的节点上
	 */
	public File getFile(String path) {
		if (!isLocal()) {
			throw new RuntimeException("文件没在本地");
		}
		if (getFileContents().containsKey(path)) {
			return new File(getAbsolutePath() + path);
		}else{
			return null;
		}
	}

	/**
	 * 重新计算所有信息，耗时较长，如果不是在包含该根目录的机器上请勿调用该方法
	 */
	public void reloadContentInfo() {
		if (!isLocal()) {
			throw new CannotReloadException("根目录不是在本地创建的，不能重载");
		}
		RootFolderWalker walker = new RootFolderWalker(this);
		walker.start();
		initialized = true;
	}

	/**
	 * 添加一条内容信息
	 * 
	 * @param path
	 * @param md5
	 */
	public void addContent(String path, String md5) {
		fileContents.put(path, md5);
	}

	/**
	 * 删除指定路径的内容信息
	 * 
	 * @param path
	 */
	public void removeContent(String path) {
		if (fileContents.containsKey(path)) {
			fileContents.remove(path);
		}
	}

}
