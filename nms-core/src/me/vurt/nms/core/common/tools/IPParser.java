package me.vurt.nms.core.common.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPParser {
	public static final Logger LOGGER = LoggerFactory.getLogger(IPParser.class);

	/**
	 * 读取本机IP时可以访问的URL
	 */
	public static final Set<String> TARGET_URLS = new HashSet<String>();
	static {
		TARGET_URLS.add("http://fw.qq.com/ipaddress");
		TARGET_URLS.add("http://www.ip138.com/ip2city.asp");
	}

	/**
	 * 获取当前节点外网IP
	 * 
	 * @return
	 */
	public static String getIP() {
		LOGGER.info("开始读取节点IP");
		long start = System.currentTimeMillis();
		String[] values = getFullIPInfo();
		long end = System.currentTimeMillis();
		if (values != null) {
			LOGGER.info("读取节点IP成功，当前IP：" + values[0] + " ，耗时：" + (end - start)
					+ "ms");
			return values[0];
		}
		return "";
	}

	/**
	 * 获取节点外网IP的详细信息，包括IP、省、市（只有通过http://fw.qq.com/ipaddress才能读到）
	 * 
	 * @return
	 */
	public static String[] getFullIPInfo() {
		String[] values;
		Iterator<String> urlIterator = TARGET_URLS.iterator();
		while (urlIterator.hasNext()) {
			String urlStr = (String) urlIterator.next();
			try {
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "GBK"));
				StringBuilder builder = new StringBuilder();
				String temp = "";
				while ((temp = reader.readLine()) != null) {
					builder.append(temp);
				}
				String context = builder.toString();
				String line = context.substring(context.indexOf("[") + 1,
						context.indexOf("]"));
				if (line.indexOf("(") != -1) {
					line = line.substring(line.indexOf("(") + 1,
							line.lastIndexOf(")")).replaceAll("\"", "");
				}
				values = line.split(",");
				reader.close();
				return values;
			} catch (MalformedURLException e) {
				LOGGER.debug("无效的URL:" + urlStr);
			} catch (IOException e) {
				LOGGER.debug("从" + urlStr + "读取IP信息失败，尝试下一个URL");
			}
		}
		LOGGER.error("读取IP信息失败");
		return null;
	}
}
