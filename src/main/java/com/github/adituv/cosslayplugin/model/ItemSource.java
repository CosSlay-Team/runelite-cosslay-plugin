package com.github.adituv.cosslayplugin.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSource
{
	private ItemMethod type;

	@SerializedName("source_name")
	private String sourceName;

	@SerializedName("drop_rate")
	private float dropRate;

	private List<String> ingredients;

	private int price;

	private Location location;

	private Requirements requirements;
}
