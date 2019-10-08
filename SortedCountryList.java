import java.io.Serializable;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SortedCountryList {

	private static final String[] PREFERRED_COUNTRY_CODES = new String[] { "ch", "de", "at", "fr", "it" };

	public static void main(String[] args) {
		SortedCountryList sortedCountryList = new SortedCountryList();
		sortedCountryList.getCountryLabelsJava8().stream().forEach(item -> System.out.println(item.getValue()));

	}

	public List<SelectItem> getCountryLabelsJava8() {
		// or use another method to get Locale, for example from PrimeFaces context
		// FacesContext context = FacesContext.getCurrentInstance();
		// UIViewRoot uiViewRoot = context.getViewRoot();
		// String lang = uiViewRoot.getLocale().getLanguage();
		// Locale outLocale = new Locale(lang);
		Locale outLocale = Locale.GERMAN;
		String[] locales = java.util.Locale.getISOCountries();

		List<SelectItem> preferredCountryList = getSelectItems(PREFERRED_COUNTRY_CODES, outLocale);
		List<SelectItem> otherCountryList = getSelectItems(locales, outLocale);
		otherCountryList = otherCountryList.stream() // convert list to stream
				.filter(line -> !Arrays.asList(PREFERRED_COUNTRY_CODES).stream()
						.anyMatch(pcode -> pcode.equalsIgnoreCase((String) line.getLabel()))) // we dont like mkyong
				.collect(Collectors.toList()); // collect the output and convert streams to a List

		otherCountryList.sort(new Comparator<SelectItem>() {
			@Override
			public int compare(SelectItem o1, SelectItem o2) {
				Collator collator = Collator.getInstance(outLocale);
				collator.setStrength(Collator.PRIMARY);
				return collator.compare(o1.getValue(), o2.getValue());
			}
		});

		preferredCountryList.addAll(otherCountryList);
		return preferredCountryList;
	}

	private List<SelectItem> getSelectItems(String[] locales, Locale outLocale) {

		return Arrays.asList(locales).stream().map(obj -> new Locale("", obj))
				.map(obj -> new SelectItem(obj.getDisplayCountry(outLocale), obj.getCountry()))
				.collect(Collectors.toList());
	}
}

/**
 * <p>
 * <strong class="changed_modified_2_0
 * changed_modified_2_0_rev_a">SelectItem</strong> represents a single
 * <em>item</em> in the list of supported <em>items</em> associated with a
 * {@link UISelectMany} or {@link UISelectOne} component.
 * </p>
 */

class SelectItem implements Serializable {

	private static final long serialVersionUID = 876782311414654999L;

	// ------------------------------------------------------------ Constructors

	/**
	 * <p>
	 * Construct a <code>SelectItem</code> with no initialized property values.
	 * </p>
	 */
	public SelectItem() {

		super();

	}

	/**
	 * <p>
	 * Construct a <code>SelectItem</code> with the specified value. The
	 * <code>label</code> property will be set to the value (converted to a String,
	 * if necessary), the <code>description</code> property will be set to
	 * <code>null</code>, the <code>disabled</code> property will be set to
	 * <code>false</code>, and the <code>escape</code> property will be set to (
	 * <code>true</code>.
	 * </p>
	 *
	 * @param value Value to be delivered to the model if this item is selected by
	 *              the user
	 */
	public SelectItem(Object value) {

		this(value, value == null ? null : value.toString(), null, false, true, false);

	}

	/**
	 * <p>
	 * Construct a <code>SelectItem</code> with the specified value and label. The
	 * <code>description</code> property will be set to <code>null</code>, the
	 * <code>disabled</code> property will be set to <code>false</code>, and the
	 * <code>escape</code> property will be set to <code>true</code>.
	 * </p>
	 *
	 * @param value Value to be delivered to the model if this item is selected by
	 *              the user
	 * @param label Label to be rendered for this item in the response
	 */
	public SelectItem(Object value, String label) {

		this(value, label, null, false, true, false);

	}

	/**
	 * <p>
	 * Construct a <code>SelectItem</code> instance with the specified value, label
	 * and description. This <code>disabled</code> property will be set to
	 * <code>false</code>, and the <code>escape</code> property will be set to
	 * <code>true</code>.
	 * </p>
	 *
	 * @param value       Value to be delivered to the model if this item is
	 *                    selected by the user
	 * @param label       Label to be rendered for this item in the response
	 * @param description Description of this item, for use in tools
	 */
	public SelectItem(Object value, String label, String description) {

		this(value, label, description, false, true, false);

	}

	/**
	 * <p>
	 * Construct a <code>SelectItem</code> instance with the specified property
	 * values. The <code>escape</code> property will be set to <code>true</code>.
	 * </p>
	 *
	 * @param value       Value to be delivered to the model if this item is
	 *                    selected by the user
	 * @param label       Label to be rendered for this item in the response
	 * @param description Description of this item, for use in tools
	 * @param disabled    Flag indicating that this option is disabled
	 */
	public SelectItem(Object value, String label, String description, boolean disabled) {

		this(value, label, description, disabled, true, false);

	}

	/**
	 * <p>
	 * Construct a <code>SelectItem</code> instance with the specified property
	 * values.
	 * </p>
	 *
	 * @param value       Value to be delivered to the model if this item is
	 *                    selected by the user
	 * @param label       Label to be rendered for this item in the response
	 * @param description Description of this item, for use in tools
	 * @param disabled    Flag indicating that this option is disabled
	 * @param escape      Flag indicating that the text of this option should be
	 *                    escaped when rendered.
	 * @since 1.2
	 */
	public SelectItem(Object value, String label, String description, boolean disabled, boolean escape) {

		this(value, label, description, disabled, escape, false);

	}

	/**
	 * <p>
	 * Construct a <code>SelectItem</code> instance with the specified property
	 * values.
	 * </p>
	 *
	 * @param value             Value to be delivered to the model if this item is
	 *                          selected by the user
	 * @param label             Label to be rendered for this item in the response
	 * @param description       Description of this item, for use in tools
	 * @param disabled          Flag indicating that this option is disabled
	 * @param escape            Flag indicating that the text of this option should
	 *                          be escaped when rendered.
	 * @param noSelectionOption Flag indicating that the current option is a "no
	 *                          selection" option
	 * @since 1.2
	 */
	public SelectItem(Object value, String label, String description, boolean disabled, boolean escape,
			boolean noSelectionOption) {

		super();
		setValue(value);
		setLabel(label);
		setDescription(description);
		setDisabled(disabled);
		setEscape(escape);
		setNoSelectionOption(noSelectionOption);

	}

	// ------------------------------------------------------ Instance Variables

	private String description = null;
	private boolean disabled = false;
	private String label = null;
	@SuppressWarnings({ "NonSerializableFieldInSerializableClass" })
	private Object value = null;

	// -------------------------------------------------------------- Properties

	/**
	 * <p>
	 * Return a description of this item, for use in development tools.
	 */
	public String getDescription() {

		return (this.description);

	}

	/**
	 * <p>
	 * Set the description of this item, for use in development tools.
	 * </p>
	 *
	 * @param description The new description
	 */
	public void setDescription(String description) {

		this.description = description;

	}

	/**
	 * <p>
	 * Return the disabled flag for this item, which should modify the rendered
	 * output to make this item unavailable for selection by the user if set to
	 * <code>true</code>.
	 * </p>
	 */
	public boolean isDisabled() {

		return (this.disabled);

	}

	/**
	 * <p>
	 * Set the disabled flag for this item, which should modify the rendered output
	 * to make this item unavailable for selection by the user if set to
	 * <code>true</code>.
	 * </p>
	 *
	 * @param disabled The new disabled flag
	 */
	public void setDisabled(boolean disabled) {

		this.disabled = disabled;

	}

	/**
	 * <p>
	 * Return the label of this item, to be rendered visibly for the user.
	 */
	public String getLabel() {

		return (this.label);

	}

	/**
	 * <p>
	 * Set the label of this item, to be rendered visibly for the user.
	 *
	 * @param label The new label
	 */
	public void setLabel(String label) {

		this.label = label;

	}

	/**
	 * <p>
	 * Return the value of this item, to be delivered to the model if this item is
	 * selected by the user.
	 */
	public Object getValue() {

		return (this.value);

	}

	/**
	 * <p>
	 * Set the value of this item, to be delivered to the model if this item is
	 * selected by this user.
	 *
	 * @param value The new value
	 *
	 */
	public void setValue(Object value) {

		this.value = value;

	}

	private boolean escape;

	/**
	 * <p class="changed_added_2_0_rev_a">
	 * If and only if this returns <code>true</code>, the code that renders this
	 * select item must escape the label using escaping syntax appropriate to the
	 * content type being rendered.
	 * </p>
	 *
	 * @since 2.0
	 */
	public boolean isEscape() {
		return this.escape;
	}

	/**
	 * <p class="changed_added_2_0_rev_a">
	 * Set the value of the escape property. See {@link #isEscape}.
	 * </p>
	 *
	 * @since 2.0
	 */
	public void setEscape(boolean escape) {
		this.escape = escape;
	}

	private boolean noSelectionOption = false;

	/**
	 * <p class="changed_added_2_0">
	 * Return the value of the <code>noSelectionOption</code> property. If the value
	 * of this property is <code>true</code>, the system interprets the option
	 * represented by this <code>SelectItem</code> instance as representing a "no
	 * selection" option. See {@link UISelectOne#validateValue} and
	 * {@link UISelectMany#validateValue} for usage.
	 * </p>
	 *
	 * @since 2.0
	 */

	public boolean isNoSelectionOption() {
		return noSelectionOption;
	}

	/**
	 * <p class="changed_added_2_0">
	 * Set the value of the <code>noSelectionOption</code> property.
	 * </p>
	 *
	 * @since 2.0
	 */

	public void setNoSelectionOption(boolean noSelectionOption) {
		this.noSelectionOption = noSelectionOption;
	}

	public enum CountryCode {

		CH("CH", "Schweiz", "Switzerland", "Suisse", "Svizzera"),
		DE("DE", "Deutschland", "Germany", "Allemagne", "Germania"),
		FR("FR", "Frankreich", "France", "France", "Francia"), IT("IT", "Italien", "Italy", "Italie", "Italia"),
		AT("AT", "Österreich", "Austria", "Autriche", "Austria"),

		AC("AC", "Ascension", "Ascension", "Ascension", "Ascensione"),
		AD("AD", "Andorra", "Andorra", "Andorre", "Andorra"),
		AE("AE", "Ver. Arabische Emirate", "United Arab Emirates", "Emirats Arabes Unis", "Emirati Arabi Uniti"),
		AF("AF", "Afghanistan", "Afghanistan", "Afghanistan", "Afganistan"),
		AG("AG", "Antigua/Barbuda", "Antigua and Barbuda", "Antigua/Barbuda", "Antigua/Barbuda"),
		AI("AI", "Anguilla/Sombreno", "Anguilla", "Anguilla/Sombreno", "Anguilla/Sombreno"),
		AL("AL", "Albanien", "Albania", "Albanie", "Albania"), AM("AM", "Armenien", "Armenia", "Arménie", "Armenia"),
		AN("AN", "Niederlande", "Netherlands Antilles", "Pays-Bas", "Paesi Bassi"),
		AO("AO", "Angola", "Angola", "Angola", "Angola"),
		AQ("AQ", "Antarktis", "Antartica", "Antarctique", "Antartide"),
		AS("AS", "Amerikanisch-Samoa", "American Samoa", "Samoa américain", "Samoa Statunitensi"),
		// AT("AT","Österreich","Austria","Autriche","Austria"),
		AU("AU", "Australien", "Australia", "Australie", "Australia"),
		AW("AW", "Aruba, Niederl. Ant.", "Aruba", "Aruba, Antilles néerl.", "Aruba, Antille Olandesi"),
		AX("AX", "Åland", "Åland Islands", "Åland, Îles", "Isole Åland"),
		AZ("AZ", "Aserbaidschan", "Azerbaijan", "Azerbaïdjan", "Azerbaigian"),
		BA("BA", "Bosnien-Herzegowina", "Bosnia and Herzegovina", "Bosnie-Herzégovine", "Bosnia-Erzegovina"),
		BB("BB", "Barbados", "Barbados", "Barbade", "Barbados"),
		BD("BD", "Bangla Desh", "Bangladesh", "Bangla Desh", "Bangladesh"),
		BE("BE", "Belgien", "Belgium", "Belgique", "Belgio"),
		BF("BF", "Burkina Faso", "Burkina Faso", "Burkina Faso", "Burkina Faso"),
		BG("BG", "Bulgarien", "Bulgaria", "Bulgarie", "Bulgaria"), BH("BH", "Bahrein", "Bahrain", "Bahreïn", "Bahrein"),
		BI("BI", "Burundi", "Burundi", "Burundi", "Burundi"),
		BL("BL", "Saint-Barthélemy", "Saint-Barthilemy", "Saint-Barthélemy", "Saint-Barthélemy"),
		BM("BM", "Bermudas", "Bermuda", "Bermudes", "Bermude"),
		BN("BN", "Brunei", "Brunei Darussalam", "Brunéi", "Brunei"),
		BO("BO", "Bolivien", "Bolivia", "Bolivie", "Bolivia"), BR("BR", "Brasilien", "Brazil", "Brésil", "Brasile"),
		BS("BS", "Bahamas", "Bahamas", "Bahamas", "Bahama"), BT("BT", "Bhutan", "Bhutan", "Bhoutan", "Bhutan"),
		BV("BV", "Bouvetinsel", "Bouvet Island", "Île Bouvet", "Isola Bouvet"),
		BW("BW", "Botswana", "Botswana", "Botswana", "Botswana"),
		BY("BY", "Weissrussland", "Belorussia", "Biélorussie", "Bielorussia"),
		BZ("BZ", "Belize", "Belize", "Belize", "Belize"), CA("CA", "Kanada", "Canada", "Canada", "Canada"),
		CC("CC", "Kokosinseln", "Cocos (Keeling) Islands", "Îles Cocos", "Isole Cocos"),
		CD("CD", "Kongo Demokratische Republik", "Congo Dem. Rep.", "Congo Rep. Dem.", "Congo Rep. Dem."),
		CF("CF", "Zentralafrikanische Rep.", "Central African Republic", "Centrafricaine, Rép.", "Rep. Centroafricana"),
		CG("CG", "Kongo (Brazzaville)", "Congo", "Congo (Brazzaville)", "Congo (Brazzaville)"),
		// CH("CH","Schweiz","Switzerland","Suisse","Svizzera"),
		CI("CI", "Elfenbeinküste", "Cote d'Ivoire", "Côte d'Ivoire", "Costa d'Avorio"),
		CK("CK", "Cook", "Cook Islands", "Cook", "Cook"), CL("CL", "Chile", "Chile", "Chili", "Cile"),
		CM("CM", "Kamerun", "Cameroon", "Cameroun", "Camerun"), CN("CN", "China", "China", "Chine", "Cina"),
		CO("CO", "Kolumbien", "Colombia", "Colombie", "Colombia"),
		CR("CR", "Costa Rica", "Costa Rica", "Costa Rica", "Costarica"), CU("CU", "Kuba", "Cuba", "Cuba", "Cuba"),
		CV("CV", "Kapverdische Inseln", "Cape Verde", "Cap-Vert", "Capo Verde"),
		CX("CX", "Weihnachtsinsel", "Christmas Island", "Île Christmas", "Isola di Natale"),
		CY("CY", "Zypern", "Cyprus", "Chypre", "Cipro"),
		CZ("CZ", "Tschechische Rep.", "Czech Republic", "Tchéquie", "Ceca, Rep."),
		// DE("DE","Deutschland","Germany","Allemagne","Germania"),
		DJ("DJ", "Djibouti", "Djibouti", "Djibouti", "Gibuti"),
		DK("DK", "Dänemark", "Denmark", "Danemark", "Danimarca"),
		DO("DO", "Dominikanische Rep.", "Dominican Republic", "Dominicaine, Rép.", "Repubblica Dominicana"),
		DY("DY", "Benin", "Benin", "Bénin", "Benin"), DZ("DZ", "Algerien", "Algeria", "Algérie", "Algeria"),
		EC("EC", "Ecuador", "Ecuador", "Equateur", "Ecuador"), EE("EE", "Estland", "Estonia", "Estonie", "Estonia"),
		EG("EG", "Aegypten", "Egypt", "Egypte", "Egitto"),
		EH("EH", "Westsahara", "Western Sahara", "Sahara occidental", "Sahara Occidentale"),
		ER("ER", "Eritrea", "Eritrea", "Erythrée", "Eritrea"), ES("ES", "Spanien", "Spain", "Espagne", "Spagna"),
		ET("ET", "Aethiopien", "Ethiopia", "Ethiopie", "Etiopia"),
		FI("FI", "Finnland", "Finland", "Finlande", "Finlandia"), FJ("FJ", "Fidschi", "Fiji", "Fidji", "Figi"),
		FK("FK", "Falkland", "Falkland Islands (Malvinas)", "Falkland", "Falkland"),
		LI("LI", "Fürstentum Liechtenstein", "Liechtenstein", "Princ. de Liechtenstein", "Princ. del Liechtenstein"),
		FM("FM", "Mikronesien (Karolinen)", "Micronesia Federated States of", "Micronésie (Carolines)",
				"Micronesia (Caroline)"),
		FO("FO", "Färöer", "Faroe Islands", "Féroé", "Faeröer"),
		// FR("FR","Frankreich","France","France","Francia"),
		GA("GA", "Gabun", "Gabon", "Gabon", "Gabon"),
		GB("GB", "Grossbritannien", "United Kingdom", "Grande Bretagne", "Gran Bretagna"),
		GD("GD", "Grenada", "Grenada", "Grenade", "Grenada"), GE("GE", "Georgien", "Georgia", "Géorgie", "Georgia"),
		GF("GF", "Franz. Guayana", "French Guiana", "Guyane française", "Guiana Francese"),
		GG("GG", "Guernsey (Kanalinsel)", "Guernesey", "Guernesey", "Guernsey"),
		GH("GH", "Ghana", "Ghana", "Ghana", "Ghana"), GI("GI", "Gibraltar", "Gibraltar", "Gibraltar", "Gibilterra"),
		GL("GL", "Grönland", "Greenland", "Groenland", "Groenlandia"), GM("GM", "Gambia", "Gambia", "Gambie", "Gambia"),
		GN("GN", "Guinea", "Guinea", "Guinée", "Guinea"),
		GP("GP", "Guadeloupe", "Guadeloupe", "Guadeloupe", "Guadalupa"),
		GQ("GQ", "Aequatorial-Guinea", "Equatorial Guinea", "Guinée équatoriale", "Guinea Equatoriale"),
		GR("GR", "Griechenland", "Greece", "Grèce", "Grecia"),
		GS("GS", "Südgeorgien und die Südlichen Sandwichinseln", "South Georgia & South Sandwich Islands",
				"Géorgie du Sud-et-les Îles Sandwich du Sud", "Georgia del Sud e isole Sandwich"),
		GT("GT", "Guatemala", "Guatemala", "Guatemala", "Guatemala"), GU("GU", "Guam", "Guam", "Guam", "Guam"),
		GW("GW", "Guinea-Bissau", "Guinea-Bissau", "Guinée-Bissau", "Guinea Bissau"),
		GY("GY", "Guayana", "Guyana", "Guyane", "Guiana"), HK("HK", "Hongkong", "Hong Kong", "Hongkong", "Hongkong"),
		HM("HM", "Heard und McDonaldinseln", "Heard and Mc Donald Islands", "Île Heard et îles McDonald",
				"Isole Heard e McDonald"),
		HN("HN", "Honduras", "Honduras", "Honduras", "Honduras"), HR("HR", "Kroatien", "Croatia", "Croatie", "Croazia"),
		HU("HU", "Ungarn", "Hungary", "Hongrie", "Ungheria"), IL("IL", "Israel", "Israel", "Israël", "Israele"),
		IM("IM", "Insel Man", "Isle of Man", "Île de Man", "Isola di Man"),
		IN("IN", "Indien", "India", "Inde", "India"),
		IO("IO", "Brit. Jungfern Inseln", "British Indian Ocean Territory", "Vierges britanniques",
				"Vergini britannica"),
		IQ("IQ", "Irak", "Iraq", "Iraq", "Iraq"), IR("IR", "Iran", "Iran (Islamic Republic of)", "Iran", "Iran"),
		IE("IE", "Irland", "Ireland", "Irlande", "Irlanda"), IS("IS", "Island", "Iceland", "Islande", "Islanda"),
		// IT("IT","Italien","Italy","Italie","Italia"),
		JA("JA", "Jamaika", "Jamaica", "Jamaïque", "Giamaica"),
		JE("JE", "Jersey (Kanalinsel)", "Jersey", "Jersey", "Jersey"),
		JO("JO", "Jordanien", "Jordan", "Jordanie", "Giordania"), JP("JP", "Japan", "Japan", "Japon", "Giappone"),
		KE("KE", "Kenia", "Kenya", "Kenya", "Kenya"), KG("KG", "Kirgistan", "Kyrgyzstan", "Kirgistan", "Kirgistan"),
		KH("KH", "Kambodscha Rep.Khm.", "Cambodia", "Cambodge, Khmère Rep.", "Cambogia, Khmer Rep."),
		KI("KI", "Kiribati (Gilbert)", "Kiribati", "Kiribati (Gilbert)", "Kiribati (Gilbert)"),
		KM("KM", "Komoren", "Comoros", "Comores", "Comore"),
		KN("KN", "St. Kitts", "Saint Kitts and Nevis", "St-Kitts", "St. Kitts"),
		KP("KP", "Nordkorea", "Korea Republic of", "Corée du Nord", "Corea del Nord"),
		KR("KR", "Südkorea", "Korea democratic people's republic of", "Corée du Sud", "Corea del Sud"),
		KS("KS", "Kosovo", "Kosovo", "Kosovo", "Kosovo"), KW("KW", "Kuwait", "Kuwait", "Koweït", "Kuwait"),
		KY("KY", "Cayman", "Cayman Islands", "Cayman", "Cayman"),
		KZ("KZ", "Kasachstan", "Kazakhstan", "Kazakhstan", "Kazakstan"),
		LA("LA", "Laos", "Lao people's democratic republic", "Laos", "Laos"),
		LB("LB", "Liberia", "Liberia", "Libéria", "Liberia"),
		LC("LC", "St. Lucia", "Saint Lucia", "Ste-Lucie", "St. Lucia"),
		LK("LK", "Sri Lanka", "Sri Lanka", "Sri Lanka", "Sri Lanka"),
		LS("LS", "Lesotho", "Lesotho", "Lesotho", "Lesotho"), LT("LT", "Litauen", "Lithuania", "Lituanie", "Lituania"),
		LU("LU", "Luxemburg", "Luxembourg", "Luxembourg", "Lussemburgo"),
		LV("LV", "Lettland", "Latvia", "Lettonie", "Lettonia"), LY("LY", "Libyen", "Libya", "Libye", "Libia"),
		MA("MA", "Marokko", "Marocco", "Maroc", "Marocco"), MC("MC", "Monaco", "Monaco", "Monaco", "Monaco"),
		MD("MD", "Moldawien", "Moldova Republic of", "Moldavie", "Moldavia"),
		ME("ME", "Montenegro", "Montenegro", "Monténégro", "Montenegro"),
		MF("MF", "Saint-Martin (franz. Teil)", "Saint-Martin", "Saint-Martin", "Saint-Martin"),
		MH("MH", "Marshall", "Marshall Islands", "Marshall", "Marshall"),
		MK("MK", "Mazedonien", "Macedonia", "Macédoine", "Macedonia"), ML("ML", "Mali", "Mali", "Mali", "Mali"),
		MM("MM", "Myanmar (Burma)", "Myanmar", "Myanmar (Birmanie)", "Myanmar (Birmania)"),
		MN("MN", "Mongolei", "Mongolia", "Mongolie", "Mongolia"), MO("MO", "Macao", "Macau", "Macao", "Macao"),
		MP("MP", "Marianen", "Northern Mariana Islands", "Mariannes", "Marianne"),
		MQ("MQ", "Martinique", "Martinique", "Martinique", "Martinica"),
		MR("MR", "Mauretanien", "Mauritania", "Mauritanie", "Mauritania"),
		MS("MS", "Montserrat", "Montserrat", "Montserrat", "Montserrat"), MT("MT", "Malta", "Malta", "Malte", "Malta"),
		MU("MU", "Mauritius", "Mauritius", "Maurice", "Maurizio"),
		MV("MV", "Malediven", "Maldives", "Maldives", "Maldive"), MW("MW", "Malawi", "Malawi", "Malawi", "Malawi"),
		MX("MX", "Mexiko", "Mexico", "Mexique", "Messico"), MY("MY", "Malaysia", "Malaysia", "Malaisie", "Malaisia"),
		MZ("MZ", "Mosambik", "Mozambique", "Mozambique", "Mozambico"),
		NA("NA", "Namibia", "Namibia", "Namibie", "Namibia"),
		NC("NC", "Neukaledonien", "New Caledonia", "Nouvelle-Calédonie", "Nuova Caledonia"),
		NF("NF", "Norfolk", "Norfolk Island", "Norfolk", "Norfolk"),
		NG("NG", "Nigeria", "Nigeria", "Nigeria", "Nigeria"),
		NI("NI", "Nicaragua", "Nicaragua", "Nicaragua", "Nicaragua"),
		NL("NL", "Niederländ. Antillen", "Netherlands", "Antilles néerlandaises", "Antille Olandesi"),
		NO("NO", "Norwegen", "Norway", "Norvège", "Norvegia"), NP("NP", "Nepal", "Nepal", "Népal", "Nepal"),
		NR("NR", "Nauru", "Nauru", "Nauru", "Nauru"), NU("NU", "Niue", "Niue", "Niue", "Niue"),
		NZ("NZ", "Neuseeland", "New Zealand", "Nouvelle-Zélande", "Nuova Zelanda"),
		OM("OM", "Oman", "Oman", "Oman", "Oman"), PA("PA", "Panama", "Panama", "Panama", "Panama"),
		PE("PE", "Peru", "Peru", "Pérou", "Perù"),
		PF("PF", "Französisch-Polynesien", "French Polynesia", "Polynésie française", "Polinesia Francese"),
		PG("PG", "Papua-Neuguinea", "Papua New Guinea", "Papouasie-Nouv.-Guin.", "Papua-Nuova Guinea"),
		PK("PK", "Pakistan", "Pakistan", "Pakistan", "Pakistan"), PL("PL", "Polen", "Poland", "Pologne", "Polonia"),
		PM("PM", "St. Pierre und Miquelon", "Saint Pierre and Miquelon", "St-Pierre et Miquelon",
				"Saint-Pierre e Miquelon"),
		PN("PN", "Pitcairninseln", "Pitcairn", "Îles Pitcairn", "Isole Pitcairn"),
		PR("PR", "Puerto Rico", "Puerto Rico", "Porto-Rico", "Portorico"),
		PS("PS", "Palästina", "Palestine", "Palestine", "Palestina"),
		PT("PT", "Portugal", "Portugal", "Portugal", "Portogallo"), PW("PW", "Palau", "Palau", "Palau", "Palau"),
		PY("PY", "Paraguay", "Paraguay", "Paraguay", "Paraguay"), QA("QA", "Qatar", "Qatar", "Qatar", "Qatar"),
		RA("RA", "Argentinien", "Argentina", "Argentine", "Argentina"),
		RC("RC", "Taiwan", "Taiwan", "Taiwan", "Taiwan"), RE("RE", "Reunion", "Réunion", "Réunion", "Riunione"),
		RH("RH", "Haiti", "Haiti", "Haïti", "Haiti"), RI("RI", "Indonesien", "Indonesia", "Indonésie", "Indonesia"),
		RL("RL", "Libanon", "Lebanon", "Liban", "Libano"),
		RM("RM", "Madagaskar", "Madagascar", "Madagascar", "Madagascar"), RN("RN", "Niger", "Niger", "Niger", "Niger"),
		RO("RO", "Rumänien", "Romania", "Roumanie", "Romania"),
		RP("RP", "Philippinen", "Philippines", "Philippines", "Filippine"),
		RS("RS", "Serbien/Montenegro", "Serbia", "Serbie/Monténégro", "Serbia/Montenegro"),
		RU("RU", "Russische Föderation", "Russian Federation", "Fédération de Russie", "Federazione de Russia"),
		RW("RW", "Rwanda", "Rwanda", "Rwanda", "Rwanda"),
		SA("SA", "Saudi-Arabien", "Saudi Arabia", "Arabie Saoudite", "Arabia Saudita"),
		SB("SB", "Salomon", "Solomon Islands", "Salomon", "Salomone"),
		SC("SC", "Seychellen", "Seychelles", "Seychelles", "Seicelle"), SD("SD", "Sudan", "Sudan", "Soudan", "Sudan"),
		SG("SG", "Singapur", "Singapore", "Singapour", "Singapore"),
		SH("SH", "St. Helena", "Saint Helena", "Ste-Hélène", "Sant'Elena"),
		SI("SI", "Slowenien", "Slovenia", "Slovénie", "Slovenia"),
		SJ("SJ", "Svalbard und Jan Mayen", "Svalbard and Jan Mayen", "Svalbard et île Jan Mayen",
				"Svalbard e Jan Mayen"),
		SK("SK", "Slowakei", "Slovakia", "Slovaquie", "Slovacca, Rep."),
		SL("SL", "Sierra Leone", "Sierra Leone", "Sierra Leone", "Sierra Leone"),
		SM("SM", "San Marino", "San Marino", "St-Marin", "San Marino"),
		SN("SN", "Senegal", "Senegal", "Sénégal", "Senegal"), SO("SO", "Somalia", "Somalia", "Somalie", "Somalia"),
		SR("SR", "Surinam", "Suriname", "Suriname", "Suriname"),
		ST("ST", "St. Tomé und Principe", "Sao Tome and Principe", "St-Tomé et Principe", "Sao Tomé e Principe"),
		SV("SV", "El Salvador", "El Salvador", "El Salvador", "El Salvador"),
		SW("SW", "Schweden", "Sweden", "Suède", "Svezia"), SY("SY", "Syrien", "Syrian Arab Republic", "Syrie", "Siria"),
		SZ("SZ", "Swasiland", "Swaziland", "Swaziland", "Swaziland"),
		TC("TC", "Turks und Caicos", "Turks and Caicos Islands", "Turques et Caicos", "Turks e Caicos"),
		TD("TD", "Tschad", "Chad", "Tchad", "Ciad"),
		TF("TF", "Französische Süd- und Antarktisgebiete", "French Southern Territories",
				"Terres australes et antarctiques françaises", "Terre Australi e Antartiche Francesi"),
		TG("TG", "Togo", "Togo", "Togo", "Togo"), TH("TH", "Thailand", "Thailand", "Thaïlande", "Tailandia"),
		TJ("TJ", "Tadschikistan", "Tajikistan", "Tadjikistan", "Tadjikistan"),
		TK("TK", "Tokelau", "Tokelau", "Tokelau", "Tokelau"),
		TL("TL", "Osttimor (Timor-Leste)", "Timor-Leste", "Timor oriental", "Timor Est"),
		TM("TM", "Turkmenistan", "Turkmenistan", "Turkménistan", "Turkmenistan"),
		TN("TN", "Tunesien", "Tunisia", "Tunisie", "Tunisia"), TO("TO", "Tonga", "Tonga", "Tonga", "Tonga"),
		TR("TR", "Türkei", "Turkey", "Turquie", "Turchia"),
		TT("TT", "Trinidad und Tobago", "Trinidad and Tobago", "Trinité et Tobago", "Trinidad e Tobago"),
		TV("TV", "Tuvalu (Ellice)", "Tuvalu", "Tuvalu (Ellice)", "Tuvalu (Ellice)"),
		TZ("TZ", "Tansania", "Tanzania United Republic of", "Tanzanie", "Tanzania"),
		UA("UA", "Ukraine", "Ukraine", "Ukraine", "Ucraina"), UG("UG", "Uganda", "Uganda", "Ouganda", "Uganda"),
		UM("UM", "United States Minor Outlying Islands", "United States Minor Outlying Islands",
				"Îles mineures éloignées des États-Unis", "Isole minori degli Stati Uniti"),
		US("US", "USA", "United States", "Etats-Unis d'Amérique", "Stati Uniti d'America"),
		UY("UY", "Uruguay", "Uruguay", "Uruguay", "Uruguay"),
		UZ("UZ", "Usbekistan", "Uzbekistan", "Ouzbékistan", "Uzbekistan"),
		VA("VA", "Vatikanstadt", "Vatican City State (Holy See)", "Vatican / (Saint-Siège)", "Città del Vaticano"),
		VC("VC", "St. Vincent", "Saint Vincent and the Grenadines", "St-Vincent", "St. Vincent"),
		VG("VG", "Britische Jungferninseln", "Virgin Islands (British)", "Îles Vierges britanniques",
				"Isole Vergini britanniche"),
		VI("VI", "Amerik. Jungfern-Ins.", "Virgin Islands (U.S.)", "Îles Vierges américaines", "Vergini, statunitense"),
		VN("VN", "Vietnam", "Vietnam", "Viét-Nam", "Vietnam"), VU("VU", "Vanuatu", "Vanuatu", "Vanuatu", "Vanuatu"),
		WD("WD", "Dominica", "Dominica", "Dominica", "Dominica"),
		WF("WF", "Wallis und Futuna", "Wallis and Futuna Islands", "Wallis et Futuna", "Wallis e Futuna"),
		WS("WS", "Westsamoa", "Samoa", "Samoa occidental", "Samoa Occidentali"),
		YE("YE", "Jemen, Arabische Rep.", "Yemen", "Yémen, Rép. Arabe", "Yemen, Rep. Araba"),
		YT("YT", "Mayotte", "Mayotte", "Mayotte", "Mayotte"),
		YU("YU", "Serbien/Montenegro", "Serbia and Montenegro the state union of", "Serbie/Monténégro",
				"Serbia/Montenegro"),
		YV("YV", "Venezuela", "Venezuela", "Vénézuéla", "Venezuela"),
		ZA("ZA", "Südafrika", "South Africa", "Afrique du Sud", "Sudafrica"),
		ZM("ZM", "Sambia", "Zambia", "Zambie", "Zambia"), ZW("ZW", "Zimbabwe", "Zimbabwe", "Zimbabwe", "Zimbabwe"),
		ZR("ZR", "Zaire", "Zaire", "Zaire", "Zaire");

		private String code;

		private String textDE;

		private String textEN;

		private String textFR;

		private String textIT;

		private CountryCode(String code, String textDE, String textEN, String textFR, String textIT) {
			this.code = code;
			this.textEN = textEN;
			this.textDE = textDE;
			this.textFR = textFR;
			this.textIT = textIT;
		}

		public String getCode() {
			return code;
		}

		public String getTextEN() {
			return textEN;
		}

		public String getTextDE() {
			return textDE;
		}

		public String getTextFR() {
			return textFR;
		}

		public String getTextIT() {
			return textIT;
		}

		public static CountryCode get(String code) {
			CountryCode ret = null;
			for (CountryCode cd : CountryCode.values()) {
				if (cd.getCode().equals(code)) {
					ret = cd;
					break;
				}
			}
			return ret;
		}

		public static String getText(String code, String lang) {
			String ret = null;
			CountryCode country = CountryCode.get(code);

			if (country != null) {
				if (lang.equalsIgnoreCase("EN")) {
					ret = country.getTextEN();
				} else if (lang.equalsIgnoreCase("DE")) {
					ret = country.getTextDE();
				} else if (lang.equalsIgnoreCase("FR")) {
					ret = country.getTextFR();
				} else if (lang.equalsIgnoreCase("IT")) {
					ret = country.getTextIT();
				}
			}

			return ret;
		}

	}

}
