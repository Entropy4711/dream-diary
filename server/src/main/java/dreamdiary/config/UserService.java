package dreamdiary.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dreamdiary.domain.Login;
import dreamdiary.repository.LoginRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private LoginRepository loginRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Login> logins = loginRepository.findByUsername(username);

		if (logins != null && logins.size() == 1) {
			Login login = logins.get(0);
			List<String> roles = login.getRoles();
			List<GrantedAuthority> authorities = new ArrayList<>(roles.size());

			for (String role : roles) {
				GrantedAuthority ga = new SimpleGrantedAuthority(role);
				authorities.add(ga);
			}

			return new User(username, login.getPassword(), authorities);
		}

		throw new UsernameNotFoundException("User with name '" + username + "' was not found!");
	}
}