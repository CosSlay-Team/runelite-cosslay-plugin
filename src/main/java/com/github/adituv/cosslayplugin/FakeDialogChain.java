package com.github.adituv.cosslayplugin;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.widgets.Widget;
import net.runelite.client.game.chatbox.ChatboxPanelManager;

@Slf4j
public class FakeDialogChain
{
	private final ChatboxPanelManager chatboxPanelManager;

	private FakeDialogInput firstDialog;
	private FakeDialogInput currentDialog;
	private FakeDialogInput lastDialog;

	private boolean shouldAdvance;

	@Inject
	public FakeDialogChain(ChatboxPanelManager chatboxPanelManager)
	{
		this.chatboxPanelManager = chatboxPanelManager;
	}

	/**
	 * Add a new unbuilt FakeDialogInput to the end of the current dialog chain
	 * @param next The next dialog input to show
	 * @return This FakeDialogManager to allow chaining calls
	 */
	public FakeDialogChain append(FakeDialogInput next)
	{
		if (lastDialog != null)
		{
			lastDialog.next(next);
		}
		else
		{
			firstDialog = next;
			currentDialog = next;
			lastDialog = next;
		}

		next.setParent(this);

		return this;
	}

	public void advance()
	{
		shouldAdvance = true;
	}

	public void show()
	{
		firstDialog.validate();
		chatboxPanelManager.openInput(firstDialog);
	}

	/**
	 * Handles the current dialog state, and advancing to the next dialog.  Should be called once every game tick.
	 */
	public void update()
	{
		if (shouldAdvance)
		{

			log.debug("Advancing dialog...");
			shouldAdvance = false;
			currentDialog = currentDialog.getNext();

			if (currentDialog != null)
			{
				currentDialog.validate();
				chatboxPanelManager.openInput(currentDialog);
			}
			else
			{
				chatboxPanelManager.close();
			}
		}
	}

	/**
	 * Immediately cancels the current dialog
	 */
	public void abort()
	{
		chatboxPanelManager.close();
		currentDialog = firstDialog;
	}

	public Widget getContainerWidget()
	{
		return chatboxPanelManager.getContainerWidget();
	}
}
