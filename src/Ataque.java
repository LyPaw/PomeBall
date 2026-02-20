public class Ataque {
    private String nombre;
    private TipoPokemon tipo;
    private int potencia;
    private int precision;
    private int pp;
    private int ppMax;


    public Ataque(String nombre, TipoPokemon tipo, int potencia, int precision, int pp, int ppMax) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.potencia = potencia;
        this.precision = precision;
        this.pp = pp;
        this.ppMax = ppMax;
    }

    @Override
    public String toString() {
        return "|" + nombre + "|";
    }

}
