package net.falconstudy.api.server.domain;

import java.time.LocalDate;

public class PersonResponsibleTestHelper {

    public static PersonResponsible createSimplePerson() {
        var personResponsible = new PersonResponsible();
        personResponsible.setId(1L);
        personResponsible.setFirstName("Milena");
        personResponsible.setLastName("Alice Carla Pinto");
        personResponsible.setCpf("331.536.130-44");
        personResponsible.setRg("38.354.981-4");
        personResponsible.setDateOfBirth(LocalDate.of(1981, 05, 05));
        personResponsible.setEmail("milena-alice@example.com");
        return personResponsible;

    }
}
