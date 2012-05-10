package be.pds.thesis;

public class User {

	public enum Role {
		ADMIN, USER;
	}
	
	private String username;
		
	private Role role;
	
	public User(String u, Role r) {
		this.username = u;
		this.role = r;
	}
	
	public User(String u) {
		this.username = u;
	}
	
	public String getUser() {
		return this.username;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	public boolean isAdmin() {
		return (this.role == Role.ADMIN);
	}
}
