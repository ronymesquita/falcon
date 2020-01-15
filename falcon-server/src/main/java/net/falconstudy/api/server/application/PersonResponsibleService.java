package net.falconstudy.api.server.application;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import net.falconstudy.api.server.domain.PersonResponsible;

public interface PersonResponsibleService {

    PersonResponsible save(PersonResponsible personResponsible) throws PersonResponsibleAlreadyExistsException;

    List<PersonResponsible> saveAll(Collection<PersonResponsible> peopleResponsible);

    Optional<PersonResponsible> findById(Long id);

    Optional<PersonResponsible> findByIdOrCpfOrRg(String searchText);

    List<PersonResponsible> findAll();

    PersonResponsible update(PersonResponsible personResponsible);

    void deleteById(Long id);

    long count();

}