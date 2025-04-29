package com.example.Inventory.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Inventory.model.Groups;
import com.example.Inventory.repository.GroupRepository;
import com.example.Inventory.exception.GroupAlreadyExistsException;
import com.example.Inventory.exception.GroupNotFoundException;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    // Agregar un nuevo Group validando unicidad
    public Groups addGroup(Groups group) {
        this.groupRepository.findByName(group.getName())
            .ifPresent(existingGroup -> {
                throw new GroupAlreadyExistsException("Group already exists");
            });
        return this.groupRepository.save(group);
    }

    // Listar todos los Groups
    public List<Groups> listGroup() {
        return this.groupRepository.findAll();
    }

    // Buscar un Group por su nombre
    public Groups findGroupByName(String name) {
        return this.groupRepository.findByName(name)
            .orElseThrow(() -> new GroupNotFoundException("Group not found"));
    }

    // Actualizar un Group
    public Groups updateGroup(Long id, Groups group) {
        Groups existingGroup = this.groupRepository.findById(id)
            .orElseThrow(() -> new GroupNotFoundException("Group not found"));

        existingGroup.setName(group.getName());
        existingGroup.setImage(group.getImage());
        existingGroup.setDescription(group.getDescription());

        return this.groupRepository.save(existingGroup);
    }

    // Eliminar un Group
    public void deleteGroup(Long id) {
        this.groupRepository.findById(id)
            .orElseThrow(() -> new GroupNotFoundException("Group not found"));
        this.groupRepository.deleteById(id);
    }
}