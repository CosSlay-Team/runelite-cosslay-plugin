package com.github.adituv.cosslayplugin.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quest
{
	private String name;
	private int id;
	private Requirements requirements;
}