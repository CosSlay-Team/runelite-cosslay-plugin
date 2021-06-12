package com.github.adituv.cosslayplugin;

import javax.inject.Inject;
import javax.inject.Provider;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.InteractingChanged;
import net.runelite.api.events.WidgetClosed;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.chatbox.ChatboxPanelManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "CosSlay"
)
public class CosslayPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ChatboxPanelManager chatboxPanelManager;

	@Inject
	private Provider<FakeNpcDialogInput> fakeNpcDialogInputProvider;

	private int npcToOverride = NpcID.GRAND_EXCHANGE_CLERK;
	private Widget originalDialog = null;
	private boolean overrideDialogShowing = false;

	// state to detect a new dialog with an npc
	private NPC playerInteractingWith = null;
	private boolean waitingForDialog = false;

	@Subscribe
	private void onInteractingChanged(InteractingChanged e)
	{
		if (e.getSource().equals(client.getLocalPlayer()))
		{
			Actor target = e.getTarget();
			if (target instanceof NPC)
			{
				playerInteractingWith = (NPC) target;
				log.debug("Player interacting with: {}", playerInteractingWith.getName());

				waitingForDialog = true;
			}
			else
			{
				playerInteractingWith = null;
			}
		}
	}

	@Subscribe
	private void onWidgetLoaded(WidgetLoaded e)
	{

		if (waitingForDialog && isDialogWidget(e.getGroupId()))
		{
			log.debug("Dialog started with {}", playerInteractingWith.getName());
			waitingForDialog = false;

			if (playerInteractingWith.getId() == npcToOverride)
			{

				originalDialog = client.getWidget(e.getGroupId(), 0);
				originalDialog.setHidden(true);

				fakeNpcDialogInputProvider
					.get()
					.npc(npcToOverride)
					.message("This is a fake dialog injected right before the real<br>npc dialog starts.")
					.onClose(() ->
						originalDialog.setHidden(false)
					).build();
			}
		}
	}

	@Subscribe
	private void onWidgetClosed(WidgetClosed e)
	{
		if (overrideDialogShowing && isDialogWidget(e.getGroupId()))
		{
			chatboxPanelManager.getContainerWidget().deleteAllChildren();
		}
	}

	private boolean isDialogWidget(int groupId)
	{
		final int DIALOG_NPC_GROUP_ID = WidgetInfo.DIALOG_NPC.getGroupId();
		final int DIALOG_PLAYER_GROUP_ID = WidgetInfo.DIALOG_PLAYER.getGroupId();
		final int DIALOG_OPTION_GROUP_ID = WidgetInfo.DIALOG_OPTION.getGroupId();

		return (
			groupId == DIALOG_NPC_GROUP_ID
				|| groupId == DIALOG_PLAYER_GROUP_ID
				|| groupId == DIALOG_OPTION_GROUP_ID
		);
	}

}
