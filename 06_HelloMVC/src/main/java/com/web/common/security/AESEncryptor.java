package com.web.common.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

// 양방향 암호화 / 복호화
// 암호화, 복호화하는 key를 잘 관리해야 한다.
// AES 방식으로 암호화, 복호화 메소드 만들어보자
public class AESEncryptor {
	// 1. key값 생성 및 가져오기 -> 유일한 값으로 고정되어야 함.
	//	1) 이미 생성된 key가 있으면 그 key를 가져와 활용
	//	2)생성된 key가 없으면 key를 새로생성해서 활용
	// key는 특정파일에 저장, 관리함
	private static SecretKey key;
	private String path; //key가 저장된 파일의 경로
	
	public AESEncryptor() {
		//key 클래스 생성 및 가져오는 로직
		this.path = AESEncryptor.class.getResource("/").getPath();
		this.path = this.path.substring(0,this.path.indexOf("classes"));
		File keyFile = new File(this.path+"bslove.bs");
		if(keyFile.exists()) {
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(keyFile))){
				AESEncryptor.key = (SecretKey) ois.readObject();
			}catch(IOException e) {
				e.printStackTrace();
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else {
			getGeneratorKey();
		}
	}
	
	private void getGeneratorKey() {
		SecureRandom rnd = new SecureRandom();
		KeyGenerator keygen = null;
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.path+"bslove.bs"))) {
			keygen = KeyGenerator.getInstance("AES");
			keygen.init(128,rnd);
			AESEncryptor.key = keygen.generateKey();
			oos.writeObject(AESEncryptor.key);
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String encryptData(String oriData) throws Exception{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, AESEncryptor.key);
		byte[] oriByte = oriData.getBytes(Charset.forName("UTF-8"));
		byte[] encByte = cipher.doFinal(oriByte);
		
		return Base64.getEncoder().encodeToString(encByte);
		
	}
	
	public static String decryptData(String encData) throws Exception{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, AESEncryptor.key);
		byte[] encByte = Base64.getDecoder().decode(encData.getBytes(Charset.forName("UTF-8")));
		byte[] decryptByte = cipher.doFinal(encByte);
		return new String(decryptByte);
	}
}
