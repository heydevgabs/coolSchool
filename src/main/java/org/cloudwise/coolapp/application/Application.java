package org.cloudwise.coolapp.application;

import jakarta.persistence.*;
import lombok.*;
import org.cloudwise.coolapp.common.Level;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "application")
public class Application {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Enumerated( EnumType.STRING)
    @Column(name = "level", nullable = false)
    private Level level;
}