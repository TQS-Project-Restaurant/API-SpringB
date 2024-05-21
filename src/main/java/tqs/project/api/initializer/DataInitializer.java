package tqs.project.api.initializer;

import java.util.ArrayList;
import java.util.List;

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

    int TOTAL_PEDIDOS = 10;

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
        int[] mesas = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // creating RANDOM pedidos
        for(int i = 0; i < TOTAL_PEDIDOS; i++) {
            PEDIDOS[i] = new Pedido();

            PEDIDOS[i].setMesa(mesas[i]);
            PEDIDOS[i].setStatus(getRandomInt(STATUS.PENDING.ordinal(), STATUS.CANCELLED.ordinal()));
            PEDIDOS[i].setLastModified(System.currentTimeMillis() % 1000);
            
            List<Bebida> bebidasList = new ArrayList<>();
            List<Prato> pratosList = new ArrayList<>();

            for(int j = 0; j < getRandomInt(1, TOTAL_PEDIDOS); j++){
                bebidasList.add(BEBIDAS[j]);
                pratosList.add(PRATOS[j]);
            }

            PEDIDOS[i].setBebidas(bebidasList);
            PEDIDOS[i].setPratos(pratosList);
            
            pedidoRepository.save(PEDIDOS[i]);
        }

        // creating at least ONE pedido with each status and STATIC information
        Pedido pedidoPending = new Pedido();

        pedidoPending.setMesa(11);
        pedidoPending.setStatus(STATUS.PENDING.ordinal());
        pedidoPending.setLastModified(System.currentTimeMillis() % 1000);
        pedidoPending.setBebidas(List.of(BEBIDAS[0], BEBIDAS[8]));
        pedidoPending.setPratos(List.of(PRATOS[4], PRATOS[2]));

        pedidoRepository.save(pedidoPending);

        Pedido pedidoPreparing = new Pedido();

        pedidoPreparing.setMesa(12);
        pedidoPreparing.setStatus(STATUS.PREPARING.ordinal());
        pedidoPreparing.setLastModified(System.currentTimeMillis() % 1000);
        pedidoPreparing.setBebidas(List.of(BEBIDAS[2], BEBIDAS[4], BEBIDAS[9]));
        pedidoPreparing.setPratos(List.of(PRATOS[4], PRATOS[2], PRATOS[3], PRATOS[4], PRATOS[2]));

        pedidoRepository.save(pedidoPreparing);

        Pedido pedidoCompleted = new Pedido();

        pedidoCompleted.setMesa(13);
        pedidoCompleted.setStatus(STATUS.COMPLETED.ordinal());
        pedidoCompleted.setLastModified(System.currentTimeMillis() % 1000);
        pedidoCompleted.setBebidas(List.of(BEBIDAS[3]));
        pedidoCompleted.setPratos(List.of(PRATOS[6]));

        pedidoRepository.save(pedidoCompleted);

        Pedido pedidoCancelled = new Pedido();

        pedidoCancelled.setMesa(14);
        pedidoCancelled.setStatus(STATUS.CANCELLED.ordinal());
        pedidoCancelled.setLastModified(System.currentTimeMillis() % 1000);
        pedidoCancelled.setBebidas(List.of(BEBIDAS[0], BEBIDAS[8], BEBIDAS[8], BEBIDAS[8], BEBIDAS[8]));
        pedidoCancelled.setPratos(List.of(PRATOS[7], PRATOS[1]));

        pedidoRepository.save(pedidoCancelled);
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

        String[] names = {"Coca-Cola", "Sumo de Laranja", "Lipton", "Limonada", "SuperBock", "Vinho da terra", "7Up", "Água", 
                            "Sangria", "Whiskey"};
        String[] imagesUrl = {"cocacola.png", "sumolaranja.png", "lipton.png", "limonada.png", "superbock.png", "vinhodaterra.png", 
                            "7up.png", "agua.png", "sangria.png", "whiskey.png"};

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

        String[] names = {"Francesinha", "Sopa Tomate", "Prego no prato", "Salmão", "Lulas grelhadas", "Salada russa", "Rojões",
                            "Sardinha assada", "Omelete", "Bacalhau com natas"};
        String[] imagesUrl = {"francesinha.png", "sopatomate.png", "pregonoprato.png", "salmao.png", "lulasgrelhadas.png", "saladarussa.png", 
                            "rojoes.png", "sardinhaassada.png", "omelete.png", "bacalhaucomnatas.png"};

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
