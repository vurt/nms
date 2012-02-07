package me.vurt.nms.core.ogsi.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.osgi.framework.launch.FrameworkFactory;

/**
 * @author yanyl
 * 
 */
public class OSGiUtil {
	/**
	 * Returns FrameworkFactory,
	 * 
	 * @return
	 * @throws Exception
	 */
	public static FrameworkFactory getFrameworkFactory() throws Exception {
		URL url = OSGiUtil.class.getClassLoader().getResource(
				"META-INF/services/org.osgi.framework.launch.FrameworkFactory");
		if (url != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));
			try {
				for (String s = br.readLine(); s != null; s = br.readLine()) {
					s = s.trim();
					// Try to load first non-empty, non-commented line.
					if ((s.length() > 0) && (s.charAt(0) != '#')) {
						return (FrameworkFactory) Class.forName(s)
								.newInstance();
					}
				}
			} finally {
				if (br != null)
					br.close();
			}
		}

		throw new Exception("Could not find framework factory.");
	}
}
