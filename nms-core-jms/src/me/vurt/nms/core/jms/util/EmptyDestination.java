package me.vurt.nms.core.jms.util;

import javax.jms.Destination;

/**
 * ��Ŀ�ĵ�
 * @author yanyl
 *
 */
public class EmptyDestination implements Destination {
	public static final Destination INSTANCE=new EmptyDestination();
}
