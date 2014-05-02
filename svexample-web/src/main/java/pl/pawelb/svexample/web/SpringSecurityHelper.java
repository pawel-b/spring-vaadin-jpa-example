package pl.pawelb.svexample.web;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * Additional Spring Security Helpers
 * 
 * @author pawelb
 * 
 */
public class SpringSecurityHelper {

	public static boolean hasRole(String role) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getAuthorities().contains(new SimpleGrantedAuthority(role));
	}

	public static String getLoggedUsername() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUsername();
	}
}
