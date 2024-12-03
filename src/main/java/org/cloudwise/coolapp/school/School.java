package org.cloudwise.coolapp.school;

import jakarta.persistence.*;
import lombok.*;
import org.cloudwise.coolapp.organization.Organization;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "school")
public class School {

    @Id
    @Column(name = "school_id", nullable = false, unique = true)
    private String schoolId; // School ID

    @Column(name = "name", nullable = false)
    private String name; // School name

    @ManyToOne
    @JoinColumn(name = "organization_name")
    private Organization organization;
}