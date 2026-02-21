import java.util.ArrayList;

/**
 * =================================================================================
 * CLASE DE MODELO DE DATOS (MODEL)
 * =================================================================================
 * Esta clase representa el equipo de Pokémon del usuario.
 * Gestiona la lista de Pokémon capturados y las restricciones del equipo (máximo 6).
 */
public class EquipoPokemon {

    // ==========================================
    // VARIABLES DE ESTADO
    // ==========================================
    private int maxEquipo; // Capacidad máxima del equipo (ej. 6)
    private ArrayList<Pokémon> pokemonList; // Lista de Pokémon en el equipo

    /**
     * Constructor del Equipo.
     * Inicializa la lista vacía y establece el límite máximo.
     *
     * @param maxEquipo Número máximo de Pokémon permitidos en el equipo.
     */
    public EquipoPokemon(int maxEquipo){
        this.maxEquipo = maxEquipo;
        this.pokemonList = new ArrayList<>();
    }

    /**
     * Obtiene la lista actual de Pokémon en el equipo.
     *
     * @return ArrayList de objetos Pokémon.
     */
    public ArrayList<Pokémon> getPokemonList() {
        return pokemonList;
    }

    /**
     * Añade un Pokémon al equipo si hay espacio disponible.
     *
     * @param pokemon El Pokémon a añadir.
     */
    public void addPokemon(Pokémon pokemon){
        if (pokemonList.size() < maxEquipo) {
            pokemonList.add(pokemon);
        } else {
            // Opcional: Lanzar excepción o mensaje si el equipo está lleno
            System.out.println("El equipo está lleno. No se puede añadir más Pokémon.");
        }
    }
    
    /**
     * Obtiene la capacidad máxima del equipo.
     *
     * @return Entero con el tamaño máximo.
     */
    public int getMaxEquipo(){
        return maxEquipo;
    }
    
    /**
     * Verifica si el equipo ha alcanzado su capacidad máxima.
     *
     * @return true si está lleno, false si hay espacio.
     */
    public boolean estaLleno() {
        return pokemonList.size() >= maxEquipo;
    }
    
    /**
     * Verifica si el equipo no tiene ningún Pokémon.
     *
     * @return true si está vacío, false si tiene al menos uno.
     */
    public boolean estaVacio() {
        return pokemonList.isEmpty();
    }
}