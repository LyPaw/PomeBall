import java.util.ArrayList;

public class EquipoPokemon {
    private int Max_Equipo;
    private ArrayList<Pokémon> PokemonList;
    public EquipoPokemon(int Max_Equipo){
        this.Max_Equipo = Max_Equipo;
    }
    private ArrayList<Pokémon> pokemonList = new ArrayList<>();

    public ArrayList<Pokémon> getPokemonList() {
        return PokemonList;
    }

    ArrayList<Pokémon> setPokemonList(Pokémon pokemon){
        PokemonList.add(pokemon);
        return PokemonList;
    }
    public int getMax_Equipo(){
        return Max_Equipo;

    }
    public void setMax_Equipo(int Max_Equipo){
        this.Max_Equipo = Max_Equipo;

    }

}
