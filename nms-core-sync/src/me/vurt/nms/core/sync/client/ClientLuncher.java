package me.vurt.nms.core.sync.client;

import me.vurt.nms.core.Luncher;

/**
 * @author yanyl
 *
 */
public class ClientLuncher implements Luncher {

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.Luncher#start()
	 */
	@Override
	public void start() {
		LOGGER.debug("[core-sync]客户端启动");
	}

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.Luncher#stop()
	 */
	@Override
	public void stop() {
		LOGGER.debug("[core-sync]客户端启动");
	}

}
