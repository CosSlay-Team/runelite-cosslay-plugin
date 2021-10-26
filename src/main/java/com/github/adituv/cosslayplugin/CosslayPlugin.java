package com.github.adituv.cosslayplugin;

import com.google.inject.Provides;
import javax.inject.Inject;
import javax.inject.Provider;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.PlayerChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.chatbox.ChatboxPanelManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "CosSlay"
)
public final class CosslayPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ChatboxPanelManager chatboxPanelManager;

	@Inject
	private Provider<FakeDialogInput> fakeDialogInputProvider;

	@Inject
	private CosslayConfig config;

	private boolean isTransformActive = false;
	private int transformNpcId = -1;

	@Provides
	CosslayConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CosslayConfig.class);
	}

	@Override
	protected void startUp()
	{
		isTransformActive = config.isTransformActive();
		transformNpcId = config.transformNpcId();
	}

	@Subscribe
	private void onConfigChanged(ConfigChanged e)
	{
		if (e.getKey().equals("isTransformActive"))
		{
			isTransformActive = config.isTransformActive();
			applyPlayerTransform();
		}
		else if (e.getKey().equals("transformNpcId"))
		{
			transformNpcId = config.transformNpcId();
			applyPlayerTransform();
		}
	}

	@Subscribe
	private void onPlayerChanged(PlayerChanged e)
	{
		applyPlayerTransform();
	}

	private void applyPlayerTransform()
	{
		Player p = client.getLocalPlayer();
		if (p == null)
		{
			return;
		}

		PlayerComposition playerComposition = p.getPlayerComposition();
		assert playerComposition != null;

		if (isTransformActive)
		{
			playerComposition.setTransformedNpcId(transformNpcId);
		}
		else
		{
			playerComposition.setTransformedNpcId(-1);
		}

		playerComposition.setHash();
	}
}
