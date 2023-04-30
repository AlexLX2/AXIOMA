package md.akdev_service_management.sm.services.config;

import md.akdev_service_management.sm.models.config.Config;
import md.akdev_service_management.sm.repositories.config.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigService {

    private final ConfigRepository configRepository;

    @Autowired
    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public String getValue(String name) {
        Config config = configRepository.findConfigByName(name);
        return config != null ? config.getValue() : null;
    }

    public void setValue(String name, String value, String description) {
        Config config = configRepository.findConfigByName(name);
        if (config == null) {
            config = new Config();
            config.setName(name);
            config.setDescription(description);
        }
        config.setValue(value);
        config.setDescription(description);
        configRepository.save(config);
    }

    public Optional<Config> findById(Integer id) {
        return configRepository.findById(id);
    }

    public List<Config> getAllSettings() {
        return configRepository.findAll();
    }

    public Config findByName(String name) {
        return configRepository.findConfigByName(name);
    }
}