package com.github.adituv.cosslayplugin.model;

import javax.annotation.Nullable;

/**
 * Represents the possible colours a kit item (customisable appearance item) can have
 */
public enum KitColor {
    COLOR_DARK_BROWN("Dark Brown", 0, -1, -1, -1, -1),
    COLOR_WHITE("White", 1, 5, 6, -1, 10),
    COLOR_LIGHT_GREY("Light Grey", 2, -1, -1, -1, -1),
    COLOR_DARK_GREY("Dark Grey", 3, -1, -1, -1, -1),
    COLOR_APRICOT("Apricot", 4, -1, -1, -1, -1),
    COLOR_STRAW("Straw", 5, 4, 5, -1, -1),
    COLOR_LIGHT_BROWN("Light Brown", 6, -1, -1, -1, -1),
    COLOR_BROWN("Brown", 7, -1, -1, 0, -1),
    COLOR_TURQUOISE("Turquoise", 8, 26, 26, -1, -1),
    COLOR_GREEN("Green", 9, 8, 9, -1, 8),
    COLOR_GINGER("Ginger", 10, -1, -1, -1, -1),
    COLOR_MAGENTA("Magenta", 11, -1, -1, -1, -1),
    COLOR_BLACK("Black", 12, 16, 16, -1, 9),
    COLOR_GREY("Grey", 13, 17, 17, 5, -1),
    COLOR_BEIGE("Beige", 14, -1, -1, -1, -1),
    COLOR_PEACH("Peach", 15, 19, 19, -1, -1),
    COLOR_LIGHT_BLUE("Light Blue", 16, -1, -1, -1, -1),
    COLOR_ROYAL_BLUE("Royal Blue", 17, -1, -1, -1, -1),
    COLOR_PALE_PINK("Pale Pink", 18, -1, -1, -1, -1),
    COLOR_INTENSE_PINK("Intense Pink", 19, -1, -1, -1, -1),
    COLOR_MAROON("Maroon", 20, 24, 24, -1, -1),
    COLOR_LIGHT_GREEN("Light Green", 21, -1, -1, -1, -1),
    COLOR_DARK_GREEN("Dark Green", 22, -1, -1, -1, -1),
    COLOR_PURPLE("Purple", 23, 10, 11, -1, -1),
    COLOR_LIGHT_PURPLE("Light Purple", 24, 28, 28, -1, -1),
    COLOR_KHAKI("Khaki", -1, 0, 1, 1, -1),
    COLOR_CHARCOAL("Charcoal", -1, 1, 2, -1, -1),
    COLOR_CRIMSON("Crimson", -1, 2, 3, -1, -1),
    COLOR_NAVY("Navy", -1, 3, 4, -1, -1),
    COLOR_RED("Red", -1, 6, 7, -1, -1),
    COLOR_BLUE("Blue", -1, 7, 8, -1, -1),
    COLOR_YELLOW("Yellow", -1, 9, 10, -1, -1),
    COLOR_ORANGE("Orange", -1, 11, 12, -1, -1),
    COLOR_ROSE("Rose", -1, 12, 13, -1, -1),
    COLOR_LIME("Lime", -1, 13, 14, -1, -1),
    COLOR_CYAN("Cyan", -1, 14, 15, -1, -1),
    COLOR_EMERALD("Emerald", -1, 15, 0, -1, -1),
    COLOR_ONION("Onion", -1, 18, 18, -1, -1),
    COLOR_LUMBRIDGE_BLUE("Lumbridge Blue", -1, 20, 20, -1, -1),
    COLOR_DEEP_BLUE("Deep Blue", -1, 21, 21, -1, -1),
    COLOR_LIGHT_PINK("Light Pink", -1, 22, 22, -1, -1),
    COLOR_CADMIUM_RED("Cadmium Red", -1, 23, 23, -1, -1),
    COLOR_PALE_GREEN("Pale Green", -1, 25, 25, -1, -1),
    COLOR_DEEP_PURPLE("Deep Purple", -1, 27, 27, -1, -1),
    COLOR_ASHEN("Ashen", -1, -1, -1, 2, -1),
    COLOR_DARK("Dark", -1, -1, -1, 3, -1),
    COLOR_TERRACOTTA("Terracotta", -1, -1, -1, 4, -1),

    COLOR_SKIN_1("Skin Color 1", -1, -1, -1, -1, 7),
    COLOR_SKIN_2("Skin Color 2", -1, -1, -1, -1, 0),
    COLOR_SKIN_3("Skin Color 3", -1, -1, -1, -1, 1),
    COLOR_SKIN_4("Skin Color 4", -1, -1, -1, -1, 2),
    COLOR_SKIN_5("Skin Color 5", -1, -1, -1, -1, 3),
    COLOR_SKIN_6("Skin Color 6", -1, -1, -1, -1, 4),
    COLOR_SKIN_7("Skin Color 7", -1, -1, -1, -1, 5),
    COLOR_SKIN_8("Skin Color 8", -1, -1, -1, -1, 6),
    COLOR_ZOMBIE_BLUE("Zombie Blue", -1, -1, -1, -1, 11),
    COLOR_TWITCH_PURPLE("Twitch Purple", -1, -1, -1, -1, 12),
    ;


    private String name;
    private int hairId;
    private int topId;
    private int legsId;
    private int bootsId;
    private int skinId;

    KitColor(String name, int hair, int top, int legs, int boots, int skin) {
        this.name = name;
        this.hairId = hair;
        this.topId = top;
        this.legsId = legs;
        this.bootsId = boots;
        this.skinId = skin;
    }

    public String getName() {
        return name;
    }

    public int getHairId() {
        return hairId;
    }

    public int getTopId() {
        return topId;
    }

    public int getLegsId() {
        return legsId;
    }

    public int getBootsId() {
        return bootsId;
    }

    public int getSkinId() {
        return skinId;
    }

    @Nullable
    public static KitColor getByName(String name) {
        for (KitColor color : KitColor.values()) {
            if (color.getName().equals(name)) {
                return color;
            }
        }
        return null;
    }

    @Nullable
    public static KitColor getByHairId(int hairId) {
        if (hairId < 0) {
            return null;
        }

        for (KitColor color : KitColor.values()) {
            if (color.getHairId() == hairId) {
                return color;
            }
        }
        return null;
    }

    @Nullable
    public static KitColor getByTopId(int topId) {
        if (topId < 0) {
            return null;
        }

        for (KitColor color : KitColor.values()) {
            if (color.getTopId() == topId) {
                return color;
            }
        }
        return null;
    }

    @Nullable
    public static KitColor getByLegsId(int legsId) {
        if (legsId < 0) {
            return null;
        }

        for (KitColor color : KitColor.values()) {
            if (color.getLegsId() == legsId) {
                return color;
            }
        }
        return null;
    }

    @Nullable
    public static KitColor getByBootsId(int bootsId) {
        if (bootsId < 0) {
            return null;
        }

        for (KitColor color : KitColor.values()) {
            if (color.getBootsId() == bootsId) {
                return color;
            }
        }
        return null;
    }

    @Nullable
    public static KitColor getBySkinId(int skinId) {
        if (skinId < 0) {
            return null;
        }

        for (KitColor color : KitColor.values()) {
            if (color.getSkinId() == skinId) {
                return color;
            }
        }
        return null;
    }

}
