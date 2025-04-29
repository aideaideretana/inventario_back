package com.example.Inventory.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Inventory.DTO.GroupRequest;
import com.example.Inventory.DTO.GroupResponse;
import com.example.Inventory.DTO.Mapper;
import com.example.Inventory.service.GroupService;
import com.example.Inventory.model.Groups;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Group", description = "Group Catalog Management")
@RestController
@RequestMapping("/group")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Operation(summary = "Add Group", description = "Register a group with name, image and description.")
    @PostMapping("/addGroup")
    public ResponseEntity<?> addGroup(@Valid @RequestBody GroupRequest groupRequest) {
        try {
            Groups group = this.groupService.addGroup(Mapper.toGroup(groupRequest));
            return new ResponseEntity<>(Mapper.toGroupResponse(group), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(409).build();
        }
    }

    @Operation(summary = "Update Group", description = "Update the details of a group.")
    @PutMapping("/updateGroup/{id}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable Long id, @RequestBody GroupRequest request) {
        Groups updatedGroup = this.groupService.updateGroup(id, Mapper.toGroup(request));
        return new ResponseEntity<>(Mapper.toGroupResponse(updatedGroup), HttpStatus.OK);
    }

    @Operation(summary = "Get Group by Name", description = "Retrieve a group by its name.")
    @GetMapping("/getGroupByName/{name}")
    public ResponseEntity<GroupResponse> getGroup(@PathVariable String name) {
        Groups group = this.groupService.findGroupByName(name);
        return new ResponseEntity<>(Mapper.toGroupResponse(group), HttpStatus.OK);
    }

    @Operation(summary = "List all Groups", description = "Retrieve all groups.")
    @GetMapping("/listGroup")
    public ResponseEntity<List<GroupResponse>> listGroup() {
        List<GroupResponse> response = this.groupService.listGroup()
            .stream()
            .map(Mapper::toGroupResponse)
            .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete Group", description = "Delete a group by ID.")
    @DeleteMapping("/deleteGroup/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        this.groupService.deleteGroup(id);
        return ResponseEntity.status(200).build();
    }
}