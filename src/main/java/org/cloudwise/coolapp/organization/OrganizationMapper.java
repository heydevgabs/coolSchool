package org.cloudwise.coolapp.organization;

public class OrganizationMapper {

    public static OrganizationDTO toDTO(Organization organization) {
        return new OrganizationDTO(organization.getName(), organization.getDescription(), organization.getSchoolIds());
    }

    public static Organization toEntity(OrganizationDTO dto) {
        return Organization.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .schoolIds(dto.getSchoolIds())
                .build();
    }
}
