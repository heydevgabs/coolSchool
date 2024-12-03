package org.cloudwise.coolapp.organization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationDTO saveOrganization(OrganizationDTO dto) {
        Organization organization = OrganizationMapper.toEntity(dto);
        Organization savedOrganization = organizationRepository.save(organization);
        return OrganizationMapper.toDTO(savedOrganization);
    }

    public List<OrganizationDTO> listOrganizations() {
        return organizationRepository.findAll().stream()
                .map(OrganizationMapper::toDTO)
                .collect( Collectors.toList());
    }
}