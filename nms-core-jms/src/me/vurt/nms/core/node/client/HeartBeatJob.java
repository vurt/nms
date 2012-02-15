package me.vurt.nms.core.node.client;

import me.vurt.nms.core.node.data.HeartBeat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * 心跳任务，定时执行，定时信息在nms-client中定义 
 * @author yanyl
 *
 */
public class HeartBeatJob {
	public static final Logger LOGGER=LoggerFactory.getLogger(HeartBeatJob.class);
	private JmsTemplate jmsTemplate;

	/**
	 * 心跳任务 
	 * @param jmsTemplate 发送心跳信息使用的jmsTemplate
	 */
	public HeartBeatJob(JmsTemplate jmsTemplate){
		this.jmsTemplate=jmsTemplate;
	}
	
	public void doHeartBeat(){
		HeartBeat heartBeat=HeartBeanFactory.createHeartBeat();
		jmsTemplate.convertAndSend(heartBeat);
		LOGGER.debug("发送心跳信息已发送："+heartBeat.toString());
		//TODO:传递心跳信息回服务器
	}
	
}
