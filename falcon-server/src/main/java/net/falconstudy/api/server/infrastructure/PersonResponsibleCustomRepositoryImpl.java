package net.falconstudy.api.server.infrastructure;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.falconstudy.api.server.domain.PersonResponsible;

public class PersonResponsibleCustomRepositoryImpl implements PersonResponsibleCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<PersonResponsible> findByIdOrCpfOrRg(String searchText) {
		if (isId(searchText)) {
			var id = Long.valueOf(searchText.replace("L", ""));
			return queryById(id);
		} else if (isCpf(searchText)) {
			return queryByCpf(searchText);
		} else if(isRg(searchText)) {
			return queryByRg(searchText);
		}

		return Optional.ofNullable(null);
	}

	private boolean isCpf(String searchText) {
		return searchText.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
	}

	private Optional<PersonResponsible> queryById(Long id) {
		var searchQuery = "SELECT DISTINCT p from PersonResponsible p JOIN p.children c WHERE p.id = :id";
		var personResponsibleQueryResult = entityManager
				.createQuery(searchQuery, PersonResponsible.class)
				.setParameter("id", id)
				.getResultList();

		if (! personResponsibleQueryResult.isEmpty()) {
			var first = 0;
			return Optional.of(personResponsibleQueryResult.get(first));
		}
		
		return Optional.ofNullable(null);
	}

	private boolean isId(String searchText) {
		return searchText.matches("^-?\\d+L?$");
	}
	
	private Optional<PersonResponsible> queryByCpf(String cpf) {
		var searchQuery = "SELECT p from PersonResponsible p JOIN p.children c WHERE p.cpf = :cpf";
		var personResponsibleQueryResult = entityManager
				.createQuery(searchQuery, PersonResponsible.class)
				.setParameter("cpf", cpf)
				.getResultList();

		if (! personResponsibleQueryResult.isEmpty()) {
			var first = 0;
			return Optional.of(personResponsibleQueryResult.get(first));
		}
		
		return Optional.ofNullable(null);
	}
	
	private boolean isRg(String searchText) {
		return searchText.matches("^\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}");
	}
	
	private Optional<PersonResponsible> queryByRg(String rg) {
		var searchQuery = "SELECT p from PersonResponsible p JOIN p.children c WHERE p.rg = :rg";
		var personResponsibleQueryResult = entityManager
				.createQuery(searchQuery, PersonResponsible.class)
				.setParameter("rg", rg)
				.getResultList();

		if (! personResponsibleQueryResult.isEmpty()) {
			var first = 0;
			return Optional.of(personResponsibleQueryResult.get(first));
		}
		
		return Optional.ofNullable(null);
	}

}
