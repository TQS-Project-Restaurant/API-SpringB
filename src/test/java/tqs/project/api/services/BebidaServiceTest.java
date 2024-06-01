package tqs.project.api.services;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.project.api.models.Bebida;
import tqs.project.api.repositories.BebidaRepository;
import tqs.project.api.services.impl.BebidaServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BebidaServiceTest {
    
    @Mock(strictness = Mock.Strictness.LENIENT )
    BebidaRepository repository;

    @InjectMocks
    private BebidaServiceImpl service;

    Bebida beverage = new Bebida();

    @BeforeEach
    void setUp(){
        beverage.setId(1L);
        beverage.setNome("Fanta");
        beverage.setPreco(2.5);
        beverage.setStock(10);

        when(repository.findById(1L)).thenReturn(Optional.of(beverage));
    }

    @Test
    void whenGetBebidaById_thenReturnBebida() {
        Bebida found = service.getBebida(beverage.getId()); 
        
        verify(repository, times(1)).findById(Mockito.any());
        assertThat(found.getNome()).isEqualTo("Fanta");
    }

    @Test
    void whenGetBebidaByWrongId_thenReturnNull() {
        Bebida found = service.getBebida(300L); 
        
        verify(repository, times(1)).findById(Mockito.any());
        assertThat(found).isNull();
    }
}
