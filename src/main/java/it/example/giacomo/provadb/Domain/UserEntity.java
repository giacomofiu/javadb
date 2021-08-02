package it.example.giacomo.provadb.Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="User")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String email;
	
	public UserEntity() {
	}
	
	public UserEntity(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
    public long getId() {
        return id;
    }	

    public void setId(long id) {
        this.id = id;
    }	
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() {
		return "name: " + name + " " + "email: " + email;
	}
	
}
