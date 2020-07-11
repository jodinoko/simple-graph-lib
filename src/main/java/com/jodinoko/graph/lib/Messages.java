package com.jodinoko.graph.lib;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Messages {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(Messages.class.getName()
			.toLowerCase(), Locale.ROOT);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
