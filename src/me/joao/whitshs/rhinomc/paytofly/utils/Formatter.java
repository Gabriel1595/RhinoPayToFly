package me.joao.whitshs.rhinomc.paytofly.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Formatter {
	
	public static String format(double coins) {
		NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
		return nf.format(coins);
	}

}
