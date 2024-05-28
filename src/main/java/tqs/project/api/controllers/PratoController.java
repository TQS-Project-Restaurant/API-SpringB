package tqs.project.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tqs.project.api.models.Prato;
import tqs.project.api.services.PratoService;

@RestController
@Tag(name = "Dish API")
@RequestMapping("/api/dishes")
@CrossOrigin(origins = "http://localhost:3000")
public class PratoController {
    
    private final PratoService pratoService;

    public PratoController(PratoService pratoService) {
        this.pratoService = pratoService;
    }


    @Operation(summary = "Get dish by ID", description = "Returns dish object by given ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "404", description = "No dish was found with given ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Prato> getPrato(@PathVariable Long id){
        Prato prato = pratoService.getPrato(id);

        if (prato == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(prato, HttpStatus.OK);
    }
}
