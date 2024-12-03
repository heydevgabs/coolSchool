package org.cloudwise.coolapp.organization;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orgranization")
public class Organization {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "organization_school_ids", joinColumns = @JoinColumn(name = "organization_name"))
    @Column(name = "school_id")
    private Set<String> schoolIds;

    public Organization(String name, Set<String> schoolIds) {
        this.name = name;
        this.schoolIds = schoolIds;
    }
}
