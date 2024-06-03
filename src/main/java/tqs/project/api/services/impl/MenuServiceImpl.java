package tqs.project.api.services.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import tqs.project.api.models.Menu;
import tqs.project.api.repositories.MenuRepository;
import tqs.project.api.services.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
    
    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository){
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu getDailyMenu() {
        Menu menu = menuRepository.findByDia(LocalDate.now());

        if (menu == null){
            return null;
        }

        return menu;
    }
}
