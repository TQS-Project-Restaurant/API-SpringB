package tqs.project.api.services.impl;

import org.springframework.stereotype.Service;

import tqs.project.api.models.Bebida;
import tqs.project.api.repositories.BebidaRepository;
import tqs.project.api.services.BebidaService;

@Service
public class BebidaServiceImpl implements BebidaService{

    private final BebidaRepository bebidaRepository;

    public BebidaServiceImpl(BebidaRepository bebidaRepository){
        this.bebidaRepository = bebidaRepository;
    }

    @Override
    public Bebida getBebida(Long id) {
        return bebidaRepository.findById(id).orElse(null);
    }
}
