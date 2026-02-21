import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * =================================================================================
 * CLASE DE UTILIDAD VISUAL (VIEW HELPER)
 * =================================================================================
 * Esta clase se encarga de crear componentes gráficos reutilizables para la interfaz.
 * Centraliza la lógica de diseño (estilos, disposición) de las tarjetas de Pokémon,
 * filas de equipo y botones con imágenes.
 */
public class TarjetaPokemon {

    /**
     * Crea una tarjeta visual completa para la vista de Pokédex Nacional.
     * Incluye imagen, nombre, tipos, ataques y botón de sonido.
     *
     * @param p El Pokémon a mostrar.
     * @return Un VBox configurado como tarjeta.
     */
    public static VBox crearTarjetaPokedex(Pokémon p) {
        VBox tarjeta = new VBox(10);
        tarjeta.setAlignment(Pos.CENTER);
        tarjeta.setPadding(new Insets(15));
        
        // ESTILO: Fondo oscuro (#1E1E1E), bordes redondeados y sombra
        tarjeta.setStyle("-fx-background-color: #1E1E1E; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #333; -fx-border-width: 1;");
        tarjeta.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.5)));
        tarjeta.setPrefWidth(220);
        
        // 1. Imagen del Pokémon (120px)
        ImageView img = crearImagen(p.getId(), 120);
        
        // 2. Nombre
        Label nombre = new Label(p.getNombre());
        nombre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        // 3. Tipos (Principal / Secundario)
        String textoTipos = p.getTipoPrincipal().toString();
        if (p.getTipoSecundario() != null && p.getTipoSecundario() != p.getTipoPrincipal()) {
            textoTipos += " / " + p.getTipoSecundario();
        }
        Label tipos = new Label(textoTipos);
        tipos.setStyle("-fx-text-fill: #aaa; -fx-font-style: italic;");
        
        // 4. Lista de Ataques (TextArea de solo lectura)
        Label lblAtaques = new Label("Ataques:");
        lblAtaques.setStyle("-fx-text-fill: #ccc;");

        TextArea ataquesArea = new TextArea(p.mostrarAtaques());
        ataquesArea.setEditable(false);
        ataquesArea.setPrefHeight(70);
        ataquesArea.setWrapText(true);
        ataquesArea.setStyle("-fx-font-size: 11px; -fx-control-inner-background: #2d2d2d; -fx-text-fill: white; -fx-background-color: #2d2d2d;");
        
        // 5. Botón de Sonido
        Button btnSonido = new Button("🔊");
        btnSonido.setStyle("-fx-background-radius: 20; -fx-background-color: #444; -fx-text-fill: white;");
        btnSonido.setOnAction(e -> SonidoPokemon.reproducirGrito(p));
        
        tarjeta.getChildren().addAll(img, nombre, tipos, lblAtaques, ataquesArea, btnSonido);
        
        return tarjeta;
    }
    
    /**
     * Crea una fila horizontal para mostrar un Pokémon en la lista del Equipo.
     * Incluye imagen pequeña, nombre, nivel simulado y barra de vida.
     *
     * @param p El Pokémon del equipo.
     * @return Un HBox configurado como fila de lista.
     */
    public static HBox crearFilaEquipo(Pokémon p) {
        HBox fila = new HBox(20);
        fila.setAlignment(Pos.CENTER_LEFT);
        // ESTILO: Fila oscura con borde sutil
        fila.setStyle("-fx-border-color: #333; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: #1E1E1E; -fx-background-radius: 5;");
        fila.setMaxWidth(600);
        
        // 1. Imagen pequeña (60px)
        ImageView img = crearImagen(p.getId(), 60);
        
        // 2. Datos (Nombre y Vida)
        VBox datos = new VBox(5);
        Label nombre = new Label(p.getNombre() + " Nv. 5");
        nombre.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
        
        HBox vidaBox = new HBox(5);
        Label hpLabel = new Label("HP:");
        hpLabel.setStyle("-fx-text-fill: #ccc;");
        ProgressBar hpBar = new ProgressBar(1.0); // Vida llena (simulada)
        hpBar.setStyle("-fx-accent: #4caf50; -fx-control-inner-background: #333;");
        vidaBox.getChildren().addAll(hpLabel, hpBar);
        
        datos.getChildren().addAll(nombre, vidaBox);
        
        // 3. Botón de Sonido
        Button btnGrito = new Button("🔊");
        btnGrito.setStyle("-fx-background-color: #333; -fx-text-fill: white;");
        btnGrito.setOnAction(e -> SonidoPokemon.reproducirGrito(p));
        
        fila.getChildren().addAll(img, datos, btnGrito);
        return fila;
    }
    
    /**
     * Crea un botón grande con imagen y texto para la selección de Pokémon inicial.
     *
     * @param p El Pokémon a seleccionar.
     * @return Un Button personalizado.
     */
    public static Button crearBotonSeleccion(Pokémon p) {
        Button btn = new Button();
        btn.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #222; -fx-text-fill: white; -fx-border-color: #444;");
        
        VBox content = new VBox(5);
        content.setAlignment(Pos.CENTER);
        
        ImageView img = crearImagen(p.getId(), 80);
        
        Label lblNombre = new Label(p.getNombre());
        lblNombre.setStyle("-fx-text-fill: white;");
        
        content.getChildren().addAll(img, lblNombre);
        btn.setGraphic(content);
        
        return btn;
    }
    
    /**
     * Método auxiliar privado para cargar imágenes desde PokeAPI de forma segura.
     * Maneja excepciones si la imagen no carga.
     *
     * @param id ID del Pokémon.
     * @param size Tamaño deseado (ancho/alto).
     * @return ImageView configurado.
     */
    public static ImageView crearImagen(int id, double size) {
        ImageView img = new ImageView();
        try {
            String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png";
            // Si la imagen es grande, intentamos usar el arte oficial de mayor calidad
            if (size > 100) {
                url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + id + ".png";
            }
            // 'true' en el constructor de Image habilita la carga en segundo plano (background loading)
            img.setImage(new Image(url, true));
            img.setFitHeight(size);
            img.setFitWidth(size);
        } catch (Exception e) { 
            // Si falla, devolvemos una imagen vacía para no romper la UI
        }
        return img;
    }
}