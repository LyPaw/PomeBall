import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Entrenador {
    private String nombre;
    private int dinero;
    private EquipoPokemon equipo;
    private inventario Inventario;
    private int AventurasCompletadas;
    private boolean pokemonInicialElegido;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;

    }
    public int getDinero() {
        return dinero;
    }
    public void setDinero(int dinero) {
        this.dinero = dinero;
    }
    public EquipoPokemon getEquipo() {
        return equipo;
    }
    public void setEquipo(EquipoPokemon equipo) {
        this.equipo = equipo;
    }

    public inventario getInventario() {
        return Inventario;
    }
    public void setInventario(inventario Inventario) {
        this.Inventario = Inventario;
    }
    public int getAventurasCompletadas() {
        return AventurasCompletadas;
    }
    public void setAventurasCompletadas(int AventurasCompletadas) {
        this.AventurasCompletadas = AventurasCompletadas;
    }
    public boolean isPokemonInicialElegido() {
        return pokemonInicialElegido;

    }
    public void setPokemonInicialElegido(boolean pokemonInicialElegido) {
        this.pokemonInicialElegido = pokemonInicialElegido;
    }
    public boolean ElegirPokemonInicial(Pokémon pokemon) {
        System.out.print("¿Has escogido pokémon inicial?");
        if (this.pokemonInicialElegido == false) {
            return false;
        }else  {
            return true;
        }
    }

    public boolean agregarPokemonAlEquipo(Pokémon pokemon){
        System.out.print("El pokémon se ha añadido al equipo-->");
        return true;
    }

    public boolean darDeComer(Pokémon pokemon,Item item){
        return true;
    }
    public List<ArrayList<Pokémon>> verEquipo(EquipoPokemon equipo){
        return Arrays.asList(equipo.getPokemonList());
    }
    public boolean capturarPokemon(Pokémon salvaje,Pokeball pokeball){
        return true;
    }





}
