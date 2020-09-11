package org.terracotta.util;

import lombok.Getter;

import java.util.regex.Pattern;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public enum ChatColor {

    /**
     * Represents the Minecraft color black
     */
    BLACK('0'),

    /**
     * Represents the Minecraft color dark blue
     */
    DARK_BLUE('1'),

    /**
     * Represents the Minecraft color dark green
     */
    DARK_GREEN('2'),

    /**
     * Represents the Minecraft color dark aqua
     */
    DARK_AQUA('3'),

    /**
     * Represents the Minecraft color dark red
     */
    DARK_RED('4'),

    /**
     * Represents the Minecraft color dark purple
     */
    PURPLE('5'),

    /**
     * Represents the Minecraft color gold
     */
    ORANGE('6'),

    /**
     * Represents the Minecraft color gray
     */
    GRAY('7'),

    /**
     * Represents the Minecraft color dark gray
     */
    DARK_GRAY('8'),

    /**
     * Represents the Minecraft color blue
     */
    BLUE('9'),

    /**
     * Represents the Minecraft color green
     */
    GREEN('a'),

    /**
     * Represents the Minecraft color aqua
     */
    AQUA('b'),

    /**
     * Represents the Minecraft color red
     */
    RED('c'),

    /**
     * Represents the Minecraft color light purple
     */
    MAGENTA('d'),

    /**
     * Represents the Minecraft color yellow
     */
    YELLOW('e'),

    /**
     * Represents the Minecraft color white
     */
    WHITE('f'),

    /**
     * Represents the Minecraft color minecoin gold
     */
    GOLD('g'),

    /**
     * Represents the Minecraft format obfuscated
     */
    OBFUSCATED('k'),

    /**
     * Represents the Minecraft format bold
     */
    BOLD('l'),

    /**
     * Represents the Minecraft format italic
     */
    ITALIC('o'),

    /**
     * Represents the Minecraft format reset
     */
    RESET('r');

    @Getter
    private final char code;
    private final String toString;

    private static final char ESCAPE = '\u00A7';
    private static final Pattern CLEAN_PATTERN = Pattern.compile("(?i)" + ChatColor.ESCAPE + "[0-9A-GK-OR]");

    /**
     * This is the constructor of this Enum
     *
     * @param code which is needed to work with the Minecraft colors
     */
    ChatColor(final char code) {
        this.code = code;
        this.toString = new String(new char[]{ChatColor.ESCAPE, this.code});
    }

    /**
     * Colorizes the given text with the given @code format
     *
     * @param format which should be immediately followed by a color code
     * @param text   which should be replaced
     *
     * @return a fresh colorized String
     */
    public static String colorize(final char format, final String text) {
        final char[] textChars = text.toCharArray();

        for (int i = 0; i < textChars.length - 1; i++) {
            if (textChars[i] == format && "0123456789AaBbCcDdEeFfGgKkLlMmNnOoRr".indexOf(textChars[i + 1]) > -1) {
                textChars[i] = ChatColor.ESCAPE;
                textChars[i + 1] = Character.toLowerCase(textChars[i + 1]);
            }
        }

        return new String(textChars);
    }


    /**
     * Removes all colors from the given input
     *
     * @param input     which should be cleaned
     * @param recursive whether the execution should be recursive
     *
     * @return a fresh cleaned String
     */
    public static String clean(final String input, final boolean recursive) {
        if (input == null) {
            return "";
        }

        final String result = ChatColor.CLEAN_PATTERN.matcher(input).replaceAll("");

        if (recursive && ChatColor.CLEAN_PATTERN.matcher(result).find()) {
            return ChatColor.clean(input, recursive);
        }

        return result;
    }

    @Override
    public String toString() {
        return this.toString;
    }
}