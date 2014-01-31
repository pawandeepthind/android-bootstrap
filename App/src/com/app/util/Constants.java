package com.app.util;

public class Constants {
	public static final String EMPTY_STRING = "";
	public static final String SPACE = " ";
	public static final String COMMA = ",";
	public static final String DOT = ".";

	public static final String title = "title";
	public static final String link = "link";

	public static final String Color = "Color";
	public static final String String = "String";
	public static final String NEWLINE = "\n";
	public static final String COLON = ":";

	// Common Constants
	public static final String stopLoading = "stopLoading";

	// Google+
	public static final int DIALOG_GET_GOOGLE_PLAY_SERVICES = 1;
	public static final int REQUEST_CODE_SIGN_IN = 1;
	public static final int REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES = 2;

	// Twitter oauth urls
	public static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";

	public interface URLParams {
		public static final String direction = "direction";
		public static final String offset = "offset";
		public static final String limit = "limit";
		public static final String desc = "desc";
		public static final String asc = "asc";
	}

	public interface App_Settings {
		public static final String apppref_filename = "App_Prefs";
		public static final String devicedensity = "devicedensity";
	}

	public interface RequestResponseHeaders {
		public static final String user = "user";
		public static final String User_AgentKey = "User-Agent";
		public static final String User_AgentValue = "(Android %s)";
		public static final String Content_Type = "Content-Type";
		public static final String Application_xwwwformurlencodedKey = "application/x-www-form-urlencoded";
	}

	public interface SpecialCharacters {
		public static String AMP = "&";
		public static String TRADEMARK = "ª";
		public static String LESSTHEN = "<";
		public static String MORETHEN = ">";
		public static String REG = "¨";
		public static String SINGLEQUOTE = "'";
		public static String QUOTE = "\"";
		public static String ELLIPSIZE = "...";
		public static String COPYRIGHT = "©";
		public static String ATTHERATE = "@";
		public static String MDASH = "Ð";
		public static String EURO = "Û";
		public static String LDQUO = "Ò";
		public static String RDQUO = "Ó";
	}
}
