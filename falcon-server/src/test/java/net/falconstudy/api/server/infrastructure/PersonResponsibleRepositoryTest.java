package net.falconstudy.api.server.infrastructure;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import net.falconstudy.api.server.domain.PersonResponsibleTestHelper;

class PersonResponsibleRepositoryTest {

    @Mock
    private PersonResponsibleRepository personResponsibleRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenSaveThenIdIsSet() {
        var personResponsible = PersonResponsibleTestHelper.createSimplePerson();
        when(personResponsibleRepository.save(personResponsible))
                .thenReturn(personResponsible);

        assertNotNull(personResponsibleRepository.save(personResponsible));
    }
}
