package me.vurt.nms.core.node;

import me.vurt.nms.core.Luncher;
import me.vurt.nms.core.LuncherAdapter;
import me.vurt.nms.core.node.client.ClientLuncher;
import me.vurt.nms.core.node.server.ServerLuncher;

/**
 * 节点的启动器
 * 
 * @author yanyl
 * 
 */
public class NodeLuncher extends LuncherAdapter{
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
