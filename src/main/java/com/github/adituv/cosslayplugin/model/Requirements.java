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
public class Requirements
{
	@SerializedName("quests")
	private String[] questIds;
	private SkillRequirements skills;

	@SerializedName("quest_points")
	private int questPoints;

	@SerializedName("warriors_guild")
	private boolean needsWarriorsGuild;

	@Expose(deserialize = false)
	private List<Quest> quests;

	public void loadQuests(Map<String, Quest> questDefinitions)
	{
		ArrayList<Quest> quests = new ArrayList<>();
		for (String qid : questIds)
		{
			quests.add(questDefinitions.get(qid));
		}

		this.quests = ImmutableList.copyOf(quests);
	}
}
