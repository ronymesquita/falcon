package net.falconstudy.api.server.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import net.falconstudy.api.server.domain.PersonResponsible;

public interface PersonResponsibleRepository
	extends JpaRepository<PersonResponsible, Long>, PersonResponsibleCustomRepository {
}
