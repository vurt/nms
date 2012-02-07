package me.vurt.nms.core.ogsi;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class NMSActivator implements BundleActivator {

	 private BundleContext m_context = null;

	    public void start(BundleContext context)
	    {
	        m_context = context;
	    }

	    public void stop(BundleContext context)
	    {
	        m_context = null;
	    }

	    public Bundle[] getBundles()
	    {
	        if (m_context != null)
	        {
	            return m_context.getBundles();
	        }
	        return null;
	    }

}
