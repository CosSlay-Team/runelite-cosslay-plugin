package com.github.adituv.cosslayplugin;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.FontID;
import net.runelite.api.FontTypeFace;
import net.runelite.api.Player;
import net.runelite.api.PlayerComposition;
import net.runelite.api.widgets.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.chatbox.ChatboxInput;

@Slf4j
public class FakeDialogInput extends ChatboxInput
{
	protected static final int CHATHEAD_MODEL_ZOOM = 796;
	protected static final int CHATHEAD_WIDTH = 32;
	protected static final int CHATHEAD_HEIGHT = 32;
	protected static final int TEXT_WIDTH = 380;
	protected static final int TEXT_LINE_HEIGHT = 17;
	protected static final int TEXT_BLOCK_HEIGHT = 67;

	private static final int KC_SPACE = 83;

	private final Client client;
	private final ClientThread clientThread;

	private Widget continueButton;

	@Setter
	private FakeDialogChain parent;

	@Getter
	private int npcId;

	@Getter
	private int fakePlayerHash;

	@Getter
	private boolean isPlayer;

	@Getter
	private int[] equipmentOverrides;

	@Getter
	private int[] colorOverrides;

	@Getter
	private DialogType dialogType;

	@Getter
	private String speakerName;

	@Getter
	private String message;

	@Getter
	private FakeDialogInput next;

	@Getter
	private Runnable onClose;

	@Inject
	protected FakeDialogInput(Client client, ClientThread clientThread)
	{
		this.client = client;
		this.clientThread = clientThread;

		this.npcId = -1;
		this.fakePlayerHash = -1;
		this.isPlayer = false;
		this.equipmentOverrides = null;
		this.colorOverrides = null;
		this.dialogType = null;
		this.speakerName = null;
		this.message = null;
		this.onClose = null;
	}

	public FakeDialogInput npc(int npcId)
	{
		this.npcId = npcId;
		this.isPlayer = false;
		return this;
	}

	/**
	 * Sets the fake player hash.  This is passed into the chat head's "Model ID" field, where it is used to determine
	 * which of the cached player heads are shown.  The value must be unique for each separate appearance, and should
	 * be -1 for the default player appearance.
	 *
	 * @param fakePlayerHash The fake hash to set
	 * @return The Fake Dialog Input being built
	 */
	public FakeDialogInput fakePlayerHash(int fakePlayerHash)
	{
		this.fakePlayerHash = fakePlayerHash;
		return this;
	}

	public FakeDialogInput player()
	{
		this.isPlayer = true;
		this.npcId = -1;
		return this;
	}

	public FakeDialogInput speakerName(String speakerName)
	{
		this.speakerName = speakerName;
		return this;
	}

	public FakeDialogInput message(String message)
	{
		this.message = message;
		return this;
	}

	public FakeDialogInput type(DialogType type)
	{
		this.dialogType = type;
		return this;
	}

	public FakeDialogInput overrides(int[] equipmentOverrides, int[] colorOverrides)
	{
		this.equipmentOverrides = equipmentOverrides;
		this.colorOverrides = colorOverrides;
		return this;
	}

	public FakeDialogInput next(FakeDialogInput next)
	{
		this.next = next;
		return this;
	}

	public FakeDialogInput onClose(Runnable onClose)
	{
		this.onClose = onClose;
		return this;
	}

	public void validate() throws IllegalStateException
	{
		if (this.npcId == -1 ^ this.isPlayer)
		{
			throw new IllegalStateException("Exactly one of NPC or Player must be set");
		}
		if (this.message == null)
		{
			throw new IllegalStateException("Message must be set");
		}
		if (this.dialogType == null)
		{
			throw new IllegalStateException("Dialog type must be set");
		}
		if (this.equipmentOverrides != null && this.equipmentOverrides.length != 12)
		{
			throw new IllegalStateException("Equipment overrides must be null or of length 12");
		}
		if (this.colorOverrides != null && this.colorOverrides.length != 5)
		{
			throw new IllegalStateException("Color overrides must be null or of length 5");
		}
	}

	@Override
	protected void open()
	{
		Widget container = parent.getContainerWidget();

		createChatHead(container);
		createSpeakerName(container);
		createContinueButton(container);
		createDialogText(container);
	}

	private void createDialogText(Widget container)
	{
		Widget w = container.createChild(-1, WidgetType.TEXT);
		w.setText(this.message);
		w.setXTextAlignment(WidgetTextAlignment.CENTER);
		w.setYTextAlignment(WidgetTextAlignment.CENTER);
		w.setFontId(FontID.QUILL_8);

		w.setOriginalX(dialogType.getTextX());
		w.setOriginalY(dialogType.getTextY());
		w.setOriginalWidth(TEXT_WIDTH);
		w.setOriginalHeight(TEXT_BLOCK_HEIGHT);

		int lineHeight = getLineHeightForText(w);
		w.setLineHeight(lineHeight);

		w.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
		w.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		w.setWidthMode(WidgetSizeMode.ABSOLUTE);
		w.setHeightMode(WidgetSizeMode.ABSOLUTE);


		w.revalidate();
	}

	private void createContinueButton(Widget container)
	{
		continueButton = container.createChild(-1, WidgetType.TEXT);
		continueButton.setText("Click here to continue");
		continueButton.setFontId(FontID.QUILL_8);
		continueButton.setXTextAlignment(WidgetTextAlignment.CENTER);
		continueButton.setYTextAlignment(WidgetTextAlignment.TOP);
		continueButton.setTextColor(0x0000FF);

		continueButton.setOriginalX(dialogType.getTextX());
		continueButton.setOriginalY(dialogType.getContinueY());
		continueButton.setOriginalWidth(TEXT_WIDTH);
		continueButton.setOriginalHeight(TEXT_LINE_HEIGHT);

		continueButton.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
		continueButton.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		continueButton.setWidthMode(WidgetSizeMode.ABSOLUTE);
		continueButton.setHeightMode(WidgetSizeMode.ABSOLUTE);

		continueButton.setAction(0, "Continue");

		continueButton.setOnOpListener((JavaScriptCallback) e ->
		{
			log.debug("\"Click here to continue\" clicked.");

			continueButton.setText("Please wait...");
			parent.advance();
		});
		continueButton.setOnMouseOverListener((JavaScriptCallback) e ->
			continueButton.setTextColor(0xFFFFFF)
		);
		continueButton.setOnMouseLeaveListener((JavaScriptCallback) e ->
			continueButton.setTextColor(0xFF)
		);
		continueButton.setOnKeyListener((JavaScriptCallback) e ->
			{
				if (e.getTypedKeyCode() == KC_SPACE)
				{
					continueButton.setText("Please wait...");

					parent.advance();
				}
			});
		continueButton.setHasListener(true);

		continueButton.revalidate();
	}

	private void createSpeakerName(Widget container)
	{
		if (this.speakerName == null)
		{
			if (this.isPlayer)
			{
				Player p = client.getLocalPlayer();

				if (p == null)
				{
					this.speakerName = "<PLAYER>";
				}
				else
				{
					this.speakerName = p.getName();
				}
			}
			else
			{
				this.speakerName = client.getNpcDefinition(this.npcId).getName();
			}
		}

		Widget w = container.createChild(-1, WidgetType.TEXT);
		w.setText(this.speakerName);
		w.setTextColor(0x800000);
		w.setFontId(FontID.QUILL_8);
		w.setXTextAlignment(WidgetTextAlignment.CENTER);
		w.setYTextAlignment(WidgetTextAlignment.TOP);

		w.setOriginalX(dialogType.getTextX());
		w.setOriginalY(dialogType.getNameY());
		w.setOriginalWidth(TEXT_WIDTH);
		w.setOriginalHeight(TEXT_LINE_HEIGHT);

		w.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
		w.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		w.setWidthMode(WidgetSizeMode.ABSOLUTE);
		w.setHeightMode(WidgetSizeMode.ABSOLUTE);

		w.revalidate();
	}

	private void createChatHead(Widget container)
	{
		final int TALKING_ANIMATION_ID = 568;

		Widget w = container.createChild(-1, WidgetType.MODEL);

		if (this.isPlayer)
		{
			w.setModelType(WidgetModelType.LOCAL_PLAYER_CHATHEAD);
		}
		else
		{
			w.setModelType(WidgetModelType.NPC_CHATHEAD);
			w.setModelId(npcId);
		}

		w.setModelZoom(CHATHEAD_MODEL_ZOOM);
		w.setAnimationId(TALKING_ANIMATION_ID);

		w.setRotationX(dialogType.getHeadRotX());
		w.setRotationY(dialogType.getHeadRotY());
		w.setRotationZ(dialogType.getHeadRotZ());

		w.setOriginalX(dialogType.getHeadX());
		w.setOriginalY(dialogType.getHeadY());
		w.setOriginalWidth(CHATHEAD_WIDTH);
		w.setOriginalHeight(CHATHEAD_HEIGHT);

		w.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
		w.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		w.setWidthMode(WidgetSizeMode.ABSOLUTE);
		w.setHeightMode(WidgetSizeMode.ABSOLUTE);

		if (this.isPlayer)
		{
			Player p = client.getLocalPlayer();

			if (p == null)
			{
				w.revalidate();
				return;
			}

			PlayerComposition playerDef = client.getLocalPlayer().getPlayerComposition();
			final int[] storedEquipmentIds = playerDef.getEquipmentIds().clone();
			final int[] storedColors = playerDef.getColors().clone();

			if (equipmentOverrides != null)
			{
				setArrayContents(playerDef.getEquipmentIds(), equipmentOverrides);
			}

			if (colorOverrides != null)
			{
				setArrayContents(playerDef.getColors(), colorOverrides);
			}

			if (fakePlayerHash != -1)
			{
				w.setModelId(fakePlayerHash);
			}

			clientThread.invokeLater(() ->
			{
				setArrayContents(playerDef.getEquipmentIds(), storedEquipmentIds);
				setArrayContents(playerDef.getColors(), storedColors);
				playerDef.setHash();
			});
		}

		w.revalidate();
	}

	private void setArrayContents(int[] originalArray, int[] newContents)
	{
		for (int i = 0; i < newContents.length; i++)
		{
			if (newContents[i] >= 0)
			{
				originalArray[i] = newContents[i];
			}
		}
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
