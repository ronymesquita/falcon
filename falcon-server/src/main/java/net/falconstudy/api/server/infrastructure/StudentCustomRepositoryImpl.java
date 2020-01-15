package net.falconstudy.api.server.infrastructure;

import java.util.Optional;
import java.util.function.BiFunction;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import net.falconstudy.api.server.application.StudentDoesNotExistsException;
import net.falconstudy.api.server.domain.Student;

public class StudentCustomRepositoryImpl implements StudentCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Optional<Student> findStudentById(Long id) {
		var queryText = "SELECT DISTINCT new net.falconstudy.api.server.domain.Student("
				+ "s.id, "
				+ "s.firstName, "
				+ "s.lastName, "
				+ "s.cpf, "
				+ "s.rg, "
				+ "s.email, "
				+ "s.dateOfBirth, "
				+ "s.dateOfRegistry, "
				+ "s.status) "
				+ "FROM Student s "
				+ "WHERE s.id = :id";
		var jpqlQuery = entityManager.createQuery(queryText, Student.class);
		jpqlQuery.setParameter("id", id);
		return Optional.ofNullable(jpqlQuery.getSingleResult());
	}
	
	@Override
	public Optional<Student> findByIdOrCpfOrRg(String searchText) throws StudentDoesNotExistsException {
		var queryText = "SELECT DISTINCT s FROM Student s "
				+ "JOIN s.peopleResponsible pr "
				+ "JOIN s.courses c "
				+ "WHERE s.id = :id "
				+ "OR s.cpf = :cpf "
				+ "OR s.rg = :rg "
				+ "OR s.email = :email";
		var jpqlQuery = entityManager.createQuery(queryText, Student.class);
		try {
			if (isId(searchText)) {
				jpqlQuery.setParameter("id", Long.valueOf(searchText));
			} else {				
				jpqlQuery.setParameter("id", 0L);
			}
			
			BiFunction<Boolean, String, String> getCleanParameter =
					(isValid, parameterText) -> isValid ? parameterText : "";
			
			jpqlQuery.setParameter("cpf", getCleanParameter.apply(isCpf(searchText), searchText));
			jpqlQuery.setParameter("rg", getCleanParameter.apply(isRg(searchText), searchText));
			jpqlQuery.setParameter("email", getCleanParameter.apply(isEmail(searchText), searchText));
			
			var student = jpqlQuery.getSingleResult();
			return Optional.of(student);
		} catch (NoResultException exception) {
			throw new StudentDoesNotExistsException(String.format(
							"The student searched with %s does not exists.",
							searchText));
		}
	}

	private boolean isId(String parameter) {
		return parameter.matches("^-?\\d+L?$");
	}
	
	private boolean isCpf(String parameter) {
		return parameter.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
	}
	
	private boolean isRg(String parameter) {
		return parameter.matches("^\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}");
	}
	
	private boolean isEmail(String parameter) {
		return parameter.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
	}

}
