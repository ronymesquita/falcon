package net.falconstudy.api.server.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.falconstudy.api.server.infrastructure.PersonResponsibleDto;

@RestController
@RequestMapping("/people-responsible")
public class PersonResponsibleController {

	@Autowired
	private PersonResponsibleService personResponsibleService;
	
	@GetMapping
	public ResponseEntity<List<PersonResponsibleDto>> findAll() {
		var peopleResponsible = personResponsibleService.findAll();
		var peopleResponsibleDto = PersonResponsibleDto.from(peopleResponsible);
		return ResponseEntity.ok(peopleResponsibleDto);
	}
	
	@GetMapping("/search")
	public ResponseEntity<PersonResponsibleDto> findByIdOrCpfOrRg(
			@RequestParam("searchText") String searchText) {
		var personResponsible = personResponsibleService.findByIdOrCpfOrRg(searchText);
		if (personResponsible.isPresent()) {
			return ResponseEntity.ok(new PersonResponsibleDto(personResponsible.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<PersonResponsibleDto> save(
			@RequestBody PersonResponsibleDto personResponsibleDto) {
		try {
			var personResponsible = personResponsibleService
					.save(personResponsibleDto.autoConvert());
			var newpersonResponsibleDto = new PersonResponsibleDto(personResponsible);
			return new ResponseEntity<>(newpersonResponsibleDto, 
					HttpStatus.CREATED);
		} catch (PersonResponsibleAlreadyExistsException exception) {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PersonResponsibleDto> update(
			@PathVariable Long id,
			@RequestBody PersonResponsibleDto personResponsibleDto) {
		personResponsibleDto.setId(id);
		var personResponsible = personResponsibleService.update(
				personResponsibleDto.autoConvert());

		var newPersonResponsibleDto = new PersonResponsibleDto(personResponsible);
		return new ResponseEntity<>(newPersonResponsibleDto, HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			personResponsibleService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (StudentDoesNotExistsException exception) {
			return ResponseEntity.notFound().build();
		}
	}
}
