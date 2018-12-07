package com.digital.dance.commons.security;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSACoder {
	/**
	 * String to hold name of the encryption algorithm.
	 */
	public static final String ALGORITHM = "RSA";

	/**
	 * String to hold name of the encryption padding.
	 */
	public static final String PADDING = "RSA/NONE/NoPadding";

	/**
	 * String to hold name of the security provider.
	 */
	public static final String PROVIDER = "BC";

	/**
	 * String to hold the name of the private key file.
	 */
	public static String privateKeyFilePath = "c:/defonds/work/20150116/private.key";

	/**
	 * String to hold name of the public key file.
	 */
	public static String publicKeyFilePath = "c:/defonds/work/20150116/public.key";

	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	public static final String RSA_None_PKCS1Padding = "RSA/ECB/PKCS1Padding";
	public static final String RSA_ECB_PKCS1Padding = "RSA/ECB/PKCS1Padding";
	private static final int MD5_LENGTH = 16;
	public static final String key_modul_seperator = "```";
	private static SecretKey aesKey;
	
	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	/**
	 * The method checks if the pair of public and private key has been
	 * generated.
	 * 
	 * @return flag indicating if the pair of keys were generated.
	 */
	public static boolean areKeysPresent() {

		File privateKey = new File(privateKeyFilePath);
		File publicKey = new File(publicKeyFilePath);

		if (privateKey.exists() && publicKey.exists()) {
			return true;
		}
		return false;
	}

	public static KeyPair initKey() throws Exception {
		KeyPair keyPair = null;
		if (!areKeysPresent()) {
			// Method generates a pair of keys using the RSA algorithm and
			// stores it
			// in their respective files
			keyPair = generateKey(false);
		} else {
			try{
				keyPair = new KeyPair(generatePublicKey(null), generatePrivateKey(null));
			} catch (Exception ex){
				keyPair = generateKey(true);
			}
						
		}
		
		return keyPair;
	}

	/**
	 * Generate key which contains a pair of private and public key using 1024
	 * bytes. Store the set of keys in Prvate.key and Public.key files.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static KeyPair generateKey(boolean isRenew) {
		KeyPair key = null;
		Boolean keepOld = true;
		try {

			//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
			keyGen.initialize(1024);
			key = keyGen.generateKeyPair();

			File privateKeyFile = new File(privateKeyFilePath);
			File publicKeyFile = new File(publicKeyFilePath);

			if (!privateKeyFile.exists()){
				//秘钥文件不存在，就要建新的秘钥文件，没有旧的秘钥可保持
				keepOld = false;
			} else if (privateKeyFile.exists() && isRenew) {
				privateKeyFile.delete();
				keepOld = false;
			}

			if (!publicKeyFile.exists()){
				//秘钥文件不存在，就要建新的秘钥文件，没有旧的秘钥可保持
				keepOld = false;
				if ( privateKeyFile.exists() ) {
					privateKeyFile.delete();
				}
			} else if (publicKeyFile.exists() && ( isRenew || !keepOld )) {
				publicKeyFile.delete();
				keepOld = false;
			}

			if(keepOld == false){
				// Create files to store public and private key
				try {
					if (privateKeyFile.getParentFile() != null) {
						privateKeyFile.getParentFile().mkdirs();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					privateKeyFile.createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					if (publicKeyFile.getParentFile() != null) {
						publicKeyFile.getParentFile().mkdirs();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					publicKeyFile.createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
				}

				// Saving the Public key in a file
				ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
				publicKeyOS.writeObject(key.getPublic());
				publicKeyOS.close();

				// Saving the Private key in a file
				ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
				privateKeyOS.writeObject(key.getPrivate());
				privateKeyOS.close();
			} else {
				PublicKey publicKeyObj = getSavedPublicKey();
				PrivateKey privateKeyObj = getSavedPrivateKey();
				key = new KeyPair(publicKeyObj, privateKeyObj);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}

	public static String getPublicKey(KeyPair keyPair) throws Exception {
		
		StringBuffer buffer = new StringBuffer();
		RSAPublicKey rSAPublicKey = (RSAPublicKey)keyPair.getPublic();
		buffer.append(rSAPublicKey.getModulus());
		buffer.append(key_modul_seperator);
		buffer.append(rSAPublicKey.getPublicExponent());

		return buffer.toString();
	}

	public static String getPublicKey(RSAPublicKey rSAPublicKey) throws Exception {

		StringBuffer buffer = new StringBuffer();

		buffer.append(rSAPublicKey.getModulus());
		buffer.append(key_modul_seperator);
		buffer.append(rSAPublicKey.getPublicExponent());

		return buffer.toString();
	}
	

	public static String getPrivateKey(KeyPair keyPair) throws Exception {

		StringBuffer buffer = new StringBuffer();
		RSAPrivateKey rSAPublicKey = (RSAPrivateKey)keyPair.getPrivate();
		buffer.append(rSAPublicKey.getModulus());
		buffer.append(key_modul_seperator);
		buffer.append(rSAPublicKey.getPrivateExponent());

		return buffer.toString();
	}

	public static String getPrivateKey(RSAPrivateKey rSAPublicKey) throws Exception {

		StringBuffer buffer = new StringBuffer();

		buffer.append(rSAPublicKey.getModulus());
		buffer.append(key_modul_seperator);
		buffer.append(rSAPublicKey.getPrivateExponent());

		return buffer.toString();
	}

	@SuppressWarnings("resource")
	public static PublicKey generatePublicKey(String publicKey) throws Exception {
		if (!areKeysPresent()) {
			// Method generates a pair of keys using the RSA algorithm and
			// stores it
			// in their respective files
			generateKey(false);
		}
		ObjectInputStream inputStream = null;

		inputStream = new ObjectInputStream(new FileInputStream(publicKeyFilePath));
		PublicKey publicKeyObj = (PublicKey) inputStream.readObject();
		inputStream.close();
		return publicKeyObj;
	}

	public static PublicKey getSavedPublicKey() throws Exception {

		ObjectInputStream inputStream = null;

		inputStream = new ObjectInputStream(new FileInputStream(publicKeyFilePath));
		PublicKey publicKeyObj = (PublicKey) inputStream.readObject();
		inputStream.close();
		return publicKeyObj;
	}

	@SuppressWarnings("resource")
	public static PrivateKey generatePrivateKey(String privateKey) throws Exception {
		if (!areKeysPresent()) {
			// Method generates a pair of keys using the RSA algorithm and
			// stores it
			// in their respective files
			generateKey(false);
		}
		ObjectInputStream inputStream = null;

		inputStream = new ObjectInputStream(new FileInputStream(privateKeyFilePath));
		PrivateKey privateKeyObj = (PrivateKey) inputStream.readObject();
		inputStream.close();
		return privateKeyObj;
	}

	public static PrivateKey getSavedPrivateKey() throws Exception {

		ObjectInputStream inputStream = null;

		inputStream = new ObjectInputStream(new FileInputStream(privateKeyFilePath));
		PrivateKey privateKeyObj = (PrivateKey) inputStream.readObject();
		inputStream.close();
		return privateKeyObj;
	}
	
    /**  
     * 使用模和指数生成RSA公钥  
     *   
     *   
     * @param modulus  
     *            模  
     * @param exponent  
     *            指数  
     * @return  
     */    
    public static RSAPublicKey generatePublic(String modulus, String exponent) throws Exception {    
            
        BigInteger b1 = new BigInteger(modulus);    
        BigInteger b2 = new BigInteger(exponent);
        //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, PROVIDER);// new org.bouncycastle.jce.provider.BouncyCastleProvider());    
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);    
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);        
    }    
    
    /**  
     * 使用模和指数生成RSA私钥  
      
     * /None/NoPadding】  
     *   
     * @param modulus  
     *            模  
     * @param exponent  
     *            指数  
     * @return  
     */    
    public static RSAPrivateKey generatePrivate(String modulus, String exponent) throws Exception {    
         
        BigInteger b1 = new BigInteger(modulus);    
        BigInteger b2 = new BigInteger(exponent);
        //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, PROVIDER);// new org.bouncycastle.jce.provider.BouncyCastleProvider());    
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);    
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);        
    }

	public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
		String[] km = key.split(key_modul_seperator);
		return encryptByPublicKey(data, generatePublic(km[0], km[1]));
	}

	public static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {
		return encryptDecrypt(Cipher.ENCRYPT_MODE, data, publicKey);
	}

	public static byte[] decryptByPrivateKey(byte[] data, String base64Key) throws Exception {
		String[] km = base64Key.split(key_modul_seperator);
		return decryptByPrivateKey(data, generatePrivate(km[0], km[1]));
	}

	public static byte[] decryptByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {
		return encryptDecrypt(Cipher.DECRYPT_MODE, data, privateKey);
	}

	public static byte[] encryptByPrivateKey(byte[] data, String base64Privatekey) throws Exception {
		String[] km = base64Privatekey.split(key_modul_seperator);
		return encryptByPrivateKey(data, generatePrivate(km[0], km[1]));
	}

	public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {
		return encryptDecrypt(Cipher.ENCRYPT_MODE, data, privateKey);
	}

	public static byte[] decryptByPublicKey(byte[] data, String base64Publickey) throws Exception {
		String[] km = base64Publickey.split(key_modul_seperator);
		return decryptByPublicKey(data, generatePublic(km[0], km[1]));
	}

	public static byte[] decryptByX509PublicKey(byte[] data, String base64Publickey) throws Exception {

		return decryptByPublicKey(data, generatePublicKey(base64Publickey));
	}

	public static byte[] decryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {
		return encryptDecrypt(Cipher.DECRYPT_MODE, data, publicKey);
	}

	private static byte[] encryptDecrypt(int type, byte[] data, Key secretKey) throws Exception {
		byte[] cryptedCipherText = null;
		try {
			//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			final Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);

			int inputBlockSize = 128;

			if (type == 1) {
				cipher.init(1, secretKey);
				inputBlockSize = 117;
			} else if (type == 2) {
				cipher.init(2, secretKey);
				inputBlockSize = 128;
			} else {
				throw new Exception("Error type :" + type);
			}

			ByteArrayOutputStream cryptedTextBuffer = new ByteArrayOutputStream();

			int i = 0;
			boolean more = true;
			while (more) {
				int startPos = inputBlockSize * i;
				if (startPos >= data.length) {
					more = false;
				} else if (startPos + inputBlockSize > data.length) {
					more = false;
					int inputLen = data.length - startPos;
					cryptedTextBuffer.write(cipher.doFinal(data, startPos, inputLen));

				} else {
					cryptedTextBuffer.write(cipher.doFinal(data, startPos, inputBlockSize));

				}
				i++;
			}

			cryptedCipherText = cryptedTextBuffer.toByteArray();
			cryptedTextBuffer.close();
		} catch (Exception e) {
			throw e;
		}
		return cryptedCipherText;
	}

	public static String encryptWithRSA_AES(String plainText, Key key) {
		try {
			SecretKey aesKey = getAESSecretKey();
			byte[] plain = plainText.getBytes("UTF-8");

			byte[] cipher = encryptData(plain, aesKey, "AES");

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plain);
			byte[] md5 = md.digest();

			byte[] aesKeyEncoded = aesKey.getEncoded();
			byte[] toencode = new byte[aesKeyEncoded.length + md5.length];
			System.arraycopy(md5, 0, toencode, 0, md5.length);
			System.arraycopy(aesKeyEncoded, 0, toencode, md5.length, aesKeyEncoded.length);

			byte[] byteEncryptWithPublicKey = encryptData(toencode, key, ALGORITHM);

			return BASE64coderUrlSafe.encode2String(byteEncryptWithPublicKey) + "*"
					+ BASE64coderUrlSafe.encode2String(cipher);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String decryptWithRSA_AES(String cipher, Key key) {
		try {
			String[] split = cipher.split("\\*");
			if (split.length != 2) {
				throw new RuntimeException("密文错误");
			}

			byte[] aeskeyAndMD5 = BASE64coderUrlSafe.decode(split[0]);
			byte[] decryptAesMd5 = decryptData(aeskeyAndMD5, key, ALGORITHM);

			byte[] encryptedData = BASE64coderUrlSafe.decode(split[1]);

			byte[] md5 = new byte[16];
			System.arraycopy(decryptAesMd5, 0, md5, 0, md5.length);
			byte[] aesKey = new byte[decryptAesMd5.length - 16];
			System.arraycopy(decryptAesMd5, md5.length, aesKey, 0, aesKey.length);

			SecretKeySpec secretKeySpecDecrypted = new SecretKeySpec(aesKey, "AES");

			byte[] decryptData = decryptData(encryptedData, secretKeySpecDecrypted, "AES");

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(decryptData);
			byte[] mdToVerify = md.digest();

			if (md5.length != mdToVerify.length) {
				throw new RuntimeException("消息摘要不正确");
			}
			for (int i = 0; i < md5.length; i++) {
				if (md5[i] != mdToVerify[i]) {
					throw new RuntimeException("消息摘要不正确");
				}
			}
			return new String(decryptData, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String encryptAndSign(String plainTxt, PublicKey publicKey, PrivateKey privateKey) throws Exception {
		byte[] plain = plainTxt.getBytes();
		SecretKey senderSecretKey = getAESSecretKey();

		byte[] byteCipherText = encryptData(plain, senderSecretKey, "AES");

		byte[] byteEncryptWithPublicKey = encryptData(senderSecretKey.getEncoded(), publicKey, ALGORITHM);
		String strSenbyteEncryptWithPublicKey = Base64Coder.encode2String(byteEncryptWithPublicKey);

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plain);
		byte[] byteMDofDataToTransmit = md.digest();
		String strMd = Base64Coder.encode2String(byteMDofDataToTransmit);

		Signature mySign = Signature.getInstance("MD5withRSA");
		mySign.initSign(privateKey);
		String rsaData = strSenbyteEncryptWithPublicKey + "*" + strMd;
		mySign.update(rsaData.getBytes());
		byte[] byteSignedData = mySign.sign();

		return Base64Coder.encode2String(byteSignedData) + "*" + rsaData + "*"
				+ Base64Coder.encode2String(byteCipherText);
	}

	public static String decryptAndVerify(String cipher, PublicKey publicKey, PrivateKey privateKey) throws Exception {
		String[] split = cipher.split("\\*");
		byte[] signedData = Base64Coder.decode(split[0]);
		String aesKey = split[1];
		String md5 = split[2];
		byte[] encodedData = Base64Coder.decode(split[3]);

		Signature myVerifySign = Signature.getInstance("MD5withRSA");
		myVerifySign.initVerify(publicKey);
		myVerifySign.update((aesKey + "*" + md5).getBytes());

		boolean verifySign = myVerifySign.verify(signedData);

		if (!verifySign) {
			throw new RuntimeException("签名验证不正确");
		}

		byte[] decryptedAesKey = decryptData(Base64Coder.decode(aesKey), privateKey, ALGORITHM);

		SecretKeySpec secretKeySpecDecrypted = new SecretKeySpec(decryptedAesKey, "AES");

		byte[] decryptData = decryptData(encodedData, secretKeySpecDecrypted, "AES");

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(decryptData);
		byte[] byteMDofDataToTransmit = md.digest();

		String strMD = Base64Coder.encode2String(byteMDofDataToTransmit);
		if (!strMD.equals(md5)) {
			throw new RuntimeException("数据验证不正确");
		}
		return new String(decryptData);
	}

	private static SecretKey getAESSecretKey() {
		if (aesKey != null)
			return aesKey;
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			aesKey = keyGen.generateKey();
			return aesKey;
		} catch (Exception exp) {
			throw new RuntimeException(exp);
		}
	}

	public static byte[] encryptData(byte[] byteDataToEncrypt, Key secretKey, String Algorithm) {
		byte[] byteCipherText = new byte['È'];
		try {
			Cipher aesCipher = Cipher.getInstance(Algorithm);

			if (Algorithm.equals("AES"))
				aesCipher.init(1, secretKey, aesCipher.getParameters());
			else if (Algorithm.equals(ALGORITHM))
				aesCipher.init(1, secretKey);
			else {
				throw new RuntimeException("不支持的加密算法 " + Algorithm);
			}

			byteCipherText = aesCipher.doFinal(byteDataToEncrypt);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return byteCipherText;
	}

	public static byte[] decryptData(byte[] byteCipherText, Key secretKey, String Algorithm) {
		byte[] byteDecryptedText = new byte['È'];
		try {
			Cipher aesCipher = Cipher.getInstance(Algorithm);
			if (Algorithm.equals("AES"))
				aesCipher.init(2, secretKey, aesCipher.getParameters());
			else if (Algorithm.equals(ALGORITHM)) {
				aesCipher.init(2, secretKey);
			}

			byteDecryptedText = aesCipher.doFinal(byteCipherText);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return byteDecryptedText;
	}

	public static String sign(byte[] data, String privateKey) throws Exception {

		return sign(data, generatePrivateKey(privateKey));
	}

	public static String sign(byte[] data, PrivateKey privateKey) throws Exception {
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(privateKey);
		signature.update(data);

		return Base64Coder.encode2String(signature.sign());
	}

	public static boolean verify(byte[] data, String base64KeyPublicKey, String sign) throws Exception {

		return verify(data, generatePublicKey(base64KeyPublicKey), sign);
	}

	public static boolean verify(byte[] data, PublicKey publicKey, String sign) throws Exception {
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initVerify(publicKey);
		signature.update(data);

		return signature.verify(Base64Coder.decode(sign));
	}

	/**
	 * @return the privateKeyFilePath
	 */
	public static String getPrivateKeyFilePath() {
		return privateKeyFilePath;
	}

	/**
	 * @param privateKeyFilePath the privateKeyFilePath to set
	 */
	public static void setPrivateKeyFilePath(String privateKeyFilePath) {
		RSACoder.privateKeyFilePath = privateKeyFilePath;
	}

	/**
	 * @return the publicKeyFilePath
	 */
	public static String getPublicKeyFilePath() {
		return publicKeyFilePath;
	}

	/**
	 * @param publicKeyFilePath the publicKeyFilePath to set
	 */
	public static void setPublicKeyFilePath(String publicKeyFilePath) {
		RSACoder.publicKeyFilePath = publicKeyFilePath;
	}

}