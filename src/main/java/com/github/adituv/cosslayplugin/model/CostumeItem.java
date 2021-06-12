package com.github.adituv.cosslayplugin.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostumeItem
{
	private int id;
	private List<ItemSource> sources;
}
