package org.cloudwise;

import org.cloudwise.coolapp.application.Application;
import org.cloudwise.coolapp.application.ApplicationDTO;
import org.cloudwise.coolapp.application.ApplicationRepository;
import org.cloudwise.coolapp.application.ApplicationService;
import org.cloudwise.coolapp.common.Level;
import org.cloudwise.coolapp.organization.Organization;
import org.cloudwise.coolapp.organization.OrganizationRepository;
import org.cloudwise.coolapp.user.User;
import org.cloudwise.coolapp.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@ExtendWith( MockitoExtension.class)
public class ApplicationLibraryTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    public void testOnlyRootLevelApplications() {
        String userId = "us1";
        User user = new User("us1", "John Doe", "school1");

        List<Application> rootApplications = List.of(
                new Application("a1", "Gmail", "www.gmail.com", Level.ROOT),
                new Application("a2", "Drive", "www.drive.com", Level.ROOT)
        );

        Organization organization = new Organization("org1", Set.of("school1", "school2"));

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(applicationRepository.findByLevel(Level.ROOT)).thenReturn(rootApplications);
        Mockito.when(organizationRepository.findAll()).thenReturn(List.of(organization));

        List<ApplicationDTO> result = applicationService.getApplicationsForUser(userId);

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().anyMatch(app -> app.getName().equals("Gmail")));
        Assertions.assertTrue(result.stream().anyMatch(app -> app.getName().equals("Drive")));
    }

    @Test
    public void testRootAndOrganizationLevelApplications() {
        String userId = "us1";
        String schoolId = "school1";
        User user = new User(userId, "John Doe", schoolId);

        Organization organization = new Organization("org1", Set.of("school1", "school2"));

        List<Application> rootApplications = List.of(
                new Application("a1", "Gmail", "www.gmail.com", Level.ROOT),
                new Application("a2", "Drive", "www.drive.com", Level.ROOT)
        );

        List<Application> organizationApplications = List.of(
                new Application("a1", "Gmail (Org Modified)", "org.gmail.com", Level.ORGANIZATION),
                new Application("a3", "Calendar", "www.calendar.com", Level.ORGANIZATION)
        );

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(organizationRepository.findAll()).thenReturn(List.of(organization));
        Mockito.when(applicationRepository.findByLevel(Level.ROOT)).thenReturn(rootApplications);

        List<String> sortedSchoolIds = List.of("school1", "school2").stream().sorted().toList();
        Mockito.when(applicationRepository.findByLevelAndSchoolIds(Level.ORGANIZATION, sortedSchoolIds))
                .thenReturn(organizationApplications);

        List<ApplicationDTO> result = applicationService.getApplicationsForUser(userId);

        Assertions.assertEquals(3, result.size());
        Assertions.assertTrue(result.stream().anyMatch(app -> app.getName().equals("Gmail (Org Modified)"))); // Organization overrides root
        Assertions.assertTrue(result.stream().anyMatch(app -> app.getName().equals("Drive"))); // Root app
        Assertions.assertTrue(result.stream().anyMatch(app -> app.getName().equals("Calendar"))); // Org-level addition
    }


    @Test
    public void testNoApplicationsForUser() {
        String userId = "us1";

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class, () -> applicationService.getApplicationsForUser(userId));
    }


    @Test
    public void testNoApplicationsDefined() {
        String userId = "us1";
        User user = new User("us1", "John Doe", "school1");
        Organization organization = new Organization("org1", Set.of("school1"));

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(organizationRepository.findAll()).thenReturn(List.of(organization));
        Mockito.when(applicationRepository.findByLevel(Level.ROOT)).thenReturn(List.of());
        Mockito.when(applicationRepository.findByLevelAndSchoolIds(Level.ORGANIZATION, List.of("school1")))
                .thenReturn(List.of());
        Mockito.when(applicationRepository.findByLevelAndSchoolIds(Level.SCHOOL, List.of("school1")))
                .thenReturn(List.of());

        List<ApplicationDTO> result = applicationService.getApplicationsForUser(userId);
        Assertions.assertTrue(result.isEmpty(), "No applications should be returned");
    }

    @Test
    public void testDuplicateApplicationIdsAcrossLevels() {
        String userId = "us1";
        User user = new User("us1", "Jane Doe", "school1");

        Organization organization = new Organization("org1", Set.of("school1"));

        List<Application> rootApplications = List.of(
                new Application("a1", "Gmail", "www.gmail.com", Level.ROOT)
        );

        List<Application> organizationApplications = List.of(
                new Application("a1", "Gmail (Org Modified)", "org.gmail.com", Level.ORGANIZATION)
        );

        List<Application> schoolApplications = List.of(
                new Application("a1", "Gmail (School Modified)", "school.gmail.com", Level.SCHOOL)
        );

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(organizationRepository.findAll()).thenReturn(List.of(organization));
        Mockito.when(applicationRepository.findByLevel(Level.ROOT)).thenReturn(rootApplications);
        Mockito.when(applicationRepository.findByLevelAndSchoolIds(Level.ORGANIZATION, List.of("school1")))
                .thenReturn(organizationApplications);
        Mockito.when(applicationRepository.findByLevelAndSchoolIds(Level.SCHOOL, List.of("school1")))
                .thenReturn(schoolApplications);

        List<ApplicationDTO> result = applicationService.getApplicationsForUser(userId);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Gmail (School Modified)", result.get(0).getName());
    }

    @Test
    public void testUserWithNoSchoolId() {
        String userId = "us1";
        User user = new User("us1", "Jane Doe", null);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Assertions.assertThrows(RuntimeException.class, () -> applicationService.getApplicationsForUser(userId));
    }

    @Test
    public void testLargeNumberOfApplications() {
        String userId = "us1";
        User user = new User("us1", "John Doe", "school1");

        Organization organization = new Organization("org1", Set.of("school1"));

        List<Application> rootApplications = IntStream.range(0, 1000)
                .mapToObj(i -> new Application("a" + i, "App" + i, "www.app" + i + ".com", Level.ROOT))
                .collect( Collectors.toList());

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(organizationRepository.findAll()).thenReturn(List.of(organization));
        Mockito.when(applicationRepository.findByLevel(Level.ROOT)).thenReturn(rootApplications);

        List<ApplicationDTO> result = applicationService.getApplicationsForUser(userId);

        Assertions.assertEquals(1000, result.size());
    }

}
