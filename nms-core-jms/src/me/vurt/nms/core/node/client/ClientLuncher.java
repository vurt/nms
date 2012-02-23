package me.vurt.nms.core.node.client;

import javax.jms.Destination;

import me.vurt.nms.core.ApplicationContextHolder;
import me.vurt.nms.core.jms.JMSFactory;
import me.vurt.nms.core.jms.MessageProducer;
import me.vurt.nms.core.jms.exception.MessageHandleException;
import me.vurt.nms.core.node.AbstractNodeLuncher;
import me.vurt.nms.core.node.client.exception.RegisterException;
import me.vurt.nms.core.node.data.RegisterRequest;
import me.vurt.nms.core.node.data.RegisterResponse;
import me.vurt.nms.core.node.util.BeanConstants;
import me.vurt.nms.core.node.util.NodeInfoReader;

import org.h2.util.New;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author yanyl
 * 
 */
public class ClientLuncher extends AbstractNodeLuncher {

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#start()
	 */
	@Override
	protected void start() {
		try {
			register();

			Scheduler heartBeatScheduler = (Scheduler) ApplicationContextHolder
					.getBean(BeanConstants.Client.HEARTBEAT_SCHEDULER);
			try {
				heartBeatScheduler.start();
			} catch (SchedulerException e) {
				LOGGER.error("心跳任务被中断了", e);
			}
		} catch (RegisterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册，发送注册请求，并等待注册响应，
	 * 
	 * @throws MessageHandleException
	 *             如果无法完成注册
	 */
	private void register() throws RegisterException {
		if (!NodeInfoReader.isRegisted()) {
			MessageProducer regProducer = JMSFactory.createProducer();
			//该bean发送消息应该都是以非持久化的方式发送，在Spring中定义
			JmsTemplate regTemplate = (JmsTemplate) ApplicationContextHolder
					.getBean(BeanConstants.REGISTRATION_JMS_TEMPLATE);
			regProducer.setJmsTemplate(regTemplate);
			regProducer.send(new RegisterRequest());
			// TODO:发送超时需要重试

			Destination responseQueue = (Destination) ApplicationContextHolder
					.getBean(BeanConstants.REGISTRATION_RESPONSE_QUEUE_BEAN);
			RegisterResponse response = (RegisterResponse) regTemplate
					.receiveSelectedAndConvert(responseQueue,
							JMSFactory.getMessageSelector());
			if (response == null) {
				// 接收响应信息超时了，重试
				LOGGER.info("注册节点时服务器没有响应，尝试再次发送注册请求");
				register();
			} else {
				if (!response.isSucceed()) {
					LOGGER.error("注册失败，错误信息：" + response.getErrorMessage());
				} else {
					
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.node.AbstractNodeLuncher#stop()
	 */
	@Override
	protected void stop() {

	}

}
