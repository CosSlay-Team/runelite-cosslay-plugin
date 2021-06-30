package com.github.adituv.cosslayplugin.model;

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostumeNpc
{
	private int id;
	private String name;
	private Equipment equipment;
	private Location location;
	private Requirements requirements;

	@SerializedName("inventory")
	private List<String> inventoryIds;

	@Expose(deserialize = false)
	private List<CostumeItem> inventory;

	public void loadItems(Map<String, CostumeItem> items)
	{
		this.equipment.loadItems(items);

		if (inventoryIds == null)
		{
			return;
		}

		ArrayList<CostumeItem> inventory = new ArrayList<>();

		for (String item : inventoryIds)
		{
			inventory.add(items.get(item));
		}

		this.inventory = ImmutableList.copyOf(inventory);
	}
}
