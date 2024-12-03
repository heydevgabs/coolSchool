package org.cloudwise.coolapp.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Tag(name = "Applications", description = "Endpoints for managing applications")
public class ApplicationsController {

    private final ApplicationService applicationService;

    @Operation(summary = "Create a new application", description = "Saves a new application in the system.")
    @PostMapping
    public ResponseEntity<ApplicationDTO> createApplication(@RequestBody ApplicationDTO applicationDTO) {
        ApplicationDTO createdApplication = applicationService.createApplication(applicationDTO);
        return ResponseEntity.ok(createdApplication);
    }

    @Operation(summary = "List all applications", description = "Retrieves a list of all applications.")
    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> listApplications() {
        List<ApplicationDTO> applications = applicationService.listApplications();
        return ResponseEntity.ok(applications);
    }

    @Operation(summary = "Filter applications by user", description = "Fetches applications visible to a specific user.")
    @GetMapping("/filter")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsForUser(@RequestParam String userId) {
        List<ApplicationDTO> applications = applicationService.getApplicationsForUser(userId);
        return ResponseEntity.ok(applications);
    }
}