package me.vurt.nms.core.node.util;

/**
 * @author yanyl
 *
 */
public class PropertyNameUtil {
	
	/**
	 *	JMS的MessageSelector不支持带'.'的属性名，所以把所有的'.'都用'_'替换
	 * @param propertyName
	 * @return
	 */
	public static String toJMSName(String propertyName){
		return propertyName.replaceAll("\\.", "_");
	}
}
