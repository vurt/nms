package me.vurt.nms.core.node.client;

import me.vurt.nms.core.ApplicationContextHolder;
import me.vurt.nms.core.node.AbstractLuncher;
import me.vurt.nms.core.node.exception.RegisterException;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * @author yanyl
 * 
 */
public class ClientLuncher extends AbstractLuncher {
	
	private Scheduler heartBeatScheduler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#start()
	 */
	@Override
	public void start() {
		try {
			LOGGER.debug("[bundle:core-node]-客户端启动...");
			RegisterHelper.register();
			
			heartBeatScheduler = (Scheduler)ApplicationContextHolder.getBean("heartBeatScheduler");
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
	public void stop() {
		if(heartBeatScheduler!=null){
			try {
				heartBeatScheduler.shutdown();
			} catch (SchedulerException e) {
			}
		}
	}

}
