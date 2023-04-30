package md.akdev_service_management.sm.controllers;


import md.akdev_service_management.sm.exceptions.NotFoundException;
import md.akdev_service_management.sm.models.config.Config;
import md.akdev_service_management.sm.services.config.ConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/settings")
public class ConfigController {
    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/all")
    ResponseEntity<?> getAll(){
        List<Config> configs = this.configService.getAllSettings();
        return ResponseEntity.ok(configs);
    }

    @PatchMapping("/update")
    public ResponseEntity<?>updateConfig( @RequestBody Config config){
        if(this.configService.findById(config.getId()).isPresent()){
            this.configService.setValue(config.getName(), config.getValue(), config.getDescription());
        }else{
            throw new NotFoundException();
        }
        return ResponseEntity.ok(Map.of("result","update successful"));
    }

    public Config getConfigByName(String name) {
        return this.configService.findByName(name);
    }

}
