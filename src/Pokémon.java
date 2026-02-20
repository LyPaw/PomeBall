import java.util.ArrayList;

public class Pokémon {

    private int id;
    private String nombre;
    private TipoPokemon tipoPrincipal;
    private TipoPokemon tipoSecundario;
    private Generacion generacion;
    private int nivel;
    private int ps;
    private int psMax;
    ArrayList<Ataque> ataques = new ArrayList<>();


    public Pokémon(int id, String nombre, TipoPokemon tipoPrincipal, TipoPokemon tipoSecundario, Generacion generacion, int nivel, int ps, int psMax) {
        this.id = id;
        this.nombre = nombre;
        this.tipoPrincipal = tipoPrincipal;
        this.tipoSecundario = tipoSecundario;
        this.generacion = generacion;
        this.nivel = nivel;
        this.ps = ps;
        this.psMax = psMax;
    }

    public void aprenderAtaque(Ataque ataque) {
        ataques.add(ataque);
    }

    public String getNombre() {
        return nombre;
    }
    public TipoPokemon getTipoPrincipal() {
        return tipoPrincipal;
    }
    public TipoPokemon getTipoSecundario() {
        return tipoSecundario;
    }
    public Generacion getGeneracion() {
        return generacion;
    }


    @Override
    public String toString() {
        return "|" + nombre;
    }

    public String mostrarAtaques(){
        return ataques.toString();
    }

}
