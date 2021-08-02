package it.example.giacomo.provadb.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Benefit")
public class Benefit {

	public Benefit() {}
	public Benefit(String benefitType, String infoBenefit) {
		this.benefitType = benefitType;
		this.infoBenefit = infoBenefit;
	}
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name="benefit_type")
	private String benefitType;

	@Column(name="info_benefit")
	private String infoBenefit;

	@Column(name="employee_id")
	private Integer employeeId;

	//Entity name is better be singular (also table name), not Employees
	//nullable=false, insertable = false, updatable = false are needed to avoid error
	//Repeated column in mapping for entity: (should be mapped with insert="false" update="false")
	//it happens when you refer to a foreign key has been already defined in your Entity, like @Column name='employee_id' and @JoinColumn name='employee_id'
	@ManyToOne
	@JoinColumn(name="employee_id", nullable=false, insertable = false, updatable = false)
	private Employees employee;
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBenefitType() {
		return benefitType;
	}
	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}
	public String getInfoBenefit() {
		return infoBenefit;
	}
	public void setInfoBenefit(String infoBenefit) {
		this.infoBenefit = infoBenefit;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Employees getEmployee() {
		return employee;
	}
	public void setEmployee(Employees employee) {
		this.employee = employee;
	}
	
	
}
