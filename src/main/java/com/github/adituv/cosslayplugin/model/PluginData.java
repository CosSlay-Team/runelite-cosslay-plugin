package com.github.adituv.cosslayplugin.model;

import com.github.adituv.cosslayplugin.util.EnumCaseInsensitiveTypeAdapter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Singleton;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class PluginData
{
	@Getter(AccessLevel.NONE)
	private final Gson gson;

	private HashMap<String, CostumeNpc> npcDefinitions;
	private HashMap<String, CostumeItem> itemDefinitions;

	@Getter
	@Setter
	private String dataFolder = ".";

	private PluginData()
	{
		this.npcDefinitions = null;
		this.itemDefinitions = null;

		GsonBuilder gsonBuilder = new GsonBuilder();

		// Register the case-insensitive adapter for all enums in the model
		gsonBuilder.registerTypeAdapter(KitColor.class, new EnumCaseInsensitiveTypeAdapter<>(KitColor.class));
		gsonBuilder.registerTypeAdapter(KitPart.class, new EnumCaseInsensitiveTypeAdapter<>(KitPart.class));
		gsonBuilder.registerTypeAdapter(ItemMethod.class, new EnumCaseInsensitiveTypeAdapter<>(ItemMethod.class));

		this.gson = gsonBuilder.create();
	}

	public Map<String, CostumeNpc> getNpcDefinitions()
	{
		return Collections.unmodifiableMap(npcDefinitions);
	}

	public Map<String, CostumeItem> getItemDefinitions()
	{
		return Collections.unmodifiableMap(itemDefinitions);
	}

	public void loadFromFiles() throws FileNotFoundException, InvalidPathException, IOException
	{
		final Type itemsMapType = new TypeToken<HashMap<String, CostumeItem>>()
		{
		}.getType();

		final Type npcMapType = new TypeToken<HashMap<String, CostumeNpc>>()
		{
		}.getType();

		String itemsPath = Path.of(dataFolder, "items.json").toString();
		String npcsPath = Path.of(dataFolder, "npcs.json").toString();

		try (Reader itemsReader = new FileReader(itemsPath))
		{
			itemDefinitions = gson.fromJson(itemsReader, itemsMapType);
		}

		try (BufferedReader npcsReader = new BufferedReader(new FileReader(npcsPath)))
		{
			npcDefinitions = gson.fromJson(npcsReader, npcMapType);
		}

		for (CostumeNpc n : npcDefinitions.values())
		{
			n.loadItems(itemDefinitions);
		}
	}
}
