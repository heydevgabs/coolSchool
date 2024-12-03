package org.cloudwise.coolapp.organization;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
@Tag(name = "Organizations", description = "Endpoints for managing organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Operation(summary = "Save an organization", description = "Creates or updates an organization.")
    @PostMapping
    public ResponseEntity<OrganizationDTO> saveOrganization(@RequestBody OrganizationDTO dto) {
        return ResponseEntity.ok(organizationService.saveOrganization(dto));
    }

    @Operation(summary = "List all organizations", description = "Retrieves a list of all organizations.")
    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> listOrganizations() {
        return ResponseEntity.ok(organizationService.listOrganizations());
    }
}
