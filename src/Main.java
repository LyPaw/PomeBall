import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void borradoPantalla() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("No se pudo limpiar la pantalla.");

        }
    }
     
     public static void mostrarMenu(CreadorPokemons c, Scanner sc,
                                    ArrayList<Pokémon> gen1,
                                    ArrayList<Pokémon> gen3,
                                    ArrayList<Pokémon> gen5) {
         
          System.out.println("Elige tu generación de iniciales: ");
          System.out.println("1 - Generación 1 | 2 - Generación 3 | 3 - Generación 5");
          
          int gen = sc.nextInt();
          
          switch (gen) {
               case 1:
                    System.out.println("Elige tu inicial Gen 1: ");
                    System.out.println("1 - Bulbasur | 2 - Charmander | 3 - Squirtle");
                    int inicial = sc.nextInt();
                    
                    // Validar que la lista tenga elementos antes de acceder
                    if(inicial >= 1 && inicial <= gen1.size()) {
                         System.out.println("Elegiste: " + gen1.get(inicial - 1));
                    }
                    break;
               
               case 2:
                   
                    System.out.println("Elige tu inicial Gen 3: ");
                    System.out.println("1 - Treecko | 2 - Torchic | 3 - Mudkip");
                    int inicial3 = sc.nextInt();
                    if(inicial3 >= 1 && inicial3 <= gen3.size()) {
                         System.out.println("Elegiste: " + gen3.get(inicial3 - 1));
                    }
                    break;
               
               case 3:
                   
                    System.out.println("Elige tu inicial Gen 5: ");
                    System.out.println("1 - Snivy | 2 - Tepig | 3 - Oshawott");
                    int inicial5 = sc.nextInt();
                    if(inicial5 >= 1 && inicial5 <= gen5.size()) {
                         System.out.println("Elegiste: " + gen5.get(inicial5 - 1));
                    }
                    break;
               
               default:
                    System.out.println("Opción no válida");
          }
     }
    
    
    
    
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        CreadorPokemons c = new CreadorPokemons();
        c.inicializarPokemons();
         ArrayList<Pokémon> listaGen1 = c.getGen1();
         ArrayList<Pokémon> listaGen3 = c.getGen3();
         ArrayList<Pokémon> listaGen5 = c.getGen5();
         
        System.out.println(c.toString());
        // Ataques 1 GEN
        System.out.println("Ataques de Charmander " + c.AtaquesCharmander());
        System.out.println("Ataques de Bulbasur " + c.AtaquesBulbasur());
        System.out.println("Ataques de Squirtle" + c.AtaquesSquirtle());
         System.out.println("=".repeat(40));
        //Ataques 2 GEN
         System.out.println("Ataques de Treecko " + c.AtaquesTreecko());
         System.out.println("Ataques de Torchic " + c.AtaquesTorchic());
         System.out.println("Ataques de Mudkip " + c.AtaquesMudkip());
         System.out.println("=".repeat(40));
         //Ataques 5 GEN
         System.out.println("Ataques de Snivy " + c.AtaquesSnivy());
         System.out.println("Ataques de Tepig " + c.AtaquesTepig());
         System.out.println("Ataques de Oshawott " +  c.AtaquesOshawott());
         
         
         mostrarMenu(c, sc, listaGen1, listaGen3, listaGen5);
         
    }
}