import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * =================================================================================
 * CLASE PRINCIPAL (CONTROLLER)
 * =================================================================================
 * Esta clase gestiona el flujo de la aplicación, la navegación entre pantallas
 * y mantiene el estado global (como el equipo del usuario).
 * 
 * Actúa como un "Controlador" que delega la creación de vistas a TarjetaPokemon
 * y el audio a SonidoPokemon.
 */
public class Main extends Application {

    // ==========================================
    // VARIABLES DE ESTADO Y DATOS
    // ==========================================
    private CreadorPokemons creador; // Base de datos de Pokémon
    private VBox root;               // Contenedor visual principal (Layout)
    private EquipoPokemon equipo;    // Modelo de datos del equipo del usuario
    private boolean inicialElegido = false; // Bandera para saber si ya eligió starter

    /**
     * ==========================================
     * INICIALIZACIÓN DE LA APP (JavaFX)
     * ==========================================
     */
    @Override
    public void start(Stage stage) {
        // 1. Inicializar lógica de negocio (Cargar datos)
        creador = new CreadorPokemons();
        creador.inicializarPokemons();
        
        // 2. Inicializar equipo vacío (Máximo 6)
        equipo = new EquipoPokemon(6);

        // 3. Configurar Layout Principal
        root = new VBox(20); // 20px de espacio vertical entre elementos
        root.setAlignment(Pos.CENTER);
        
        // APLICAR MODO OSCURO GLOBAL
        // Fondo negro (#121212), texto base 14px
        root.setStyle("-fx-padding: 20; -fx-font-size: 14px; -fx-background-color: #121212;");

        // 4. Cargar la primera pantalla
        mostrarPantallaInicio();

        // 5. Configurar Ventana (Stage) y Escena
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Pokémon Adventure");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * ==========================================
     * PANTALLA: MENÚ PRINCIPAL
     * ==========================================
     * Muestra el título y los botones principales.
     * Cambia dinámicamente dependiendo de si el usuario ya eligió su inicial.
     */
    private void mostrarPantallaInicio() {
        root.getChildren().clear(); // Limpiar pantalla anterior

        // Título
        Label label = new Label("Bienvenido al mundo Pokémon");
        label.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #FFCB05;"); // Amarillo Pokémon
        root.getChildren().add(label);
        
        // Botón Dinámico: "Empezar" o "Ver Equipo"
        if (!inicialElegido) {
            Button btn = new Button("¡Empezar aventura!");
            // Estilo Azul
            btn.setStyle("-fx-background-color: #3B4CCA; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 10 20;");
            btn.setOnAction(e -> mostrarSeleccionGeneracion());
            root.getChildren().add(btn);
        } else {
            Button btnEquipo = new Button("Ver Equipo Pokémon");
            // Estilo Verde
            btnEquipo.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 10 20;");
            btnEquipo.setOnAction(e -> mostrarEquipo());
            root.getChildren().add(btnEquipo);
        }
        
        // Botón Pokédex (Siempre visible)
        Button btnVerTodo = new Button("Ver Pokédex Nacional");
        // Estilo Rojo
        btnVerTodo.setStyle("-fx-background-color: #CC0000; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        btnVerTodo.setOnAction(e -> mostrarPokedex());
        root.getChildren().add(btnVerTodo);
    }

    /**
     * ==========================================
     * PANTALLA: VER EQUIPO
     * ==========================================
     * Muestra la lista de Pokémon capturados por el usuario.
     */
    private void mostrarEquipo() {
        root.getChildren().clear();
        
        Label label = new Label("Tu Equipo Pokémon");
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        root.getChildren().add(label);
        
        VBox listaEquipo = new VBox(10);
        listaEquipo.setAlignment(Pos.CENTER);
        
        if (equipo.estaVacio()) {
            Label vacio = new Label("Tu equipo está vacío.");
            vacio.setStyle("-fx-text-fill: #aaa;");
            listaEquipo.getChildren().add(vacio);
        } else {
            // Iterar sobre el equipo y crear filas visuales
            for (Pokémon p : equipo.getPokemonList()) {
                // DELEGACIÓN: Usamos TarjetaPokemon para crear la UI de la fila
                HBox fila = TarjetaPokemon.crearFilaEquipo(p);
                listaEquipo.getChildren().add(fila);
            }
        }
        
        root.getChildren().add(listaEquipo);
        
        // Botón Volver
        Button btnVolver = new Button("Volver al Menú");
        btnVolver.setStyle("-fx-background-color: #555; -fx-text-fill: white;");
        btnVolver.setOnAction(e -> mostrarPantallaInicio());
        root.getChildren().add(btnVolver);
    }

    /**
     * ==========================================
     * PANTALLA: POKÉDEX NACIONAL
     * ==========================================
     * Muestra todos los Pokémon disponibles en una cuadrícula (Grid).
     */
    private void mostrarPokedex() {
        root.getChildren().clear();
        
        Label titulo = new Label("Pokédex Nacional");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        // Configuración del Grid (TilePane)
        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(15));
        tilePane.setHgap(20); // Espacio horizontal
        tilePane.setVgap(20); // Espacio vertical
        tilePane.setPrefColumns(3); // Columnas preferidas
        tilePane.setAlignment(Pos.CENTER);
        
        // Recopilar todos los Pokémon de todas las generaciones
        ArrayList<Pokémon> todos = new ArrayList<>();
        todos.addAll(creador.getGen1());
        todos.addAll(creador.getGen3());
        todos.addAll(creador.getGen5());
        
        // Crear una tarjeta por cada Pokémon
        for (Pokémon p : todos) {
            // DELEGACIÓN: Usamos TarjetaPokemon para crear la tarjeta visual
            tilePane.getChildren().add(TarjetaPokemon.crearTarjetaPokedex(p));
        }
        
        // ScrollPane para permitir desplazamiento si hay muchos Pokémon
        ScrollPane scrollPane = new ScrollPane(tilePane);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #121212; -fx-background-color: transparent;"); // Fondo transparente para ver el negro
        scrollPane.setPadding(new Insets(10));
        
        Button btnVolver = new Button("Volver al Menú");
        btnVolver.setStyle("-fx-font-size: 14px; -fx-padding: 8 15; -fx-background-color: #555; -fx-text-fill: white;");
        btnVolver.setOnAction(e -> mostrarPantallaInicio());
        
        root.getChildren().addAll(titulo, scrollPane, btnVolver);
    }

    /**
     * ==========================================
     * FLUJO DE SELECCIÓN: ELEGIR GENERACIÓN
     * ==========================================
     */
    private void mostrarSeleccionGeneracion() {
        root.getChildren().clear();
        Label label = new Label("Elige tu generación de iniciales:");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        VBox botones = new VBox(15);
        botones.setAlignment(Pos.CENTER);
        
        // Botones para cada generación
        Button btnGen1 = crearBotonGeneracion("Generación 1 (Kanto)");
        btnGen1.setOnAction(e -> mostrarSeleccionInicial(creador.getGen1()));
        
        Button btnGen3 = crearBotonGeneracion("Generación 3 (Hoenn)");
        btnGen3.setOnAction(e -> mostrarSeleccionInicial(creador.getGen3()));
        
        Button btnGen5 = crearBotonGeneracion("Generación 5 (Unova)");
        btnGen5.setOnAction(e -> mostrarSeleccionInicial(creador.getGen5()));
        
        botones.getChildren().addAll(btnGen1, btnGen3, btnGen5);
        
        Button btnVolver = new Button("Volver al inicio");
        btnVolver.setStyle("-fx-background-color: #555; -fx-text-fill: white;");
        btnVolver.setOnAction(e -> mostrarPantallaInicio());

        root.getChildren().addAll(label, botones, btnVolver);
    }
    
    // Helper para estilo de botones de generación
    private Button crearBotonGeneracion(String texto) {
        Button btn = new Button(texto);
        btn.setPrefWidth(200);
        btn.setStyle("-fx-font-size: 14px; -fx-padding: 10; -fx-background-color: #333; -fx-text-fill: white; -fx-border-color: #555;");
        return btn;
    }

    /**
     * ==========================================
     * FLUJO DE SELECCIÓN: ELEGIR POKÉMON
     * ==========================================
     */
    private void mostrarSeleccionInicial(ArrayList<Pokémon> pokemons) {
        root.getChildren().clear();
        Label label = new Label("Elige tu inicial:");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        root.getChildren().add(label);

        HBox container = new HBox(20);
        container.setAlignment(Pos.CENTER);

        for (Pokémon p : pokemons) {
            // DELEGACIÓN: Crear botón con imagen usando TarjetaPokemon
            Button btn = TarjetaPokemon.crearBotonSeleccion(p);
            btn.setOnAction(e -> mostrarDetallePokemon(p));
            container.getChildren().add(btn);
        }
        root.getChildren().add(container);
        
        Button btnVolver = new Button("Volver a elegir generación");
        btnVolver.setStyle("-fx-background-color: #555; -fx-text-fill: white;");
        btnVolver.setOnAction(e -> mostrarSeleccionGeneracion());
        root.getChildren().add(btnVolver);
    }

    /**
     * ==========================================
     * PANTALLA: DETALLE Y CONFIRMACIÓN
     * ==========================================
     * Muestra la imagen grande, reproduce el sonido y permite confirmar la elección.
     */
    private void mostrarDetallePokemon(Pokémon p) {
        root.getChildren().clear();
        Label label = new Label("¿Quieres elegir a " + p.getNombre() + "?");
        label.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        // Imagen Grande (Official Artwork)
        ImageView imageView = new ImageView();
        try {
            String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + p.getId() + ".png";
            Image image = new Image(imageUrl, true);
            imageView.setImage(image);
            imageView.setFitHeight(250);
            imageView.setFitWidth(250);
        } catch (Exception e) { }
        
        // DELEGACIÓN: Reproducir sonido
        SonidoPokemon.reproducirGrito(p);
        
        // Área de texto con detalles
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setText("Detalles:\n" + p.toString() + "\n\nAtaques:\n" + p.mostrarAtaques());
        textArea.setMaxHeight(100);
        textArea.setMaxWidth(400);
        textArea.setStyle("-fx-control-inner-background: #2d2d2d; -fx-text-fill: white;");
        
        // Botón Confirmar
        Button btnElegir = new Button("¡Te elijo a ti, " + p.getNombre() + "!");
        btnElegir.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 10 20;");
        btnElegir.setOnAction(e -> {
            equipo.addPokemon(p); // Añadir al equipo
            inicialElegido = true; // Marcar como elegido
            mostrarPantallaInicio(); // Volver al inicio
        });
        
        // Botón Volver
        Button btnVolver = new Button("Volver atrás");
        btnVolver.setStyle("-fx-background-color: #555; -fx-text-fill: white;");
        btnVolver.setOnAction(e -> mostrarSeleccionGeneracion());
        
        HBox botones = new HBox(20, btnVolver, btnElegir);
        botones.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(label, imageView, textArea, botones);
    }

    // Launcher estándar
    public static void main(String... args) {
        launch(args);
    }
}