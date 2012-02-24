package me.vurt.nms.core.node.client;

import me.vurt.nms.core.data.DataFactory;
import me.vurt.nms.core.data.HeartBeat;
import me.vurt.nms.core.jms.MessageProducer;
import me.vurt.nms.core.jms.exception.MessageSendFailedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 心跳任务，定时执行，定时信息在nms-client中定义 
 * @author yanyl
 *
 */
public class HeartBeatJob {
	public static final Logger LOGGER=LoggerFactory.getLogger(HeartBeatJob.class);
	private MessageProducer producer;

	/**
	 * 心跳任务 
	 * @param producer 发送心跳信息使用的jmsTemplate
	 */
	public HeartBeatJob(MessageProducer producer){
		this.producer=producer;
	}
	
	public void doHeartBeat(){
		HeartBeat heartBeat=DataFactory.createHeartBeat();
		try {
			producer.send(heartBeat);
		} catch (MessageSendFailedException e) {
			e.printStackTrace();
		}
		LOGGER.debug("发送心跳信息已发送："+heartBeat.toString());
		//TODO:传递心跳信息回服务器
	}
	
}
