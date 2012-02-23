package me.vurt.nms.core.node.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import me.vurt.nms.core.Activator;
import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.data.impl.HeartBeatImpl;
import me.vurt.nms.core.node.data.impl.RegisterRequestImpl;
import me.vurt.nms.core.node.util.NodeConstants;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class DataFactory {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			NodeConstants.DATE_FORMAT_PATTERN);

	/**
	 * 创建一个心跳数据包，在创建时会读取所有插件中的扩展数据并将其添加到心跳数据中去，在插件中自定义扩展数据的方法见
	 * {@link HeartBeatExtender}
	 * 
	 * @return 创建好的心跳数据
	 */
	public static HeartBeat createHeartBeat() {
		HeartBeatImpl heartBeat = new HeartBeatImpl(Node.CURRENT);
		heartBeat.setHeartBeatTime(DATE_FORMAT.format(new Date()));
		BundleContext context = Activator.getContext();
		Collection<ServiceReference<HeartBeatExtender>> refs;
		try {
			refs = context.getServiceReferences(HeartBeatExtender.class, null);
			for (ServiceReference<HeartBeatExtender> ref : refs) {
				heartBeat
						.addExtendData(context.getService(ref).getExtendData());
			}
		} catch (InvalidSyntaxException e) {
			// filter错误，没有filter，不必处理
		}
		return heartBeat;
	}
	
	/**
	 * 创建注册请求
	 * @return
	 */
	public static RegisterRequest createRegisterRequest(){
		return new RegisterRequestImpl(Node.CURRENT);
	}
}
