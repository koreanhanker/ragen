/******************************************************************************************************
* @���ϸ�		: kr.co.itfact.scrm.util.StringUtils.java
* @����			: String Util
* @��������		: 
* @��������		: 
* @�÷ο�		: 
*					1. XSSFilter(String)
*					 - Cross-site script ���� ���� (��ü)
*					 - ��ü �ϰ� REPLACE
*					2 .XSSFilter(String,String)
*					 - Cross-site script ���� �������� (���� �׸� ����)
*					 - sParam ���� : A(&), L(<), G(>), D("), S('), P(%)
*					 - Example     : XXFilter(ss,"PA") ;
*					3. SQLInjectionFilter(String)
*					 - SqlInjection script ���� ���� (��ü)
*					 - ��ü �ϰ� REPLACE
*					4 .SQLInjectionFilter(String,String)
*					 - SqlInjection script ���� �������� (���� �׸� ����)
*					 - sParam ���� : O(or), A(and), S(select), D(delete), I(insert) ,
*                					1(;) , 2(:)  , 3(--), 4(\\)
* @�˼�			: 
* @�ۼ���		: choi woo keun
* @������		: 2013.10.07
* @�����̷�
-----------------------------------------------------------------------------
No.		����				������			����
-----------------------------------------------------------------------------
1		 2013.11.28 	lee jeong gon	�ּ� ����
******************************************************************************************************/
package ragen.common.util;

import jakarta.mail.internet.InternetAddress;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

public class StringUtils extends org.apache.commons.lang3.StringUtils  {
	// Cross-site script 관련 필터 (전체)
	public static String XSSFilter (Object sInvalid) {
		String sValid = (String)sInvalid;
		if (sValid == null || sValid.equals("")) { return ""; }

		sValid = sValid.replaceAll("&", "&amp;");
		sValid = sValid.replaceAll("<", "&lt;");
		sValid = sValid.replaceAll(">", "&gt;");
		sValid = sValid.replaceAll("\"", "&qout;");
		sValid = sValid.replaceAll("\'", "&#039;");
		sValid = sValid.replaceAll("%", "");

		return sValid;
	}

	// Cross-site script 관련 필터 (선택),  sExt 유형 : A(&), L(<), G(>), D("), S('), P(%), Z(Nothing)
	public static String XSSFilter (Object sInvalid, String sParam) {
		String sValid = (String)sInvalid;
		int isChecked = 0;

		String sPattern = "ALGDSPZ" ;
		String sExt = "" ;

		if (sValid == null || sValid.equals("")) return "";
		if (sParam == null || sParam.equals("")) return XSSFilter(sInvalid);

		for (int i=0;i<sPattern.length();i++) {
			if (sParam.indexOf(sPattern.charAt(i)) == -1) {
				sExt = sExt + sPattern.substring(i,i+1);
			}
		}

		for (int i=0;i<sExt.length();i++) {
			if (sExt.toUpperCase().charAt(i)=='A') {
				sValid = sValid.replaceAll("&", "&amp;");
				isChecked=1 ;
			} else if (sExt.toUpperCase().charAt(i)=='L') {
				sValid = sValid.replaceAll("<", "&lt;");
				isChecked=1 ;
			} else if (sExt.toUpperCase().charAt(i)=='G') {
				sValid = sValid.replaceAll(">", "&gt;");
				isChecked=1 ;
			} else if (sExt.toUpperCase().charAt(i)=='D') {
				sValid = sValid.replaceAll("\"", "&qout;");
				isChecked=1 ;
			} else if (sExt.toUpperCase().charAt(i)=='S') {
				sValid = sValid.replaceAll("\'", "&#039;");
				isChecked=1 ;
			} else if (sExt.toUpperCase().charAt(i)=='P') {
				sValid = sValid.replaceAll("%", "");
				isChecked=1 ;
			} else if (sExt.toUpperCase().charAt(i)=='Z') {
				isChecked=1 ;
			}
		}

		if (isChecked==0) return XSSFilter(sInvalid);

		return sValid;
	}

	// SqlInjection script 관련 필터 (전체)
	public static String SQLInjectionFilter (Object sInvalid) {
		String sValid = (String)sInvalid;
		if (sValid == null || sValid.equals("")) return "";

		sValid = sValid.replaceAll(" (?i)or ", "");
		sValid = sValid.replaceAll(" (?i)and ", "");
		sValid = sValid.replaceAll(" (?i)select ", "");
		sValid = sValid.replaceAll(" (?i)delete ", "");
		sValid = sValid.replaceAll(" (?i)insert ","");

		// 이함수는 제외 한다. (사유 : 웹켄트 입력시 & , < > 의 변환문자 손상
		//sValid = sValid.replaceAll(";", "");
		sValid = sValid.replaceAll(":", "");
		sValid = sValid.replaceAll("--", "");
		sValid = sValid.replaceAll("\\\\", "");

		return sValid;
	}

	// SqlInjection script 관련 필터 (선택),
	//  sExt 유형 : O(or), A(and), S(select), D(delete), I(insert) , Z(Nothing)
	//              1(;) , 2(:)  , 3(--), 4(\\)
	public static String SQLInjectionFilter (Object sInvalid,String sParam) {
		String sValid = (String)sInvalid;
		int isChecked = 0;

		String sPattern = "OASDI1234Z" ;
		String sExt = "" ;

		if (sValid == null || sValid.equals("")) return "";
		if (sParam == null || sParam.equals("")) return SQLInjectionFilter(sInvalid);

		for (int i = 0; i < sPattern.length(); i++) {
			if (sParam.indexOf(sPattern.charAt(i)) == -1) {
				sExt = sExt + sPattern.substring(i,i + 1);
			}
		}

		for (int i = 0; i < sExt.length(); i++) {
			if (sExt.toUpperCase().charAt(i) == 'O') {
				sValid = sValid.replaceAll(" (?i)or ", "");
				isChecked=1;
			} else if (sExt.toUpperCase().charAt(i) == 'A') {
				sValid = sValid.replaceAll(" (?i)and ", "");
				isChecked=1;
			} else if (sExt.toUpperCase().charAt(i) == 'S') {
				sValid = sValid.replaceAll(" (?i)select ", "");
				isChecked=1;
			} else if (sExt.toUpperCase().charAt(i) == 'D') {
				sValid = sValid.replaceAll(" (?i)delete ", "");
				isChecked=1;
			} else if (sExt.toUpperCase().charAt(i) == 'I') {
				sValid = sValid.replaceAll(" (?i)insert ","");
				isChecked=1;
				// 이함수는 제외 한다. (사유 : 웹켄트 입력시 & , < > 의 변환문자 손상
			} else if (sExt.toUpperCase().charAt(i) == '1') {
				sValid = sValid.replaceAll(";", "");
				isChecked=1;
			} else if (sExt.toUpperCase().charAt(i) == '2') {
				sValid = sValid.replaceAll(":", "");
				isChecked=1;
			} else if(sExt.toUpperCase().charAt(i) == '3') {
				sValid = sValid.replaceAll("--", "");
				isChecked=1;
			} else if (sExt.toUpperCase().charAt(i) == '4') {
				sValid = sValid.replaceAll("\\\\", "");
				isChecked=1;
			} else if (sExt.toUpperCase().charAt(i) == 'Z') {
				isChecked=1;
			}
		}

		if (isChecked == 0) {
			return SQLInjectionFilter(sInvalid);
		}

		return sValid;
	}

	public static String clobToString(Clob clob) throws SQLException, IOException {
		if (clob == null) return "";
		StringBuffer out = new StringBuffer();
		String str = "";

		BufferedReader br = null;
		try {
			br = new BufferedReader(clob.getCharacterStream());
			while ((str = br.readLine()) != null) {
				out.append(str);
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return out.toString();
	}

	public static byte[] hexToBytes(String strHex) {
		int len = strHex.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i +=2) {
			data[i / 2] = (byte)((Character.digit(strHex.charAt(i), 16) << 4) + Character.digit(strHex.charAt(i + 1), 16));
		}
		return data;
	}

	public static String bytesToHex(byte[] arrBytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arrBytes.length; i++) {
			sb.append(String.format("%02x ", arrBytes[i]&0xff));
		}
		return sb.toString();
	}

	public static InternetAddress[] listToArray(List<String> list, String personal) throws UnsupportedEncodingException {
		if (list == null) return null;

		InternetAddress[] addresses = new InternetAddress[list.size()];
		for (int i = 0; i < list.size(); i++) {
			String address = list.get(i);
			addresses[i] = new InternetAddress(address, personal);
		}

		return addresses;
	}

	//문서파일 여부 확인하기
	public static boolean isDocumentFile(File file) {
		String fileName = file.getName();
		String fileExtension = getFileExtension(fileName);

		// 문서 파일 확장자 목록을 정의하고 여기에 추가합니다.
		String[] documentExtensions = {"xlsx","xls","doc", "docx", "txt", "pdf", "ppt", "pptx"};

		for (String extension : documentExtensions) {
			if (fileExtension.equalsIgnoreCase(extension)) {
				return true;
			}
		}

		return false;
	}

	//동영상 여부 확인하기
	public static boolean isVideoFile(File file) {
		String fileName = file.getName();
		String fileExtension = getFileExtension(fileName);

		// 문서 파일 확장자 목록을 정의하고 여기에 추가합니다.
		String[] videoExtensions = {"mp4", "m4v", "avi", "asf", "wmv", "mkv", "ts", "mpg", "mpeg", "mov", "flv", "ogv"};

		for (String extension : videoExtensions) {
			if (fileExtension.equalsIgnoreCase(extension)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Is audio file boolean.
	 *오디오 여부 확인하기
	 * @param file the file
	 * @return the boolean
	 */

	public static boolean isAudioFile(File file) {
		String fileName = file.getName();
		String fileExtension = getFileExtension(fileName);

		// 문서 파일 확장자 목록을 정의하고 여기에 추가합니다.
		String[] audioExtensions = {"mp3", "wav", "flac", "tta", "tak", "aac", "wma", "ogg", "m4a"};

		for (String extension : audioExtensions) {
			if (fileExtension.equalsIgnoreCase(extension)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets file extension.
	 *
	 * @param fileName the file name
	 * @return the file extension
	 */
	public static String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
			return fileName.substring(dotIndex + 1);
		}
		return "";
	}

	/**
	 * Null-safe check if the specified map is empty.
	 * <p>
	 * Null returns true.
	 *
	 * @param map  the map to check, may be null
	 * @return true if empty or null
	 * @since 3.2
	 */
	public static boolean isMapEmpty (final Map<?, ?> map){
		return map == null || map.isEmpty();
	}

	/**
	 * Generate unique random numbers string.
	 *랜덤 번호 생성
	 * 중복되지 않는 난수를 공백으로 구분된 문자열로 반환
	 * @param count the count
	 * @return the string
	 */
	public static String generateUniqueRandomNumbers(int count) {
		Random random = new Random();
		Set<Integer> generatedNumbers = new HashSet<>();

		while (generatedNumbers.size() < count) {
			int randomNumber = random.nextInt(100); // 0부터 99까지의 난수 생성

			generatedNumbers.add(randomNumber);
		}

		StringBuilder result = new StringBuilder();
		for (int number : generatedNumbers) {
			result.append(number);
		}

		return result.toString().trim();
	}

	/**
	 * Gets media type for file name.
	 *
	 * @param fileName the file name
	 * @return the media type for file name
	 */
	public static MediaType getMediaTypeForFileName(String fileName) {
		String fileExtension = StringUtils.getFileExtension(fileName);

		if (fileExtension != null) {
			switch (fileExtension.toLowerCase()) {
				case "jpg":
				case "jpeg":
					return MediaType.IMAGE_JPEG;
				case "png":
					return MediaType.IMAGE_PNG;
				case "gif":
					return MediaType.IMAGE_GIF;
			}
		}

		return null; // 지원하지 않는 확장자인 경우
	}
}

