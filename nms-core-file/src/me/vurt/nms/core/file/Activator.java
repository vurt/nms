package me.vurt.nms.core.file;

import me.vurt.nms.core.AbstractActivator;
import me.vurt.nms.core.EmptyLuncher;
import me.vurt.nms.core.Luncher;

public class Activator extends AbstractActivator {

	@Override
	protected Luncher getLuncher() {
		// TODO Auto-generated method stub
		return new EmptyLuncher();
	}

	@Override
	public void doStart() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doStop() {
		// TODO Auto-generated method stub
		
	}

}
