package com.eden.gallery.repository;

import com.eden.gallery.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository  extends JpaRepository<Group, Long> {

    Group findByName(String name);
}
