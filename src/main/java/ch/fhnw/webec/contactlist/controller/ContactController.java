package ch.fhnw.webec.contactlist.controller;

import ch.fhnw.webec.contactlist.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ContactController {

    private final ContactService service;

    @Autowired
    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView getIndex(@RequestParam(required = false) Long select) {
        final Map<String, Object> model = new HashMap<>();
        model.put("persons", service.getAllPersons());
        if (select != null) {
            service.findPerson(select).ifPresent(
                    person -> model.put("selected", person)
            );
        }
        return new ModelAndView("index", model);
    }
}
