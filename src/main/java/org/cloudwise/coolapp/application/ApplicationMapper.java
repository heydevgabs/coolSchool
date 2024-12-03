package org.cloudwise.coolapp.application;

public class ApplicationMapper {

    public static ApplicationDTO toDTO(Application application) {
        return new ApplicationDTO(
                application.getId(),
                application.getName(),
                application.getUrl(),
                application.getLevel()
        );
    }

    public static Application toEntity(ApplicationDTO dto) {
        return Application.builder()
                .id(dto.getId())
                .name(dto.getName())
                .url(dto.getUrl())
                .level(dto.getLevel())
                .build();
    }
}