package net.falconstudy.api.server.infrastructure;

import java.util.Optional;

import net.falconstudy.api.server.application.StudentDoesNotExistsException;
import net.falconstudy.api.server.domain.PersonResponsible;

public interface PersonResponsibleCustomRepository {

	Optional<PersonResponsible> findByIdOrCpfOrRg(String searchText) throws StudentDoesNotExistsException;
}
