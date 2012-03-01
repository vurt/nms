package me.vurt.nms.core.sync.server;

import me.vurt.nms.core.Luncher;

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
	}

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.Luncher#stop()
	 */
	@Override
	public void stop() {
		LOGGER.debug("[core-sync]服务端关闭");
	}

}
