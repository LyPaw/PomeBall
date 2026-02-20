import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * Clase principal de la aplicación Pokémon.
 * <p>
 * Esta clase extiende de {@link Application} para proporcionar una interfaz gráfica de usuario (GUI)
 * utilizando JavaFX. Gestiona el flujo de la aplicación, desde la pantalla de bienvenida
 * hasta la selección de generaciones y Pokémon iniciales.
 * </p>
 */
public class Main extends Application {

    /**
     * Instancia de CreadorPokemons que contiene la lógica de negocio y los datos de los Pokémon.
     */
    private CreadorPokemons creador;

    /**
     * Contenedor principal (layout) de la interfaz gráfica.
     * Se utiliza un VBox para organizar los elementos verticalmente.
     */
    private VBox root;

    /**
     * Método de entrada principal para la aplicación JavaFX.
     * <p>
     * Se encarga de inicializar los datos de los Pokémon, configurar el escenario (Stage)
     * y la escena (Scene) principal, y mostrar la primera pantalla.
     * </p>
     *
     * @param stage El escenario principal (ventana) de la aplicación.
     */
    @Override
    public void start(Stage stage) {
        // Inicialización de la lógica de negocio
        creador = new CreadorPokemons();
        creador.inicializarPokemons();

        // Configuración del layout principal
        root = new VBox(20); // Espaciado de 20px entre elementos
        root.setAlignment(Pos.CENTER); // Centrar elementos
        // Estilo CSS básico para mejorar la apariencia
        root.setStyle("-fx-padding: 20; -fx-font-size: 14px;");

        // Cargar la pantalla inicial
        mostrarPantallaInicio();

        // Configuración y visualización de la ventana
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Pokémon");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Muestra la pantalla de bienvenida de la aplicación.
     * <p>
     * Ofrece opciones para comenzar la aventura (selección paso a paso)
     * o ver un resumen completo de todos los datos cargados.
     * </p>
     */
    private void mostrarPantallaInicio() {
        // Limpiar el contenido anterior del layout
        root.getChildren().clear();

        Label label = new Label("Bienvenido al mundo Pokémon");
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Button btn = new Button("¡Empezar aventura!");
        btn.setOnAction(e -> mostrarSeleccionGeneracion());
        
        // Opción para ver el log completo (equivalente a la antigua salida de consola)
        Button btnVerTodo = new Button("Ver todos los Pokémon y ataques");
        btnVerTodo.setOnAction(e -> mostrarTodo());

        root.getChildren().addAll(label, btn, btnVerTodo);
    }

    /**
     * Muestra una vista con todos los Pokémon y sus ataques en un área de texto.
     * <p>
     * Esta función replica la funcionalidad original de imprimir todos los datos
     * por consola, pero ahora los muestra en un componente {@link TextArea}.
     * </p>
     */
    private void mostrarTodo() {
        root.getChildren().clear();
        
        TextArea textArea = new TextArea();
        textArea.setEditable(false); // El usuario no debe editar este texto
        
        // Construcción del texto a mostrar
        StringBuilder sb = new StringBuilder();
        sb.append(creador.toString()).append("\n");
        sb.append("=".repeat(40)).append("\n");
        
        // Ataques Gen 1
        sb.append("Ataques de Charmander ").append(creador.getAtaquesPokemon("Charmander")).append("\n");
        sb.append("Ataques de Bulbasur ").append(creador.getAtaquesPokemon("Bulbasur")).append("\n");
        sb.append("Ataques de Squirtle ").append(creador.getAtaquesPokemon("Squirtle")).append("\n");
        sb.append("=".repeat(40)).append("\n");
        
        // Ataques Gen 3
        sb.append("Ataques de Treecko ").append(creador.getAtaquesPokemon("Treecko")).append("\n");
        sb.append("Ataques de Torchic ").append(creador.getAtaquesPokemon("Torchic")).append("\n");
        sb.append("Ataques de Mudkip ").append(creador.getAtaquesPokemon("Mudkip")).append("\n");
        sb.append("=".repeat(40)).append("\n");
        
        // Ataques Gen 5
        sb.append("Ataques de Snivy ").append(creador.getAtaquesPokemon("Snivy")).append("\n");
        sb.append("Ataques de Tepig ").append(creador.getAtaquesPokemon("Tepig")).append("\n");
        sb.append("Ataques de Oshawott ").append(creador.getAtaquesPokemon("Oshawott")).append("\n");

        textArea.setText(sb.toString());
        
        Button btnVolver = new Button("Volver");
        btnVolver.setOnAction(e -> mostrarPantallaInicio());
        
        root.getChildren().addAll(new Label("Resumen de Pokémon y Ataques"), textArea, btnVolver);
    }

    /**
     * Muestra la pantalla de selección de Generación.
     * <p>
     * Permite al usuario elegir entre las generaciones disponibles (1, 3 y 5).
     * </p>
     */
    private void mostrarSeleccionGeneracion() {
        root.getChildren().clear();
        Label label = new Label("Elige tu generación de iniciales:");
        
        Button btnGen1 = new Button("Generación 1 (Kanto)");
        btnGen1.setOnAction(e -> mostrarSeleccionInicial(creador.getGen1()));
        
        Button btnGen3 = new Button("Generación 3 (Hoenn)");
        btnGen3.setOnAction(e -> mostrarSeleccionInicial(creador.getGen3()));
        
        Button btnGen5 = new Button("Generación 5 (Unova)");
        btnGen5.setOnAction(e -> mostrarSeleccionInicial(creador.getGen5()));
        
        Button btnVolver = new Button("Volver al inicio");
        btnVolver.setOnAction(e -> mostrarPantallaInicio());

        root.getChildren().addAll(label, btnGen1, btnGen3, btnGen5, btnVolver);
    }

    /**
     * Muestra la pantalla de selección de Pokémon inicial.
     * <p>
     * Genera dinámicamente botones basados en la lista de Pokémon proporcionada.
     * </p>
     *
     * @param pokemons Lista de objetos {@link Pokémon} disponibles para elegir en la generación seleccionada.
     */
    private void mostrarSeleccionInicial(ArrayList<Pokémon> pokemons) {
        root.getChildren().clear();
        Label label = new Label("Elige tu inicial:");
        root.getChildren().add(label);

        // Crear un botón por cada Pokémon en la lista
        for (Pokémon p : pokemons) {
            Button btn = new Button(p.getNombre());
            btn.setOnAction(e -> mostrarDetallePokemon(p));
            root.getChildren().add(btn);
        }
        
        Button btnVolver = new Button("Volver a elegir generación");
        btnVolver.setOnAction(e -> mostrarSeleccionGeneracion());
        root.getChildren().add(btnVolver);
    }

    /**
     * Muestra los detalles y ataques del Pokémon seleccionado.
     *
     * @param p El objeto {@link Pokémon} seleccionado por el usuario.
     */
    private void mostrarDetallePokemon(Pokémon p) {
        root.getChildren().clear();
        Label label = new Label("Has elegido a: " + p.getNombre());
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setText("Detalles:\n" + p.toString() + "\n\nAtaques:\n" + p.mostrarAtaques());
        
        Button btnReiniciar = new Button("Reiniciar aventura");
        btnReiniciar.setOnAction(e -> mostrarPantallaInicio());
        
        root.getChildren().addAll(label, textArea, btnReiniciar);
    }

    /**
     * Método main estándar de Java.
     * <p>
     * Lanza la aplicación JavaFX llamando a {@link #launch(String...)}.
     * </p>
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String... args) {
        launch(args);
    }
}