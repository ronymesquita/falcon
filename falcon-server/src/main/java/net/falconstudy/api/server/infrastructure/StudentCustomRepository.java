package net.falconstudy.api.server.infrastructure;

import java.util.Optional;

import net.falconstudy.api.server.domain.Student;

public interface StudentCustomRepository {

	Optional<Student> findStudentById(Long id);
	Optional<Student> findByIdOrCpfOrRg(String searchText);
}
