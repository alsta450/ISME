package operations;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.Person;

public interface PersonOperations extends JpaRepository<Person, Integer>{
	public Person getPersonByID(int id);
}
