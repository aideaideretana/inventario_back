package com.example.Inventory.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Inventory.model.Groups;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Long> {
    Optional<Groups> findByName(String name);
}