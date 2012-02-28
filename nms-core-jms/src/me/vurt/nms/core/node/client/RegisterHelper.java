package me.vurt.nms.core.node.client;

import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import me.vurt.nms.core.ApplicationContextHolder;
import me.vurt.nms.core.data.DataFactory;
import me.vurt.nms.core.data.RegisterRequest;
import me.vurt.nms.core.data.RegisterResponse;
import me.vurt.nms.core.data.exception.RegisterException;
import me.vurt.nms.core.jms.JMSFactory;
import me.vurt.nms.core.jms.MessageProducer;
import me.vurt.nms.core.jms.exception.MessageHandleException;
import me.vurt.nms.core.jms.exception.MessageReceiveFailedException;
import me.vurt.nms.core.jms.exception.MessageSendFailedException;
import me.vurt.nms.core.node.Node;
import me.vurt.nms.core.node.util.BeanConstants;
import me.vurt.nms.core.node.util.NodeConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author yanyl
 * 
 */
public class RegisterHelper {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RegisterHelper.class);

	private static Destination registerQueue = (Destination) ApplicationContextHolder
			.getBean(BeanConstants.REGISTRATION_QUEUE_BEAN);
	private static Destination responseQueue = (Destination) ApplicationContextHolder
			.getBean(BeanConstants.REGISTRATION_RESPONSE_QUEUE_BEAN);

	/**
	 * 注册，发送注册请求，并等待注册响应，如果服务器无响应则一直重试直至响应为止
	 * 
	 * @throws MessageHandleException
	 *             如果无法完成注册
	 */
	public static void register() throws RegisterException {
		MessageProducer regProducer = JMSFactory.createProducer();
		regProducer.setDeliveryPersistent(false);
		regProducer.setTimeToLive(5000);
		regProducer.setReceiveTimeout(5000);
		RegisterRequest request = DataFactory.createRegisterRequest();
		doRegister(regProducer, request);
	}

	/**
	 * @param regProducer
	 * @param request
	 * @throws RegisterException
	 */
	private static void doRegister(MessageProducer regProducer,
			RegisterRequest request) throws RegisterException {
		try {
			LOGGER.info("正在向服务器注册当前节点: group=" + request.getGroup() + ";id="
					+ request.getId());
			RegisterResponse response = (RegisterResponse) regProducer
					.sendAndReceive(registerQueue, responseQueue, request);
			if (response == null) {
				// 接收响应信息超时了，重试
				LOGGER.info("注册节点时服务器没有响应，尝试再次发送注册请求");
				doRegister(regProducer, request);
			} else {
				if (!response.isSucceed()) {
					LOGGER.error("注册失败，错误信息：" + response.getErrors());
					throw new RegisterException(response.getErrors().toString());
				} else {
					LOGGER.info("连接成功");
					// 每次连接都会返回一个Key
					((ClientNode) Node.CURRENT).setKey(response.getResponse(
							NodeConstants.RESPONSE_NODE_KEY).toString());
				}
			}
		} catch (MessageSendFailedException e) {
			throw new RegisterException("发送注册信息失败", e);
		} catch (MessageReceiveFailedException e) {
			throw new RegisterException("接受注册响应失败", e);
		}
	}
}
