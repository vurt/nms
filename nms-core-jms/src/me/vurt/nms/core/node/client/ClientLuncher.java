package me.vurt.nms.core.node.client;

import me.vurt.nms.core.ApplicationContextHolder;
import me.vurt.nms.core.data.exception.RegisterException;
import me.vurt.nms.core.node.AbstractNodeLuncher;
import me.vurt.nms.core.node.util.BeanConstants;

import org.nutz.mock.Mock.servlet;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * @author yanyl
 * 
 */
public class ClientLuncher extends AbstractNodeLuncher {
	
	private Scheduler heartBeatScheduler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#start()
	 */
	@Override
	protected void start() {
		try {
			RegisterHelper.register();

			heartBeatScheduler = (Scheduler) ApplicationContextHolder
					.getBean(BeanConstants.Client.HEARTBEAT_SCHEDULER);
			try {
				heartBeatScheduler.start();
			} catch (SchedulerException e) {
				LOGGER.error("心跳任务被中断了", e);
			}
		} catch (RegisterException e) {
			LOGGER.error("连接服务器出现错误，连接中断，错误信息:" + e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#stop()
	 */
	@Override
	protected void stop() {
		if(heartBeatScheduler!=null){
			try {
				heartBeatScheduler.shutdown();
			} catch (SchedulerException e) {
			}
		}
	}

}
