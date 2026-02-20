import java.util.ArrayList;

public class EquipoPokemon {
    private int maxEquipo;
    private ArrayList<Pokémon> PokemonList = new ArrayList<>();
    public EquipoPokemon(int maxEquipo){
        this.maxEquipo = maxEquipo;
    }
    ArrayList<Pokémon>  getPokemonList(){
        return PokemonList;
    }
    ArrayList<Pokémon> setPokemonList(Pokémon pokemon){
        PokemonList.add(pokemon);
        return PokemonList;
    }
    public int getMaxEquipo(){
        return maxEquipo;

    }
    public void setMaxEquipo(int Max_Equipo){
        this.maxEquipo = Max_Equipo;

    }

}
