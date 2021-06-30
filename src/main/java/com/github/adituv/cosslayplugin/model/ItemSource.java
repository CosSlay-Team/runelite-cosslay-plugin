package com.github.adituv.cosslayplugin.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ItemSource
{
	private ItemMethod type;

	@SerializedName("source_name")
	private String sourceName;

	@SerializedName("drop_rate")
	private float dropRate;

	@SerializedName("ingredients")
	private List<String> ingredientIds;

	private transient List<CostumeItem> ingredients;

	private int price;

	private Location location;

	private Requirements requirements;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private boolean dataLoaded = false;

	public void loadExtraData(Map<String, CostumeItem> items, Map<String, Quest> quests)
	{
		if (dataLoaded)
		{
			return;
		}

		ingredients = new ArrayList<CostumeItem>();

		if (ingredientIds != null)
		{
			for (String id : ingredientIds)
			{
				CostumeItem i = items.get(id);

				if (i != null)
				{
					ingredients.add(items.get(id));
				}
				else
				{
					log.error("Missing item definition: {}", id);
				}
			}
		}

		requirements.loadExtraData(quests);

		dataLoaded = true;
	}
}
