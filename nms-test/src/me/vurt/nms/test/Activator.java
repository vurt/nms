package me.vurt.nms.test;

import me.vurt.nms.core.AbstractActivator;
import me.vurt.nms.core.EmptyLuncher;
import me.vurt.nms.core.Luncher;

public class Activator extends AbstractActivator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.AbstractActivator#getLuncher()
	 */
	@Override
	protected Luncher getLuncher() {
		return new EmptyLuncher();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.AbstractActivator#doStart()
	 */
	@Override
	public void doStart() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.vurt.nms.core.AbstractActivator#doStop()
	 */
	@Override
	public void doStop() {

	}

}
