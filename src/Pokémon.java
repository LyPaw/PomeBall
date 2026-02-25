import java.util.ArrayList;

public class Pokémon {

    int id;
    String nombre;
    TipoPokemon tipoPrincipal;
    TipoPokemon tipoSecundario;
    Generacion generacion;
    int nivel;
    int ps;
    int psMax;
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

    public int getId() {
        return id;
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
        return nombre + " (Nº" + id + ")";
    }

    public String mostrarAtaques() {
        if (ataques.isEmpty()) return "Sin ataques";
        StringBuilder sb = new StringBuilder();
        for (Ataque a : ataques) {
            sb.append("• ").append(a.nombre)
              .append(" [").append(a.tipo).append("]")
              .append(" — Potencia: ").append(a.potencia)
              .append(", Precisión: ").append(a.precision).append("\n");
        }
        return sb.toString().trim();
    }

}