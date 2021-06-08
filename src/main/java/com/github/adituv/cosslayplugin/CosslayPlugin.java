package com.github.adituv.cosslayplugin;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.CommandExecuted;
import net.runelite.api.kit.KitType;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.lang.reflect.Field;

@Slf4j
@PluginDescriptor(
	name = "CosSlay"
)
public class CosslayPlugin extends Plugin
{
	@Inject
	private Client client;

}
