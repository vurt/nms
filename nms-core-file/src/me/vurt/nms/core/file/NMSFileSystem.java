package me.vurt.nms.core.file;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import me.vurt.nms.core.file.data.RootFolder;
import me.vurt.nms.core.file.exception.InvalidRootFolderException;
import me.vurt.nms.core.node.Node;

import org.apache.commons.io.FileUtils;

/**
 * 
 * NMS文件系统，一般是由多个根目录组成。
 * 
 * <PRE>
 *     如果是客户端，那么这些根目录都应该在同一个目录中，把这个目录叫做<I>SuperRoot</I>吧，
 * 所有客户端根目录下的文件在服务端都是可读写的
 *     如果是服务端，则没有要在同一目录中的限制
 *     文件系统中所有文件信息都不会持久化，实时按需创建，根据自己的需要再开始计算文件内容
 * </PRE>
 * 
 * @author Vurt
 * @see RootFolder
 */
public class NMSFileSystem {
	private static Map<String, RootFolder> rootFolderMap = new HashMap<String, RootFolder>();

	/**
	 * 创建根目录，名称必须唯一，如果同名根目录已经被创建过，则检查新文件路径是否与上次创建时的相同，如相同则直接返回上次创建的根目录对象，
	 * 如果不同则抛出异常。 仅创建实例，不会计算根目录中的内容信息，如需计算，请手动调用
	 * {@link RootFolder#reloadContentInfo()}
	 * 
	 * @param rootFolder
	 *            根目录{@link File 文件}
	 * @param name
	 *            根目录的名称
	 * @throws InvalidRootFolderException
	 *             如果根目录{@link File 文件}无效或者与已有根目录对象命名冲突
	 */
	public static RootFolder createInstance(File rootFolder, String name)
			throws InvalidRootFolderException {
		if (rootFolder == null) {
			throw new InvalidRootFolderException("根目录不能为空");
		}
		if (!rootFolder.isDirectory()) {
			throw new InvalidRootFolderException(rootFolder.getAbsolutePath()
					+ "不是一个文件夹!");
		}
		if (!rootFolder.exists()) {
			try {
				FileUtils.forceMkdir(rootFolder);
			} catch (IOException e) {
				throw new InvalidRootFolderException(
						rootFolder.getAbsolutePath() + "不存在，创建也失败...", e);
			}
		}
		if (rootFolderMap.containsKey(name)) {
			if (!rootFolder.getAbsolutePath().equals(
					rootFolderMap.get(name).getAbsolutePath())) {
				throw new InvalidRootFolderException("已存在名为" + name
						+ "的根目录了，不能重复创建");
			} else {
				return rootFolderMap.get(name);
			}
		}
		RootFolder folder = new RootFolder(Node.CURRENT.getId(), rootFolder,
				name);
		rootFolderMap.put(name, folder);
		return folder;
	}

	/**
	 * 获取当前节点的所有根目录
	 * 
	 * @return 只读的根目录集合
	 */
	public Collection<RootFolder> getRootFolders() {
		// TODO：根目录内容也要只读
		// TODO：排序
		return Collections.unmodifiableCollection(rootFolderMap.values());
	}
}
