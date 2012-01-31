package me.vurt.nms.core.test;

import org.osgi.framework.Bundle;

import me.vurt.nms.core.ogsi.NMSCoreThread;

/**
 * @author yanyl
 *
 */
public class OSGITest {
	public static void main(String[] args) {
		NMSCoreThread thread=new NMSCoreThread();
		thread.setDaemon(true);
		thread.start();
		for(Bundle bundle:thread.getInstalledBundles()){
			System.out.println("id:"+bundle.getBundleId()+",name:"+bundle.getSymbolicName());
		}
	}
}
