package restruntime.restruntime.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import restruntime.restruntime.service.ApiGenerateService;

@RestController
public class ApiController {
    private final ApiGenerateService apiGenerateService;

    public ApiController(ApiGenerateService apiGenerateService) {
        this.apiGenerateService = apiGenerateService;
    }

    @GetMapping(value = "/generate",consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> generate(){
        return ResponseEntity.ok(apiGenerateService.generate());
    }

    @GetMapping(value = "/wrap",consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> wrap() throws NoSuchMethodException {
        return ResponseEntity.ok(apiGenerateService.wrap());
    }
}
