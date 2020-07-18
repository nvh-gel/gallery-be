package com.eden.gallery;

import com.eden.gallery.model.Event;
import com.eden.gallery.model.Group;
import com.eden.gallery.repository.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    private final GroupRepository groupRepository;

    public Initializer(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Denver JUG", "Utah JUG", "Seattle JUG", "Richmond JUG").forEach(name -> {
            groupRepository.save(new Group(name));
        });

        Group djug = groupRepository.findByName("Denver JUG");
        Event e= Event.builder().title("Full stack reactive")
                .description("reactive with spring boot + react")
                .date(Instant.parse("2020-01-01T00:00:00.000Z"))
                .build();
        djug.setEvents(Collections.singleton(e));
        groupRepository.save(djug);

        groupRepository.findAll().forEach(System.out::println);
    }
}
