package com.github.adituv.cosslayplugin.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Appearance
{
	private NpcSex sex;

	@SerializedName("skin_color")
	private KitColor skinColor;

	@SerializedName("hair_color")
	private KitColor hairColor;

	@SerializedName("torso_color")
	private KitColor torsoColor;

	@SerializedName("legs_color")
	private KitColor legsColor;

	@SerializedName("boot_color")
	private KitColor bootColor;

	private KitPart hair;
	private KitPart beard;
	private KitPart torso;
	private KitPart arms;
	private KitPart legs;
}
