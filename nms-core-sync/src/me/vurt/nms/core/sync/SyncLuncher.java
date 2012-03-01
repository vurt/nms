package me.vurt.nms.core.sync;

import me.vurt.nms.core.Luncher;
import me.vurt.nms.core.LuncherAdapter;
import me.vurt.nms.core.sync.client.ClientLuncher;
import me.vurt.nms.core.sync.server.ServerLuncher;

/**
 * @author yanyl
 *
 */
public class SyncLuncher extends LuncherAdapter {

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.LuncherAdapter#getServerLuncherType()
	 */
	@Override
	protected Class<? extends Luncher> getServerLuncherType() {
		return ServerLuncher.class;
	}

	/* (non-Javadoc)
	 * @see me.vurt.nms.core.LuncherAdapter#getClientLuncherType()
	 */
	@Override
	protected Class<? extends Luncher> getClientLuncherType() {
		return ClientLuncher.class;
	}

}
