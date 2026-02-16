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

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        CreadorPokemons c = new CreadorPokemons();
        c.inicializarPokemons();
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
         
    }
}