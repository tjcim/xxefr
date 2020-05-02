package com.trevor.login;

public class LoginService {
	
	public boolean isUserValid(String email, String password) {
		if (email.contentEquals("user@xxefr.com") && password.contentEquals("xxe"))
			return true;
		
		return false;
	}

}
