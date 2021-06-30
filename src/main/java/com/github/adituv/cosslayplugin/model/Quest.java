package com.github.adituv.cosslayplugin.model;

import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quest
{
	private String name;
	private int id;
	private Requirements requirements;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private boolean dataLoaded = false;

	public void loadExtraData(Map<String, Quest> quests)
	{
		if (!dataLoaded)
		{
			requirements.loadExtraData(quests);
			dataLoaded = true;
		}
	}
}