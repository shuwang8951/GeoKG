package njnu.utility;

/**
 * 全角转半角
 */
public class Tools {
	public static String CtoH(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == 12288) {
				result += (char) (str.charAt(i) - 12256);
				continue;
			}
			if (str.charAt(i) > 65293 && str.charAt(i) < 65306)
				result += (char) (str.charAt(i) - 65248);
			else
				result += (char) (str.charAt(i));
		}
		return result;
	}
}

