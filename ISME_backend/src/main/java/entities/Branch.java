package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Branch")
public class Branch {
	
	@Id
	@Column(name="street")
	private String street;
	
	@Id
	@Column(name="city")
	private String city;
	
	@Id
	@Column(name="zip")
	private String zip;
	
	@Column(name="name")
	private String name;
	
	@Column(name="area")
	private String area;
	
}
