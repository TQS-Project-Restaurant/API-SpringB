package tqs.project.api.initializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tqs.project.api.models.Bebida;
import tqs.project.api.models.Pedido;
import tqs.project.api.models.Prato;
import tqs.project.api.others.STATUS;
import tqs.project.api.repositories.BebidaRepository;
import tqs.project.api.repositories.PedidoRepository;
import tqs.project.api.repositories.PratoRepository;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {
    
    BebidaRepository bebidaRepository;
    PratoRepository pratoRepository;
    PedidoRepository pedidoRepository;

    int TOTAL_PEDIDOS = 7;

    Bebida[] BEBIDAS = new Bebida[TOTAL_PEDIDOS];
    Prato[] PRATOS = new Prato[TOTAL_PEDIDOS];
    Pedido[] PEDIDOS = new Pedido[TOTAL_PEDIDOS];

    @Autowired
    public DataInitializer(BebidaRepository bebidaRepository, PratoRepository pratoRepository, PedidoRepository pedidoRepository){
        this.bebidaRepository = bebidaRepository;
        this.pratoRepository = pratoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public void run(String... args) throws Exception{
        if(!pedidoRepository.findAll().isEmpty()){
            return;
        }

        this.loadBebidas();
        this.loadPratos();
        this.loadPedidos();
    }

    public int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Double getRandomDouble(Double min, Double max) {
        double randomValue = (Math.random() * (max - min)) + min;
        return (Math.round(randomValue * 100.0) / 100.0);
    }

    private void loadPedidos(){
        int[] mesas = {1, 2, 3, 4, 5, 6, 7};

        for(int i = 0; i < TOTAL_PEDIDOS; i++) {
            PEDIDOS[i] = new Pedido();

            PEDIDOS[i].setMesa(mesas[i]);
            PEDIDOS[i].setStatus(getRandomInt(STATUS.PENDING.ordinal(), STATUS.CANCELED.ordinal()));
            
            List<Bebida> bebidasList = new ArrayList<>();
            List<Prato> pratosList = new ArrayList<>();

            int v = getRandomInt(1, TOTAL_PEDIDOS);
            for(int j = 0; j < v; j++){
                bebidasList.add(BEBIDAS[j]);
                pratosList.add(PRATOS[j]);
            }

            PEDIDOS[i].setBebidas(bebidasList);
            PEDIDOS[i].setPratos(pratosList);
            
            pedidoRepository.save(PEDIDOS[i]);
        }
    }

    private void loadBebidas(){
        Double precoMin = 0.99;
        Double precoMax =  5.00;
        Double hidratosMin = 0.2;
        Double hidratosMax = 40.0;
        Double proteinaMin = 0.1;
        Double proteinaMax = 4.0;
        int kcalMin = 30;
        int kcalMax = 300;

        String[] names = {"Coca-Cola", "Sumo de Laranja", "Lipton", "Limonada", "SuperBock", "Vinho da terra", "7Up"};
        String[] imagesUrl = {"cocacola.png", "sumolaranja.png", "lipton.png", "limonada.png", "superbock.png", "vinhodaterra.png", "7up.png"};

        for(int i = 0; i < TOTAL_PEDIDOS; i++){
            BEBIDAS[i] = new Bebida();

            BEBIDAS[i].setNome(names[i]);
            BEBIDAS[i].setPreco(getRandomDouble(precoMin, precoMax));
            BEBIDAS[i].setHidratosCarbono(getRandomDouble(hidratosMin, hidratosMax));
            BEBIDAS[i].setProteina(getRandomDouble(proteinaMin, proteinaMax));
            BEBIDAS[i].setKcal(getRandomInt(kcalMin, kcalMax));
            BEBIDAS[i].setStock(20);
            BEBIDAS[i].setImagemUrl("/images/drinks/" + imagesUrl[i]);

            BEBIDAS[i] = bebidaRepository.save(BEBIDAS[i]);
        }
    }

    private void loadPratos(){
        Double precoMin = 3.20;
        Double precoMax =  27.50;
        Double hidratosMin = 3.5;
        Double hidratosMax = 85.0;
        Double proteinaMin = 1.0;
        Double proteinaMax = 40.5;
        int kcalMin = 200;
        int kcalMax = 1350;

        String[] names = {"Francesinha", "Sopa Tomate", "Prego no prato", "Salmão", "Lulas grelhadas", "Salada russa", "Rojões"};
        String[] imagesUrl = {"francesinha.png", "sopatomate.png", "pregonoprato.png", "salmao.png", "lulasgrelhadas.png", "saladarussa.png", "rojoes.png"};

        for(int i = 0; i < TOTAL_PEDIDOS; i++){
            PRATOS[i] = new Prato();

            PRATOS[i].setNome(names[i]);
            PRATOS[i].setPreco(getRandomDouble(precoMin, precoMax));
            PRATOS[i].setHidratosCarbono(getRandomDouble(hidratosMin, hidratosMax));
            PRATOS[i].setProteina(getRandomDouble(proteinaMin, proteinaMax));
            PRATOS[i].setKcal(getRandomInt(kcalMin, kcalMax));
            PRATOS[i].setStock(20);
            PRATOS[i].setImagemUrl("/images/food/" + imagesUrl[i]);

            PRATOS[i] = pratoRepository.save(PRATOS[i]);
        }
    }
}
