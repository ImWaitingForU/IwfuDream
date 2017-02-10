package com.readboy.magicbook.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created with IntelliJ IDEA. User: zhangmin Module: Contacts Date: 14-5-26
 * Time: 下午5:00
 */

public class MD5Utils {
	/**
	 * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
	 */
	protected static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	protected static MessageDigest messagedigest = null;
	
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
			System.err.println(MD5Utils.class.getName()
					+ "初始化失败，MessageDigest不支持MD5Util。");
			nsaex.printStackTrace();
		}
	}

	/**
	 * MD5加密
	 * 
	 * @param str
	 *            转换前的字符串
	 * @return 转换后的字符串
	 */
	public static String getMD5Upper(String str) throws IOException {
		MessageDigest md5 = null;
		try {
			// 返回实现指定摘要算法的 MessageDigest对象 例如：MD5、SHA算法
			md5 = MessageDigest.getInstance("MD5");
			md5.reset(); // 重置MessageDigest对象
			md5.update(str.getBytes());// 指定byte数组更新
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			e.printStackTrace();
		}
		// 完成哈希算法，完成后 MessageDigest对象被设为初始状态，而且该方法只能调用一次
		byte[] byteArray = md5.digest();
		StringBuffer md5StrBuff = new StringBuffer ();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0x00FF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toUpperCase();
	}

	/**
	 * 生成字符串的md5校验值
	 * 
	 * @param s
	 * @return
	 */
	public static String getMD5(String s) {
		return getMD5(s.getBytes());
	}

	/**
	 * 判断字符串的md5校验码是否与一个已知的md5码相匹配
	 * 
	 * @param password
	 *            要校验的字符串
	 * @param md5PwdStr
	 *            已知的md5校验码
	 * @return
	 */
	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5(password);
		return s.equals(md5PwdStr);
	}

	/**
	 * 生成文件的md5校验值
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5(String fileName) throws IOException {
		File file = new File (fileName);
		return getFileMD5(file);
	}	
	
	/**
	 * 生成文件的md5校验值
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5(File file) throws IOException {
		InputStream fis;
		fis = new FileInputStream (file);
		byte[] buffer = new byte[4*1024*1024];
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0) {
			messagedigest.update(buffer, 0, numRead);
		}
		fis.close();
		return bufferToHex(messagedigest.digest());
	}

	public static String getMD5(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer (2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString().toUpperCase();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>>
												// 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
		char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
	
	/**
	 * @aim 获取MD5值
	 * @param s 传入字符串
	 * @return MD5值
	 */
	public final static String getMD5String(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4',
				'5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			//获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			//使用指定的字节更新摘要
			mdInst.update(btInput);
			//获得密文
			byte[] md = mdInst.digest();
			//把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String (str).toUpperCase();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	

	/**
	 * @aim 获取文件的编码类型
	 * @param file
	 * @return
	 */
	public static String getCharset(File file) {
        String charset = "UTF-8";
        BufferedInputStream bis = null;
        byte[] first3Bytes = new byte[3];
        try {
            bis = new BufferedInputStream (new FileInputStream (file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read != -1) {
            	if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
            		charset = "UTF-16LE";
            	} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
            		charset = "UTF-16BE";
            	} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
            			&& first3Bytes[2] == (byte) 0xBF) {
            		charset = "UTF-8";
            	}
            }
            bis.close();
            bis = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
        		if (bis != null) {
        			bis.close();
        			bis = null;
        		}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

        return charset;
    }
	
	
	
	/**
	 * @aim 获取文件的编码类型
	 * @return
	 */
	public static String getCharset(byte[] first3Bytes) {
        String charset = "GBK";
        try {
        	if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
        		charset = "UTF-16LE";
        	} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
        		charset = "UTF-16BE";
        	} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
        			&& first3Bytes[2] == (byte) 0xBF) {
        		charset = "UTF-8";
        	}
        } catch (Exception e) {
            e.printStackTrace();
		}
        return charset;
    }
}
