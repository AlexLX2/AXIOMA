package md.akdev_service_management.sm.repositories.config;

import md.akdev_service_management.sm.models.config.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {
    Config findConfigByName(String name);
}
