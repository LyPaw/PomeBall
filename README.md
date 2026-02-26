```
                                            ,'\
              _.----.        ____         ,'  _\   ___    ___     ____
          _,-'       `.     |    |  /`.   \,-'    |   \  /   |   |    \  |`.
          \      __    \    '-.  | /   `.  ___    |    \/    |   '-.   \ |  |
           \.    \ \   |  __  |  |/    ,','_  `.  |          | __  |    \|  |
             \    \/   /,' _`.|      ,' / / / /   |          ,' _`.|     |  |
              \     ,-'/  /   \    ,'   | \/ / ,`.|         /  /   \  |     |
               \    \ |   \_/  |   `-.  \    `'  /|  |    ||   \_/  | |\    |
                \    \ \      /       `-.`.___,-' |  |\  /| \      /  | |   |
                 \    \ `.__,'|  |`-._    `|      |__| \/ |  `.__,'|  | |   |
                  \_.-'       |__|    `-._ |              '-.|     '-.| |   |
                                          `'                            '-._|
                         ."-,.__
                         `.     `.  ,
                      .--'  .._,'"-' `.
                     .    .'         `'
                     `.   /          ,'
                       `  '--.   ,-"'
                        `"`   |  \
                           -. \, |
                            `--Y.'      ___.
                                 \     L._, \
                       _.,        `.   <  <\                _
                     ,' '           `, `.   | \            ( `
                  ../, `.            `  |    .\`.           \ \_
                 ,' ,..  .           _.,'    ||\l            )  '".
                , ,'   \           ,'.-.`-._,'  |           .  _._`.
              ,' /      \ \        `' ' `--/   | \          / /   ..\
            .'  /        \ .         |\__ - _ ,'` `        / /     `.`.
            |  '          ..         `-...-"  |  `-'      / /        . `.
            | /           |L__           |    |          / /          `. `.
           , /            .   .          |    |         / /             ` `
          / /          ,. ,`._ `-_       |    |  _   ,-' /               ` \
         / .           \"`_/. `-_ \_,.  ,'    +-' `-'  _,        ..,-.    \`.
        .  '         .-f    ,'   `    '.       \__.---'     _   .'   '     \ \
        ' /          `.'    l     .' /          \..      ,_|/   `.  ,'`     L`
        |'      _.-""` `.    \ _,'  `            \ `.___`.'"`-.  , |   |    | \
        ||    ,'      `. `.   '       _,...._        `  |    `/ '  |   '     .|
        ||  ,'          `. ;.,.---' ,'       `.   `.. `-'  .-' /_ .'    ;_   ||
        || '              V      / /           `   | `   ,'   ,' '.    !  `. ||
        ||/            _,-------7 '              . |  `-'    l         /    `||
        . |          ,' .-   ,' ||               | .-.        `.      .'     ||
         `'        ,'    `".'    |               |    `.        '. -.'       `'
                  /      ,'      |               |,'    \-.._,.'/'
                  .     /        .               .       \    .''
                .`.    |         `.             /         :_,'.'
                  \ `...\   _     ,'-.        .'         /_.-'
                   `-.__ `,  `'   .  _.>----''.  _  __  /
                        .'        /"'          |  "'   '_
                       /_|.-'\ ,".             '.'`__'-( \
                         / ,"'"\,'               `/  `-.|" mh
```

<div align="center">

# Proyecto Pokémon

*Un juego Pokémon desarrollado en Java, con transición a JavaFX*

[![License: CC BY-NC 4.0](https://img.shields.io/badge/License-CC%20BY--NC%204.0-lightgrey)](https://creativecommons.org/licenses/by-nc/4.0/)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-23-blue?logo=java&logoColor=white)
![Estado](https://img.shields.io/badge/Estado-En%20desarrollo-yellow)
[![Pokédex Web](https://img.shields.io/badge/Pok%C3%A9dex-Ver%20web-%23e94560?style=flat&logo=github)](https://LyPaw.github.io/PomeBall)

</div>

---

## Descripción

**Proyecto Pokémon** es un juego por consola desarrollado en Java puro, actualmente en transición hacia una interfaz gráfica con **JavaFX**. El proyecto simula mecánicas clásicas de la saga Pokémon: elección de Pokémon inicial, sistema de ataques con PP, gestión del equipo del entrenador, inventario de ítems y captura de Pokémon salvajes.

---

## Estructura del proyecto

```
PokemonProject/
├── src/
│   ├── MainApp.java           ← Punto de entrada JavaFX
│   ├── Pokémon.java           ← Modelo base de Pokémon
│   ├── Ataque.java            ← Sistema de ataques y PP
│   ├── Entrenador.java        ← Lógica del entrenador
│   ├── EquipoPokemon.java     ← Gestión del equipo (máx. 6)
│   ├── Inventario.java        ← Inventario de ítems
│   ├── Item.java              ← Modelo de ítem
│   ├── Pokeball.java          ← Lógica de captura
│   ├── CreadorPokemons.java   ← Inicialización de Pokémon por generación
│   ├── TipoPokemon.java       ← Enum de tipos
│   ├── TipoItem.java          ← Enum de tipos de ítem
│   └── Generacion.java        ← Enum de generaciones
└── docs/
    ├── index.html             ← Pokédex web (GitHub Pages)
    └── pokemons.json          ← Datos de Pokémon disponibles
```

---

## Pokémon disponibles

<table>
  <tbody>
    <tr>
      <td>001</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/001.gif" width="64"/></td>
      <td>002</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/002.gif" width="94"/></td>
      <td>003</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/003.gif" width="94"/></td>
      <td>004</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/004.gif" width="65"/></td>
      <td>005</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/005.gif" width="64"/></td>   
    </tr>
    <tr>
      <td>006</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/006.gif" width="80"/></td>
      <td>007</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/007.gif" width="76"/></td>
      <td>008</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/008.gif" width="64"/></td>
      <td>009</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/009.gif" width="85"/></td>
      <td>010</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/010.gif" width="72"/></td>
    </tr>
    <tr>
      <td>011</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/011.gif" width="64"/></td>
      <td>012</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/012.gif" width="85"/></td>
      <td>013</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/013.gif" width="56"/></td>
      <td>014</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/014.gif" width="50"/></td>
      <td>015</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/015.gif" width="70"/></td>
    </tr>
    <tr>
      <td>016</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/016.gif" width="64"/></td>
      <td>017</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/017.gif" width="95"/></td>
      <td>018</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/018.gif" width="95"/></td>
      <td>019</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/019.gif" width="64"/></td>
      <td>020</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/020.gif" width="85"/></td>
    </tr>
     <tr>
      <td>021</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/021.gif" width="50"/></td>
      <td>022</td><td><img src="https://raw.githubusercontent.com/LyPaw/PomeBall/main/docs/spriteV/022.gif" width="100"/></td>
    </tr>
  </tbody>
</table>

> 🌐 Consulta la [Pokédex web](https://LyPaw.github.io/PomeBall) para ver los datos.

---

## Equipo

<div align="center">
<table>
<tr>
  <td align="center">
    <a href="https://github.com/LyPaw">
      <img src="https://github.com/LyPaw.png?size=100" width="80" style="border-radius:50%"/><br>
      <sub><b>LyPaw</b></sub>
    </a><br>
    <sub>Dev</sub>
  </td>
  <td align="center">
    <a href="https://github.com/CodeDiegoF">
      <img src="https://github.com/CodeDiegoF.png?size=100" width="80" style="border-radius:50%"/><br>
      <sub><b>CodeDiegoF</b></sub>
    </a><br>
    <sub>Dev</sub>
  </td>
  <td align="center">
    <a href="https://github.com/EvoXgamer-14">
      <img src="https://github.com/EvoXgamer-14.png?size=100" width="80" style="border-radius:50%"/><br>
      <sub><b>EvoXgamer</b></sub>
    </a><br>
    <sub>Dev</sub>
  </td>
  <td align="center">
    <a href="https://github.com/FranJFM">
      <img src="https://github.com/FranJFM.png?size=100" width="80" style="border-radius:50%"/><br>
      <sub><b>FranJFM</b></sub>
    </a><br>
    <sub>Dev</sub>
  </td>
  <td align="center">
    <a href="https://github.com/Adrian190907">
      <img src="https://github.com/Adrian190907.png?size=100" width="80" style="border-radius:50%"/><br>
      <sub><b>Adrian190907</b></sub>
    </a><br>
    <sub>Dev</sub>
  </td>
</tr>
</table>
</div>

---

## 📋 Roadmap

- [x] Modelo base de Pokémon, Ataque y Entrenador
- [x] Sistema de generaciones y selección de inicial
- [x] Sistema de PP en ataques
- [x] Inventario y captura de Pokémon
- [x] Pokédex web con GitHub Pages
- [ ] Interfaz gráfica con JavaFX
- [ ] Pantalla de selección de Pokémon inicial
- [ ] Sistema de combate por turnos
- [ ] Mapa y navegación entre zonas

---

<div align="center">

*Proyecto sin fines comerciales · Pokémon es propiedad de Nintendo / Game Freak / Creatures Inc.*

[![License: CC BY-NC 4.0](https://img.shields.io/badge/License-CC%20BY--NC%204.0-lightgrey)](https://creativecommons.org/licenses/by-nc/4.0/)

</div>
