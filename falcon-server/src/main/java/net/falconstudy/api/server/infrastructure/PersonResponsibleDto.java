package net.falconstudy.api.server.infrastructure;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.falconstudy.api.server.domain.PersonResponsible;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class PersonResponsibleDto {

    @EqualsAndHashCode.Include
    private Long id = 0L;
    private String firstName = "";
    private String lastName = "";
    private String cpf = "";
    private String rg = "";
    private String email = "";

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    public PersonResponsibleDto(PersonResponsible personResponsible) {
        id = personResponsible.getId();
        firstName = personResponsible.getFirstName();
        lastName = personResponsible.getLastName();
        cpf = personResponsible.getCpf();
        rg = personResponsible.getRg();
        email = personResponsible.getEmail();
        dateOfBirth = personResponsible.getDateOfBirth();
    }

    public PersonResponsible autoConvert() {
        var personResponsible = new PersonResponsible();
        personResponsible.setId(id);
        personResponsible.setFirstName(firstName);
        personResponsible.setLastName(lastName);
        personResponsible.setCpf(cpf);
        personResponsible.setRg(rg);
        personResponsible.setEmail(email);
        personResponsible.setDateOfBirth(dateOfBirth);
        return personResponsible;
    }

    public static List<PersonResponsibleDto> from(List<PersonResponsible> peopleResponsible) {
        return peopleResponsible != null
                ? peopleResponsible.stream()
                        .map(PersonResponsibleDto::new)
                        .collect(Collectors.toList())
                : List.of();
    }

}
