package com.example.Inventory.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import com.example.Inventory.exception.GroupAlreadyExistsException;
import com.example.Inventory.exception.GroupNotFoundException;
import com.example.Inventory.model.Groups;
import com.example.Inventory.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addGroup_Success() {
        Groups group = new Groups("BTS", "bts.jpg", "Famous Kpop group");

        when(groupRepository.findByName("BTS")).thenReturn(Optional.empty());
        when(groupRepository.save(group)).thenReturn(group);

        Groups result = groupService.addGroup(group);

        assertEquals("BTS", result.getName());
        verify(groupRepository, times(1)).save(group);
    }

    @Test
    void addGroup_AlreadyExists_ThrowsException() {
        Groups group = new Groups("BTS", "bts.jpg", "Famous Kpop group");

        when(groupRepository.findByName("BTS")).thenReturn(Optional.of(group));

        assertThrows(GroupAlreadyExistsException.class, () -> {
            groupService.addGroup(group);
        });
    }

    @Test
    void findGroupByName_Success() {
        Groups group = new Groups("BTS", "bts.jpg", "Famous Kpop group");

        when(groupRepository.findByName("BTS")).thenReturn(Optional.of(group));

        Groups result = groupService.findGroupByName("BTS");

        assertEquals("BTS", result.getName());
    }

    @Test
    void findGroupByName_NotFound_ThrowsException() {
        when(groupRepository.findByName("EXO")).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> {
            groupService.findGroupByName("EXO");
        });
    }

    @Test
    void listGroup_ReturnsList() {
        Groups group = new Groups("BTS", "bts.jpg", "Famous Kpop group");

        when(groupRepository.findAll()).thenReturn(Collections.singletonList(group));

        List<Groups> result = groupService.listGroup();

        assertEquals(1, result.size());
        assertEquals("BTS", result.get(0).getName());
    }
}