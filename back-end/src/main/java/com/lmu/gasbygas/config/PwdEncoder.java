package com.lmu.gasbygas.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PwdEncoder implements PasswordEncoder {


	@Override
	public String encode(CharSequence rawPassword) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.reset();

			byte[] passBytes = new byte[rawPassword.length()];
			for (int i = 0; i < passBytes.length; i++) {
				passBytes[i] = (byte) rawPassword.charAt(i);
			}
			byte[] digested = md.digest(passBytes);

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digested.length; i++) {
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (encode(rawPassword).equals(encodedPassword)) {
			return true;
		}
		return false;
	}
}
