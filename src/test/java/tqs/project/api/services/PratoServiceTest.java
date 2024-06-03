package tqs.project.api.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.project.api.models.Prato;
import tqs.project.api.repositories.PratoRepository;
import tqs.project.api.services.impl.PratoServiceImpl;

@ExtendWith(MockitoExtension.class)
class PratoServiceTest {
    
    @Mock(strictness = Mock.Strictness.LENIENT )
    private PratoRepository repository;

    @InjectMocks
    private PratoServiceImpl service;

    Prato prato = new Prato();

    @BeforeEach
    void setUp(){
        prato.setId(1L);
        prato.setNome("Francesinha");
        prato.setPreco(2.5);
        prato.setStock(10);

        when(repository.findById(1L)).thenReturn(Optional.of(prato));
    }

    @Test
    void whenGetPratoById_thenReturnPrato() {
        Prato found = service.getPrato(prato.getId()); 
        
        verify(repository, times(1)).findById(Mockito.any());
        assertThat(found.getNome()).isEqualTo("Francesinha");
    }

    @Test
    void whenGetPratoByWrongId_thenReturnNull() {
        Prato found = service.getPrato(300L); 
        
        verify(repository, times(1)).findById(Mockito.any());
        assertThat(found).isNull();
    }
}
