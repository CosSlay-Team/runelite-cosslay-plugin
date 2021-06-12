package com.github.adituv.cosslayplugin.model;

import com.google.gson.annotations.SerializedName;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Equipment
{
	@SerializedName("head")
	private String headId;

	@SerializedName("cape")
	private String capeId;

	@SerializedName("neck")
	private String neckId;

	@SerializedName("top")
	private String topId;

	@SerializedName("weapon")
	private String weaponId;

	@SerializedName("shield")
	private String shieldId;

	@SerializedName("legs")
	private String legsId;

	@SerializedName("hands")
	private String handsId;

	@SerializedName("boots")
	private String bootsId;

	private CostumeItem head;
	private CostumeItem cape;
	private CostumeItem neck;
	private CostumeItem top;
	private CostumeItem weapon;
	private CostumeItem shield;
	private CostumeItem legs;
	private CostumeItem hands;
	private CostumeItem boots;

	public void loadItems(Map<String, CostumeItem> items)
	{
		this.head = items.get(headId);
		this.cape = items.get(capeId);
		this.neck = items.get(neckId);
		this.top = items.get(topId);
		this.weapon = items.get(weaponId);
		this.shield = items.get(shieldId);
		this.legs = items.get(legsId);
		this.hands = items.get(handsId);
		this.boots = items.get(bootsId);
	}
}
