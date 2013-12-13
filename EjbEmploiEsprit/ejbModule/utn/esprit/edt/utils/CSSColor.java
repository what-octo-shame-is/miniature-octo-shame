package utn.esprit.edt.utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 */
public class CSSColor {

	public static String[] colors = { "AliceBlue", "AntiqueWhite", "Aqua",
			"Aquamarine", "Azure", "Beige", "Bisque", "Black",
			"BlanchedAlmond", "Blue", "BlueViolet", "Brown", "BurlyWood",
			"CadetBlue", "Chartreuse", "Chocolate", "Coral", "CornflowerBlue",
			"Cornsilk", "Crimson", "Cyan", "DarkBlue", "DarkCyan",
			"DarkGoldenRod", "DarkGray", "DarkGreen", "DarkKhaki",
			"DarkMagenta", "DarkOliveGreen", "Darkorange", "DarkOrchid",
			"DarkRed", "DarkSalmon", "DarkSeaGreen", "DarkSlateBlue",
			"DarkSlateGray", "DarkTurquoise", "DarkViolet", "DeepPink",
			"DeepSkyBlue", "DimGray", "DodgerBlue", "FireBrick", "FloralWhite",
			"ForestGreen", "Fuchsia", "Gainsboro", "GhostWhite", "Gold",
			"GoldenRod", "Gray", "Green", "GreenYellow", "HoneyDew", "HotPink",
			"IndianRed", "Indigo", "Ivory", "Khaki", "Lavender",
			"LavenderBlush", "LawnGreen", "LemonChiffon", "LightBlue",
			"LightCoral", "LightCyan", "LightGoldenRodYellow", "LightGrey",
			"LightGreen", "LightPink", "LightSalmon", "LightSeaGreen",
			"LightSkyBlue", "LightSlateGray", "LightSteelBlue", "LightYellow",
			"Lime", "LimeGreen", "Linen", "Magenta", "Maroon",
			"MediumAquaMarine", "MediumBlue", "MediumOrchid", "MediumPurple",
			"MediumSeaGreen", "MediumSlateBlue", "MediumSpringGreen",
			"MediumTurquoise", "MediumVioletRed", "MidnightBlue", "MintCream",
			"MistyRose", "Moccasin", "NavajoWhite", "Navy", "OldLace", "Olive",
			"OliveDrab", "Orange", "OrangeRed", "Orchid", "PaleGoldenRod",
			"PaleGreen", "PaleTurquoise", "PaleVioletRed", "PapayaWhip",
			"PeachPuff", "Peru", "Pink", "Plum", "PowderBlue", "Purple", "Red",
			"RosyBrown", "RoyalBlue", "SaddleBrown", "Salmon", "SandyBrown",
			"SeaGreen", "SeaShell", "Sienna", "Silver", "SkyBlue", "SlateBlue",
			"SlateGray", "Snow", "SpringGreen", "SteelBlue", "Tan", "Teal",
			"Thistle", "Tomato", "Turquoise", "Violet", "Wheat", "White",
			"WhiteSmoke", "Yellow", "YellowGreen", };

	public static String[] darkColors = { "Black", "Blue", "DarkBlue",
			"DarkMagenta", "DarkRed", "DarkSlateBlue", "DarkSlateGray",
			"FireBrick", "Indigo", "Maroon", "MediumBlue", "MidnightBlue",
			"Navy", };

	public static int countColors() {
		return CSSColor.colors.length;
	}

	public static String getColor(int i) {
		return CSSColor.colors[i];
	}

	public static boolean isDark(String color) {
		for (String darkColor : darkColors)
			if (color.equals(darkColor))
				return true;
		return false;
	}

	public static String getLightest() {
		return "White";
	}

	public static String getDarkest() {
		return "Black";
	}
}
