package com.chry.util.security;

public class Token {
	public static long EXPIRETIME = 5 * 60 * 1000;
	String _token;
	private long _exprieTime;
	
	private static String _generateToken(long epoch, String id) {
		char[] idChar = id.toCharArray();
		char[] epochChar = ("" + epoch).toCharArray();
		int random = (int)(epoch % 7);
		String token = "";
		int i, j;
		for (i=0, j=epochChar.length-1; i< idChar.length; i++,j--) {
			if(j >=0) {
				token += (char)((int)(epochChar[j]) + random);
			}
			token += (char)((int)(idChar[i]) + random);
		}
		for (; j >= 0; j--) {
			token += (char)((int)(epochChar[j]) + random);
		}
		return token;
	}
	public Token(String id) {
		 long epoch = System.currentTimeMillis();
		 _token = _generateToken(epoch, id);
		_exprieTime = System.currentTimeMillis() + EXPIRETIME;
	}
		
	public void reNew() {
		_exprieTime = System.currentTimeMillis() + EXPIRETIME;
	}
	
	public boolean isExpired() {
		return _exprieTime <= System.currentTimeMillis();
	}
	
	public String toString() {
		return _token;
	}

}
