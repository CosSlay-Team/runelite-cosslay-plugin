package com.github.adituv.cosslayplugin.model;

import com.github.adituv.cosslayplugin.util.EnumCaseInsensitiveTypeAdapter;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Singleton;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Singleton
@Slf4j
public class PluginData {
    @Getter(AccessLevel.NONE)
    private Gson gson;

    private ImmutableMap<String, CostumeNpc> costumeDefinitions;
    private ImmutableMap<String, CostumeItem> itemDefinitions;
    private ImmutableMap<String, ItemSource> sourceDefinitions;

    private PluginData instance;

    private PluginData() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Register the case-insensitive adapter for all enums in the model
        gsonBuilder.registerTypeAdapter(KitColor.class, new EnumCaseInsensitiveTypeAdapter<KitColor>(KitColor.class));
        gsonBuilder.registerTypeAdapter(KitPart.class, new EnumCaseInsensitiveTypeAdapter<KitPart>(KitPart.class));
        gsonBuilder.registerTypeAdapter(ItemMethod.class, new EnumCaseInsensitiveTypeAdapter<ItemMethod>(ItemMethod.class));

        this.gson = gsonBuilder.create();
    }

    public PluginData getInstance() {
        if (instance == null) {
            instance = new PluginData();
        }

        return instance;
    }
}
