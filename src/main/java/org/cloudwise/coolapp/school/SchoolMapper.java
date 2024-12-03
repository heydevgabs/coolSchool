package org.cloudwise.coolapp.school;

public class SchoolMapper {

    public static SchoolDTO toDTO(School school) {
        return new SchoolDTO(school.getSchoolId(), school.getName());
    }

    public static School toEntity(SchoolDTO dto) {
        return School.builder()
                .schoolId(dto.getSchoolId())
                .name(dto.getName())
                .build();
    }
}