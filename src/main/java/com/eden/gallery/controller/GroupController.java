package com.eden.gallery.controller;

import com.eden.gallery.model.Group;
import com.eden.gallery.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    private final GroupRepository groupRepository;

    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping("/groups")
    public Collection<Group> groups() {
        return groupRepository.findAll();
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable Long id){
        Optional<Group> groupOpt = groupRepository.findById(id);

        return groupOpt.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/group")
    public ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) throws URISyntaxException {
        LOGGER.info("Create new group: {}", group);

        Group result = groupRepository.save(group);
        return ResponseEntity.created(new URI("/api/group/" + result.getId()))
                .body(result);
    }

    @PutMapping("/group")
    public ResponseEntity<Group> updateGroup(@Valid @RequestBody Group group) {
        LOGGER.info("Updating group: {}", group);

        Group result = groupRepository.save(group);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<Group> deleteGroup(@PathVariable Long id) {
        LOGGER.info("Deleting group id: {}", id);

        groupRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
