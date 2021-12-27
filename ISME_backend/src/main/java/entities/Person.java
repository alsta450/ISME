package entities;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Person")
public class Person {

	@Id
	@Column(name = "svnr")
	private int svnr;

	
	@Id
	@Column(name = "firstname")
	private String firstName;
	
	@Id
	@Column(name = "lastname")
	private String lastName;
	
	@Id
	@Column(name = "iban")
	private String iban;
	
	@Id
	@Column(name = "birthday")
	private Date birthday;
	
	
}
