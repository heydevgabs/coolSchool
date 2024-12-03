package org.cloudwise.coolapp.school;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolDTO saveSchool(SchoolDTO dto) {
        School school = SchoolMapper.toEntity(dto);
        School savedSchool = schoolRepository.save(school);
        return SchoolMapper.toDTO(savedSchool);
    }

    public List<SchoolDTO> listSchools() {
        return schoolRepository.findAll().stream()
                .map(SchoolMapper::toDTO)
                .collect( Collectors.toList());
    }
}