package com.pluralsight.conferencedemo.controller;


import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {

    @Autowired
    private SessionRepository sessionRepository; //Gives CRUD access

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    } //Returns all speakers GET HTTP verb

    @GetMapping
    @RequestMapping( value = "{id}", method = RequestMethod.GET)
    public Session get(@PathVariable Long id) {
        return sessionRepository.getOne(id);
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
    //Also need to check for children before deleting
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT) // PUT all attributes PATCH -portion
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        // Add validation
    Session existingSession = sessionRepository.getOne(id);  //What is alternative ? FindById ?
    BeanUtils.copyProperties(session, existingSession,"session_id"); //copies ( leaves ID alone )
    return sessionRepository.saveAndFlush(existingSession);
    }
}
