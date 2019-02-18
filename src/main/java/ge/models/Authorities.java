package ge.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
public class Authorities {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;

	@Size (min = 1, max = 20)
	private String authority;

	@ManyToOne
	@Size (min = 1, max = 20)
	@JoinColumn (name = "username", nullable = false)
	private RegisteredUser user;

	public int getId () {
		return id;
	}

	public void setId (int id) {
		this.id = id;
	}

	public String getAuthority () {
		return authority;
	}

	public void setAuthority (String authority) {
		this.authority = authority;
	}

	public RegisteredUser getUser () {
		return user;
	}

	public void setUser (RegisteredUser user) {
		this.user = user;
	}
}
