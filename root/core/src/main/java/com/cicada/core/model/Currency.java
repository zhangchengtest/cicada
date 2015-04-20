package com.cicada.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cicada.core.inernal.UseTypeHandler;

/**
 * Major currency.
 * 
 * @author hermano
 *
 */
@UseTypeHandler(value = "com.cicada.dao.handler.CurrencyTypeHandler")
public class Currency implements Serializable {

	private static final long serialVersionUID = 5514582134075491438L;

	private static Map<String, Currency> nameMap = new HashMap<String, Currency>(100);
	private static Map<String, Currency> codeMap = new HashMap<String, Currency>(100);
	private static List<Currency> allValues = new ArrayList<Currency>(100);

	/**
	 * 阿尔及利亚|第纳尔
	 */
	public static final Currency DZD = new Currency("DZD", "012", (short) 2, "", "DZD");

	/**
	 * 阿根廷|比索
	 */
	public static final Currency ARS = new Currency("ARS", "032", (short) 2, "", "ARS");

	/**
	 * Australia Australian dollar, AUD ($)
	 */
	public static final Currency AUD = new Currency("AUD", "036", (short) 2, "$", "Australia Australian dollar");

	/**
	 * 巴哈马元
	 */
	public static final Currency BSD = new Currency("BSD", "044", (short) 2, "", "BSD");

	/**
	 * 巴林|第纳儿
	 */
	public static final Currency BHD = new Currency("BHD", "048", (short) 3, "", "BHD");

	/**
	 * 孟加拉国|塔卡
	 */
	public static final Currency BDT = new Currency("BDT", "050", (short) 2, "", "BDT");

	/**
	 * 博茨瓦纳|普拉
	 */
	public static final Currency BWP = new Currency("BWP", "072", (short) 2, "", "BWP");

	/**
	 * 文莱元
	 */
	public static final Currency BND = new Currency("BND", "096", (short) 2, "", "BND");

	/**
	 * Canada Canadian dollar, CAD ($)
	 */
	public static final Currency CAD = new Currency("CAD", "124", (short) 2, "$", "Canada Canadian dollar");

	/**
	 * 斯里兰卡|卢比
	 */
	public static final Currency LKR = new Currency("LKR", "144", (short) 2, "", "LKR");

	/**
	 * China Chinese yuan, CNY (¥)
	 */
	public static final Currency CNY = new Currency("CNY", "156", (short) 2, "¥", "China Chinese yuan");

	/**
	 * 哥伦比亚|比索
	 */
	public static final Currency COP = new Currency("COP", "170", (short) 2, "", "COP");

	/**
	 * 科摩罗法郎
	 */
	public static final Currency KMF = new Currency("KMF", "174", (short) 0, "", "KMF");

	/**
	 * 哥斯达黎加科朗
	 */
	public static final Currency CRC = new Currency("CRC", "188", (short) 2, "", "CRC");

	/**
	 * 捷克|克郎
	 */
	public static final Currency CZK = new Currency("CZK", "203", (short) 2, "", "CZK");

	/**
	 * 丹麦克朗
	 */
	public static final Currency DKK = new Currency("DKK", "208", (short) 2, "", "DKK");

	/**
	 * 埃塞俄比亚比尔
	 */
	public static final Currency ETB = new Currency("ETB", "230", (short) 2, "", "ETB");

	/**
	 * 斐济元
	 */
	public static final Currency FJD = new Currency("FJD", "242", (short) 2, "", "FJD");

	/**
	 * 冈比亚|达拉西
	 */
	public static final Currency GMD = new Currency("GMD", "270", (short) 2, "", "GMD");

	/**
	 * 几内亚法郎
	 */
	public static final Currency GNF = new Currency("GNF", "324", (short) 0, "", "GNF");

	/**
	 * Hong Kong Hong Kong dollar, HKD ($)
	 */
	public static final Currency HKD = new Currency("HKD", "344", (short) 2, "$", "Hong Kong Hong Kong dollar");

	/**
	 * 匈牙利|福林
	 */
	public static final Currency HUF = new Currency("HUF", "348", (short) 2, "", "HUF");

	/**
	 * 冰岛|克朗
	 */
	public static final Currency ISK = new Currency("ISK", "352", (short) 2, "", "ISK");

	/**
	 * 印度|卢比
	 */
	public static final Currency INR = new Currency("INR", "356", (short) 2, "", "INR");

	/**
	 * 印尼卢比
	 */
	public static final Currency IDR = new Currency("IDR", "360", (short) 0, "", "IDR");

	/**
	 * 以色列|谢克尔
	 */
	public static final Currency ILS = new Currency("ILS", "376", (short) 2, "", "ILS");

	/**
	 * Japan Japanese yen, JPY (¥)
	 */
	public static final Currency JPY = new Currency("JPY", "392", (short) 0, "¥", "Japan Japanese yen");

	/**
	 * 哈萨克斯|坦坚戈
	 */
	public static final Currency KZT = new Currency("KZT", "398", (short) 2, "", "KZT");

	/**
	 * 约旦第纳尔
	 */
	public static final Currency JOD = new Currency("JOD", "400", (short) 3, "", "JOD");

	/**
	 * 肯尼亚|先令
	 */
	public static final Currency KES = new Currency("KES", "404", (short) 2, "", "KES");

	/**
	 * 韩元
	 */
	public static final Currency KRW = new Currency("KRW", "410", (short) 0, "", "KRW");

	/**
	 * 科威特|第纳尔
	 */
	public static final Currency KWD = new Currency("KWD", "414", (short) 3, "", "KWD");

	/**
	 * 老挝|基普
	 */
	public static final Currency LAK = new Currency("LAK", "418", (short) 2, "", "LAK");

	/**
	 * 黎巴嫩镑
	 */
	public static final Currency LBP = new Currency("LBP", "422", (short) 2, "", "LBP");

	/**
	 * 澳门元
	 */
	public static final Currency MOP = new Currency("MOP", "446", (short) 2, "", "MOP");

	/**
	 * 马拉维|克瓦查
	 */
	public static final Currency MWK = new Currency("MWK", "454", (short) 2, "", "MWK");

	/**
	 * 马来西亚|林吉特
	 */
	public static final Currency MYR = new Currency("MYR", "458", (short) 2, "", "MYR");

	/**
	 * 马尔代夫|拉菲亚
	 */
	public static final Currency MVR = new Currency("MVR", "462", (short) 2, "", "MVR");

	/**
	 * 毛里塔尼亚|乌吉亚
	 */
	public static final Currency MRO = new Currency("MRO", "478", (short) 2, "", "MRO");

	/**
	 * 毛里求斯|卢比
	 */
	public static final Currency MUR = new Currency("MUR", "480", (short) 2, "", "MUR");

	/**
	 * Mexico Mexican peso, MXN ($)
	 */
	public static final Currency MXN = new Currency("MXN", "484", (short) 2, "$", "Mexico Mexican peso");

	/**
	 * 蒙古|图格里克
	 */
	public static final Currency MNT = new Currency("MNT", "496", (short) 2, "", "MNT");

	/**
	 * 摩洛哥迪拉姆
	 */
	public static final Currency MAD = new Currency("MAD", "504", (short) 2, "", "MAD");

	/**
	 * 尼泊尔|卢比
	 */
	public static final Currency NPR = new Currency("NPR", "524", (short) 2, "", "NPR");

	/**
	 * New Zealand New Zealand dollar, NZD ($)
	 */
	public static final Currency NZD = new Currency("NZD", "554", (short) 2, "$", "New Zealand New Zealand dollar");

	/**
	 * 尼日利亚|奈拉
	 */
	public static final Currency NGN = new Currency("NGN", "566", (short) 2, "", "NGN");

	/**
	 * 挪威克郎
	 */
	public static final Currency NOK = new Currency("NOK", "578", (short) 2, "", "NOK");

	/**
	 * 巴基斯坦|卢比
	 */
	public static final Currency PKR = new Currency("PKR", "586", (short) 2, "", "PKR");

	/**
	 * 巴布亚新几内亚|基纳
	 */
	public static final Currency PGK = new Currency("PGK", "598", (short) 2, "", "PGK");

	/**
	 * 秘鲁|新索尔
	 */
	public static final Currency PEN = new Currency("PEN", "604", (short) 2, "", "PEN");

	/**
	 * 菲律宾比索
	 */
	public static final Currency PHP = new Currency("PHP", "608", (short) 2, "", "PHP");

	/**
	 * 卡塔尔里亚尔
	 */
	public static final Currency QAR = new Currency("QAR", "634", (short) 2, "", "QAR");

	/**
	 * Russia Russian ruble, RUB (&#8381;)
	 */
	public static final Currency RUB = new Currency("RUB", "643", (short) 2, "&#8381;", "Russia Russian ruble");

	/**
	 * 卢旺达法郎
	 */
	public static final Currency RWF = new Currency("RWF", "646", (short) 0, "", "RWF");

	/**
	 * 沙特|里亚尔
	 */
	public static final Currency SAR = new Currency("SAR", "682", (short) 2, "", "SAR");

	/**
	 * 塞舌尔|卢比
	 */
	public static final Currency SCR = new Currency("SCR", "690", (short) 2, "", "SCR");

	/**
	 * 塞拉利昂|里昂
	 */
	public static final Currency SLL = new Currency("SLL", "694", (short) 2, "", "SLL");

	/**
	 * Singapore Singapore dollar, SGD ($)
	 */
	public static final Currency SGD = new Currency("SGD", "702", (short) 2, "$", "Singapore Singapore dollar");

	/**
	 * 越南盾
	 */
	public static final Currency VND = new Currency("VND", "704", (short) 0, "", "VND");

	/**
	 * 南非|兰特
	 */
	public static final Currency ZAR = new Currency("ZAR", "710", (short) 2, "", "ZAR");

	/**
	 * 苏丹镑
	 */
	public static final Currency SDD = new Currency("SDD", "736", (short) 2, "", "SDD");

	/**
	 * Sweden Swedish krona, SEK (kr)
	 */
	public static final Currency SEK = new Currency("SEK", "752", (short) 2, "kr", "Sweden Swedish krona");

	/**
	 * Switzerland Swiss franc, CHF (Fr)
	 */
	public static final Currency CHF = new Currency("CHF", "756", (short) 2, "Fr", "Switzerland Swiss franc");

	/**
	 * 叙利亚|镑
	 */
	public static final Currency SYP = new Currency("SYP", "760", (short) 2, "", "SYP");

	/**
	 * 泰铢
	 */
	public static final Currency THB = new Currency("THB", "764", (short) 2, "", "THB");

	/**
	 * 汤加|潘加
	 */
	public static final Currency TOP = new Currency("TOP", "776", (short) 2, "", "TOP");

	/**
	 * 阿联酋|迪拉姆
	 */
	public static final Currency AED = new Currency("AED", "784", (short) 2, "", "AED");

	/**
	 * 乌干达|先令
	 */
	public static final Currency UGX = new Currency("UGX", "800", (short) 0, "", "UGX");

	/**
	 * 埃及|磅
	 */
	public static final Currency EGP = new Currency("EGP", "818", (short) 2, "", "EGP");

	/**
	 * United Kingdom Pound sterling, GBP (£)
	 */
	public static final Currency GBP = new Currency("GBP", "826", (short) 2, "£", "United Kingdom Pound sterling");

	/**
	 * 坦桑尼亚|先令
	 */
	public static final Currency TZS = new Currency("TZS", "834", (short) 2, "", "TZS");

	/**
	 * United States dollar, USD ($)
	 */
	public static final Currency USD = new Currency("USD", "840", (short) 2, "$", "United States dollar");

	/**
	 * 乌拉圭|比索
	 */
	public static final Currency UYU = new Currency("UYU", "858", (short) 2, "", "UYU");

	/**
	 * 也门|里亚尔
	 */
	public static final Currency YER = new Currency("YER", "886", (short) 2, "", "YER");

	/**
	 * 赞比亚|克瓦查
	 */
	public static final Currency ZMK = new Currency("ZMK", "894", (short) 2, "", "ZMK");

	/**
	 * 新台币
	 */
	public static final Currency TWD = new Currency("TWD", "901", (short) 2, "", "TWD");

	/**
	 * 加纳|塞地
	 */
	public static final Currency GHS = new Currency("GHS", "936", (short) 2, "", "GHS");

	public static final Currency VEF = new Currency("VEF", "937", (short) 2, "", "VEF");

	/**
	 * 阿塞拜疆|马纳特
	 */
	public static final Currency AZN = new Currency("AZN", "944", (short) 2, "", "AZN");

	/**
	 * 罗马尼亚|列伊
	 */
	public static final Currency RON = new Currency("RON", "946", (short) 2, "", "RON");

	/**
	 * Turkey Turkish lira, TRY (&#8378;)
	 */
	public static final Currency TRY = new Currency("TRY", "949", (short) 2, "&#8378;", "Turkey Turkish lira");

	/**
	 * 中非金融合作法郎
	 */
	public static final Currency XAF = new Currency("XAF", "950", (short) 0, "", "XAF");

	/**
	 * 非洲金融共同体法郎
	 */
	public static final Currency XOF = new Currency("XOF", "952", (short) 0, "", "XOF");

	/**
	 * 大溪地|太平洋法郎
	 */
	public static final Currency XPF = new Currency("XPF", "953", (short) 0, "", "XPF");

	/**
	 * 马达加斯加|阿里亚里
	 */
	public static final Currency MGA = new Currency("MGA", "969", (short) 2, "", "MGA");

	/**
	 * 阿富汗尼
	 */
	public static final Currency AFN = new Currency("AFN", "971", (short) 2, "", "AFN");

	/**
	 * 塔吉克斯坦|索莫尼
	 */
	public static final Currency TJS = new Currency("TJS", "972", (short) 2, "", "TJS");

	/**
	 * 白俄罗斯卢布
	 */
	public static final Currency BYR = new Currency("BYR", "974", (short) 2, "", "BYR");

	/**
	 * 保加利亚列弗
	 */
	public static final Currency BGN = new Currency("BGN", "975", (short) 2, "", "BGN");

	/**
	 * 刚果法郎
	 */
	public static final Currency CDF = new Currency("CDF", "976", (short) 2, "", "CDF");

	/**
	 * Euro, EUR (€)
	 */
	public static final Currency EUR = new Currency("EUR", "978", (short) 2, "&#8364;", "Euro");

	/**
	 * 乌克兰币
	 */
	public static final Currency UAH = new Currency("UAH", "980", (short) 2, "", "UAH");

	/**
	 * 波兰|兹罗提
	 */
	public static final Currency PLN = new Currency("PLN", "985", (short) 2, "", "PLN");

	/**
	 * 巴西|雷亚尔
	 */
	public static final Currency BRL = new Currency("BRL", "986", (short) 2, "", "BRL");

	// ///////////////////////////////////////////////////////////////////////////////////////////////////

	private String name;
	private String code;
	private short precision;
	private String symbol;
	private String description;

	private Currency(String name, String code, short precision, String symbol, String description) {
		this.name = name;
		this.code = code;
		this.precision = precision;
		this.symbol = symbol;
		this.description = description;
		nameMap.put(name, this);
		codeMap.put(code, this);
		allValues.add(this);
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public short getPrecision() {
		return precision;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getDescription() {
		return description;
	}

	public static Currency parseByName(String name) {
		return nameMap.get(name);
	}

	public static Currency parseByCode(String code) {
		return codeMap.get(code);
	}

	public static List<Currency> values() {
		return Collections.unmodifiableList(allValues);
	}

	@Override
	public String toString() {
		return getName() + "(" + getDescription() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + precision;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Currency other = (Currency) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (precision != other.precision)
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

	public static List<Currency> getAllValues() {
		return allValues;
	}
}
