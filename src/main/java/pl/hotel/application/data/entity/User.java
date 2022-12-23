package pl.hotel.application.data.entity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import pl.hotel.application.data.Role;

@Entity
@Table(name = "AppUser")
public class User{// extends AbstractEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
    private String username;
    private String name;
    private String password;
    private String email;
    
 /*   @Enumerated(EnumType.STRING)
    private Role role;*/
/*    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Lob
    @Column(length = 1000000)
    private byte[] profilePicture;
*/
    
    public String getUsername() {
        return username;
    }
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/*
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}*/
	public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


}
