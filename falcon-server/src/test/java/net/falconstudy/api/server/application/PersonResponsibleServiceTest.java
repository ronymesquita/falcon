package net.falconstudy.api.server.application;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import net.falconstudy.api.server.domain.PersonResponsible;
import net.falconstudy.api.server.infrastructure.PersonResponsibleRepository;

@ExtendWith(MockitoExtension.class)
class PersonResponsibleServiceTest {
    
    @TestConfiguration
    static class TestConfigurationBean {
        
        @Bean
        public PersonResponsibleService personResponsibleService(
                @Autowired PersonResponsibleRepository personResponsibleRepository
        ) {
            return new PersonResponsibleRestService(personResponsibleRepository);
        }
    }

    @Mock
    private PersonResponsibleRestService personResponsibleService;

    @Test
    void whenSaveValidPersonResponsibleShouldWorks() throws Exception {
        var personResponsible = new PersonResponsible();
        when(personResponsibleService.save(personResponsible))
            .thenReturn(personResponsible);
        var personResponsibleSaved = personResponsibleService.save(personResponsible);
        
        verify(personResponsibleService).save(personResponsible);
        assertThat(personResponsibleSaved, is(Matchers.notNullValue()));
    }
    
    @Test
    void whenSaveExistingPersonResponsibleShouldThrowsPersonResponsibleAlreadyExistsException() {
        var personResponsible = new PersonResponsible();
        personResponsible.setId(1L);
        when(personResponsibleService.save(personResponsible))
            .thenReturn(personResponsible);
        when(personResponsibleService.save(personResponsible))
            .thenThrow(PersonResponsibleAlreadyExistsException.class);

        assertThrows(PersonResponsibleAlreadyExistsException.class, () -> {
            personResponsibleService.save(personResponsible);
            personResponsibleService.save(personResponsible);
            verify(personResponsibleService, times(2)).save(personResponsible);
        });
    }
    
    @Test
    void whenSaveAllValidShouldWorks() {
        var peopleResponsible = List.<PersonResponsible>of(
                new PersonResponsible(),
                new PersonResponsible()
        );
        when(personResponsibleService.saveAll(peopleResponsible))
            .thenReturn(peopleResponsible);
        var savedPeopleResponsible = personResponsibleService.saveAll(peopleResponsible);
        
        verify(personResponsibleService).saveAll(peopleResponsible);
        assertThat(savedPeopleResponsible, hasSize(2));
    }
    
    @Test
    void whenFindExistingPersonResponsibleShouldReturnValid() {
        var personResponsible = new PersonResponsible();
        personResponsible.setId(1L);
        personResponsible.setFirstName("Maria");
        when(personResponsibleService.findById(1L))
            .thenReturn(Optional.of(personResponsible));        
        var searchedPersonResponsible = personResponsibleService.findById(personResponsible.getId());
        
        verify(personResponsibleService).findById(personResponsible.getId());
        assertEquals(searchedPersonResponsible.get().getId(), personResponsible.getId());
        assertEquals(searchedPersonResponsible.get().getFirstName(), personResponsible.getFirstName());
    }
    
    @Test
    void whenFindNotSavedPersonResponsibleShouldReturnOptionalEmpty() throws Exception {
        when(personResponsibleService.findById(0L))
            .thenReturn(Optional.empty());

        assertEquals(personResponsibleService.findById(0L), Optional.empty());
    }
    
    @Test
    void whenFindAllPersonResponsibleShouldWorks() throws Exception {
        var peopleResponsible = List.of(new PersonResponsible(), new PersonResponsible());
        when(personResponsibleService.findAll())
            .thenReturn(peopleResponsible);
        
        assertThat(personResponsibleService.findAll(), hasSize(2));
    }
    
    @Test
    void whenUpdatePersonResponsibleThenShouldWorks() throws Exception {
        var personResponsible = new PersonResponsible();
        personResponsible.setId(1L);
        personResponsible.setFirstName("João");
        
        when(personResponsibleService.update(personResponsible))
            .thenAnswer(invocationMock -> {
                var registeredPersonResponsible = invocationMock.getArgument(0, PersonResponsible.class);
                registeredPersonResponsible.setId(personResponsible.getId());
                registeredPersonResponsible.setFirstName(personResponsible.getFirstName());
                registeredPersonResponsible.setLastName("Apóstolo");
                return registeredPersonResponsible;
            });
        
        var updatedPersonResponsible = personResponsibleService.update(personResponsible);
        assertEquals(updatedPersonResponsible.getLastName(), "Apóstolo");
    }
    
    @Test
    void whenDeleteByIdPersonResponsibleShouldWorks() throws Exception {
        var personResponsible = new PersonResponsible();
        personResponsible.setId(1L);
        personResponsible.setFirstName("João");
        lenient().when(personResponsibleService.save(personResponsible)).thenReturn(personResponsible);
        doNothing()
            .when(personResponsibleService).deleteById(personResponsible.getId());
        personResponsibleService.deleteById(personResponsible.getId());
        
        verify(personResponsibleService).deleteById(personResponsible.getId());
    }
    
    @Test
    void whenCountPersonResponsibleShouldWorks() throws Exception {
        var peopleResponsible = List.of(new PersonResponsible(), new PersonResponsible());
        lenient()
            .when(personResponsibleService.saveAll(peopleResponsible))
            .thenReturn(peopleResponsible);
        when(personResponsibleService.count()).thenReturn(2L);

        assertEquals(personResponsibleService.count(), 2L);
    }
}
