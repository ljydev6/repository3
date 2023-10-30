package com.web.common.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class PasswordEncoder extends HttpServletRequestWrapper{
	
	public PasswordEncoder(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getParameter(String name) {
		if(name.contains("password")) {
			String orival = super.getParameter(name);
			String change = getSHA512(orival);
			return change;
		}
		return super.getParameter(name);
	}
	
	public String getSHA512(String oriVal) {
		// java.security.MessageDigest 클래스를 이용
		MessageDigest md = null;
		try {
		md = MessageDigest.getInstance("SHA-512");
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		byte[] oriDataByte = oriVal.getBytes();
		md.update(oriDataByte);//sha-512방식으로 암호화
		byte[] encryptData = md.digest();
		//문자열로 변환하기 위해서 Base64클래스가 제공하는 인코더를 이용
		String encryptStr = Base64.getEncoder().encodeToString(encryptData);
		return encryptStr;
	}
}
