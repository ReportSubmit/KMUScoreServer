package kmu.itsp.score.core.util;

/**
 * 숫자 값을 문자열로 바꾸거나 문자열을 숫자 값으로 바꾸는 유틸 클래스 
 * @author WJ
 *
 */
public class NumberConvertUtil {

	/**
	 * 파라미터 s가 double형 인지 확인하는 메소드
	 * @param s 문자열
	 * @return true or false
	 */
	// this method must move other class
	public static boolean isStringDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 문자열을 double형으로 변경하고 소수점 아래 무의미한 값을 제거하여 문자열로 반환 메소드 
	 * @param s 문자열
	 * @return String
	 */
	public static String cutDigitInDouble(String s) {
		double dbValue = Double.parseDouble(s);

		return formatDouble(dbValue + 0.0);
		// double value is -0.0 so -0.0 + 0.0 = 0.0
	}
	/**
	 * double 값의 소수점 17자리까지하며 소수점아래 무의미한 0 값을 제거하여 문자열로 반환 
	 * @param x 실수형 double
	 * @return String
	 */
	public static String formatDouble(double x) {
        return String.format("%.17g", x).replaceFirst("\\.?0+(e|$)", "$1");
	}

}
