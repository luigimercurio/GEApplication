package ge.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity (name = "Users")
public class RegisteredUser {
	@Id
	@Size (min = 1, max = 20, message = "User name must be between 1 and 20 characters long")
	private String username;

	@NotNull
	@Size (min = 8, max = 60, message = "Password must be between 8 and 20 characters long")
	private String password;

	@NotNull
	private boolean enabled;

	@OneToMany (mappedBy = "user")
	private Set<Authorities> authorities;

	public RegisteredUser () {
	}

	public String getUsername () {
		return username;
	}

	public void setUsername (String username) {
		this.username = username;
	}

	public String getPassword () {
		return password;
	}

	public void setPassword (String password) {
		this.password = password;
	}

	public boolean isEnabled () {
		return enabled;
	}

	public void setEnabled (boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Authorities> getAuthorities () {
		return authorities;
	}

	public void setAuthorities (Set<Authorities> authorities) {
		this.authorities = authorities;
	}
}
