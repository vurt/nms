package me.vurt.nms.core.jms.util;

import javax.jms.Destination;

/**
 * ©уд©╣д╣ь
 * @author yanyl
 *
 */
public class EmptyDestination implements Destination {
	public static final Destination INSTANCE=new EmptyDestination();
}
