package com.github.adituv.cosslayplugin;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup("cosslay")
public interface CosslayConfig extends Config
{
	@ConfigSection(
		name = "NPC Transformation",
		description = "Controls for transforming the player into an NPC",
		position = 0
	)
	static final String transformSection = "NPC Transformation";

	@ConfigItem(
		name = "Active",
		keyName = "isTransformActive",
		description = "Should the player be transformed right now?",
		section = "NPC Transformation"
	)
	default boolean isTransformActive()
	{
		return false;
	}

	@ConfigItem(
		name = "NPC ID",
		keyName = "transformNpcId",
		description = "The numerical identifier of the NPC to transform the player into",
		section = "NPC Transformation"
	)
	@Range(
		min = -1
	)
	default int transformNpcId()
	{
		return -1;
	}
}
