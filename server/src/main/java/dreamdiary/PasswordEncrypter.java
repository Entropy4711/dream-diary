package dreamdiary;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncrypter {
	
	public static void main(String args[]) throws Exception {
		String cryptedPassword = new BCryptPasswordEncoder().encode("password");
		System.out.println(cryptedPassword);
	}
}