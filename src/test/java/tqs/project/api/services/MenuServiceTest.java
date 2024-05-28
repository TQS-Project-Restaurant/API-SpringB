package tqs.project.api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.project.api.models.Menu;
import tqs.project.api.repositories.MenuRepository;
import tqs.project.api.services.impl.MenuServiceImpl;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {
    @Mock(strictness = Mock.Strictness.LENIENT )
    private MenuRepository repository;

    @InjectMocks
    private MenuServiceImpl service;

    Menu menu = new Menu();

    @BeforeEach
    void setUp(){
        menu.setDia(LocalDate.now());
    }

    @Test
    void givenMenu_whenGetDailyMenu_thenReturnTodaysMenu(){
        when(repository.findByDia(LocalDate.now())).thenReturn(menu);

        Menu found = service.getDailyMenu();

        assertThat(found.getDia()).isEqualTo(LocalDate.now().toString());
        verify(repository, times(1)).findByDia(LocalDate.now());
    }

    @Test
    void givenNoMenu_whenGetDailyMenu_thenReturnTodaysMenu(){
        Menu found = service.getDailyMenu();

        assertThat(found).isNull();
        verify(repository, times(1)).findByDia(LocalDate.now());
    }
}
