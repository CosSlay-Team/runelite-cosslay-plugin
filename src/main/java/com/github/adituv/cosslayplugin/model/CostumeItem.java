package com.github.adituv.cosslayplugin.model;

import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostumeItem
{
	private int[] ids;
	private List<ItemSource> sources;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private boolean dataLoaded = false;

	public int getPrimaryId()
	{
		if (ids.length == 0)
		{
			throw new IllegalStateException("Item has no ids");
		}
		else
		{
			return ids[0];
		}
	}

	public void loadExtraData(Map<String, CostumeItem> items, Map<String, Quest> quests)
	{
		if (!dataLoaded)
		{
			for (ItemSource s : sources)
			{
				s.loadExtraData(items, quests);
			}

			dataLoaded = true;
		}
	}
}
