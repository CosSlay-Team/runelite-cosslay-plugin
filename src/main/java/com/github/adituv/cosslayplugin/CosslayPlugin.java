package com.github.adituv.cosslayplugin;

import com.github.adituv.cosslayplugin.model.KitColor;
import javax.inject.Inject;
import javax.inject.Provider;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.InteractingChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
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

	private boolean shouldOverrideDialog = true;
	private FakeDialogChain cosmoDialogChain;

	@Override
	protected void startUp() throws Exception
	{
		cosmoDialogChain = createCosmoDialogChain();
	}

	private FakeDialogChain createCosmoDialogChain()
	{
		final int FAKEHASH_COSMO = 2;

		int[] cosmoEquips = new int[] {ItemID.MASK_OF_BALANCE + 512, -1, -1, -1, -1, -1, -1, -1, 256, -1, -1, -1};
		int[] cosmoColors = new int[] {-1, -1, -1, -1, KitColor.COLOR_BLACK.getSkinId()};

		FakeDialogInput dialog1 = fakeDialogInputProvider.get()
			.type(DialogType.DIALOG_HEAD_LEFT)
			.player()
			.speakerName("Cosmo")
			.overrides(cosmoEquips, cosmoColors)
			.fakePlayerHash(FAKEHASH_COSMO)
			.message("You are not ready.  I will contact you when it is<br>time.");

		FakeDialogInput dialog2 = fakeDialogInputProvider.get()
			.type(DialogType.DIALOG_HEAD_RIGHT)
			.player()
			.message("Well, that was strange.");

		FakeDialogChain cosmoChain = new FakeDialogChain(chatboxPanelManager);
		cosmoChain.append(dialog1);
		cosmoChain.append(dialog2);

		return cosmoChain;
	}

	@Subscribe
	private void onInteractingChanged(InteractingChanged e)
	{
		if (e.getSource() == client.getLocalPlayer() && e.getTarget() != null)
		{
			log.debug("Setting dialog override trigger");
			shouldOverrideDialog = true;
		}
	}

	@Subscribe
	private void onWidgetLoaded(WidgetLoaded e)
	{
		if (e.getGroupId() == WidgetID.DIALOG_NPC_GROUP_ID
			|| e.getGroupId() == WidgetID.DIALOG_PLAYER_GROUP_ID
			|| e.getGroupId() == WidgetID.DIALOG_OPTION_GROUP_ID)
		{
			if (shouldOverrideDialog)
			{
				shouldOverrideDialog = false;

				cosmoDialogChain.show();
			}
		}
	}

	@Subscribe
	private void onMenuOptionClicked(MenuOptionClicked e)
	{
		int widgetGroupId = WidgetInfo.TO_GROUP(e.getWidgetId());
		String option = e.getMenuOption();

		// Close custom chatbox panels when the player does anything requiring navigation.  This is to
		// emulate the OnDialogAbortListener which doesn't trigger in all situations
		int VIEWPORT_GROUP_ID = 0;
		if (widgetGroupId == VIEWPORT_GROUP_ID && !option.equals("Cancel") && !option.equals("Examine"))
		{
			cosmoDialogChain.abort();
		}
	}

	@Subscribe
	private void onGameTick(GameTick e)
	{
		cosmoDialogChain.update();
	}
}
