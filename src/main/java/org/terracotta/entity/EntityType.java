package org.terracotta.entity;

import lombok.Getter;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public enum EntityType {

    /**
     * Entity type representation for a chicken
     */
    CHICKEN("minecraft:chicken", 10),

    /**
     * Entity type representation for a cow
     */
    COW("minecraft:cow", 11),

    /**
     * Entity type representation for a pig
     */
    PIG("minecraft:pig", 12),

    /**
     * Entity type representation for a sheep
     */
    SHEEP("minecraft:sheep", 13),

    /**
     * Entity type representation for a wolf
     */
    WOLF("minecraft:wolf", 14),

    /**
     * Entity type representation for a villager
     */
    VILLAGER("minecraft:villager", 15),

    /**
     * Entity type representation for a mooshroom
     */
    MOOSHROOM("minecraft:mooshroom", 16),

    /**
     * Entity type representation for a squid
     */
    SQUID("minecraft:squid", 17),

    /**
     * Entity type representation for a rabbit
     */
    RABBIT("minecraft:rabbit", 18),

    /**
     * Entity type representation for a bat
     */
    BAT("minecraft:bat", 19),

    /**
     * Entity type representation for an iron golem
     */
    IRON_GOLEM("minecraft:iron_golem", 20),

    /**
     * Entity type representation for a snow golem
     */
    SNOW_GOLEM("minecraft:snow_golem", 21),

    /**
     * Entity type representation for an ocelot
     */
    OCELOT("minecraft:ocelot", 22),

    /**
     * Entity type representation for a horse
     */
    HORSE("minecraft:horse", 23),

    /**
     * Entity type representation for a donkey
     */
    DONKEY("minecraft:donkey", 24),

    /**
     * Entity type representation for a mule
     */
    MULE("minecraft:mule", 25),

    /**
     * Entity type representation for a skeleton horse
     */
    SKELETON_HORSE("minecraft:skeleton_horse", 26),

    /**
     * Entity type representation for a zombie horse
     */
    ZOMBIE_HORSE("minecraft:zombie_horse", 27),

    /**
     * Entity type representation for a polar bear
     */
    POLAR_BEAR("minecraft:polar_bear", 28),

    /**
     * Entity type representation for a llama
     */
    LLAMA("minecraft:llama", 29),

    /**
     * Entity type representation for a parrot
     */
    PARROT("minecraft:parrot", 30),

    /**
     * Entity type representation for a dolphin
     */
    DOLPHIN("minecraft:dolphin", 31),

    /**
     * Entity type representation for a zombie
     */
    ZOMBIE("minecraft:zombie", 32),

    /**
     * Entity type representation for a creeper
     */
    CREEPER("minecraft:creeper", 33),

    /**
     * Entity type representation for a skeleton
     */
    SKELETON("minecraft:skeleton", 34),

    /**
     * Entity type representation for a spider
     */
    SPIDER("minecraft:spider", 35),

    /**
     * Entity type representation for a zombified piglin
     */
    ZOMBIFIED_PIGLIN("minecraft:zombified_piglin", 36),

    /**
     * Entity type representation for a slime
     */
    SLIME("minecraft:slime", 37),

    /**
     * Entity type representation for a enderman
     */
    ENDERMAN("minecraft:enderman", 38),

    /**
     * Entity type representation for a silverfish
     */
    SILVERFISH("minecraft:silverfish", 39),

    /**
     * Entity type representation for a cave spider
     */
    CAVE_SPIDER("minecraft:cave_spider", 40),

    /**
     * Entity type representation for a ghast
     */
    GHAST("minecraft:ghast", 41),

    /**
     * Entity type representation for a magma cube
     */
    MAGMA_CUBE("minecraft:magma_cube", 42),

    /**
     * Entity type representation for a blaze
     */
    BLAZE("minecraft:blaze", 43),

    /**
     * Entity type representation for a zombie villager
     */
    ZOMBIE_VILLAGER("minecraft:zombie_villager", 44),

    /**
     * Entity type representation for a witch
     */
    WITCH("minecraft:witch", 45),

    /**
     * Entity type representation for a stray
     */
    STRAY("minecraft:stray", 46),

    /**
     * Entity type representation for a husk
     */
    HUSK("minecraft:husk", 47),

    /**
     * Entity type representation for a wither skeleton
     */
    WITHER_SKELETON("minecraft:wither_skeleton", 48),

    /**
     * Entity type representation for a guardian
     */
    GUARDIAN("minecraft:guardian", 49),

    /**
     * Entity type representation for a elder guardian
     */
    ELDER_GUARDIAN("minecraft:elder_guardian", 50),

    /**
     * Entity type representation for a wither
     */
    WITHER("minecraft:wither", 52),

    /**
     * Entity type representation for a ender dragon
     */
    ENDER_DRAGON("minecraft:ender_dragon", 53),

    /**
     * Entity type representation for a shulker
     */
    SHULKER("minecraft:shulker", 54),

    /**
     * Entity type representation for an endermite
     */
    ENDERMITE("minecraft:endermite", 55),

    /**
     * Entity type representation for a vindicator
     */
    VINDICATOR("minecraft:vindicator", 57),

    /**
     * Entity type representation for a phantom
     */
    PHANTOM("minecraft:phantom", 58),

    /**
     * Entity type representation for a ravager
     */
    RAVAGER("minecraft:ravager", 59),

    /**
     * Entity type representation for an armor stand
     */
    ARMOR_STAND("minecraft:armor_stand", 61),

    /**
     * Entity type representation for a player
     */
    PLAYER("minecraft:player", 63),

    /**
     * Entity type representation for a item
     */
    ITEM("minecraft:item", 64),

    /**
     * Entity type representation for a primed tnt
     */
    TNT("minecraft:tnt", 65),

    /**
     * Entity type representation for a falling block
     */
    FALLING_BLOCK("minecraft:falling_block", 66),

    /**
     * Entity type representation for a moving block
     */
    MOVING_BLOCK("minecraft:moving_block", 67),

    /**
     * Entity type representation for a xp bottle
     */
    XP_BOTTLE("minecraft:xp_bottle", 68),

    /**
     * Entity type representation for a xp orb
     */
    XP_ORB("minecraft:xp_orb", 69),

    /**
     * Entity type representation for an eye of ender signal
     */
    EYE_OF_ENDER_SIGNAL("minecraft:eye_of_ender_signal", 70),

    /**
     * Entity type representation for an ender crystal
     */
    ENDER_CRYSTAL("minecraft:ender_crystal", 71),

    /**
     * Entity type representation for a fireworks rocket
     */
    FIREWORKS_ROCKET("minecraft:fireworks_rocket", 72),

    /**
     * Entity type representation for a thrown trident
     */
    THROWN_TRIDENT("minecraft:thrown_trident", 73),

    /**
     * Entity type representation for a turtle
     */
    TURTLE("minecraft:turtle", 74),

    /**
     * Entity type representation for a cat
     */
    CAT("minecraft:cat", 75),

    /**
     * Entity type representation for a shulker bullet
     */
    SHULKER_BULLET("minecraft:shulker_bullet", 76),

    /**
     * Entity type representation for a fishing hook
     */
    FISHING_HOOK("minecraft:fishing_hook", 77),

    /**
     * Entity type representation for a dragon fireball
     */
    DRAGON_FIREBALL("minecraft:dragon_fireball", 79),

    /**
     * Entity type representation for an arrow
     */
    ARROW("minecraft:arrow", 80),

    /**
     * Entity type representation for a snowball
     */
    SNOWBALL("minecraft:snowball", 81),

    /**
     * Entity type representation for an egg
     */
    EGG("minecraft:egg", 82),

    /**
     * Entity type representation for a painting
     */
    PAINTING("minecraft:painting", 83),

    /**
     * Entity type representation for a minecart
     */
    MINECART("minecraft:minecart", 84),

    /**
     * Entity type representation for a fireball
     */
    FIREBALL("minecraft:fireball", 85),

    /**
     * Entity type representation for a splash potion
     */
    SPLASH_POTION("minecraft:splash_potion", 86),

    /**
     * Entity type representation for an ender pearl
     */
    ENDER_PEARL("minecraft:ender_pearl", 87),

    /**
     * Entity type representation for a leash knot
     */
    LEASH_KNOT("minecraft:leash_knot", 88),

    /**
     * Entity type representation for a wither skull
     */
    WITHER_SKULL("minecraft:wither_skull", 89),

    /**
     * Entity type representation for a boat
     */
    BOAT("minecraft:boat", 90),

    /**
     * Entity type representation for a wither skull dangerous
     */
    WITHER_SKULL_DANGEROUS("minecraft:wither_skull_dangerous", 91),

    /**
     * Entity type representation for a lightning bolt
     */
    LIGHTNING_BOLT("minecraft:lightning_bolt", 93),

    /**
     * Entity type representation for a small fireball
     */
    SMALL_FIREBALL("minecraft:small_fireball", 94),

    /**
     * Entity type representation for an area effect cloud
     */
    AREA_EFFECT_CLOUD("minecraft:area_effect_cloud", 95),

    /**
     * Entity type representation for a hopper minecart
     */
    HOPPER_MINECART("minecraft:hopper_minecart", 96),

    /**
     * Entity type representation for a tnt minecart
     */
    TNT_MINECART("minecraft:tnt_minecart", 97),

    /**
     * Entity type representation for a chest minecart
     */
    CHEST_MINECART("minecraft:chest_minecart", 98),

    /**
     * Entity type representation for a command block minecart
     */
    COMMAND_BLOCK_MINECART("minecraft:command_block_minecart", 100),

    /**
     * Entity type representation for a lingering potion
     */
    LINGERING_POTION("minecraft:lingering_potion", 101),

    /**
     * Entity type representation for a llama spit
     */
    LLAMA_SPIT("minecraft:llama_spit", 102),

    /**
     * Entity type representation for a evocation fang
     */
    EVOCATION_FANG("minecraft:evocation_fang", 103),

    /**
     * Entity type representation for a evocation illager
     */
    EVOCATION_ILLAGER("minecraft:evocation_illager", 104),

    /**
     * Entity type representation for a vex
     */
    VEX("minecraft:vex", 105),

    /**
     * Entity type representation for a pufferfish
     */
    PUFFERFISH("minecraft:pufferfish", 108),

    /**
     * Entity type representation for a salmon
     */
    SALMON("minecraft:salmon", 109),

    /**
     * Entity type representation for a drowned
     */
    DROWNED("minecraft:drowned", 110),

    /**
     * Entity type representation for a tropical fish
     */
    TROPICAL_FISH("minecraft:tropical_fish", 111),

    /**
     * Entity type representation for a cod
     */
    COD("minecraft:cod", 112),

    /**
     * Entity type representation for a panda
     */
    PANDA("minecraft:panda", 113),

    /**
     * Entity type representation for a pillager
     */
    PILLAGER("minecraft:pillager", 114),

    /**
     * Entity type representation for a villager v2
     */
    VILLAGER_V2("minecraft:villager_v2", 115),

    /**
     * Entity type representation for a zombie villager v2
     */
    ZOMBIE_VILLAGER_V2("minecraft:zombie_villager_v2", 116),

    /**
     * Entity type representation for a wandering trader
     */
    WANDERING_TRADER("minecraft:wandering_trader", 118),

    /**
     * Entity type representation for a fox
     */
    FOX("minecraft:fox", 121),

    /**
     * Entity type representation for a bee
     */
    BEE("minecraft:bee", 122),

    /**
     * Entity type representation for a piglin
     */
    PIGLIN("minecraft:piglin", 123),

    /**
     * Entity type representation for a hoglin
     */
    HOGLIN("minecraft:hoglin", 124),

    /**
     * Entity type representation for a strider
     */
    STRIDER("minecraft:strider", 125),

    /**
     * Entity type representation for a zoglin
     */
    ZOGLIN("minecraft:zoglin", 126);

    @Getter
    private final String persistentId;
    @Getter
    private final int networkId;

    EntityType(final String persistentId, final int networkId) {
        this.persistentId = persistentId;
        this.networkId = networkId;
    }
}