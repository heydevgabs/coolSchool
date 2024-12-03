package org.cloudwise.coolapp.application;

import org.cloudwise.coolapp.common.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, String> {

    List<Application> findByLevel( Level level);
    @Query("SELECT a FROM Application a WHERE a.level = :level AND a.id IN :schoolIds")
    List<Application> findByLevelAndSchoolIds(@Param("level") Level level, @Param("schoolIds") List<String> schoolIds);
}
