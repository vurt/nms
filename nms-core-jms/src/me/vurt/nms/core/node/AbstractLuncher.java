package me.vurt.nms.core.node;

import me.vurt.nms.core.node.client.ClientLuncher;
import me.vurt.nms.core.node.server.ServerLuncher;
import me.vurt.nms.core.node.util.GlobalConfigReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 节点的启动器
 * 
 * @author yanyl
 * 
 */
public abstract class AbstractLuncher {
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractLuncher.class);
	
	/**
	 * 启动器实例
	 */
	public static final AbstractLuncher INSTANCE=init();
	
	private static AbstractLuncher init(){
		if(GlobalConfigReader.isClient()){
			return new ClientLuncher();
		}else{
			return new ServerLuncher();
		}
	}

	/**
	 * 启动所有需要在初始化时启动的处理线程
	 */
	public abstract void start();
	
	/**
	 * 关闭所有已启动的处理线程
	 */
	public abstract void stop();

}
