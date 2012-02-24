package me.vurt.nms.core.node.client;

import javax.jms.Destination;

import me.vurt.nms.core.ApplicationContextHolder;
import me.vurt.nms.core.data.DataFactory;
import me.vurt.nms.core.data.RegisterRequest;
import me.vurt.nms.core.data.RegisterResponse;
import me.vurt.nms.core.jms.JMSFactory;
import me.vurt.nms.core.jms.MessageProducer;
import me.vurt.nms.core.jms.exception.MessageHandleException;
import me.vurt.nms.core.jms.exception.MessageReceiveFailedException;
import me.vurt.nms.core.jms.exception.MessageSendFailedException;
import me.vurt.nms.core.node.AbstractNodeLuncher;
import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.client.exception.RegisterException;
import me.vurt.nms.core.node.util.BeanConstants;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

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
		if (!Node.CURRENT.isRegisted()) {
			MessageProducer regProducer = JMSFactory.createProducer();
			Destination registerQueue= (Destination)ApplicationContextHolder.getBean(BeanConstants.REGISTRATION_QUEUE_BEAN);
			Destination responseQueue = (Destination) ApplicationContextHolder
					.getBean(BeanConstants.REGISTRATION_RESPONSE_QUEUE_BEAN);
			
			RegisterRequest request=DataFactory.createRegisterRequest();
			try {
				LOGGER.info("正在向服务器注册当前节点: group="+request.getGroup()+";id="+request.getId());
				RegisterResponse response =(RegisterResponse)regProducer.sendAndReceive(registerQueue, responseQueue, request);
				if (response == null) {
					// 接收响应信息超时了，重试
					LOGGER.info("注册节点时服务器没有响应，尝试再次发送注册请求");
					register();
				} else {
					if (!response.isSucceed()) {
						LOGGER.error("注册失败，错误信息：" + response.getErrors());
						throw new RegisterException(response.getErrors().toString());
					} else {
						LOGGER.info("注册成功");
					}
				}
			} catch (MessageSendFailedException e) {
			} catch (MessageReceiveFailedException e) {
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
