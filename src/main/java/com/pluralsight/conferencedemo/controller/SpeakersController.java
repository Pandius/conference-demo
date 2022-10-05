package com.pluralsight.conferencedemo.controller;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    //@GetMapping
    @RequestMapping(value = "{id}",
            method = RequestMethod.GET)
    public Optional<Speaker> get(@PathVariable Long id) {
        return speakerRepository.findById(id);
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public Speaker create(@RequestBody final Speaker speaker) {
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        //Also need to check for children before deleting
        speakerRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT) // PUT all attributes PATCH -portion
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
        // Add validation
        Speaker existingSpeaker = speakerRepository.getOne(id);  //What is alternative ? FindById ?
        BeanUtils.copyProperties(speaker, existingSpeaker,"speaker_id"); //copies ( leaves ID alone )
        return speakerRepository.saveAndFlush(existingSpeaker);
    }
}
