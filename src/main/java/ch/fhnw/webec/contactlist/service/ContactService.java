package ch.fhnw.webec.contactlist.service;

import ch.fhnw.webec.contactlist.model.ContactDetail;
import ch.fhnw.webec.contactlist.model.ContactOverview;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContactService {

    private static final String CSV_FILE = "contacts.csv";

    private final List<ContactDetail> personList;

    public ContactService() throws IOException {
        final Path path = Paths.get(ContactService.class.getResource(CSV_FILE).getPath());
        try (final Stream<String> lines = Files.lines(path)) {
            personList = lines
                    .map(line -> {
                        final String[] values = line.split(",");
                        return new ContactDetail(Long.parseLong(values[0]), values[1], values[2], values[3], values[4]);
                    })
                    .collect(Collectors.toList());
        }
    }

    public List<ContactOverview> getAllPersons() {
        return personList.stream()
                .map(person -> new ContactOverview(person.getId(), person.getLastName() + ", " + person.getFirstName()))
                .collect(Collectors.toList());
    }

    public Optional<ContactDetail> findPerson(long id) {
        return personList.stream()
                .filter(person -> person.getId() == id)
                .findAny();
    }
}
