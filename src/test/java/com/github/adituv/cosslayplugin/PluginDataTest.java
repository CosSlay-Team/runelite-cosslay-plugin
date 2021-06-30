package com.github.adituv.cosslayplugin;

import com.github.adituv.cosslayplugin.model.PluginData;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import org.junit.Test;

public class PluginDataTest
{
	private Injector injector;
	private PluginData pluginData;

	public PluginDataTest()
	{
		this.injector = Guice.createInjector();
		this.pluginData = injector.getInstance(PluginData.class);
	}

	// Temporary gigantic integration test
	@Test
	public void loadFromFilesWorks() throws IOException
	{
		this.pluginData.loadFromFiles();
		assert pluginData != null;
	}
}
