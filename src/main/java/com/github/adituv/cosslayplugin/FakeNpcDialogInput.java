package com.github.adituv.cosslayplugin;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.FontID;
import net.runelite.api.FontTypeFace;
import net.runelite.api.widgets.*;
import net.runelite.client.game.chatbox.ChatboxInput;
import net.runelite.client.game.chatbox.ChatboxPanelManager;

@Slf4j
public class FakeNpcDialogInput extends ChatboxInput
{
	private final ChatboxPanelManager chatboxPanelManager;
	private final Client client;

	@Getter
	private int npcId = -1;

	@Getter
	private String message;

	@Getter
	private Runnable onClose;

	@Inject
	protected FakeNpcDialogInput(Client client, ChatboxPanelManager chatboxPanelManager)
	{
		this.chatboxPanelManager = chatboxPanelManager;
		this.client = client;
	}

	public FakeNpcDialogInput npc(int npcId)
	{
		this.npcId = npcId;
		return this;
	}

	public FakeNpcDialogInput message(String message)
	{
		this.message = message;
		return this;
	}

	public FakeNpcDialogInput onClose(Runnable onClose)
	{
		this.onClose = onClose;
		return this;
	}

	public FakeNpcDialogInput build()
	{
		if (this.npcId == -1)
		{
			throw new IllegalStateException("NPC must be set");
		}
		if (this.message == null)
		{
			throw new IllegalStateException("Message must be set");
		}

		chatboxPanelManager.openInput(this);
		return this;
	}

	@Override
	protected void open()
	{
		Widget container = chatboxPanelManager.getContainerWidget();

		createChatHead(container);
		createNpcName(container);
		createContinueButton(container);
		createDialogText(container);
	}

	private void createDialogText(Widget container)
	{
		Widget dialogText = container.createChild(-1, WidgetType.TEXT);
		dialogText.setText(this.message);
		dialogText.setXTextAlignment(WidgetTextAlignment.CENTER);
		dialogText.setYTextAlignment(WidgetTextAlignment.CENTER);
		dialogText.setFontId(FontID.QUILL_8);

		dialogText.setOriginalX(109);
		dialogText.setOriginalY(32);
		dialogText.setOriginalWidth(380);
		dialogText.setOriginalHeight(67);

		int lineHeight = getLineHeightForText(dialogText);
		dialogText.setLineHeight(lineHeight);

		dialogText.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
		dialogText.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		dialogText.setWidthMode(WidgetSizeMode.ABSOLUTE);
		dialogText.setHeightMode(WidgetSizeMode.ABSOLUTE);


		dialogText.revalidate();
	}

	private void createContinueButton(Widget container)
	{
		Widget continueButton = container.createChild(-2, WidgetType.TEXT);
		continueButton.setText("Click here to continue");
		continueButton.setFontId(FontID.QUILL_8);
		continueButton.setXTextAlignment(WidgetTextAlignment.CENTER);
		continueButton.setYTextAlignment(WidgetTextAlignment.TOP);
		continueButton.setTextColor(0x0000FF);

		continueButton.setOriginalX(109);
		continueButton.setOriginalY(96);
		continueButton.setOriginalWidth(380);
		continueButton.setOriginalHeight(17);

		continueButton.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
		continueButton.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		continueButton.setWidthMode(WidgetSizeMode.ABSOLUTE);
		continueButton.setHeightMode(WidgetSizeMode.ABSOLUTE);

		continueButton.setAction(0, "Continue");
		continueButton.setOnOpListener((JavaScriptCallback) e -> {
			log.debug("Continue clicked");
			if (onClose != null)
			{
				onClose.run();
			}
		});
		continueButton.setOnOpListener((JavaScriptCallback) e ->
		{
			log.debug("\"Click here to continue\" clicked.");

			chatboxPanelManager.close();
		});
		continueButton.setOnMouseOverListener((JavaScriptCallback) e ->
			continueButton.setTextColor(0xFFFFFF)
		);
		continueButton.setOnMouseLeaveListener((JavaScriptCallback) e ->
			continueButton.setTextColor(0xFF)
		);
		continueButton.setHasListener(true);

		continueButton.revalidate();
	}

	private void createNpcName(Widget container)
	{
		Widget npcName = container.createChild(-1, WidgetType.TEXT);
		npcName.setText(client.getNpcDefinition(npcId).getName());
		npcName.setTextColor(0x800000);
		npcName.setFontId(FontID.QUILL_8);
		npcName.setXTextAlignment(WidgetTextAlignment.CENTER);
		npcName.setYTextAlignment(WidgetTextAlignment.TOP);

		npcName.setOriginalX(109);
		npcName.setOriginalY(16);
		npcName.setOriginalWidth(380);
		npcName.setOriginalHeight(17);

		npcName.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
		npcName.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		npcName.setWidthMode(WidgetSizeMode.ABSOLUTE);
		npcName.setHeightMode(WidgetSizeMode.ABSOLUTE);

		npcName.revalidate();
	}

	private void createChatHead(Widget container)
	{
		final int TALKING_ANIMATION_ID = 568;

		Widget chatHead = container.createChild(-1, WidgetType.MODEL);
		chatHead.setModelType(WidgetModelType.NPC_CHATHEAD);
		chatHead.setModelId(npcId);
		chatHead.setModelZoom(796);
		chatHead.setAnimationId(TALKING_ANIMATION_ID);
		chatHead.setRotationX(40);
		chatHead.setRotationY(0);
		chatHead.setRotationZ(1882);

		chatHead.setOriginalX(47);
		chatHead.setOriginalY(50);
		chatHead.setOriginalWidth(32);
		chatHead.setOriginalHeight(32);

		chatHead.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
		chatHead.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		chatHead.setWidthMode(WidgetSizeMode.ABSOLUTE);
		chatHead.setHeightMode(WidgetSizeMode.ABSOLUTE);

		chatHead.revalidate();
	}

	@Override
	protected void close()
	{
		if (onClose != null)
		{
			onClose.run();
		}
	}

	private int getLineHeightForText(Widget w)
	{
		FontTypeFace widgetFont = w.getFont();
		int width = w.getOriginalWidth();
		String message = w.getText();
		String[] forcedLines = message.split("<br>");
		int numLines = 0;
		for (String s : forcedLines)
		{
			int numWraps = widgetFont.getTextWidth(s) / width;
			numLines += numWraps + 1;
		}

		int lineHeight;

		switch (numLines)
		{
			case 2:
				lineHeight = 28;
				break;
			case 3:
				lineHeight = 20;
				break;
			default:
				lineHeight = 16;
		}

		return lineHeight;
	}
}
