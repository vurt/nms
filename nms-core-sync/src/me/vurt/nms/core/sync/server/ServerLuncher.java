package me.vurt.nms.core.sync.server;

import me.vurt.nms.core.Luncher;
import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.Server;

/**
 * @author yanyl
 *
 */
public class ServerLuncher implements Luncher {

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.Luncher#start()
	 */
	@Override
	public void start() {
		LOGGER.debug("[core-sync]服务端启动");
		LOGGER.debug("系统中有"+((Server)Node.CURRENT).getAllNodes().size()+"个节点");
	}

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.Luncher#stop()
	 */
	@Override
	public void stop() {
		LOGGER.debug("[core-sync]服务端关闭");
	}

}
