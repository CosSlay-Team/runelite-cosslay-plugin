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
public class Requirements
{
	@SerializedName("quests")
	private String[] questIds;

	private SkillRequirements skills;

	@SerializedName("quest_points")
	private int questPoints;

	@SerializedName("warriors_guild")
	@Getter(AccessLevel.NONE)
	private boolean needsWarriorsGuild;

	@Setter(AccessLevel.NONE)
	private transient List<Quest> quests;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private boolean dataLoaded = false;

	public boolean needsWarriorsGuild()
	{
		return needsWarriorsGuild;
	}

	public void loadExtraData(Map<String, Quest> questDefinitions)
	{
		if (dataLoaded)
		{
			return;
		}

		ArrayList<Quest> quests = new ArrayList<>();

		if (questIds != null)
		{
			for (String qid : questIds)
			{
				Quest q = questDefinitions.get(qid);

				if (q == null)
				{
					log.error("Missing quest definition: {}", qid);
				}
				else
				{
					quests.add(questDefinitions.get(qid));
				}
			}
		}

		this.quests = ImmutableList.copyOf(quests);

		dataLoaded = true;
	}
}
