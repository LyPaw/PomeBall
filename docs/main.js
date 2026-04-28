/* ══════════════════════════════════════════════════════
PomeBall — Lógica principal v2.0
══════════════════════════════════════════════════════ */

// Pokéball SVG en Base64 para usar como fallback
const POKEBALL_SVG = `data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAxMDAgMTAwIj48Y2lyY2xlIGN4PSI1MCIgY3k9IjUwIiByPSI0OCIgZmlsbD0iI2ZmZiIgc3Ryb2tlPSIjMzMzIiBzdHJva2Utd2lkdGg9IjQiLz48cGF0aCBkPSJNMCA1MCBhNTAgNTAgMCAwIDAgMTAwIDBaIiBmaWxsPSIjZmYwMDAwIi8+PGNpcmNsZSBjeD0iNTAiIGN5PSI1MCIgcj0iMTIiIGZpbGw9IiNmZmYiIHN0cm9rZT0iIzMzMyIgc3Ryb2tlLXdpZHRoPSI0Ii8+PGNpcmNsZSBjeD0iNTAiIGN5PSI1MCIgcj0iNiIgZmlsbD0iIzMzMyIvPjwvc3ZnPg==`;

// ══════════════════════════════════════════════════════
// TEMA (claro / oscuro)
// ══════════════════════════════════════════════════════
function toggleTheme() {
  const html = document.documentElement;
  const isDark = html.getAttribute('data-theme') === 'dark';
  html.setAttribute('data-theme', isDark ? 'light' : 'dark');
  document.getElementById('themeToggle').textContent = isDark ? '◐ TEMA' : '◑ TEMA';
  localStorage.setItem('pk-theme', isDark ? 'light' : 'dark');
}

const savedTheme = localStorage.getItem('pk-theme');
if (savedTheme) {
  document.documentElement.setAttribute('data-theme', savedTheme);
  if (savedTheme === 'dark') document.getElementById('themeToggle').textContent = '◑ TEMA';
}

// ══════════════════════════════════════════════════════
// HELPERS DE DATOS (Limpieza de espacios en claves)
// ══════════════════════════════════════════════════════
function cleanKeys(obj) {
  if (typeof obj !== 'object' || obj === null) return obj;
  if (Array.isArray(obj)) return obj.map(cleanKeys);
  return Object.fromEntries(
    Object.entries(obj).map(([k, v]) => [k.trim(), cleanKeys(v)])
  );
}

// ══════════════════════════════════════════════════════
// CARGA DE DATOS
// ══════════════════════════════════════════════════════
let allPokemons = [];
let PERFILES = {};
let CURIOSIDADES = {};
let SPRITE_SIZES = {};
let currentSearch = '';
let currentSearchId = '';
let activeGens = new Set();
let activeTipos = new Set();

async function loadPokemons() {
  try {
    const [pokemonsRes, perfilesRes, curiosidadesRes] = await Promise.all([
      fetch('pokemons.json'),
      fetch('perfiles.json'),
      fetch('curiosidades.json'),
    ]);

    const pokemonsData = await pokemonsRes.json();
    // Aplanar y limpiar datos
    const rawList = Array.isArray(pokemonsData) ? pokemonsData : Object.values(pokemonsData).flat();
    allPokemons = rawList.map(cleanKeys);

    PERFILES = cleanKeys(await perfilesRes.json());
    CURIOSIDADES = cleanKeys(await curiosidadesRes.json());

    try {
      const sizesRes = await fetch('sprite_sizes.json');
      if (sizesRes.ok) SPRITE_SIZES = cleanKeys(await sizesRes.json());
    } catch (_) { /* Ignorar si no existe */ }

    render();
  } catch (e) {
    console.error("Error cargando datos:", e);
    document.getElementById('grid').innerHTML = `
      <div class="gc-empty">
        <p>⚠️ ERROR AL CARGAR DATOS</p>
        <p>Revisa que tus archivos JSON no tengan comas finales sobrantes.</p>
      </div>`;
  }
}

// ══════════════════════════════════════════════════════
// SISTEMA DE SPRITES EN CASCADA
// ══════════════════════════════════════════════════════
function getShowdownUrl(id, name, isShiny) {
  // Showdown usa IDs estándar. Para formas custom (>10000) no tendrá sprite, caerá al fallback.
  const suffix = isShiny ? '-shiny' : '';
  return `https://play.pokemonshowdown.com/sprites/ani${suffix}/${id}.gif`;
}

function getPokeapiUrl(id, isShiny) {
  const suffix = isShiny ? '/shiny' : '';
  return `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon${suffix}/${id}.png`;
}

function setSpriteFallback(img, id, name, isShiny) {
  img.src = ''; // Limpiar previo
  img.classList.add('loading');
  
  // 1. Intento Showdown
  img.onerror = () => {
    // 2. Fallback PokéAPI (usando ID base si es posible, o el mismo ID)
    img.onerror = () => {
      // 3. Fallback Final: Pokéball
      img.onerror = null;
      img.classList.remove('loading');
      img.src = POKEBALL_SVG;
      img.style.filter = 'grayscale(0.5)'; // Estilo sutil para el placeholder
    };
    img.src = getPokeapiUrl(id, isShiny);
  };
  
  img.src = getShowdownUrl(id, name, isShiny);
  img.onload = () => {
    img.classList.remove('loading');
    img.style.filter = '';
  };
}

// ══════════════════════════════════════════════════════
// RENDER
// ══════════════════════════════════════════════════════
const TIPO_COLORS = {
  fuego:'#e05010', agua:'#2288CC', planta:'#338833', veneno:'#8844AA', normal:'#888860',
  electrico:'#BB9900', 'eléctrico':'#BB9900', tierra:'#AA7740', roca:'#887720',
  bicho:'#667700', fantasma:'#553377', acero:'#7788AA', hielo:'#5599AA',
  lucha:'#993322', psiquico:'#CC3366', 'psíquico':'#CC3366', 'dragón':'#5522CC',
  siniestro:'#554433', hada:'#BB5577',
};

function render() {
  const grid = document.getElementById('grid');
  const q = currentSearch.toLowerCase();
  
  const filtered = allPokemons.filter(p => {
    const matchSearch = !q || p.nombre.toLowerCase().includes(q);
    const matchId = !currentSearchId || p.id === parseInt(currentSearchId);
    const matchGen = activeGens.size === 0 || activeGens.has(p.generacion);
    const matchTipo = activeTipos.size === 0 || p.tipo.some(t => activeTipos.has(t));
    return matchSearch && matchId && matchGen && matchTipo;
  });

  document.getElementById('count').textContent = filtered.length;

  if (!filtered.length) {
    grid.innerHTML = '<div class="gc-empty">NO SE ENCONTRARON POKÉMON</div>';
    return;
  }

  let html = '';
  let separatorAdded = false;

  filtered.forEach((p, i) => {
    // Lógica del Separador Gráfico
    if (!separatorAdded && p.id > 1025) {
      html += `
        <div class="gc-separator">
          <span class="gc-separator-line"></span>
          <span class="gc-separator-text">✨ FORMAS Y VARIANTES ✨</span>
          <span class="gc-separator-line"></span>
        </div>`;
      separatorAdded = true;
    }

    const tiposBadges = p.tipo.map(t => 
      `<span class="gc-tipo" style="background:${TIPO_COLORS[t] || '#888'}">${t.toUpperCase()}</span>`
    ).join('');

    const ataquesTags = (p.ataques || []).map(a => `<span class="gc-ataque">${a}</span>`).join('');
    const nombreEscaped = (p.nombre).replace(/'/g, "\\'");

    html += `
      <div class="gc-card" data-pkid="${p.id}" style="animation-delay:${i * 0.04}s" onclick="openModalCard(this)">
        <div class="gc-card-bar">
          <span class="gc-card-num">NO.${String(p.id).padStart(3,'0')}</span>
          <span class="gc-card-gen">GEN ${p.generacion}</span>
        </div>
        <div class="gc-card-img">
          <img data-id="${p.id}" alt="${p.nombre}" src="" />
          <button class="gc-shiny-btn" title="Ver shiny" onclick="event.stopPropagation();toggleShiny(this,${p.id},'${nombreEscaped}')">✨</button>
          <div class="gc-sound">🔊</div>
        </div>
        <div class="gc-card-body">
          <div class="gc-card-name">${p.nombre}</div>
          <div class="gc-tipos">${tiposBadges}</div>
          <div class="gc-ataques">${ataquesTags}</div>
        </div>
        <div class="gc-region">${p.region || ''}</div>
      </div>`;
  });

  grid.innerHTML = html;

  // Cargar sprites tras inyectar HTML
  grid.querySelectorAll('img[data-id]').forEach(img => {
    const id = parseInt(img.dataset.id);
    const pokemon = allPokemons.find(x => x.id === id);
    const nombre = pokemon ? pokemon.nombre : '';
    
    // Aplicar tamaño custom si existe
    const sizes = SPRITE_SIZES[String(id)];
    if (sizes && sizes.card) {
      img.style.width = sizes.card + 'px';
      img.style.height = sizes.card + 'px';
    }
    
    setSpriteFallback(img, id, nombre, false);
  });
}

// ══════════════════════════════════════════════════════
// FILTROS Y BÚSQUEDA
// ══════════════════════════════════════════════════════
document.querySelectorAll('.gc-btn[data-filter^="gen:"]').forEach(btn => {
  btn.addEventListener('click', () => {
    const gen = parseInt(btn.dataset.filter.split(':')[1]);
    if (activeGens.has(gen)) {
      activeGens.clear();
      btn.classList.remove('active');
    } else {
      activeGens.clear();
      document.querySelectorAll('.gc-btn[data-filter^="gen:"]').forEach(b => b.classList.remove('active'));
      activeGens.add(gen);
      btn.classList.add('active');
    }
    render();
  });
});

document.querySelectorAll('.gc-tipo-btn').forEach(btn => {
  btn.addEventListener('click', () => {
    const tipo = btn.dataset.filter.split(':')[1];
    if (activeTipos.has(tipo)) {
      activeTipos.delete(tipo);
      btn.classList.remove('active');
    } else {
      if (activeTipos.size >= 2) {
        const primero = activeTipos.values().next().value;
        activeTipos.delete(primero);
        document.querySelector(`.gc-tipo-btn[data-filter="tipo:${primero}"]`)?.classList.remove('active');
      }
      activeTipos.add(tipo);
      btn.classList.add('active');
    }
    render();
  });
});

document.getElementById('search').addEventListener('input', e => { currentSearch = e.target.value; render(); });
document.getElementById('searchId').addEventListener('input', e => { currentSearchId = e.target.value.trim(); render(); });

// ══════════════════════════════════════════════════════
// MODAL Y SONIDO (Simplificado para brevedad, mantener lógica anterior)
// ══════════════════════════════════════════════════════
let currentModalId = null;
let currentAudio = null;

function openModalCard(el) {
  const pkid = parseInt(el.dataset.pkid);
  const p = allPokemons.find(x => x.id === pkid);
  if (!p) return;
  
  currentModalId = p.id;
  
  // Reproducir sonido
  if (currentAudio) { currentAudio.pause(); }
  currentAudio = new Audio(`https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/${p.id}.ogg`);
  currentAudio.play().catch(() => {});

  // Llenar modal... (Aquí iría tu lógica de llenado de modal existente)
  // Para no hacer el código infinito, asumo que mantienes tu función openModal anterior
  // pero llamando a setSpriteFallback para el sprite del modal.
  alert(`Abriendo modal de ${p.nombre}. (Integra tu lógica de modal existente aquí llamando a setSpriteFallback)`);
}

function toggleShiny(btn, id, nombre) {
  const card = btn.closest('.gc-card');
  const img = card.querySelector('img');
  const isShiny = btn.classList.contains('active');
  
  if (isShiny) {
    btn.classList.remove('active');
    card.classList.remove('shiny');
    setSpriteFallback(img, id, nombre, false);
  } else {
    btn.classList.add('active');
    card.classList.add('shiny');
    setSpriteFallback(img, id, nombre, true);
  }
}

// INICIO
loadPokemons();
