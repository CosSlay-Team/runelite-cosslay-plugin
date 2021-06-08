# CosSlay Notes
*These notes are a draft of the gamemode rules.  Nothing in here is final.*

## Account Summary
The CosSlayer is an Ironman who completes Slayer Tasks on behalf of various
NPCs throughout the game.  As part of this, you take on their full appearance
and equipment for that single task.

(Potential lore - they're paying you to do the task for them?  Maybe to avoid
spending slayer points?)

## NPCs and Tiers
The NPCs that you help are divided into tiers of difficulty.  The current tier
depends on combat or slayer level.  The tiers are as follows:

| Tier     | Level        | Master              |
|----------|--------------|---------------------|
| Beginner | 3-39 Combat  | Turael/Spria        |
| Easy     | 40-69 Combat | Vannaka             |
| Medium   | 70-99 Combat | Chaeldar            |
| Hard     | 100+ Combat  | Konar/Nieve/Duradel |
| Elite    | 75 Slayer    | Konar/Nieve/Duradel |
| Master   | 91 Slayer    | Konar/Nieve/Duradel |

In the case of Hard through Master tiers, more than one tier can be active at
a time.  For example, when you reach 75 slayer, both the Hard and Elite tiers
are active; similarly, at 91 slayer all three tiers are active simultaneously.

## Progression
The core gameplay loop goes like this:
* Select an NPC from the current tier(s)
* Change appearance and gather gear to match the NPC
* Talk to the NPC
* Get and complete a slayer task in that NPC's gear
    * If a task requires specialist gear, such as earmuffs, that may be worn 
      instead of the NPC's gear in that slot only
* Free Time
    * 15 minutes of free time is given for each total task completed.  For
      example, the first task gives 15 minutes; the third task gives 45 
      minutes.  The free time may be used to do any activity except Slayer.

## Acquiring Gear
When acquiring gear, the acquisition method is chosen randomly between
available methods.  A method is considered available if it is possible if it
requires the completion of at most one quest, and does not require gaining a
Slayer level.

For the purpose of choosing the acquisition method, there are four general
"method groups":

1. Item spawn
2. Bought from a shop
3. Created through skilling
4. From any random drop table

If the item is available through method 1 or method 2 without gaining ANY xp,
the item may be obtained through any such method.  Otherwise, the acquisition
process is randomised with the following algorithm:

* A method group is selected with uniform probability from those which have at
  least one available method.
    * For example, Monk robe cannot roll method #4 (chest in Hallowed
      Sepulchre) unless Sins of the Father has already been completed
* Any available method from that group is selected with uniform probability

### Creation through Skilling
When creating an item through skilling, the gathering skills must be trained
in their entirety before the artisan skills.

For the these purposes, the gathering skills are the ones required to obtain
the raw materials for the item, and artisan skills are any and all skills
required to process the materials towards the final item.

### Treasure Trails
Treasure Trail caskets are considered an available source, and therefore
possible for random selection, if the CosSlayer has a clue scroll of the
required tier, or if the item is only available through Treasure Trails.

Items may be obtained for an emote clue in the following ways:
* Method groups 1, 2 or 3 if no levels or quests are required.
* Method group 4 if the item is no rarer than 1/64, up to 64kc is allowed.
  If the item is obtained before 64kc, no further kc is allowed.  If the
  item is not obtained at 64kc, the clue must be dropped.

## Free Activities
* Quests may be completed at any time, provided all requirements are met
* XP gained incidentally through transport around the game is permitted at all
  times.  For example, magic xp from teleports (but not spamming for
  training!), or woodcutting xp from canoes.
* If the CosSlayer loses a part of their costume, such as to a pker, they must
  reobtain it using any available method.  The random process does not apply.
    * XP-less methods are mandatory if possible
    * If the item is only available through method group 4, the highest
      non-boss drop rate method should be used.
    * Any other items obtained while regaining a costume piece must not be
      kept.