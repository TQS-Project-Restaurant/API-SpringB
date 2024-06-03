package tqs.project.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tqs.project.api.models.Menu;
import tqs.project.api.services.MenuService;

@RestController
@Tag(name = "Menu API")
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
public class MenuController {

    private final MenuService menuService;
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    public MenuController(MenuService menuService){
        this.menuService = menuService;
    }


    @Operation(summary = "Get today's menu", description = "Returns today's menu object")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "404", description = "Not found - No menu was found")
    })
    @GetMapping
    public ResponseEntity<Menu> getDailyMenu(){
        Menu menu = menuService.getDailyMenu();

        if (menu == null){
            logger.warn("Tried to retrieve object DAILY MENU; However, it was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info("Retrieved object DAILY MENU");
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }
}
