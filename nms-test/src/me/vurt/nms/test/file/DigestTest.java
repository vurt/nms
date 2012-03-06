package me.vurt.nms.test.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import me.vurt.nms.core.common.tools.GlobalConfigFileReader;
import me.vurt.nms.core.file.util.DigestTool;
import me.vurt.nms.core.file.util.DigestTool.DigestAlgorithm;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author yanyl
 * 
 */
public class DigestTest {

	@Test
	public void toolTest() {
		File file = GlobalConfigFileReader.getConfigFile("ADInsight.exe");
		Assert.assertTrue(file.exists());

		int digestTime = 50;

		String utilResult = "";
		try {
			long start = System.currentTimeMillis();
			for (int i = 0; i < digestTime; i++) {
				utilResult = DigestUtils.md5Hex(new FileInputStream(file));
			}
			long end = System.currentTimeMillis();
			System.out.println("计算摘要" + digestTime + "次，Utils耗时:"
					+ (end - start));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		DigestTool tool = DigestTool.getInstance(DigestAlgorithm.MD5);

		String toolResult = "";
		try {
			long start = System.currentTimeMillis();
			for (int i = 0; i < digestTime; i++) {
				toolResult = tool.digestHex(new FileInputStream(file));
			}
			long end = System.currentTimeMillis();
			System.out.println("计算摘要" + digestTime + "次，Tool耗时:"
					+ (end - start));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(toolResult, utilResult);
	}
}
