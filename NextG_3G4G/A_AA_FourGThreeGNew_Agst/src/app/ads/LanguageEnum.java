package app.ads;

public enum LanguageEnum {

	SYSTEM_LANG("System Language", "System lang"), ENGLISH("English", "en"), SPANISH(
			"Español", "es"), PORTUGUESE("Português", "pt"), RUSSIAN("Pyccĸий",
			"ru"), FRENCH("Français", "fr"), GERMAN("Deutsch", "de"), ITALIAN(
			"Italiano", "it"), ARABIC("العربية", "ar"), CHINESE("中文", "zh"), KOREAN(
			"한국어", "ko"), JAPANESE("日本語", "ja"), TURKISH("Tϋrkçe", "tr"), INDONESIAN(
			"Bahasa indonesia", "id"), THAI("ภาษาไทย", "th"), MALASIAN("Malay",
			"ms"), HINDI("हिंदी", "hi");

	public final String LANGUAGE;
	public final String LANGUAGE_CODE;

	private LanguageEnum(String lang, String lang_code) {
		// TODO Auto-generated constructor stub
		LANGUAGE = lang;
		LANGUAGE_CODE = lang_code;
	}

}
