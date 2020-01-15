package net.falconstudy.api.server.application;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.falconstudy.api.server.domain.PersonResponsible;
import net.falconstudy.api.server.infrastructure.PersonResponsibleRepository;

@Service
public class PersonResponsibleRestService implements PersonResponsibleService  {

	private PersonResponsibleRepository personResponsibleRepository;
	
	@Autowired
	public PersonResponsibleRestService(PersonResponsibleRepository personResponsibleRepository) {
       this.personResponsibleRepository = personResponsibleRepository;
    }

	@Override
    public PersonResponsible save(PersonResponsible personResponsible)
			throws PersonResponsibleAlreadyExistsException {
		boolean personResponsibleAlreadyExists = false;
		var registeredPersonResponsible = personResponsibleRepository
				.save(personResponsible);
		var personResponsibleId = personResponsible.getId();
		if (personResponsibleId != null 
				&& personResponsibleRepository.existsById(personResponsibleId)) {
			personResponsibleAlreadyExists = true;
		}
		
		if (personResponsibleAlreadyExists) {
			throw new PersonResponsibleAlreadyExistsException(
					String.format("The person responsible with ID %d already exists.", 
							personResponsible.getId()));
		}
		
		return registeredPersonResponsible;
	}
	
	@Override
    public List<PersonResponsible> saveAll(Collection<PersonResponsible> peopleResponsible) {
		return personResponsibleRepository.saveAll(peopleResponsible);
	}
	
	@Override
    public Optional<PersonResponsible> findById(Long id) {
		return personResponsibleRepository.findById(id);
	}
	
	@Override
    public Optional<PersonResponsible> findByIdOrCpfOrRg(String searchText) {
		return personResponsibleRepository.findByIdOrCpfOrRg(searchText);
	}
	
	@Override
    public List<PersonResponsible> findAll() {
		return personResponsibleRepository.findAll();
	}
	
	@Override
    public PersonResponsible update(PersonResponsible personResponsible) {
		return personResponsibleRepository.save(personResponsible);
	}
	
	@Override
    public void deleteById(Long id) {
		if (! personResponsibleRepository.existsById(id)) {
			throw new PersonResponsibleDoesNotExistsException(
					String.format("The person responsible with ID %d does not exists.", id));
		}
		
		this.personResponsibleRepository.deleteById(id);
	}
	
	@Override
    public long count() {
		return this.personResponsibleRepository.count();
	}

}
