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

# PomeBall Pokédex

*Una Pokédex web interactiva con sprites animados, filtros y datos completos*

[![License: CC BY-NC 4.0](https://img.shields.io/badge/License-CC%20BY--NC%204.0-lightgrey)](https://creativecommons.org/licenses/by-nc/4.0/)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=black)
![Estado](https://img.shields.io/badge/Estado-En%20desarrollo-yellow)
[![Pokédex Web](https://img.shields.io/badge/Pok%C3%A9dex-Ver%20web-%23e94560?style=flat&logo=github)](https://LyPaw.github.io/PomeBall)

</div>

---

## Descripción

**PomeBall Pokédex** es una Pokédex web estática desarrollada con HTML, CSS y JavaScript puro. Permite explorar los Pokémon disponibles con sus sprites animados, estadísticas, tipos, ataques, perfiles y curiosidades. Incluye filtros por generación y tipo, búsqueda por nombre o número, modo shiny, tema claro/oscuro y un diseño retro inspirado en las Game Boy.

---

## Estructura del proyecto

```
PomeBall/
└── docs/
    ├── index.html          ← Pokédex web (GitHub Pages)
    ├── pokemons.json       ← Datos de Pokémon disponibles
    ├── perfiles.json       ← Altura, peso, especie y descripción
    ├── curiosidades.json   ← Datos curiosos por Pokémon
    ├── sprite_sizes.json   ← Tamaños personalizados de sprites
    └── spriteV/            ← Sprites animados (.gif) y recursos visuales
```

---

## 🔍 Pokémon Disponibles

Esta sección detalla los registros desde Kanto hasta los inicios de Hoenn (#001 - #300). Para ver sprites animados y datos técnicos, visita la [Pokédex Web](https://LyPaw.github.io/PomeBall).

---

### 🌿 Generación I (Kanto)

| Nº | Nombre | Tipo(s) |
| :--- | :--- | :--- |
| 001-003 | **Bulbasaur / Ivysaur / Venusaur** | ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) |
| 004-006 | **Charmander / Charmeleon / Charizard** | ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 007-009 | **Squirtle / Wartortle / Blastoise** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) |
| 010-012 | **Caterpie / Metapod / Butterfree** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 013-015 | **Weedle / Kakuna / Beedrill** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) |
| 016-018 | **Pidgey / Pidgeotto / Pidgeot** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 019-020 | **Rattata / Raticate** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 021-022 | **Spearow / Fearow** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 023-024 | **Ekans / Arbok** | ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) |
| 025-026 | **Pikachu / Raichu** | ![Eléctrico](https://img.shields.io/badge/Eléctrico-F7D02C?style=flat-square) |
| 027-028 | **Sandshrew / Sandslash** | ![Tierra](https://img.shields.io/badge/Tierra-E2BF65?style=flat-square) |
| 029-034 | **Línea Nidoran / Queen / King** | ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) ![Tierra](https://img.shields.io/badge/Tierra-E2BF65?style=flat-square) |
| 035-036 | **Clefairy / Clefable** | ![Hada](https://img.shields.io/badge/Hada-D685AD?style=flat-square) |
| 037-038 | **Vulpix / Ninetales** | ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) |
| 039-040 | **Jigglypuff / Wigglytuff** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) ![Hada](https://img.shields.io/badge/Hada-D685AD?style=flat-square) |
| 041-042 | **Zubat / Golbat** | ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 043-045 | **Oddish / Gloom / Vileplume** | ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) |
| 046-047 | **Paras / Parasect** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) |
| 048-049 | **Venonat / Venomoth** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) |
| 050-051 | **Diglett / Dugtrio** | ![Tierra](https://img.shields.io/badge/Tierra-E2BF65?style=flat-square) |
| 052-053 | **Meowth / Persian** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 054-055 | **Psyduck / Golduck** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) |
| 056-057 | **Mankey / Primeape** | ![Lucha](https://img.shields.io/badge/Lucha-C22E28?style=flat-square) |
| 058-059 | **Growlithe / Arcanine** | ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) |
| 060-062 | **Poliwag / Poliwhirl / Poliwrath** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Lucha](https://img.shields.io/badge/Lucha-C22E28?style=flat-square) |
| 063-065 | **Abra / Kadabra / Alakazam** | ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) |
| 066-068 | **Machop / Machoke / Machamp** | ![Lucha](https://img.shields.io/badge/Lucha-C22E28?style=flat-square) |
| 069-071 | **Bellsprout / Weepinbell / Victreebel** | ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) |
| 072-073 | **Tentacool / Tentacruel** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) |
| 074-076 | **Geodude / Graveler / Golem** | ![Roca](https://img.shields.io/badge/Roca-B6A136?style=flat-square) ![Tierra](https://img.shields.io/badge/Tierra-E2BF65?style=flat-square) |
| 077-078 | **Ponyta / Rapidash** | ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) |
| 079-080 | **Slowpoke / Slowbro** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) |
| 081-082 | **Magnemite / Magneton** | ![Eléctrico](https://img.shields.io/badge/Eléctrico-F7D02C?style=flat-square) ![Acero](https://img.shields.io/badge/Acero-B7B7CE?style=flat-square) |
| 083 | **Farfetch'd** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 084-085 | **Doduo / Dodrio** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 086-087 | **Seel / Dewgong** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Hielo](https://img.shields.io/badge/Hielo-96D9D6?style=flat-square) |
| 088-089 | **Grimer / Muk** | ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) |
| 090-091 | **Shellder / Cloyster** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Hielo](https://img.shields.io/badge/Hielo-96D9D6?style=flat-square) |
| 092-094 | **Gastly / Haunter / Gengar** | ![Fantasma](https://img.shields.io/badge/Fantasma-705746?style=flat-square) ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) |
| 095 | **Onix** | ![Roca](https://img.shields.io/badge/Roca-B6A136?style=flat-square) ![Tierra](https://img.shields.io/badge/Tierra-E2BF65?style=flat-square) |
| 096-097 | **Drowzee / Hypno** | ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) |
| 098-099 | **Krabby / Kingler** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) |
| 100-101 | **Voltorb / Electrode** | ![Eléctrico](https://img.shields.io/badge/Eléctrico-F7D02C?style=flat-square) |
| 102-103 | **Exeggcute / Exeggutor** | ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) |
| 104-105 | **Cubone / Marowak** | ![Tierra](https://img.shields.io/badge/Tierra-E2BF65?style=flat-square) |
| 106-107 | **Hitmonlee / Hitmonchan** | ![Lucha](https://img.shields.io/badge/Lucha-C22E28?style=flat-square) |
| 108 | **Lickitung** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 109-110 | **Koffing / Weezing** | ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) |
| 111-112 | **Rhyhorn / Rhydon** | ![Tierra](https://img.shields.io/badge/Tierra-E2BF65?style=flat-square) ![Roca](https://img.shields.io/badge/Roca-B6A136?style=flat-square) |
| 113 | **Chansey** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 114 | **Tangela** | ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) |
| 115 | **Kangaskhan** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 116-117 | **Horsea / Seadra** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) |
| 118-119 | **Goldeen / Seaking** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) |
| 120-121 | **Staryu / Starmie** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) |
| 122 | **Mr. Mime** | ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) ![Hada](https://img.shields.io/badge/Hada-D685AD?style=flat-square) |
| 123 | **Scyther** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 124 | **Jynx** | ![Hielo](https://img.shields.io/badge/Hielo-96D9D6?style=flat-square) ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) |
| 125 | **Electabuzz** | ![Eléctrico](https://img.shields.io/badge/Eléctrico-F7D02C?style=flat-square) |
| 126 | **Magmar** | ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) |
| 127 | **Pinsir** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) |
| 128 | **Tauros** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 129-130 | **Magikarp / Gyarados** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 131 | **Lapras** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Hielo](https://img.shields.io/badge/Hielo-96D9D6?style=flat-square) |
| 132 | **Ditto** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 133-136 | **Eevee & Evoluciones** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) |
| 137 | **Porygon** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 138-141 | **Omanyte / Kabuto Lines** | ![Roca](https://img.shields.io/badge/Roca-B6A136?style=flat-square) ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) |
| 142 | **Aerodactyl** | ![Roca](https://img.shields.io/badge/Roca-B6A136?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 143 | **Snorlax** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 144-146 | **Aves Legendarias** | ![Hielo](https://img.shields.io/badge/Hielo-96D9D6?style=flat-square) ![Eléctrico](https://img.shields.io/badge/Eléctrico-F7D02C?style=flat-square) ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) |
| 147-149 | **Dratini / Dragonair / Dragonite** | ![Dragón](https://img.shields.io/badge/Dragón-6F35FC?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 150-151 | **Mewtwo / Mew** | ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) |

---

### 🌊 Generación II (Johto)

| Nº | Nombre | Tipo(s) |
| :--- | :--- | :--- |
| 152-154 | **Chikorita / Bayleef / Meganium** | ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) |
| 155-157 | **Cyndaquil / Quilava / Typhlosion** | ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) |
| 158-160 | **Totodile / Croconaw / Feraligatr** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) |
| 161-164 | **Sentret / Hoothoot Lines** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 165-168 | **Ledyba / Spinarak Lines** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 169 | **Crobat** | ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 170-171 | **Chinchou / Lanturn** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Eléctrico](https://img.shields.io/badge/Eléctrico-F7D02C?style=flat-square) |
| 172-176 | **Pichu / Cleffa / Igglybuff / Togepi** | ![Hada](https://img.shields.io/badge/Hada-D685AD?style=flat-square) ![Eléctrico](https://img.shields.io/badge/Eléctrico-F7D02C?style=flat-square) |
| 177-178 | **Natu / Xatu** | ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 179-181 | **Mareep / Flaaffy / Ampharos** | ![Eléctrico](https://img.shields.io/badge/Eléctrico-F7D02C?style=flat-square) |
| 182 | **Bellossom** | ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) |
| 183-184 | **Marill / Azumarill** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Hada](https://img.shields.io/badge/Hada-D685AD?style=flat-square) |
| 185 | **Sudowoodo** | ![Roca](https://img.shields.io/badge/Roca-B6A136?style=flat-square) |
| 187-189 | **Hoppip / Skiploom / Jumpluff** | ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 190 | **Aipom** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 191-192 | **Sunkern / Sunflora** | ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) |
| 193 | **Yanma** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 194-195 | **Wooper / Quagsire** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Tierra](https://img.shields.io/badge/Tierra-E2BF65?style=flat-square) |
| 196-197 | **Espeon / Umbreon** | ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) ![Siniestro](https://img.shields.io/badge/Siniestro-705746?style=flat-square) |
| 198-199 | **Murkrow / Slowking** | ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) ![Siniestro](https://img.shields.io/badge/Siniestro-705746?style=flat-square) |
| 201 | **Unown** | ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) |
| 202 | **Wobbuffet** | ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) |
| 203 | **Girafarig** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) |
| 204-205 | **Pineco / Forretress** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Acero](https://img.shields.io/badge/Acero-B7B7CE?style=flat-square) |
| 206 | **Dunsparce** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 207-208 | **Gligar / Steelix** | ![Tierra](https://img.shields.io/badge/Tierra-E2BF65?style=flat-square) ![Acero](https://img.shields.io/badge/Acero-B7B7CE?style=flat-square) |
| 209-210 | **Snubbull / Granbull** | ![Hada](https://img.shields.io/badge/Hada-D685AD?style=flat-square) |
| 211-213 | **Qwilfish / Scizor / Shuckle** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Acero](https://img.shields.io/badge/Acero-B7B7CE?style=flat-square) |
| 214 | **Heracross** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Lucha](https://img.shields.io/badge/Lucha-C22E28?style=flat-square) |
| 215-217 | **Sneasel / Teddiursa / Ursaring** | ![Siniestro](https://img.shields.io/badge/Siniestro-705746?style=flat-square) ![Hielo](https://img.shields.io/badge/Hielo-96D9D6?style=flat-square) |
| 218-219 | **Slugma / Magcargo** | ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) ![Roca](https://img.shields.io/badge/Roca-B6A136?style=flat-square) |
| 220-221 | **Swinub / Piloswine** | ![Hielo](https://img.shields.io/badge/Hielo-96D9D6?style=flat-square) ![Tierra](https://img.shields.io/badge/Tierra-E2BF65?style=flat-square) |
| 222-224 | **Corsola / Remoraid / Octillery** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) |
| 225-227 | **Delibird / Mantine / Skarmory** | ![Acero](https://img.shields.io/badge/Acero-B7B7CE?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 228-230 | **Houndour / Houndoom / Kingdra** | ![Siniestro](https://img.shields.io/badge/Siniestro-705746?style=flat-square) ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) |
| 231-233 | **Phanpy / Donphan / Porygon2** | ![Tierra](https://img.shields.io/badge/Tierra-E2BF65?style=flat-square) |
| 234-235 | **Stantler / Smeargle** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 236-240 | **Tyrogue / Hitmontop / Babys** | ![Lucha](https://img.shields.io/badge/Lucha-C22E28?style=flat-square) |
| 241-242 | **Miltank / Blissey** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 243-245 | **Bestias Legendarias** | ![Eléctrico](https://img.shields.io/badge/Eléctrico-F7D02C?style=flat-square) ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) |
| 246-248 | **Larvitar / Pupitar / Tyranitar** | ![Roca](https://img.shields.io/badge/Roca-B6A136?style=flat-square) ![Siniestro](https://img.shields.io/badge/Siniestro-705746?style=flat-square) |
| 249-251 | **Lugia / Ho-Oh / Celebi** | ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) |

---

### 🏜️ Generación III (Hoenn)

| Nº | Nombre | Tipo(s) |
| :--- | :--- | :--- |
| 252-254 | **Treecko / Grovyle / Sceptile** | ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) |
| 255-257 | **Torchic / Combusken / Blaziken** | ![Fuego](https://img.shields.io/badge/Fuego-EE8130?style=flat-square) ![Lucha](https://img.shields.io/badge/Lucha-C22E28?style=flat-square) |
| 258-260 | **Mudkip / Marshtomp / Swampert** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Tierra](https://img.shields.io/badge/Tierra-E2BF65?style=flat-square) |
| 261-264 | **Poochyena / Zigzagoon Lines** | ![Siniestro](https://img.shields.io/badge/Siniestro-705746?style=flat-square) ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 265-269 | **Wurmple / Cascoon / Dustox Lines** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Veneno](https://img.shields.io/badge/Veneno-A33EA1?style=flat-square) |
| 270-275 | **Lotad / Seedot Lines** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) |
| 276-279 | **Taillow / Wingull Lines** | ![Agua](https://img.shields.io/badge/Agua-6390F0?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 280-282 | **Ralts / Kirlia / Gardevoir** | ![Psíquico](https://img.shields.io/badge/Psíquico-F95587?style=flat-square) ![Hada](https://img.shields.io/badge/Hada-D685AD?style=flat-square) |
| 283-284 | **Surskit / Masquerain** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Volador](https://img.shields.io/badge/Volador-A98FF3?style=flat-square) |
| 285-286 | **Shroomish / Breloom** | ![Planta](https://img.shields.io/badge/Planta-7AC74C?style=flat-square) ![Lucha](https://img.shields.io/badge/Lucha-C22E28?style=flat-square) |
| 287-289 | **Slakoth / Vigoroth / Slaking** | ![Normal](https://img.shields.io/badge/Normal-A8A77A?style=flat-square) |
| 290-292 | **Nincada / Ninjask / Shedinja** | ![Bicho](https://img.shields.io/badge/Bicho-A6B91A?style=flat-square) ![Fantasma](https://img.shields.io/badge/Fantasma-705746?style=flat-square) |
| 293-295 | **Whismur / Loudred / Exploud** | ![Normal](https://img.shields.io/badge/Norm

> 🌐 Consulta la [Pokédex web](https://LyPaw.github.io/PomeBall) para ver todos los datos.

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

- [x] Pokédex web desplegada en GitHub Pages
- [x] Sprites animados con fallback a imagen estática
- [x] Modo shiny en modal de detalle
- [x] Filtros por generación y tipo (hasta 2 tipos simultáneos)
- [x] Búsqueda por nombre y número
- [x] Perfiles: altura, peso, especie y descripción
- [x] Curiosidades por Pokémon
- [x] Estadísticas base con barras animadas
- [x] Tema claro / oscuro
- [x] Sonido de grito al abrir cada Pokémon
- [ ] Ampliar el Pokédex con más Pokémon y generaciones
- [ ] Añadir cadenas de evolución en el modal
- [ ] Comparador de estadísticas entre dos Pokémon
- [ ] Sección de información sobre ataques

---

<div align="center">

*Proyecto sin fines comerciales · Pokémon es propiedad de Nintendo / Game Freak / Creatures Inc.*

[![License: CC BY-NC 4.0](https://img.shields.io/badge/License-CC%20BY--NC%204.0-lightgrey)](https://creativecommons.org/licenses/by-nc/4.0/)

</div>
