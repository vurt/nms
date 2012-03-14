package me.vurt.nms.test.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import me.vurt.nms.core.common.tools.GlobalConfigFileReader;
import me.vurt.nms.core.file.NMSFileSystem;
import me.vurt.nms.core.file.data.FolderContent;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author yanyl
 * 
 */
public class RootFolderTest {

	private void initLog() throws IOException {
		File logConfig = GlobalConfigFileReader
				.getConfigFile("log4j.properties");

		if (logConfig != null) {
			InputStream is = new FileInputStream(logConfig);

			Properties props = new Properties();
			props.load(is);

			PropertyConfigurator.configure(props);
		} else {
			System.err.println("没有找到日志文件");
		}
	}

	@Test
	public void test() throws Exception {
		initLog();

		FolderContent rootFolder = NMSFileSystem.createInstance(new File(
				"D:/apache-tomcat-6.0.33"), "tomcat");

		rootFolder.reloadContentInfo();

		FolderContent rootFolder1 = NMSFileSystem.createInstance(new File(
				"D:/RootFolderTest/apache-tomcat-6.0.33"), "tomcat1");

		rootFolder1.reloadContentInfo();

		Assert.assertArrayEquals(rootFolder.getFileContents().keySet()
				.toArray(), rootFolder1.getFileContents().keySet().toArray());

		Assert.assertArrayEquals(rootFolder.getFileContents().values()
				.toArray(), rootFolder1.getFileContents().values().toArray());
	}
}
