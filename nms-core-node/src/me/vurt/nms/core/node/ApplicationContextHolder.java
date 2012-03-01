package me.vurt.nms.core.node;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.osgi.context.support.OsgiBundleXmlApplicationContext;

/**
 * 保存当前Bundle的ApplicationContext的对象，Spring会在启动时自动注入
 * 
 * @author yanyl
 * 
 */
public class ApplicationContextHolder {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationContextHolder.class);
	
	private static ApplicationContext context=init();

	private static final ApplicationContext init(){
		OsgiBundleXmlApplicationContext applicationContext = new OsgiBundleXmlApplicationContext(
				new String[] { "spring/heartBeatScheduler.xml" });
		applicationContext.setBundleContext(Activator.getContext());
		applicationContext.refresh();
		return applicationContext;
	}

	/**
	 * 获取Bundle中的ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}

	/**
	 * 从ApplicationContext中获取指定id的bean
	 * 
	 * @param id
	 *            bean id
	 * @return bean，不存在则为null
	 */
	public static Object getBean(String id) {
		if (checkApplicationContext()) {
			return context.getBean(id);
		}
		return null;
	}

	/**
	 * 从ApplicationContext中获取指定id和类型的bean
	 * 
	 * @param id
	 *            bean id
	 * @param clazz
	 *            期望的类型
	 * @return bean的实例
	 * @throws NoSuchBeanDefinitionException
	 *             if there's no such bean definition
	 * @throws BeanNotOfRequiredTypeException
	 *             if the bean is not of the required type
	 * @throws BeansException
	 *             if the bean could not be created
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String id, Class<T> clazz) {
		if (checkApplicationContext()) {
			return (T) context.getBean(id, clazz);
		}
		return null;
	}

	/**
	 * 检查ApplicationContext的状态
	 * 
	 * @return 正常则返回true，其他返回false
	 */
	private static boolean checkApplicationContext() {
		if (context == null) {
			LOGGER.error("无法获取ApplicationContext");
			return false;
		}
		return true;
	}

}
