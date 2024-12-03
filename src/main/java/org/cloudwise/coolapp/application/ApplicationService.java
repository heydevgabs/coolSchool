package org.cloudwise.coolapp.application;

import lombok.RequiredArgsConstructor;
import org.cloudwise.coolapp.common.Level;
import org.cloudwise.coolapp.organization.Organization;
import org.cloudwise.coolapp.organization.OrganizationRepository;
import org.cloudwise.coolapp.user.User;
import org.cloudwise.coolapp.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    public ApplicationDTO createApplication(ApplicationDTO applicationDTO) {
        Application application = ApplicationMapper.toEntity(applicationDTO);
        Application savedApplication = applicationRepository.save(application);

        return ApplicationMapper.toDTO(savedApplication);
    }

    public List<ApplicationDTO> listApplications() {
        return applicationRepository.findAll().stream()
                .map(ApplicationMapper::toDTO)
                .collect( Collectors.toList());
    }

    public List<ApplicationDTO> getApplicationsForUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String schoolId = user.getSchoolId();

        Organization organization = organizationRepository.findAll()
                .stream()
                .filter(org -> org.getSchoolIds().contains(schoolId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        List<Application> rootApplications = applicationRepository.findByLevel(Level.ROOT);
        List<Application> organizationApplications = applicationRepository.findByLevelAndSchoolIds(Level.ORGANIZATION, new ArrayList<>(organization.getSchoolIds()));
        List<Application> schoolApplications = applicationRepository.findByLevelAndSchoolIds(Level.SCHOOL, List.of(schoolId));

        Map<String, Application> applicationMap = new LinkedHashMap<>();

        rootApplications.forEach(app -> applicationMap.put(app.getId(), app));
        organizationApplications.forEach(app -> applicationMap.put(app.getId(), app));
        schoolApplications.forEach(app -> applicationMap.put(app.getId(), app));

        return applicationMap.values().stream()
                .map(ApplicationMapper::toDTO)
                .collect(Collectors.toList());
    }
}