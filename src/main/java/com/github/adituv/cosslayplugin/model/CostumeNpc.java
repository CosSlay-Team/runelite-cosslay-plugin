package com.github.adituv.cosslayplugin.model;

import com.google.common.collect.ImmutableList;
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
public class CostumeNpc
{
	private int id;
	private String name;
	private Equipment equipment;
	private Location location;
	private Requirements requirements;

	@SerializedName("inventory")
	private List<String> inventoryIds;

	private transient List<CostumeItem> inventory;


	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private boolean dataLoaded = false;

	public void loadExtraData(Map<String, CostumeItem> itemDefinitions, Map<String, Quest> quests)
	{
		if (!dataLoaded)
		{
			this.equipment.loadExtraData(itemDefinitions);

			ArrayList<CostumeItem> inventory = new ArrayList<>();

			for (String item : inventoryIds)
			{
				CostumeItem i = itemDefinitions.get(item);

				if (i != null)
				{
					inventory.add(itemDefinitions.get(item));
				}
				else
				{
					log.error("Missing item definition: {}", item);
				}
			}

			this.inventory = ImmutableList.copyOf(inventory);

			this.requirements.loadExtraData(quests);

			dataLoaded = true;
		}
	}
}
