import java.util.ArrayList;
import java.util.HashMap;

public class CreadorPokemons {


    /**
     * Generaciones
     */
    private HashMap<Generacion,ArrayList<Pokémon>> pokemonsPorGeneracion = new HashMap<>();
    private ArrayList<Pokémon> gen1 =  new ArrayList<>();
    private ArrayList<Pokémon> gen3 =  new ArrayList<>();
    private ArrayList<Pokémon> gen5 =  new ArrayList<>();
    
    // metodos para poder acceder a ellos en el menú de inicio
     public ArrayList<Pokémon> getGen1() {
          return this.gen1;
     }
     
     public ArrayList<Pokémon> getGen3() {
          return this.gen3;
     }
     
     public ArrayList<Pokémon> getGen5() {
          return this.gen5;
     }
    /**
     * Ataques
     */
    Ataque ascuas = new Ataque("Ascuas",TipoPokemon.FUEGO,80,100);
    Ataque drenadoras = new Ataque("Drenadoras",TipoPokemon.PLANTA,80,100);
    Ataque latigoCepa = new Ataque("Latigo Cepa",TipoPokemon.PLANTA,80,20);
    Ataque burbuja = new Ataque("Burbuja",TipoPokemon.AGUA,40,100);


    /**
     * CreacionPokemon
     */
    
    // GEN 1
    Pokémon charmander;
    Pokémon bulbasur;
    Pokémon squirtle;
    
    // GEN 3
     Pokémon treecko;
     Pokémon torchic;
     Pokémon mudkip;
     
     //GEN %
     Pokémon snivy;
     Pokémon tepig;
     Pokémon oshawott;

    public void inicializarPokemons(){
        /**
         * Lo he hecho asi para que sea muy facil meterlos y se puedan reutilizar
         */

        //Gen
         bulbasur = new Pokémon(1,"Bulbasur",TipoPokemon.PLANTA,TipoPokemon.PLANTA,Generacion.GEN1,1,45,45);
         bulbasur.aprenderAtaque(latigoCepa);
         bulbasur.aprenderAtaque(drenadoras);
         gen1.add(bulbasur);
        
        /////////////
         charmander = new Pokémon(4,"Charmander",TipoPokemon.FUEGO,TipoPokemon.FUEGO,Generacion.GEN1,1,39,39);
         charmander.aprenderAtaque(ascuas);
         gen1.add(charmander);
        /// //////////
        squirtle = new Pokémon(7,"Squirtle",TipoPokemon.AGUA,TipoPokemon.AGUA,Generacion.GEN1,1,44,44);
        squirtle.aprenderAtaque(burbuja);
        gen1.add(squirtle);

        pokemonsPorGeneracion.put(Generacion.GEN1,gen1);
        /// //////////
        
        //Gen3
         treecko = new Pokémon(252, "Treecko", TipoPokemon.PLANTA, TipoPokemon.PLANTA, Generacion.GEN3, 1,40,40);
         treecko.aprenderAtaque(latigoCepa);
         gen3.add(treecko);
         /////////////
         torchic = new Pokémon(255, "Torchic", TipoPokemon.FUEGO, TipoPokemon.FUEGO, Generacion.GEN3, 1,45,45);
         torchic.aprenderAtaque(ascuas);
         gen3.add(torchic);
         /////////////
         mudkip = new Pokémon(258, "Mudkip", TipoPokemon.AGUA, TipoPokemon.AGUA, Generacion.GEN3, 1,50,50);
         mudkip.aprenderAtaque(burbuja);
         gen3.add(mudkip);
         
         pokemonsPorGeneracion.put(Generacion.GEN3,gen3);
         
         //Gen5
         snivy = new Pokémon(495, "Snivy", TipoPokemon.PLANTA, TipoPokemon.PLANTA, Generacion.GEN5, 1,45,45);
         snivy.aprenderAtaque(latigoCepa);
         gen5.add(snivy);
         /////////////
         tepig = new Pokémon(498, "Tepig", TipoPokemon.FUEGO, TipoPokemon.FUEGO, Generacion.GEN5, 1,65,65);
         tepig.aprenderAtaque(ascuas);
         gen5.add(tepig);
         /////////////
         oshawott= new Pokémon(501, "Oshawott", TipoPokemon.AGUA, TipoPokemon.AGUA, Generacion.GEN5, 1,55,55);
         oshawott.aprenderAtaque(burbuja);
         gen5.add(oshawott);
         
         pokemonsPorGeneracion.put(Generacion.GEN5,gen5);
    }


     public String AtaquesCharmander(){
        return charmander.mostrarAtaques();
    }
     public String AtaquesBulbasur(){
        return bulbasur.mostrarAtaques();
    }
     public String AtaquesSquirtle(){
        return squirtle.mostrarAtaques();
    }
    
     public String AtaquesTorchic(){return torchic.mostrarAtaques();}
     public String AtaquesTreecko(){return treecko.mostrarAtaques();}
     public String AtaquesMudkip(){return mudkip.mostrarAtaques();}
     
     public String AtaquesSnivy(){return snivy.mostrarAtaques();}
     public String AtaquesTepig(){return tepig.mostrarAtaques();}
     public String AtaquesOshawott(){return oshawott.mostrarAtaques();}


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        /**
         * Itera las claves el HashMap(Generaciones) y las va imprimiento
         */
        for (Generacion gen : pokemonsPorGeneracion.keySet()) {
            sb.append(gen).append(":\n");

            /**
             * Itera los nombre del HashMap y los imprime
             */
            for (Pokémon p : pokemonsPorGeneracion.get(gen)) {
                sb.append("  - ").append(p).append("\n");
            }
        }

        return sb.toString();
    }



}
