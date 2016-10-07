package com.chry.util.cipher;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.chry.util.FileUtil;

public class RSA {
	public static String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	
	public static void generateKeyFile() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		String rsaPrivateKey = Coder.encryptBASE64(privateKey.getEncoded());
		String rsaPublicKey = Coder.encryptBASE64(publicKey.getEncoded());
		System.out.println(rsaPrivateKey + "\n\n" + rsaPublicKey);
//		FileUtil.WriteStringToFile(rsaPublicKey, "publicKey");
//		FileUtil.WriteStringToFile(rsaPrivateKey, "publicKey");
	}


	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Coder.decryptBASE64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);
		return Coder.encryptBASE64(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {
		byte[] keyBytes = Coder.decryptBASE64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		return signature.verify(Coder.decryptBASE64(sign));
	}
	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = Coder.decryptBASE64(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 解密<br>
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
		byte[] keyBytes = Coder.decryptBASE64(key);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}
	
	/**
	 * 加密<br>
	 * 用公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key)
			throws Exception {
		// 对公钥解密
		byte[] keyBytes = Coder.decryptBASE64(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用私钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key)
			throws Exception {
		byte[] keyBytes = Coder.decryptBASE64(key);

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
/*	
	public static void main(String args[]) throws Exception {
//		generateKeyFile();
		String pubKey =
		"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ7Vc7QMxGcLSATNxO6aISd1bcjC\n" +
		"L1wneWVkvdfavQdqtBu7aOE+rlEqRx8+NcikCWw5G0XZ4n+Dk0eeBFj6EHokXOTM3hWlTkPy4212\n" +
		"GIVgLo25enNiKXgOUo735poLd5AJTxl/aMYDG06kXjObDIAnaGWpjHSVjUFxCt1AZ3S3AgMBAAEC\n" +
		"gYBWSVlg/+qWnC9UxMBuavcF66pnfO6jnqI5bgAgSqmUZOhRhH9Wlmm2mEQo+e/X/x1LWL6zNMny\n" +
		"QfV526M6/yaBhLuFoMAQM0zi/6pOxkXC4pEO8AFYaqpCsGAxWQwwx6xbPRDSzO1tsMGmnL4Hsa7K\n" +
		"VtVK0w20zV5V/RiGTaXySQJBAPa+sJvxa9NC1VeOnjvyppltB9s47NNPZkIQFDuiEctVFTnwJfk7\n" +
		"8BVX7WCj+oPUm2FrBhFilwC1omzAnEP2Pi0CQQCkyp3NRIX1lhFvzGH8gDARme3OFwNN5WJ6Ev74\n" +
		"8ZPoOyLh+q6ZQ5a+zrHqoGJ+v98JxOcF74YNVLXHSkedSjDzAkEA1cR7KX9NFShPwJ10hSdCIqYN\n" +
		"KmX091VQmGdpwAg3NluuGhXuMDE3XKZip1kpTlFBlHKuPR2XCSxTXbi5KUAKuQJAXu04qZ3nbOjd\n" +
		"coFRkJpnFjyNeEJZbO3OJHP83HqMsSIfnf1Bxpfgpll6s6VgF5N/b6k1S34Gwnchn4NybXDncQJA\n" +
		"Hclp4vbPNYZx13cSgXyYQ3wxGk6T2E2gl6jVLdq3t5THffxGLlWCnxhgSu92UkiMpCyOEsgfEY+h\n" +
		"J2rteCEQyw==";

		String priKey =
		"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCe1XO0DMRnC0gEzcTumiEndW3Iwi9cJ3llZL3X\n" +
		"2r0HarQbu2jhPq5RKkcfPjXIpAlsORtF2eJ/g5NHngRY+hB6JFzkzN4VpU5D8uNtdhiFYC6NuXpz\n" +
		"Yil4DlKO9+aaC3eQCU8Zf2jGAxtOpF4zmwyAJ2hlqYx0lY1BcQrdQGd0twIDAQAB";
		
		byte[] data = null;
		String test = "Hello World";
		encryptByPublicKey(data, test);
		String result = null;
		decryptByPrivateKey(data, result);
		System.out.println("\n\n" + result);
	}
*/	
}
