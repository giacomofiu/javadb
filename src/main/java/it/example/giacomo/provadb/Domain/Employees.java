package it.example.giacomo.provadb.Domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Employees")
public class Employees {

	public Employees() {}
	public Employees(String first, String last) {
		this.first = first;
		this.last = last;
	}
		
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name="age")
	private Integer age;
	@Column(name="first")
	private String first;
	@Column(name="last")
	private String last;

	@OneToMany(mappedBy="employee")
	private Set<Benefit> benefitSet;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public Set<Benefit> getBenefitSet() {
		return benefitSet;
	}
	public void setBenefitSet(Set<Benefit> benefitSet) {
		this.benefitSet = benefitSet;
	}
	
}
