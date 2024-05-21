package tqs.project.api.services.impl;

import org.springframework.stereotype.Service;

import tqs.project.api.models.Prato;
import tqs.project.api.repositories.PratoRepository;
import tqs.project.api.services.PratoService;

@Service
public class PratoServiceImpl implements PratoService{

    private final PratoRepository pratoRepository;

    public PratoServiceImpl(PratoRepository pratoRepository){
        this.pratoRepository = pratoRepository;
    }

    @Override
    public Prato getPrato(Long id) {
        return pratoRepository.findById(id).orElse(null);
    }
}
