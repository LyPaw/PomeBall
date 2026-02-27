import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase principal de la aplicación Pokémon con interfaz visual mejorada.
 * - Estadísticas con barras de progreso (PS, Ataque, Defensa, Velocidad)
 * - Cadena de evoluciones desde la PokéAPI (evoluciones no descubiertas en negro)
 */
public class Main extends Application {

    // -----------------------------------------------------------------------
    //  Constantes de diseño
    // -----------------------------------------------------------------------

    private static final String SPRITE_BASE =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    private static final String POKEAPI_BASE = "https://pokeapi.co/api/v2/";

    private static final Map<String, String> TYPE_COLORS = new HashMap<>() {{
        put("FUEGO",     "#FF9741");
        put("AGUA",      "#3892DC");
        put("PLANTA",    "#38BF4B");
        put("NORMAL",    "#919AA2");
        put("ELECTRICO", "#FBD100");
        put("PSIQUICO",  "#FF6675");
        put("HIELO",     "#70CBD4");
        put("DRAGON",    "#006FC9");
        put("SINIESTRO", "#5B5466");
        put("HADA",      "#FB89EB");
        put("LUCHA",     "#CE4069");
        put("VENENO",    "#AB6AC8");
        put("TIERRA",    "#D97845");
        put("VOLADOR",   "#89AAE3");
        put("BICHO",     "#91C12F");
        put("ROCA",      "#C5B78C");
        put("FANTASMA",  "#5269AC");
        put("ACERO",     "#5A8EA2");
    }};

    // Caché de imágenes para no recargar sprites
    private static final Map<Integer, Image> IMAGE_CACHE = new HashMap<>();

    // -----------------------------------------------------------------------
    //  Estado de la aplicación
    // -----------------------------------------------------------------------

    private CreadorPokemons creador;
    private StackPane root;
    private VBox content;

    // -----------------------------------------------------------------------
    //  Arranque JavaFX
    // -----------------------------------------------------------------------

    @Override
    public void start(Stage stage) {
        creador = new CreadorPokemons();
        creador.inicializarPokemons();

        Pane background = crearFondo();

        content = new VBox(20);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(30));

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root = new StackPane(background, scroll);

        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("✦ PokéRealm ✦");
        stage.setScene(scene);
        stage.show();

        mostrarPantallaInicio();
    }

    // -----------------------------------------------------------------------
    //  Fondo temático
    // -----------------------------------------------------------------------

    private Pane crearFondo() {
        Pane bg = new Pane();
        bg.setStyle("""
            -fx-background-image: url('file:docs/spriteV/fondoPokemon.png');
            -fx-background-size: cover;
            -fx-background-position: center;
            -fx-background-repeat: no-repeat;
            """);

        for (int i = 0; i < 6; i++) {
            double size = 80 + i * 60;
            javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(size / 2);
            circle.setFill(Color.TRANSPARENT);
            circle.setStroke(Color.rgb(255, 255, 255, 0.04));
            circle.setStrokeWidth(1.5);
            circle.setLayoutX(720 - i * 30);
            circle.setLayoutY(100 + i * 20);
            bg.getChildren().add(circle);
        }

        javafx.scene.shape.Circle pokeball = new javafx.scene.shape.Circle(90);
        pokeball.setFill(Color.TRANSPARENT);
        pokeball.setStroke(Color.rgb(255, 255, 255, 0.06));
        pokeball.setStrokeWidth(3);
        pokeball.setLayoutX(800);
        pokeball.setLayoutY(60);
        bg.getChildren().add(pokeball);

        return bg;
    }

    // -----------------------------------------------------------------------
    //  Pantalla de inicio
    // -----------------------------------------------------------------------

    private void mostrarPantallaInicio() {
        content.getChildren().clear();

        Label titulo = new Label("POKÉREALM");
        titulo.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 42px;
                -fx-font-weight: bold;
                -fx-font-family: 'Georgia';
                -fx-effect: dropshadow(gaussian, rgba(255,200,0,0.8), 20, 0.4, 0, 0);
                """);

        Label subtitulo = new Label("¿Estás listo para tu aventura?");
        subtitulo.setStyle("""
                -fx-text-fill: rgba(255,255,255,0.7);
                -fx-font-size: 16px;
                -fx-font-family: 'Georgia';
                """);

        ImageView pokeballImg = cargarSprite(0, 80);

        Button btnAventura = crearBotonPrincipal("⚔  Empezar Aventura", "#FFD700", "#333");
        btnAventura.setOnAction(e -> mostrarSeleccionGeneracion());

        Button btnVerTodo = crearBotonPrincipal("📋  Ver todos los Pokémon", "#4A90D9", "white");
        btnVerTodo.setOnAction(e -> mostrarTodo());

        VBox box = new VBox(16, titulo, subtitulo, pokeballImg, btnAventura, btnVerTodo);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(40));

        content.getChildren().add(box);
    }

    // -----------------------------------------------------------------------
    //  Pantalla: ver todos
    // -----------------------------------------------------------------------

    private void mostrarTodo() {
        content.getChildren().clear();

        Label titulo = labelTitulo("Todos los Pokémon por Generación");
        content.getChildren().add(titulo);

        content.getChildren().add(labelSeccion("— Generación 1 · Kanto —"));
        HBox gen1Row = new HBox(20);
        gen1Row.setAlignment(Pos.CENTER);
        for (Pokémon p : creador.getGen1()) gen1Row.getChildren().add(tarjetaPokemon(p));
        content.getChildren().add(gen1Row);

        content.getChildren().add(labelSeccion("— Generación 3 · Hoenn —"));
        HBox gen3Row = new HBox(20);
        gen3Row.setAlignment(Pos.CENTER);
        for (Pokémon p : creador.getGen3()) gen3Row.getChildren().add(tarjetaPokemon(p));
        content.getChildren().add(gen3Row);

        content.getChildren().add(labelSeccion("— Generación 5 · Unova —"));
        HBox gen5Row = new HBox(20);
        gen5Row.setAlignment(Pos.CENTER);
        for (Pokémon p : creador.getGen5()) gen5Row.getChildren().add(tarjetaPokemon(p));
        content.getChildren().add(gen5Row);

        content.getChildren().add(crearBotonVolver(() -> mostrarPantallaInicio()));
    }

    // -----------------------------------------------------------------------
    //  Pantalla: selección de generación
    // -----------------------------------------------------------------------

    private void mostrarSeleccionGeneracion() {
        content.getChildren().clear();

        content.getChildren().add(labelTitulo("Elige tu Generación"));
        content.getChildren().add(labelSeccion("¿Con qué región quieres comenzar?"));

        HBox genRow = new HBox(24);
        genRow.setAlignment(Pos.CENTER);

        genRow.getChildren().add(tarjetaGeneracion("GEN 1", "Kanto", "#E8A838",
                new int[]{4, 1, 7}, () -> mostrarSeleccionInicial(creador.getGen1())));
        genRow.getChildren().add(tarjetaGeneracion("GEN 3", "Hoenn", "#5DB9E0",
                new int[]{255, 252, 258}, () -> mostrarSeleccionInicial(creador.getGen3())));
        genRow.getChildren().add(tarjetaGeneracion("GEN 5", "Unova", "#78C85A",
                new int[]{498, 495, 501}, () -> mostrarSeleccionInicial(creador.getGen5())));

        content.getChildren().add(genRow);
        content.getChildren().add(crearBotonVolver(() -> mostrarPantallaInicio()));
    }

    private VBox tarjetaGeneracion(String gen, String region, String color,
                                   int[] ids, Runnable onClic) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setPrefWidth(200);
        card.setStyle(String.format("""
                -fx-background-color: rgba(255,255,255,0.08);
                -fx-background-radius: 18;
                -fx-border-color: %s;
                -fx-border-width: 2;
                -fx-border-radius: 18;
                -fx-cursor: hand;
                """, color));

        Label lGen = new Label(gen);
        lGen.setStyle(String.format("-fx-text-fill: %s; -fx-font-size: 22px; " +
                "-fx-font-weight: bold; -fx-font-family: 'Georgia';", color));

        Label lRegion = new Label(region);
        lRegion.setStyle("-fx-text-fill: rgba(255,255,255,0.7); -fx-font-size: 13px;");

        HBox sprites = new HBox(4);
        sprites.setAlignment(Pos.CENTER);
        for (int id : ids) sprites.getChildren().add(cargarSprite(id, 52));

        Button btn = crearBotonPrincipal("Elegir", color, "#111");
        btn.setOnAction(e -> onClic.run());

        card.getChildren().addAll(lGen, lRegion, sprites, btn);

        card.setOnMouseEntered(e -> card.setStyle(String.format("""
                -fx-background-color: rgba(255,255,255,0.14);
                -fx-background-radius: 18;
                -fx-border-color: %s;
                -fx-border-width: 2.5;
                -fx-border-radius: 18;
                -fx-cursor: hand;
                -fx-effect: dropshadow(gaussian, %s, 18, 0.3, 0, 0);
                """, color, color)));
        card.setOnMouseExited(e -> card.setStyle(String.format("""
                -fx-background-color: rgba(255,255,255,0.08);
                -fx-background-radius: 18;
                -fx-border-color: %s;
                -fx-border-width: 2;
                -fx-border-radius: 18;
                -fx-cursor: hand;
                """, color)));

        return card;
    }

    // -----------------------------------------------------------------------
    //  Pantalla: selección de inicial (con stats y evoluciones en negro)
    // -----------------------------------------------------------------------

    private void mostrarSeleccionInicial(ArrayList<Pokémon> pokemons) {
        content.getChildren().clear();
        content.getChildren().add(labelTitulo("Elige tu Pokémon inicial"));
        content.getChildren().add(labelSeccion("Estudia sus estadísticas y evoluciones antes de decidir"));

        VBox columna = new VBox(24);
        columna.setAlignment(Pos.CENTER);
        columna.setMaxWidth(860);

        for (Pokémon p : pokemons) {
            columna.getChildren().add(tarjetaPokemonSeleccionable(p));
        }

        content.getChildren().add(columna);
        content.getChildren().add(crearBotonVolver(() -> mostrarSeleccionGeneracion()));
    }

    private HBox tarjetaPokemonSeleccionable(Pokémon p) {
        String colorTipo = colorDeTipo(p.getTipoPrincipal().name());
        int id = getPokemonId(p);

        // -- Izquierda: sprite + nombre + tipo --
        ImageView sprite = cargarSprite(id, 96);
        Label nombre = new Label(p.getNombre());
        nombre.setStyle(String.format("""
                -fx-text-fill: %s;
                -fx-font-size: 20px;
                -fx-font-weight: bold;
                -fx-font-family: 'Georgia';
                """, colorTipo));
        VBox izquierda = new VBox(8, sprite, nombre, etiquetaTipo(p.getTipoPrincipal().name()));
        izquierda.setAlignment(Pos.CENTER);
        izquierda.setMinWidth(130);

        // -- Centro: barras de stats --
        Label lPS  = new Label("PS");      Label vPS  = new Label("..."); ProgressBar bPS  = crearBarra(colorTipo);
        Label lAtk = new Label("Ataque");  Label vAtk = new Label("..."); ProgressBar bAtk = crearBarra(colorTipo);
        Label lDef = new Label("Defensa"); Label vDef = new Label("..."); ProgressBar bDef = crearBarra(colorTipo);
        Label lSpd = new Label("Veloc.");  Label vSpd = new Label("..."); ProgressBar bSpd = crearBarra(colorTipo);
        VBox statsBox = new VBox(7,
                labelMiniSeccion("📊 Estadísticas"),
                filaStatsBarra(lPS, vPS, bPS),
                filaStatsBarra(lAtk, vAtk, bAtk),
                filaStatsBarra(lDef, vDef, bDef),
                filaStatsBarra(lSpd, vSpd, bSpd));
        statsBox.setMinWidth(230);
        statsBox.setAlignment(Pos.CENTER_LEFT);

        Task<int[]> statsTask = new Task<>() {
            @Override protected int[] call() throws Exception {
                String json = fetchString(POKEAPI_BASE + "pokemon/" + id + "/");
                return new int[]{
                        extraerStatPorNombre(json, "hp"),
                        extraerStatPorNombre(json, "attack"),
                        extraerStatPorNombre(json, "defense"),
                        extraerStatPorNombre(json, "speed")
                };
            }
        };
        statsTask.setOnSucceeded(e -> {
            int[] vals = statsTask.getValue();
            actualizarBarra(vPS, bPS, vals[0], 255);
            actualizarBarra(vAtk, bAtk, vals[1], 255);
            actualizarBarra(vDef, bDef, vals[2], 255);
            actualizarBarra(vSpd, bSpd, vals[3], 255);
        });
        new Thread(statsTask) {{ setDaemon(true); }}.start();

        // -- Derecha: evoluciones en negro --
        HBox evolRow = new HBox(8);
        evolRow.setAlignment(Pos.CENTER);
        Label cargando = new Label("Cargando...");
        cargando.setStyle("-fx-text-fill: rgba(255,255,255,0.3); -fx-font-size: 11px;");
        evolRow.getChildren().add(cargando);
        cargarCadenaEvolucionOscura(id, colorTipo, evolRow);

        VBox evolBox = new VBox(7, labelMiniSeccion("🔮 Evoluciones"), evolRow);
        evolBox.setAlignment(Pos.CENTER);
        evolBox.setMinWidth(220);

        // -- Boton elegir --
        Button btn = crearBotonPrincipal("¡Elegir!", colorTipo, "#111");
        btn.setOnAction(e -> mostrarDetallePokemon(p));

        // -- Tarjeta horizontal --
        HBox card = new HBox(20, izquierda, separadorVertical(), statsBox,
                separadorVertical(), evolBox, separadorVertical(), btn);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(20, 28, 20, 28));
        card.setStyle(String.format("""
                -fx-background-color: rgba(255,255,255,0.07);
                -fx-background-radius: 20;
                -fx-border-color: %s66;
                -fx-border-width: 1.5;
                -fx-border-radius: 20;
                """, colorTipo));
        card.setOnMouseEntered(ev -> card.setStyle(String.format("""
                -fx-background-color: rgba(255,255,255,0.12);
                -fx-background-radius: 20;
                -fx-border-color: %s;
                -fx-border-width: 2;
                -fx-border-radius: 20;
                -fx-effect: dropshadow(gaussian, %s, 20, 0.25, 0, 0);
                """, colorTipo, colorTipo)));
        card.setOnMouseExited(ev -> card.setStyle(String.format("""
                -fx-background-color: rgba(255,255,255,0.07);
                -fx-background-radius: 20;
                -fx-border-color: %s66;
                -fx-border-width: 1.5;
                -fx-border-radius: 20;
                """, colorTipo)));
        return card;
    }

    private javafx.scene.shape.Rectangle separadorVertical() {
        javafx.scene.shape.Rectangle r = new javafx.scene.shape.Rectangle(1, 90);
        r.setFill(Color.rgb(255, 255, 255, 0.1));
        return r;
    }

    private Label labelMiniSeccion(String texto) {
        Label l = new Label(texto);
        l.setStyle("-fx-text-fill: rgba(255,255,255,0.5); -fx-font-size: 11px; -fx-font-weight: bold;");
        return l;
    }

    private void cargarCadenaEvolucionOscura(int pokemonId, String colorTipo, HBox contenedor) {
        Task<ArrayList<int[]>> task = new Task<>() {
            @Override
            protected ArrayList<int[]> call() throws Exception {
                String especieJson = fetchString(POKEAPI_BASE + "pokemon-species/" + pokemonId + "/");
                String urlCadena   = extraerValorCadena(especieJson, "evolution_chain", "url");
                String cadenaJson  = fetchString(urlCadena);
                ArrayList<int[]> resultado = new ArrayList<>();
                recorrerCadenaTexto(cadenaJson, resultado);
                return resultado;
            }
        };
        task.setOnSucceeded(e -> {
            ArrayList<int[]> evoluciones = task.getValue();
            Platform.runLater(() -> {
                contenedor.getChildren().clear();
                for (int i = 0; i < evoluciones.size(); i++) {
                    int evoId = evoluciones.get(i)[0];
                    // El Pokémon elegido (pokemonId) se ve con color; el resto en negro
                    boolean esElegido = (evoId == pokemonId);
                    contenedor.getChildren().add(nodoEvolucionSeleccion(evoId, esElegido, colorTipo));
                    if (i < evoluciones.size() - 1) {
                        Label flecha = new Label("→");
                        flecha.setStyle("-fx-text-fill: rgba(255,255,255,0.3); -fx-font-size: 18px;");
                        contenedor.getChildren().add(flecha);
                    }
                }
            });
        });
        task.setOnFailed(e -> Platform.runLater(() -> {
            contenedor.getChildren().clear();
            Label err = new Label("Sin datos");
            err.setStyle("-fx-text-fill: rgba(255,100,100,0.5); -fx-font-size: 11px;");
            contenedor.getChildren().add(err);
        }));
        new Thread(task) {{ setDaemon(true); }}.start();
    }

    private VBox nodoEvolucionSeleccion(int id, boolean esBase, String colorTipo) {
        VBox nodo = new VBox(4);
        nodo.setAlignment(Pos.CENTER);
        ImageView iv = cargarSprite(id, 64);
        if (esBase) {
            Label lId = new Label("#" + id);
            lId.setStyle(String.format(
                    "-fx-text-fill: %s; -fx-font-size: 11px; -fx-font-weight: bold;", colorTipo));
            nodo.getChildren().addAll(iv, lId);
        } else {
            ColorAdjust sil = new ColorAdjust();
            sil.setBrightness(-1.0);
            sil.setSaturation(-1.0);
            iv.setEffect(sil);
            Label misterio = new Label("???");
            misterio.setStyle("-fx-text-fill: rgba(255,255,255,0.25); -fx-font-size: 11px; -fx-font-weight: bold;");
            nodo.getChildren().addAll(iv, misterio);
        }
        return nodo;
    }


    // -----------------------------------------------------------------------
    //  Pantalla: detalle del Pokémon elegido (MEJORADA)
    // -----------------------------------------------------------------------

    private void mostrarDetallePokemon(Pokémon p) {
        content.getChildren().clear();

        String colorTipo = colorDeTipo(p.getTipoPrincipal().name());
        int id = getPokemonId(p);

        // ── Cabecera ──────────────────────────────────────────────────────
        VBox header = new VBox(8);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20, 20, 10, 20));

        Label elegido = new Label("¡Has elegido a...");
        elegido.setStyle("-fx-text-fill: rgba(255,255,255,0.6); -fx-font-size: 15px;");

        Label nombre = new Label(p.getNombre().toUpperCase());
        nombre.setStyle(String.format("""
                -fx-text-fill: %s;
                -fx-font-size: 34px;
                -fx-font-weight: bold;
                -fx-font-family: 'Georgia';
                -fx-effect: dropshadow(gaussian, %s, 14, 0.5, 0, 0);
                """, colorTipo, colorTipo));

        ImageView sprite = cargarSprite(id, 140);

        header.getChildren().addAll(elegido, nombre, sprite, etiquetaTipo(p.getTipoPrincipal().name()));

        // ── Estadísticas con barras ──────────────────────────────────────
        VBox statsCard = crearTarjetaStats(p, colorTipo, id);

        // ── Ataques ───────────────────────────────────────────────────────
        VBox ataquesCard = crearTarjetaAtaques(p, colorTipo);

        HBox infoRow = new HBox(16, statsCard, ataquesCard);
        infoRow.setAlignment(Pos.CENTER);


        // ── Botón reiniciar ────────────────────────────────────────────────
        Button btnReiniciar = crearBotonPrincipal("🔄  Reiniciar aventura", colorTipo, "#111");
        btnReiniciar.setOnAction(e -> mostrarPantallaInicio());

        content.getChildren().addAll(header, infoRow, btnReiniciar);
    }

    // -----------------------------------------------------------------------
    //  Carga asíncrona de la cadena de evolución desde PokéAPI
    // -----------------------------------------------------------------------

    /**
     * Obtiene la cadena evolutiva desde la PokéAPI en un hilo secundario
     * y actualiza la UI en el hilo de JavaFX.
     *
     * @param pokemonId    ID numérico del Pokémon elegido
     * @param nombreElegido nombre del Pokémon base (el que "ya se conoce")
     * @param colorTipo    color hex del tipo principal
     * @param contenedor   HBox donde insertar los nodos visuales
     */
    private void cargarCadenaEvolucion(int pokemonId, String nombreElegido,
                                       String colorTipo, HBox contenedor) {
        Task<ArrayList<int[]>> task = new Task<>() {
            @Override
            protected ArrayList<int[]> call() throws Exception {
                // 1) Obtener especie y extraer la URL de la cadena evolutiva
                String especieJson = fetchString(POKEAPI_BASE + "pokemon-species/" + pokemonId + "/");
                String urlCadena = extraerValorCadena(especieJson, "evolution_chain", "url");

                // 2) Obtener la cadena evolutiva
                String cadenaJson = fetchString(urlCadena);

                // 3) Recorrer el bloque "chain" y extraer IDs en orden
                ArrayList<int[]> resultado = new ArrayList<>();
                recorrerCadenaTexto(cadenaJson, resultado);
                return resultado;
            }
        };

        task.setOnSucceeded(e -> {
            ArrayList<int[]> evoluciones = task.getValue();
            contenedor.getChildren().clear();

            boolean primeroPasado = false; // ¿Ya encontramos el Pokémon elegido?

            for (int i = 0; i < evoluciones.size(); i++) {
                int[] entry = evoluciones.get(i);
                int evoId        = entry[0];
                boolean esElegido = (evoId == pokemonId);

                if (esElegido) primeroPasado = true;

                // Nodo de evolución
                VBox nodo = crearNodoEvolucion(evoId, esElegido, primeroPasado && !esElegido, colorTipo);
                contenedor.getChildren().add(nodo);

                // Flecha entre nodos
                if (i < evoluciones.size() - 1) {
                    Label flecha = new Label("→");
                    flecha.setStyle("-fx-text-fill: rgba(255,255,255,0.4); -fx-font-size: 22px;");
                    contenedor.getChildren().add(flecha);
                }
            }
        });

        task.setOnFailed(e -> Platform.runLater(() -> {
            contenedor.getChildren().clear();
            Label err = new Label("No se pudo cargar la cadena de evolución.");
            err.setStyle("-fx-text-fill: rgba(255,100,100,0.7); -fx-font-size: 12px;");
            contenedor.getChildren().add(err);
        }));

        Thread hilo = new Thread(task);
        hilo.setDaemon(true);
        hilo.start();
    }

    /**
     * Recorre el JSON de la cadena evolutiva como texto plano.
     * Busca todas las apariciones de "species" con su "url" en orden
     * y extrae el ID numérico de cada una.
     *
     * La PokéAPI devuelve los eslabones en orden base → intermedia → final,
     * así que basta con leer todas las URLs de species en orden de aparición.
     */
    private void recorrerCadenaTexto(String json, ArrayList<int[]> lista) {
        // Buscar todas las URLs de species en orden de aparición
        // Patrón: "species":{..."url":"https://pokeapi.co/api/v2/pokemon-species/N/"}
        Pattern pat = Pattern.compile(
                "\"species\"\\s*:\\s*\\{[^}]*\"url\"\\s*:\\s*\"([^\"]+)\"");
        Matcher m = pat.matcher(json);
        while (m.find()) {
            int id = extraerIdDeUrl(m.group(1));
            lista.add(new int[]{id});
        }
    }

    /**
     * Crea el nodo visual de un Pokémon en la cadena de evolución.
     *
     * @param id           ID del Pokémon
     * @param esElegido    true si es el Pokémon que el jugador eligió
     * @param esMisterioso true si es una evolución aún no descubierta (se muestra en negro)
     * @param colorTipo    color del tipo del Pokémon elegido
     */
    private VBox crearNodoEvolucion(int id, boolean esElegido, boolean esMisterioso, String colorTipo) {
        VBox nodo = new VBox(6);
        nodo.setAlignment(Pos.CENTER);
        nodo.setPadding(new Insets(10));
        nodo.setPrefWidth(110);

        if (esMisterioso) {
            // ── Silueta negra (Pokémon desconocido) ──────────────────────
            nodo.setStyle("""
                    -fx-background-color: rgba(0,0,0,0.35);
                    -fx-background-radius: 14;
                    -fx-border-color: rgba(255,255,255,0.1);
                    -fx-border-width: 1.5;
                    -fx-border-radius: 14;
                    """);

            // Sprite con efecto de silueta negra
            ImageView iv = cargarSprite(id, 80);
            ColorAdjust silhouette = new ColorAdjust();
            silhouette.setBrightness(-1.0); // completamente negro
            silhouette.setSaturation(-1.0);
            iv.setEffect(silhouette);

            Label misterio = new Label("???");
            misterio.setStyle("""
                    -fx-text-fill: rgba(255,255,255,0.3);
                    -fx-font-size: 14px;
                    -fx-font-weight: bold;
                    -fx-font-family: 'Georgia';
                    """);

            Label noDesc = new Label("No descubierto");
            noDesc.setStyle("-fx-text-fill: rgba(255,255,255,0.2); -fx-font-size: 10px;");

            nodo.getChildren().addAll(iv, misterio, noDesc);

        } else {
            // ── Pokémon conocido (elegido o pre-evoluciones) ──────────────
            String borde = esElegido ? colorTipo : "rgba(255,255,255,0.2)";
            String glow  = esElegido
                    ? String.format("-fx-effect: dropshadow(gaussian, %s, 16, 0.5, 0, 0);", colorTipo)
                    : "";

            nodo.setStyle(String.format("""
                    -fx-background-color: rgba(255,255,255,0.07);
                    -fx-background-radius: 14;
                    -fx-border-color: %s;
                    -fx-border-width: %s;
                    -fx-border-radius: 14;
                    %s
                    """, borde, esElegido ? "2" : "1", glow));

            ImageView iv = cargarSprite(id, 80);

            // Nombre obtenido desde la caché o PokéAPI
            Label lNombre = new Label("#" + id);
            lNombre.setStyle(String.format("-fx-text-fill: %s; -fx-font-size: 12px; -fx-font-weight: bold;",
                    esElegido ? colorTipo : "rgba(255,255,255,0.7)"));

            if (esElegido) {
                Label badge = new Label("✦ Tu Pokémon");
                badge.setStyle(String.format("""
                        -fx-text-fill: %s;
                        -fx-font-size: 10px;
                        -fx-background-color: rgba(255,255,255,0.1);
                        -fx-background-radius: 8;
                        -fx-padding: 2 8 2 8;
                        """, colorTipo));
                nodo.getChildren().addAll(iv, lNombre, badge);
            } else {
                nodo.getChildren().addAll(iv, lNombre);
            }
        }

        return nodo;
    }

    // -----------------------------------------------------------------------
    //  Tarjeta de estadísticas con barras de progreso
    // -----------------------------------------------------------------------

    /**
     * Crea la tarjeta de estadísticas base.
     * Las stats numéricas (PS, Ataque, Defensa, Velocidad) se obtienen
     * de la PokéAPI de forma asíncrona y se muestran con ProgressBar.
     */
    private VBox crearTarjetaStats(Pokémon p, String colorTipo, int id) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(18));
        card.setPrefWidth(270);
        card.setStyle(String.format("""
                -fx-background-color: rgba(255,255,255,0.07);
                -fx-background-radius: 16;
                -fx-border-color: %s55;
                -fx-border-width: 1.5;
                -fx-border-radius: 16;
                """, colorTipo));

        Label titulo = new Label("📊  Estadísticas");
        titulo.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        // Info estática
        card.getChildren().add(titulo);
        card.getChildren().add(filaStats("Generación",     p.getGeneracion().name(),     colorTipo));
        card.getChildren().add(filaStats("Tipo principal", p.getTipoPrincipal().name(),  colorTipo));
        card.getChildren().add(filaStats("Tipo sec.",      p.getTipoSecundario().name(), colorTipo));

        // Separador
        Label sep = new Label("─────────────────");
        sep.setStyle("-fx-text-fill: rgba(255,255,255,0.1); -fx-font-size: 10px;");
        card.getChildren().add(sep);

        // Barras de stats (se rellenan de forma asíncrona desde la API)
        Label lPS    = new Label("PS");
        Label vPS    = new Label("...");
        ProgressBar bPS    = crearBarra(colorTipo);

        Label lAtk   = new Label("Ataque");
        Label vAtk   = new Label("...");
        ProgressBar bAtk   = crearBarra(colorTipo);

        Label lDef   = new Label("Defensa");
        Label vDef   = new Label("...");
        ProgressBar bDef   = crearBarra(colorTipo);

        Label lSpd   = new Label("Velocidad");
        Label vSpd   = new Label("...");
        ProgressBar bSpd   = crearBarra(colorTipo);

        card.getChildren().addAll(
                filaStatsBarra(lPS,  vPS,  bPS),
                filaStatsBarra(lAtk, vAtk, bAtk),
                filaStatsBarra(lDef, vDef, bDef),
                filaStatsBarra(lSpd, vSpd, bSpd)
        );

        // Carga asíncrona de stats desde PokéAPI
        Task<int[]> statsTask = new Task<>() {
            @Override
            protected int[] call() throws Exception {
                String json = fetchString(POKEAPI_BASE + "pokemon/" + id + "/");
                // Extraer los base_stat en orden: hp, attack, defense, ..., speed
                // La API devuelve los stats en un array; los buscamos por nombre
                int hp      = extraerStatPorNombre(json, "hp");
                int attack  = extraerStatPorNombre(json, "attack");
                int defense = extraerStatPorNombre(json, "defense");
                int speed   = extraerStatPorNombre(json, "speed");
                return new int[]{hp, attack, defense, speed};
            }
        };

        statsTask.setOnSucceeded(e -> {
            int[] vals = statsTask.getValue();
            // Máximo referencial: 255 (máximo posible en la franquicia)
            actualizarBarra(vPS,  bPS,  vals[0], 255);
            actualizarBarra(vAtk, bAtk, vals[1], 255);
            actualizarBarra(vDef, bDef, vals[2], 255);
            actualizarBarra(vSpd, bSpd, vals[3], 255);
        });

        Thread t = new Thread(statsTask);
        t.setDaemon(true);
        t.start();

        return card;
    }

    /** Crea una ProgressBar estilizada con el color del tipo. */
    private ProgressBar crearBarra(String colorTipo) {
        ProgressBar bar = new ProgressBar(0);
        bar.setPrefWidth(150);
        bar.setPrefHeight(8);
        bar.setStyle(String.format("""
                -fx-accent: %s;
                -fx-control-inner-background: rgba(255,255,255,0.1);
                -fx-background-radius: 6;
                -fx-border-radius: 6;
                """, colorTipo));
        return bar;
    }

    /** Actualiza la barra y el label de valor una vez que llegan los datos. */
    private void actualizarBarra(Label valorLabel, ProgressBar bar, int valor, int maximo) {
        Platform.runLater(() -> {
            valorLabel.setText(String.valueOf(valor));
            bar.setProgress((double) valor / maximo);
        });
    }

    /** Fila con nombre de stat, barra de progreso y valor numérico. */
    private HBox filaStatsBarra(Label lClave, Label lValor, ProgressBar barra) {
        lClave.setStyle("-fx-text-fill: rgba(255,255,255,0.55); -fx-font-size: 12px;");
        lClave.setMinWidth(65);

        lValor.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold;");
        lValor.setMinWidth(30);

        HBox row = new HBox(8, lClave, barra, lValor);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    // -----------------------------------------------------------------------
    //  Tarjeta de ataques
    // -----------------------------------------------------------------------

    private VBox crearTarjetaAtaques(Pokémon p, String colorTipo) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(18));
        card.setPrefWidth(260);
        card.setStyle(String.format("""
                -fx-background-color: rgba(255,255,255,0.07);
                -fx-background-radius: 16;
                -fx-border-color: %s55;
                -fx-border-width: 1.5;
                -fx-border-radius: 16;
                """, colorTipo));

        Label titulo = new Label("⚔  Ataques");
        titulo.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        card.getChildren().add(titulo);

        String raw = p.mostrarAtaques()
                .replace("[", "").replace("]", "").trim();

        for (String ataque : raw.split(",")) {
            String nombreAtaque = ataque.replace("|", "").trim();
            if (!nombreAtaque.isEmpty()) {
                Label lAtaque = new Label("▸  " + nombreAtaque);
                lAtaque.setStyle(String.format("""
                        -fx-text-fill: %s;
                        -fx-font-size: 13px;
                        -fx-background-color: rgba(255,255,255,0.06);
                        -fx-background-radius: 8;
                        -fx-padding: 6 12 6 12;
                        """, colorTipo));
                card.getChildren().add(lAtaque);
            }
        }

        return card;
    }

    // -----------------------------------------------------------------------
    //  Componentes visuales de Pokémon (vista general)
    // -----------------------------------------------------------------------

    private VBox tarjetaPokemon(Pokémon p) {
        String colorTipo = colorDeTipo(p.getTipoPrincipal().name());
        int id = getPokemonId(p);

        VBox card = new VBox(6);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(12));
        card.setPrefWidth(140);
        card.setStyle(String.format("""
                -fx-background-color: rgba(255,255,255,0.07);
                -fx-background-radius: 14;
                -fx-border-color: %s44;
                -fx-border-width: 1.5;
                -fx-border-radius: 14;
                """, colorTipo));

        ImageView sprite = cargarSprite(id, 72);

        Label nombre = new Label(p.getNombre());
        nombre.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold;");

        Label ataques = new Label("⚔ " + p.mostrarAtaques()
                .replace("[", "").replace("]", "")
                .replace("|", "").trim());
        ataques.setWrapText(true);
        ataques.setMaxWidth(130);
        ataques.setStyle("-fx-text-fill: rgba(255,255,255,0.6); -fx-font-size: 11px;");

        card.getChildren().addAll(sprite, nombre, etiquetaTipo(p.getTipoPrincipal().name()), ataques);
        return card;
    }

    // -----------------------------------------------------------------------
    //  Utilidades: red, IDs, colores
    // -----------------------------------------------------------------------

    // -----------------------------------------------------------------------
    //  Utilidades: red y parsing manual de JSON (sin dependencias externas)
    // -----------------------------------------------------------------------

    /**
     * Hace una petición HTTP GET y devuelve el cuerpo completo como String.
     * Se llama siempre desde un hilo secundario (Task).
     */
    private String fetchString(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(6000);
        conn.setReadTimeout(6000);

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        return sb.toString();
    }

    /**
     * Extrae el valor de una clave de tipo String dentro de un objeto anidado.
     * Ejemplo: extraerValorCadena(json, "evolution_chain", "url")
     * Busca: "evolution_chain":{"url":"VALOR",...}
     */
    private String extraerValorCadena(String json, String objetoPadre, String clave) {
        // Localizar el bloque del objeto padre
        int idx = json.indexOf("\"" + objetoPadre + "\"");
        if (idx == -1) return "";
        // Dentro de ese bloque buscar la clave
        int desde = json.indexOf("{", idx);
        int hasta = json.indexOf("}", desde);
        String bloque = json.substring(desde, hasta + 1);
        return extraerString(bloque, clave);
    }

    /**
     * Extrae el valor String de una clave simple en un fragmento JSON plano.
     * Ejemplo: extraerString(json, "url") → "https://..."
     */
    private String extraerString(String json, String clave) {
        Pattern p = Pattern.compile("\"" + clave + "\"\\s*:\\s*\"([^\"]+)\"");
        Matcher m = p.matcher(json);
        return m.find() ? m.group(1) : "";
    }

    /**
     * Extrae el base_stat de un stat concreto buscando su nombre en el JSON.
     * La PokéAPI devuelve stats así:
     * {"base_stat":45,"effort":0,"stat":{"name":"hp","url":"..."}}
     */
    private int extraerStatPorNombre(String json, String nombreStat) {
        // Buscar el bloque que contiene el nombre del stat
        Pattern p = Pattern.compile(
                "\"base_stat\"\\s*:\\s*(\\d+)[^}]+\"name\"\\s*:\\s*\"" + nombreStat + "\"");
        Matcher m = p.matcher(json);
        if (m.find()) return Integer.parseInt(m.group(1));

        // La API a veces pone el "name" antes que "base_stat"; segundo intento
        Pattern p2 = Pattern.compile(
                "\"name\"\\s*:\\s*\"" + nombreStat + "\"[^{]*\\{[^}]*\"base_stat\"\\s*:\\s*(\\d+)");
        Matcher m2 = p2.matcher(json);
        if (m2.find()) return Integer.parseInt(m2.group(1));

        return 0;
    }

    /**
     * Extrae el ID numérico del final de una URL de la PokéAPI.
     * Ejemplo: "https://pokeapi.co/api/v2/pokemon-species/4/" → 4
     */
    private int extraerIdDeUrl(String url) {
        String[] partes = url.split("/");
        return Integer.parseInt(partes[partes.length - 1]);
    }

    /** Carga el sprite con caché. Si ya fue descargado, lo reutiliza. */
    private ImageView cargarSprite(int id, int size) {
        ImageView iv = new ImageView();
        iv.setFitWidth(size);
        iv.setFitHeight(size);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);

        if (id > 0) {
            if (IMAGE_CACHE.containsKey(id)) {
                iv.setImage(IMAGE_CACHE.get(id));
            } else {
                try {
                    Image img = new Image(SPRITE_BASE + id + ".png", size, size, true, true, true);
                    IMAGE_CACHE.put(id, img);
                    iv.setImage(img);
                } catch (Exception ignored) {}
            }
        }
        return iv;
    }

    /** Mapa nombre → ID numérico de la PokéAPI. */
    private int getPokemonId(Pokémon p) {
        Map<String, Integer> ids = new HashMap<>() {{
            put("Bulbasur", 1);  put("Bulbasaur", 1);
            put("Charmander", 4);
            put("Squirtle", 7);
            put("Treecko", 252);
            put("Torchic", 255);
            put("Mudkip", 258);
            put("Snivy", 495);
            put("Tepig", 498);
            put("Oshawott", 501);
        }};
        return ids.getOrDefault(p.getNombre(), 0);
    }

    /** Etiqueta de tipo con fondo de color. */
    private Label etiquetaTipo(String tipo) {
        String color = colorDeTipo(tipo);
        Label l = new Label(tipo);
        l.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-text-fill: white;
                -fx-font-size: 11px;
                -fx-font-weight: bold;
                -fx-padding: 3 10 3 10;
                -fx-background-radius: 20;
                """, color));
        return l;
    }

    private String colorDeTipo(String tipo) {
        return TYPE_COLORS.getOrDefault(tipo.toUpperCase(), "#888888");
    }

    private Label labelTitulo(String texto) {
        Label l = new Label(texto);
        l.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 26px;
                -fx-font-weight: bold;
                -fx-font-family: 'Georgia';
                """);
        return l;
    }

    private Label labelSeccion(String texto) {
        Label l = new Label(texto);
        l.setStyle("-fx-text-fill: rgba(255,255,255,0.55); -fx-font-size: 14px;");
        return l;
    }

    private HBox filaStats(String clave, String valor, String colorTipo) {
        Label lClave = new Label(clave + ":");
        lClave.setStyle("-fx-text-fill: rgba(255,255,255,0.5); -fx-font-size: 13px;");
        lClave.setMinWidth(120);

        Label lValor = new Label(valor);
        lValor.setStyle(String.format("-fx-text-fill: %s; -fx-font-size: 13px; " +
                "-fx-font-weight: bold;", colorTipo));

        HBox row = new HBox(8, lClave, lValor);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private Button crearBotonPrincipal(String texto, String bgColor, String textColor) {
        Button btn = new Button(texto);
        btn.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-text-fill: %s;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 24;
                -fx-padding: 10 26 10 26;
                -fx-cursor: hand;
                """, bgColor, textColor));
        btn.setOnMouseEntered(e -> btn.setStyle(String.format("""
                -fx-background-color: derive(%s, 20%%);
                -fx-text-fill: %s;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 24;
                -fx-padding: 10 26 10 26;
                -fx-cursor: hand;
                -fx-effect: dropshadow(gaussian, %s, 12, 0.3, 0, 0);
                """, bgColor, textColor, bgColor)));
        btn.setOnMouseExited(e -> btn.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-text-fill: %s;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 24;
                -fx-padding: 10 26 10 26;
                -fx-cursor: hand;
                """, bgColor, textColor)));
        return btn;
    }

    private Button crearBotonVolver(Runnable accion) {
        Button btn = crearBotonPrincipal("← Volver", "rgba(255,255,255,0.12)", "white");
        btn.setOnAction(e -> accion.run());
        return btn;
    }

    // -----------------------------------------------------------------------
    //  Main
    // -----------------------------------------------------------------------

    public static void main(String... args) {
        launch(args);
    }
}