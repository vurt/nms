package me.vurt.nms.core.node.server.dao;

import javax.sql.DataSource;

import me.vurt.nms.core.common.properties.PropertiesManager;
import me.vurt.nms.core.node.util.NodeInfoReader;
import me.vurt.nms.core.node.util.NodeConstants;

import org.apache.commons.dbcp.BasicDataSource;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;

public class DAOUtil {
	/**
	 * 服务端数据源
	 */
	private static BasicDataSource sds;

	private static Dao sDao;

	/**
	 * 获取服务端的DataSource
	 * 
	 * @return
	 */
	public static DataSource getServerDataSource() {
		if (sds == null) {
			PropertiesManager configManager = NodeInfoReader
					.getPropertiesManager();
			sds = new BasicDataSource();
			sds.setDriverClassName(configManager
					.read(NodeConstants.PROPERTY_DB_DRIVER));
			sds.setUrl(configManager.read(NodeConstants.PROPERTY_DB_URL));
			sds.setUsername(configManager
					.read(NodeConstants.PROPERTY_DB_USERNAME));
			sds.setPassword(configManager
					.read(NodeConstants.PROPERTY_DB_PASSWORD));
		}
		return sds;
	}

	/**
	 * 获取服务端dao
	 * @return
	 */
	public static Dao getServerDao() {
		if (sDao == null) {
			sDao = new NutDao(getServerDataSource());
		}
		return sDao;
	}

}
