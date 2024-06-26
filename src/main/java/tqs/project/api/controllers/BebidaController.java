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
import tqs.project.api.models.Bebida;
import tqs.project.api.services.BebidaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Tag(name = "Beverage API")
@RequestMapping("/api/beverages")
@CrossOrigin(origins = "*")
public class BebidaController {
    
    private final BebidaService bebidaService;
    private static final Logger logger = LoggerFactory.getLogger(BebidaController.class);

    public BebidaController(BebidaService bebidaService){
        this.bebidaService = bebidaService;
    }

    @Operation(summary = "Get beverage by ID", description = "Returns beverage object by given ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "404", description = "No beverage was found with given ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Bebida> getBebida(@PathVariable Long id){
        Bebida beverage = bebidaService.getBebida(id);

        if (beverage == null){
            logger.warn("Tried to retrieve object BEBIDA with ID {}; However, it was not found.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info("Retrieved object BEBIDA with ID {}", id);
        return new ResponseEntity<>(beverage, HttpStatus.OK);
    }
}
