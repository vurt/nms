package me.vurt.nms.core.file;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import me.vurt.nms.core.file.data.FolderContent;
import me.vurt.nms.core.file.exception.InvalidRootFolderException;
import me.vurt.nms.core.node.Node;

import org.apache.commons.io.FileUtils;

/**
 *  NMS文件系统，每个节点的文件系统都是由两个根目录组成的，他们分别是：
 * <PRE>
 * 1.{@link #SYSTEM 系统根目录}，即NMS系统运行目录，其中可以在NMS中访问的内容包括:
 *   /bundle(所有NMS系统使用的插件)[r|w]
 *   /conf(配置文件)[r|w]
 *   /log(运行日志)[r]
 *   注：r表示可读，w表示可写
 * 2.{@link #USER 用户根目录}，在配置文件中通过{@link Constants#PROPERTY_FILE_SYSTEM_USER_DIR 用户根目录}
 * 属性定义，是用户数据存放的根目录，其中所有内容都是自定义且可读写的
 * </PRE>
 * 文件根目录中的文件信息在最初都是空的，请根据自己的需要再开始计算文件内容(如果文件很多则耗时很长)
 * 
 * @author Vurt
 * @see FolderContent
 */
public class NMSFileSystem {
	public static final FolderContent SYSTEM=null;
	
	public static final FolderContent USER=null;
	
	
	
	/**
	 * 创建根目录，名称必须唯一，如果同名根目录已经被创建过，则检查新文件路径是否与上次创建时的相同，如相同则直接返回上次创建的根目录对象，
	 * 如果不同则抛出异常。 仅创建实例，不会计算根目录中的内容信息，如需计算，请手动调用
	 * {@link FolderContent#reloadContentInfo()}
	 * 
	 * @param rootFolder
	 *            根目录{@link File 文件}
	 * @param name
	 *            根目录的名称
	 * @throws InvalidRootFolderException
	 *             如果根目录{@link File 文件}无效或者与已有根目录对象命名冲突
	 */
	private static FolderContent createInstance(File rootFolder)
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
		FolderContent folder = new FolderContent(Node.CURRENT.getId(), rootFolder);
		return folder;
	}
	
	/**
	 * 获取指定名称的根目录<BR>
	 * 如果当前节点是服务端，那么该根节点必须被初始化过才能获取;<BR>
	 * 如果是客户端，则会根据根目录名称和在nms-config.properties中配置的{@link Constants#PROPERTY_CLIENT_FILE_SYSTEM_ROOT 客户端文件系统根}属性自动生成一个
	 * @param name 根目录名称
	 * @return 指定根目录，如果该根目录没有被初始化过则返回null
	 */
	public static FolderContent getRootFolder(String name){
		return null;
	}
}
