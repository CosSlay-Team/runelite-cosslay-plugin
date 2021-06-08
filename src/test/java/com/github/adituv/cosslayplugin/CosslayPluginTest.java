package com.github.adituv.cosslayplugin;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class CosslayPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(CosslayPlugin.class);
		RuneLite.main(args);
	}
}