package org.cloudwise.coolapp.school;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
@Tag(name = "Schools", description = "Endpoints for managing schools")
public class SchoolController {

    private final SchoolService schoolService;

    @Operation(summary = "Save a school", description = "Creates or updates a school.")
    @PostMapping
    public ResponseEntity<SchoolDTO> saveSchool(@RequestBody SchoolDTO dto) {
        return ResponseEntity.ok(schoolService.saveSchool(dto));
    }

    @Operation(summary = "List all schools", description = "Retrieves a list of all schools.")
    @GetMapping
    public ResponseEntity<List<SchoolDTO>> listSchools() {
        return ResponseEntity.ok(schoolService.listSchools());
    }
}