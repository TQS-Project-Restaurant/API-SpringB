package tqs.project.api.initializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import tqs.project.api.models.Bebida;
import tqs.project.api.models.Menu;
import tqs.project.api.models.Pedido;
import tqs.project.api.models.Prato;
import tqs.project.api.models.Utilizador;
import tqs.project.api.others.ROLES;
import tqs.project.api.others.STATUS;
import tqs.project.api.repositories.BebidaRepository;
import tqs.project.api.repositories.MenuRepository;
import tqs.project.api.repositories.PedidoRepository;
import tqs.project.api.repositories.PratoRepository;
import tqs.project.api.repositories.UtilizadorRepository;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {
    
    BebidaRepository bebidaRepository;
    PratoRepository pratoRepository;
    PedidoRepository pedidoRepository;
    MenuRepository menuRepository;
    UtilizadorRepository utilizadorRepository;
    PasswordEncoder passwordEncoder;

    int TOTAL_PEDIDOS = 10;

    Bebida[] BEBIDAS = new Bebida[TOTAL_PEDIDOS];
    Prato[] PRATOS = new Prato[TOTAL_PEDIDOS];
    Pedido[] PEDIDOS = new Pedido[TOTAL_PEDIDOS];
    Menu[] MENUS = new Menu[3];
    Utilizador[] UTILIZADORES = new Utilizador[4];

    @Autowired
    public DataInitializer(BebidaRepository bebidaRepository, PratoRepository pratoRepository, PedidoRepository pedidoRepository,
                            MenuRepository menuRepository, UtilizadorRepository utilizadorRepository, PasswordEncoder passwordEncoder){
        this.bebidaRepository = bebidaRepository;
        this.pratoRepository = pratoRepository;
        this.pedidoRepository = pedidoRepository;
        this.menuRepository = menuRepository;
        this.utilizadorRepository = utilizadorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(String... args) throws Exception{
        if(!pedidoRepository.findAll().isEmpty()){
            return;
        }

        this.loadBebidas();
        this.loadPratos();
        this.loadPedidos();
        this.loadMenus();
        this.loadUsers();
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

    private void loadMenus(){
        MENUS[0] = new Menu();

        MENUS[0].setDia(LocalDate.now());
        MENUS[0].setPratos(Arrays.asList(PRATOS[0], PRATOS[1], PRATOS[6], PRATOS[9]));
        MENUS[0].setBebidas(Arrays.asList(BEBIDAS[0], BEBIDAS[1], BEBIDAS[6], BEBIDAS[9]));

        menuRepository.save(MENUS[0]);

        MENUS[1] = new Menu();

        MENUS[1].setDia(LocalDate.now().plusDays(1));
        MENUS[1].setPratos(Arrays.asList(PRATOS[4], PRATOS[2], PRATOS[6], PRATOS[7]));
        MENUS[1].setBebidas(Arrays.asList(BEBIDAS[2], BEBIDAS[1], BEBIDAS[4], BEBIDAS[3]));

        menuRepository.save(MENUS[1]);

        MENUS[2] = new Menu();

        MENUS[2].setDia(LocalDate.now().plusDays(2));
        MENUS[2].setPratos(Arrays.asList(PRATOS[3], PRATOS[8], PRATOS[5], PRATOS[0]));
        MENUS[2].setBebidas(Arrays.asList(BEBIDAS[7], BEBIDAS[5], BEBIDAS[8], BEBIDAS[9]));

        menuRepository.save(MENUS[2]);
    }

    private void loadUsers(){
        UTILIZADORES[0] = new Utilizador();

        UTILIZADORES[0].setEmail("user@gmail.com");
        UTILIZADORES[0].setPassword(passwordEncoder.encode("user"));
        UTILIZADORES[0].setRole(ROLES.USER);

        utilizadorRepository.save(UTILIZADORES[0]);


        UTILIZADORES[1] = new Utilizador();

        UTILIZADORES[1].setEmail("kitchen@gmail.com");
        UTILIZADORES[1].setPassword(passwordEncoder.encode("kitchen"));
        UTILIZADORES[1].setRole(ROLES.KITCHEN);

        utilizadorRepository.save(UTILIZADORES[1]);


        UTILIZADORES[2] = new Utilizador();

        UTILIZADORES[2].setEmail("waiter@gmail.com");
        UTILIZADORES[2].setPassword(passwordEncoder.encode("waiter"));
        UTILIZADORES[2].setRole(ROLES.WAITER);

        utilizadorRepository.save(UTILIZADORES[2]);


        UTILIZADORES[3] = new Utilizador();

        UTILIZADORES[3].setEmail("user2@gmail.com");
        UTILIZADORES[3].setPassword(passwordEncoder.encode("user2"));
        UTILIZADORES[3].setRole(ROLES.USER);

        utilizadorRepository.save(UTILIZADORES[3]);
    }
}
