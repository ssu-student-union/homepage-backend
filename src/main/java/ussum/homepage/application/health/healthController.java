package ussum.homepage.application.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthController {

    @GetMapping("/health")
    @Operation(
            summary = "Health Check",
            description = "Check if the server is running",
            responses = {
                    @ApiResponse(responseCode = "200", description = "서버가 켜져있는 경우")
            }
    )
    public ResponseEntity<?> health() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    @Operation(summary = "main cicd용 헬스체크")
    public ResponseEntity<Void> healthForCicd() {
        return ResponseEntity.ok().build();
    }

}