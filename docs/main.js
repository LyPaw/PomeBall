/* ══════════════════════════════════════════════════════
   PomeBall — Lógica principal
   Autor: LyPaw
   Separado en archivo externo para mejor mantenimiento
══════════════════════════════════════════════════════ */

// ══════════════════════════════════════════════════════
// TEMA (claro / oscuro)
// ══════════════════════════════════════════════════════

function toggleTheme() {
  const html   = document.documentElement;
  const isDark = html.getAttribute('data-theme') === 'dark';
  html.setAttribute('data-theme', isDark ? 'light' : 'dark');
  document.getElementById('themeToggle').textContent = isDark ? '◐ TEMA' : '◑ TEMA';
  localStorage.setItem('pk-theme', isDark ? 'light' : 'dark');
}

// Aplicar tema guardado al cargar
const savedTheme = localStorage.getItem('pk-theme');
if (savedTheme) {
  document.documentElement.setAttribute('data-theme', savedTheme);
  if (savedTheme === 'dark') document.getElementById('themeToggle').textContent = '◑ TEMA';
}

// ══════════════════════════════════════════════════════
// SPRITES — URLs y carga con fallback
// ══════════════════════════════════════════════════════

/**
 * Convierte el nombre de un Pokémon al slug de Pokémon Showdown.
 * Ej: "Farfetch'd" → "farfetchd"  |  "Nidoran♀" → "nidoranf"
 */
function toShowdownName(nombre) {
  return nombre
    .toLowerCase()
    .normalize('NFD')               // descompone caracteres con tilde (é → e + ́)
    .replace(/[\u0300-\u036f]/g, '') // elimina los diacríticos
    .replace(/♀/g, 'f')             // símbolo femenino → f
    .replace(/♂/g, 'm')             // símbolo masculino → m
    .replace(/[^a-z0-9-]/g, '');     // elimina todo lo que no sea letra/número
}

// URLs de sprites normales — prioridad: Showdown animated GIF → BW animated GIF → PNG estático
const SPRITE_SHOWDOWN  = nombre => `https://play.pokemonshowdown.com/sprites/ani/${toShowdownName(nombre)}.gif`;
const SPRITE_BW        = id     => `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/${id}.gif`;
const SPRITE_PNG       = id     => `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png`;

// URLs de sprites shiny
const SPRITE_SHINY_SD  = nombre => `https://play.pokemonshowdown.com/sprites/ani-shiny/${toShowdownName(nombre)}.gif`;
const SPRITE_SHINY_BW  = id     => `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/shiny/${id}.gif`;
const SPRITE_SHINY_PNG = id     => `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/${id}.png`;

/** Carga sprite normal con fallback: Showdown → BW animated → PNG estático */
function setSpriteWithFallback(imgEl, id, nombre, loaderEl) {
  const pokeName = nombre || imgEl.alt || String(id);
  if (loaderEl) loaderEl.classList.add('visible');
  imgEl.classList.add('loading');

  imgEl.onload = () => {
    imgEl.classList.remove('loading');
    if (loaderEl) loaderEl.classList.remove('visible');
    imgEl.onload = null;
  };
  imgEl.src = SPRITE_SHOWDOWN(pokeName);
  imgEl.onerror = function () {
    this.onerror = null;
    this.src = SPRITE_BW(id);
    this.onerror = function () {
      this.onerror = null;
      this.src = SPRITE_PNG(id);
    };
  };
}

/** Carga sprite shiny con fallback: Showdown shiny → BW shiny → PNG shiny estático */
function setShinyWithFallback(imgEl, id, nombre, loaderEl) {
  const pokeName = nombre || imgEl.alt || String(id);
  if (loaderEl) loaderEl.classList.add('visible');
  imgEl.classList.add('loading');

  imgEl.onload = () => {
    imgEl.classList.remove('loading');
    if (loaderEl) loaderEl.classList.remove('visible');
    imgEl.onload = null;
  };
  imgEl.src = SPRITE_SHINY_SD(pokeName);
  imgEl.onerror = function () {
    this.onerror = null;
    this.src = SPRITE_SHINY_BW(id);
    this.onerror = function () {
      this.onerror = null;
      this.src = SPRITE_SHINY_PNG(id);
    };
  };
}

/** Alterna entre sprite normal y shiny en la card */
function toggleShiny(btn, id, nombre) {
  const card    = btn.closest('.gc-card');
  const img     = card.querySelector('img[data-id]');
  const isShiny = btn.classList.contains('active');

  if (isShiny) {
    btn.classList.remove('active');
    card.classList.remove('shiny');
    btn.title = 'Ver shiny';
    setSpriteWithFallback(img, id, nombre);
  } else {
    btn.classList.add('active');
    card.classList.add('shiny');
    btn.title = 'Ver normal';
    setShinyWithFallback(img, id, nombre);
  }
}

// ══════════════════════════════════════════════════════
// SONIDO — Gritos de Pokémon
// ══════════════════════════════════════════════════════

const CRY_URL = id =>
  `https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/${id}.ogg`;

let currentAudio = null;

function playSound(id, cardEl) {
  if (currentAudio) {
    currentAudio.pause();
    currentAudio.currentTime = 0;
    document.querySelectorAll('.gc-card.playing').forEach(c => c.classList.remove('playing'));
  }
  const audio = new Audio(CRY_URL(id));
  currentAudio = audio;
  cardEl.classList.add('playing');
  audio.play().catch(() => {});
  audio.onended = () => cardEl.classList.remove('playing');
}

// ══════════════════════════════════════════════════════
// DATOS ESTÁTICOS
// ══════════════════════════════════════════════════════

const TIPO_COLORS = {
  fuego:'#e05010',     agua:'#2288CC',       planta:'#338833',
  veneno:'#8844AA',    normal:'#888860',      electrico:'#BB9900',
  'eléctrico':'#BB9900', tierra:'#AA7740',    roca:'#887720',
  bicho:'#667700',     fantasma:'#553377',    acero:'#7788AA',
  hielo:'#5599AA',     lucha:'#993322',       psiquico:'#CC3366',
  'psíquico':'#CC3366', 'dragón':'#5522CC',  siniestro:'#554433',
  hada:'#BB5577',
};

const MAX_STAT = 255;
const STAT_LABELS = {
  ps:'PS', ataque:'ATAQUE', defensa:'DEFENSA',
  atesp:'AT.ESP', defesp:'DEF.ESP', velocidad:'VELOCIDAD',
};

// ══════════════════════════════════════════════════════
// ESTADO DE LA APLICACIÓN
// ══════════════════════════════════════════════════════

let allPokemons    = [];
let PERFILES       = {};
let CURIOSIDADES   = {};
let SPRITE_SIZES   = {};
let currentSearch   = '';
let currentSearchId = '';
let activeGens      = new Set();
let activeTipos     = new Set();

// ══════════════════════════════════════════════════════
// CARGA DE DATOS (JSON)
// ══════════════════════════════════════════════════════

async function loadPokemons() {
  try {
    const [pokemonsRes, perfilesRes, curiosidadesRes] = await Promise.all([
      fetch('pokemons.json'),
      fetch('perfiles.json'),
      fetch('curiosidades.json'),
    ]);

    // Soporta tanto array plano como { generacion_1: [...], generacion_2: [...], ... }
    const pokemonsData = await pokemonsRes.json();
    allPokemons = Array.isArray(pokemonsData)
      ? pokemonsData
      : Object.values(pokemonsData).flat();

    PERFILES     = await perfilesRes.json();
    CURIOSIDADES = await curiosidadesRes.json();

    // sprite_sizes.json es opcional — no bloquea la carga si no existe
    try {
      const sizesRes = await fetch('sprite_sizes.json');
      if (sizesRes.ok) SPRITE_SIZES = await sizesRes.json();
    } catch (_) { /* archivo no disponible, se ignora */ }

    render();
  } catch (e) {
    document.getElementById('grid').innerHTML =
      '<div class="gc-empty">ERROR: NO SE PUDO CARGAR<br>pokemons.json / perfiles.json / curiosidades.json</div>';
  }
}

// ══════════════════════════════════════════════════════
// RENDER — Genera las cards del grid
// ══════════════════════════════════════════════════════

function render() {
  const grid = document.getElementById('grid');
  const q    = currentSearch.toLowerCase();

  const filtered = allPokemons.filter(p => {
    const matchSearch = !q || p.nombre.toLowerCase().includes(q);
    const matchId     = !currentSearchId || p.id === parseInt(currentSearchId);
    const matchGen    = activeGens.size  === 0 || activeGens.has(p.generacion);
    const matchTipo   = activeTipos.size === 0 || p.tipo.some(t => activeTipos.has(t));
    return matchSearch && matchId && matchGen && matchTipo;
  });

  document.getElementById('count').textContent = filtered.length;

  if (!filtered.length) {
    grid.innerHTML = '<div class="gc-empty">NO SE ENCONTRARON POKÉMON</div>';
    return;
  }

  grid.innerHTML = filtered.map((p, i) => {
    const tiposBadges = p.tipo.map(t =>
      `<span class="gc-tipo" style="background:${TIPO_COLORS[t] || '#888'}">${t.toUpperCase()}</span>`
    ).join('');

    const ataquesTags = (p.ataques || []).map(a =>
      `<span class="gc-ataque">${a}</span>`
    ).join('');

    // Nombre escapado para uso seguro en atributo onclick
    const spriteName = p.variante ? `${p.nombre}-${p.variante}` : p.nombre; const nombreEscaped = spriteName.replace(/'/g, "\\'");

    return `
      <div class="gc-card" data-pkid="${p.id}" style="animation-delay:${i * 0.04}s" onclick="openModal(${p.id}, '${p.variante || \"\"}')">
        <div class="gc-card-bar">
          <span class="gc-card-num">NO.${String(p.id).padStart(3,'0')}</span>
          <span class="gc-card-gen">GEN ${p.generacion}</span>
        </div>
        <div class="gc-card-img">
          <img data-id="${p.id}" data-nombre="${p.variante ? p.nombre + '-' + p.variante : p.nombre}" alt="${p.nombre}" src=""/>
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
  }).join('');

  // Cargar sprites para todos los Pokémon del grid
  grid.querySelectorAll('img[data-id]').forEach(img => {
    const id     = parseInt(img.dataset.id);
    const nombre = img.dataset.nombre || img.alt;

    // Aplicar tamaño custom si existe en sprite_sizes.json
    const sizes = SPRITE_SIZES[String(id)];
    if (sizes && sizes.card) {
      img.style.width  = sizes.card + 'px';
      img.style.height = sizes.card + 'px';
    }
    setSpriteWithFallback(img, id, nombre);
  });
}

// ══════════════════════════════════════════════════════
// FILTROS Y BÚSQUEDA — Event listeners
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

document.getElementById('search').addEventListener('input', e => {
  currentSearch = e.target.value;
  render();
});

document.getElementById('searchId').addEventListener('input', e => {
  currentSearchId = e.target.value.trim();
  render();
});

// ══════════════════════════════════════════════════════
// HELPERS DE ESTADÍSTICAS
// ══════════════════════════════════════════════════════

function getStatClass(v) {
  if (v < 50)  return 'stat-low';
  if (v < 80)  return 'stat-mid';
  if (v < 110) return 'stat-high';
  return 'stat-great';
}

function getStatNumColor(v) {
  if (v < 50)  return '#e05050';
  if (v < 80)  return '#d4a020';
  if (v < 110) return '#40a840';
  return '#2288CC';
}

function renderStats(stats) {
  const c = document.getElementById('modalStats');
  if (!stats) {
    c.innerHTML = '<div style="color:var(--muted);font-family:\'Press Start 2P\',monospace;font-size:0.38rem;padding:0.5rem 0">SIN DATOS</div>';
    return;
  }
  const total = Object.values(stats).reduce((a, b) => a + b, 0);
  c.innerHTML = Object.entries(stats).map(([k, v]) => `
    <div class="modal-stat">
      <div class="modal-stat-row">
        <span class="modal-stat-lbl">${STAT_LABELS[k] || k.toUpperCase()}</span>
        <div class="modal-stat-bar-wrap">
          <div class="modal-stat-bar-fill ${getStatClass(v)}" data-target="${Math.min((v / MAX_STAT) * 100, 100).toFixed(1)}%" style="width:0%"></div>
        </div>
        <span class="modal-stat-num" style="color:${getStatNumColor(v)}">${v}</span>
      </div>
    </div>`).join('') +
    `<div class="modal-stat-total">
      <span class="modal-stat-total-lbl">TOTAL</span>
      <span class="modal-stat-total-num">${total}</span>
    </div>`;

  // Animación de las barras (pequeño delay para que el CSS transition sea visible)
  setTimeout(() => {
    c.querySelectorAll('.modal-stat-bar-fill').forEach(b => b.style.width = b.dataset.target);
  }, 80);
}

// ══════════════════════════════════════════════════════
// MODAL — Abrir / Cerrar / Shiny
// ══════════════════════════════════════════════════════

let currentModalId   = null;
let currentModalName = null;
let modalIsShiny     = false;
let currentModalVariant = null;

function openModal(id, variante = null) {
  const p = allPokemons.find(x => x.id === id && (variante ? x.variante === variante : !x.variante));
  if (!p) return;

  currentModalId   = id;
  currentModalName = p.variante ? p.nombre + "-" + p.variante : p.nombre;

  // Reproducir grito
  if (currentAudio) { currentAudio.pause(); currentAudio.currentTime = 0; }
  const audio = new Audio(CRY_URL(id));
  currentAudio = audio;
  audio.play().catch(() => {});

  // Barra título
  document.getElementById('modalBarTitle').textContent = `INFO. POKÉMON — ${p.nombre.toUpperCase()}`;

  // Resetear estado shiny
  modalIsShiny = false;
  document.getElementById('modalShinyBtn').classList.remove('active');
  document.getElementById('modalSprite').closest('.modal-sprite-wrap').classList.remove('shiny');
  document.getElementById('modalShinyBtn').title = 'Ver shiny';

  // Sprite
  const spriteEl = document.getElementById('modalSprite');
  spriteEl.dataset.pkid = id;
  spriteEl.alt = p.nombre;
  document.getElementById('modalLoader').classList.add('visible');

  // Aplicar tamaño custom modal si existe en sprite_sizes.json
  const customSizes = SPRITE_SIZES[String(id)];
  if (customSizes && customSizes.modal) {
    spriteEl.style.width  = customSizes.modal + 'px';
    spriteEl.style.height = customSizes.modal + 'px';
  } else {
    spriteEl.style.width  = '170px';
    spriteEl.style.height = '170px';
  }
  setSpriteWithFallback(spriteEl, p.id, currentModalName, document.getElementById('modalLoader'));

  // Datos básicos
  document.getElementById('modalNum').textContent       = `NO.${String(p.id).padStart(3, '0')}`;
  document.getElementById('modalName').textContent      = p.nombre;
  document.getElementById('modalNameNum').textContent   = `NO.${String(p.id).padStart(3, '0')}`;
  document.getElementById('modalRegion').textContent    = p.region || '—';
  document.getElementById('modalGen').textContent       = `GEN ${p.generacion}`;

  // Stats
  renderStats(p.stats || null);

  // Perfil (perfiles.json)
  const perf = PERFILES[String(p.id)] || {};
  const setField = (id, val, placeholder) => {
    const el = document.getElementById(id);
    if (val) { el.textContent = val; el.className = 'modal-perfil-value'; }
    else     { el.textContent = placeholder; el.className = 'modal-perfil-value placeholder'; }
  };
  setField('modalAltura',      perf.altura,      'Pendiente de añadir');
  setField('modalPeso',        perf.peso,        'Pendiente de añadir');
  setField('modalEspecie',     perf.especie,     'Pendiente de añadir');
  setField('modalDescripcion', perf.descripcion, 'Sin descripción registrada todavía.');

  // Tipos
  document.getElementById('modalTipos').innerHTML = p.tipo.map(t =>
    `<span class="modal-tipo" style="background:${TIPO_COLORS[t] || '#888'}">${t.toUpperCase()}</span>`
  ).join('');

  // Ataques
  document.getElementById('modalAtaques').innerHTML = (p.ataques || []).map(a =>
    `<span class="modal-ataque">${a}</span>`
  ).join('');

  // Curiosidades (curiosidades.json)
  const curiosidades = CURIOSIDADES[String(id)] || [];
  const container    = document.getElementById('modalCuriosidades');
  if (curiosidades.length === 0) {
    container.innerHTML = '<div class="modal-no-curiosidades">Sin datos curiosos registrados todavía.</div>';
  } else {
    container.innerHTML = curiosidades.map(c =>
      `<div class="modal-curiosidad-item">
        <span class="modal-curiosidad-icon">${c.icon || '▶'}</span>
        <span class="modal-curiosidad-text">${c.texto}</span>
      </div>`
    ).join('');
  }

  document.getElementById('modalOverlay').classList.add('open');
  document.body.style.overflow = 'hidden';
}

function toggleModalShiny() {
  const btn  = document.getElementById('modalShinyBtn');
  const img  = document.getElementById('modalSprite');
  const wrap = img.closest('.modal-sprite-wrap');
  modalIsShiny = !modalIsShiny;

  if (modalIsShiny) {
    btn.classList.add('active');
    wrap.classList.add('shiny');
    btn.title = 'Ver normal';
    setShinyWithFallback(img, currentModalId, currentModalName, document.getElementById('modalLoader'));
  } else {
    btn.classList.remove('active');
    wrap.classList.remove('shiny');
    btn.title = 'Ver shiny';
    setSpriteWithFallback(img, currentModalId, currentModalName, document.getElementById('modalLoader'));
  }
}

function closeModal() {
  document.getElementById('modalOverlay').classList.remove('open');
  document.body.style.overflow = '';
  currentModalId   = null;
  currentModalName = null;
}

function closeModalOutside(e) {
  if (e.target === document.getElementById('modalOverlay')) closeModal();
}

// ══════════════════════════════════════════════════════
// SPRITE VIEWER — Galería normal + shiny
// ══════════════════════════════════════════════════════

function openSpriteViewer() {
  if (!currentModalId) return;
  const p  = allPokemons.find(x => x.id === currentModalId && (currentModalVariant ? x.variante === currentModalVariant : !x.variante));
  const id = currentModalId;

  document.getElementById('svPokeName').textContent = p ? p.nombre.toUpperCase() : `#${id}`;

  setSpriteWithFallback(
    document.getElementById('svFront'),
    id, currentModalName || '',
    document.getElementById('svFrontLoader')
  );
  setShinyWithFallback(
    document.getElementById('svShinyFront'),
    id, currentModalName || '',
    document.getElementById('svShinyLoader')
  );

  document.getElementById('svOverlay').classList.add('open');
}

function closeSpriteViewer() {
  document.getElementById('svOverlay').classList.remove('open');
}

function closeSVOutside(e) {
  if (e.target === document.getElementById('svOverlay')) closeSpriteViewer();
}

// Cerrar con tecla Escape
document.addEventListener('keydown', e => {
  if (e.key === 'Escape') {
    closeSpriteViewer();
    closeModal();
  }
});

// ══════════════════════════════════════════════════════
// INICIO
// ══════════════════════════════════════════════════════
loadPokemons();

