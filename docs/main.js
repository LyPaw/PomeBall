/* ══════════════════════════════════════════════════════
PomeBall — Lógica principal v3.0 (CORREGIDA + SPRITES ANIMADOS)
══════════════════════════════════════════════════════ */

// Pokéball SVG en Base64 para fallback
const POKEBALL_SVG = `data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAxMDAgMTAwIj48Y2lyY2xlIGN4PSI1MCIgY3k9IjUwIiByPSI0OCIgZmlsbD0iI2ZmZiIgc3Ryb2tlPSIjMzMzIiBzdHJva2Utd2lkdGg9IjQiLz48cGF0aCBkPSJNMCA1MCBhNTAgNTAgMCAwIDAgMTAwIDBaIiBmaWxsPSIjZmYwMDAwIi8+PGNpcmNsZSBjeD0iNTAiIGN5PSI1MCIgcj0iMTIiIGZpbGw9IiNmZmYiIHN0cm9rZT0iIzMzMyIgc3Ryb2tlLXdpZHRoPSI0Ii8+PGNpcmNsZSBjeD0iNTAiIGN5PSI1MCIgcj0iNiIgZmlsbD0iIzMzMyIvPjwvc3ZnPg==`;

// Mapeo de nombres para URLs de Showdown (normalización)
const NAME_TO_SHOWDOWN = {
  "Nidoran♀": "nidoran-f", "Nidoran♂": "nidoran-m",
  "Farfetch'd": "farfetchd", "Mr. Mime": "mr-mime", "Mime Jr.": "mime-jr",
  "Tipo: Null": "type-null", "Sirfetch'd": "sirfetchd", "Mr. Rime": "mr-rime",
  "Tapu Koko": "tapu-koko", "Tapu Lele": "tapu-lele", "Tapu Bulu": "tapu-bulu",
  "Tapu Fini": "tapu-fini", "Type: Null": "type-null"
};

function toShowdownName(name) {
  if (NAME_TO_SHOWDOWN[name]) return NAME_TO_SHOWDOWN[name];
  // Formas variantes: extraer nombre base antes del paréntesis
  const baseName = name.split(' (')[0].toLowerCase()
    .replace(/[^a-z0-9]/g, '-')
    .replace(/-+/g, '-')
    .replace(/^-|-$/g, '');
  return baseName || 'missingno';
}

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

// Inicializar tema guardado
document.addEventListener('DOMContentLoaded', () => {
  const savedTheme = localStorage.getItem('pk-theme');
  if (savedTheme) {
    document.documentElement.setAttribute('data-theme', savedTheme);
    const toggleBtn = document.getElementById('themeToggle');
    if (toggleBtn && savedTheme === 'dark') {
      toggleBtn.textContent = '◑ TEMA';
    }
  }
  loadPokemons();
});

// ══════════════════════════════════════════════════════
// HELPERS DE DATOS (Limpieza de espacios en claves JSON)
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
    // Aplanar y limpiar datos de todas las generaciones
    const rawList = Array.isArray(pokemonsData) 
      ? pokemonsData 
      : Object.values(pokemonsData).flat();
    allPokemons = rawList.map(cleanKeys).filter(p => p.id && p.nombre);

    PERFILES = cleanKeys(await perfilesRes.json());
    CURIOSIDADES = cleanKeys(await curiosidadesRes.json());

    // Cargar tamaños custom de sprites (opcional)
    try {
      const sizesRes = await fetch('sprite_sizes.json');
      if (sizesRes.ok) SPRITE_SIZES = cleanKeys(await sizesRes.json());
    } catch (_) { /* Ignorar si no existe */ }

    render();
    setupEventListeners();
  } catch (e) {
    console.error("❌ Error cargando datos:", e);
    const grid = document.getElementById('grid');
    if (grid) {
      grid.innerHTML = `
        <div class="gc-empty">
          <p>⚠️ ERROR AL CARGAR DATOS</p>
          <p>Revisa que tus archivos JSON estén bien formados.</p>
          <p><small>${e.message}</small></p>
        </div>`;
    }
  }
}

// ══════════════════════════════════════════════════════
// SISTEMA DE SPRITES ANIMADOS EN CASCADA
// ══════════════════════════════════════════════════════
function getShowdownUrl(name, isShiny) {
  const sdName = toShowdownName(name);
  const suffix = isShiny ? '-shiny' : '';
  return `https://play.pokemonshowdown.com/sprites/ani${suffix}/${sdName}.gif`;
}

function getPokeapiAnimatedUrl(id, isShiny) {
  // PokéAPI sprites animados (formato .gif)
  const suffix = isShiny ? '/shiny' : '';
  return `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon${suffix}/versions/generation-v/black-white/animated/${id}.gif`;
}

function getPokeapiStaticUrl(id, isShiny) {
  const suffix = isShiny ? '/shiny' : '';
  return `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon${suffix}/${id}.png`;
}

function setSpriteAnimated(img, id, name, isShiny, size = 96) {
  img.classList.add('loading');
  img.style.opacity = '0.5';
  
  let attempts = 0;
  const tryNext = () => {
    attempts++;
    switch(attempts) {
      case 1:
        // 1. Showdown animado (prioridad)
        img.src = getShowdownUrl(name, isShiny);
        break;
      case 2:
        // 2. PokéAPI animado (Gen 5+)
        img.src = getPokeapiAnimatedUrl(id, isShiny);
        break;
      case 3:
        // 3. PokéAPI estático
        img.src = getPokeapiStaticUrl(id, isShiny);
        break;
      default:
        // 4. Fallback final: Pokéball
        img.onerror = null;
        img.onload = null;
        img.classList.remove('loading');
        img.style.opacity = '1';
        img.src = POKEBALL_SVG;
        img.style.filter = 'grayscale(0.3) brightness(0.9)';
        img.alt = 'Sprite no disponible';
    }
  };

  img.onload = () => {
    img.classList.remove('loading');
    img.style.opacity = '1';
    img.style.filter = '';
    // Ajustar tamaño si es necesario
    if (size && size !== 96) {
      img.style.width = size + 'px';
      img.style.height = size + 'px';
    }
  };

  img.onerror = tryNext;
  
  // Iniciar carga
  tryNext();
}

// ══════════════════════════════════════════════════════
// COLORES DE TIPOS
// ══════════════════════════════════════════════════════
const TIPO_COLORS = {
  fuego:'#e05010', agua:'#2288CC', planta:'#338833', veneno:'#8844AA', normal:'#888860',
  electrico:'#BB9900', 'eléctrico':'#BB9900', tierra:'#AA7740', roca:'#887720',
  bicho:'#667700', fantasma:'#553377', acero:'#7788AA', hielo:'#5599AA',
  lucha:'#993322', psiquico:'#CC3366', 'psíquico':'#CC3366', 'dragón':'#5522CC',
  siniestro:'#554433', hada:'#BB5577', volador:'#6699FF',
};

// ══════════════════════════════════════════════════════
// RENDER PRINCIPAL
// ══════════════════════════════════════════════════════
function render() {
  const grid = document.getElementById('grid');
  if (!grid) return;
  
  const q = currentSearch.toLowerCase();
  
  const filtered = allPokemons.filter(p => {
    const matchSearch = !q || p.nombre.toLowerCase().includes(q);
    const matchId = !currentSearchId || p.id === parseInt(currentSearchId);
    const matchGen = activeGens.size === 0 || activeGens.has(p.generacion);
    const matchTipo = activeTipos.size === 0 || p.tipo?.some(t => activeTipos.has(t));
    return matchSearch && matchId && matchGen && matchTipo;
  });

  // Actualizar contador
  const countEl = document.getElementById('count');
  if (countEl) countEl.textContent = filtered.length;

  if (!filtered.length) {
    grid.innerHTML = '<div class="gc-empty">🔍 NO SE ENCONTRARON POKÉMON</div>';
    return;
  }

  let html = '';
  let separatorAdded = false;

  filtered.forEach((p, i) => {
    // Separador gráfico para formas/variantes (IDs > 10000)
    if (!separatorAdded && p.id > 10000) {
      html += `
        <div class="gc-separator" style="grid-column:1/-1;margin:1rem 0;">
          <span class="gc-separator-line"></span>
          <span class="gc-separator-text">✨ FORMAS Y VARIANTES ✨</span>
          <span class="gc-separator-line"></span>
        </div>`;
      separatorAdded = true;
    }

    const tiposBadges = (p.tipo || []).map(t => 
      `<span class="gc-tipo" style="background:${TIPO_COLORS[t] || '#888'}">${t.toUpperCase()}</span>`
    ).join('');

    const ataquesTags = (p.ataques || []).slice(0, 3).map(a => 
      `<span class="gc-ataque">${a}</span>`
    ).join('');
    
    const nombreEscaped = (p.nombre || '').replace(/'/g, "\\'").replace(/"/g, '&quot;');
    const regionText = p.region ? `📍 ${p.region}` : '';

    html += `
      <article class="gc-card" data-pkid="${p.id}" tabindex="0" 
               style="animation-delay:${(i % 20) * 0.03}s"
               onclick="openModal(${p.id})"
               onkeydown="if(event.key==='Enter'||event.key===' ')openModal(${p.id})">
        <header class="gc-card-bar">
          <span class="gc-card-num">NO.${String(p.id).padStart(4,'0')}</span>
          <span class="gc-card-gen">GEN ${p.generacion}</span>
        </header>
        <figure class="gc-card-img">
          <img data-id="${p.id}" alt="${p.nombre}" src="" loading="lazy" />
          <button class="gc-shiny-btn" title="Alternar Shiny ✨" 
                  onclick="event.stopPropagation();toggleShiny(${p.id},'${nombreEscaped}')">✨</button>
          <figcaption class="gc-sound" title="Reproducir grito">🔊</figcaption>
        </figure>
        <div class="gc-card-body">
          <h3 class="gc-card-name">${p.nombre}</h3>
          <div class="gc-tipos">${tiposBadges}</div>
          <div class="gc-ataques">${ataquesTags || '<span class="gc-ataque">—</span>'}</div>
        </div>
        <footer class="gc-region">${regionText}</footer>
      </article>`;
  });

  grid.innerHTML = html;

  // Cargar sprites animados tras inyectar HTML
  grid.querySelectorAll('img[data-id]').forEach(img => {
    const id = parseInt(img.dataset.id);
    const pokemon = allPokemons.find(x => x.id === id);
    const nombre = pokemon?.nombre || '';
    
    // Aplicar tamaño custom si existe
    const sizes = SPRITE_SIZES[String(id)];
    const size = sizes?.card || 96;
    
    setSpriteAnimated(img, id, nombre, false, size);
  });
}

// ══════════════════════════════════════════════════════
// EVENT LISTENERS
// ══════════════════════════════════════════════════════
function setupEventListeners() {
  // Filtros de generación
  document.querySelectorAll('.gc-btn[data-filter^="gen:"]').forEach(btn => {
    btn.addEventListener('click', (e) => {
      e.preventDefault();
      const gen = parseInt(btn.dataset.filter.split(':')[1]);
      if (activeGens.has(gen)) {
        activeGens.delete(gen);
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

  // Filtros de tipo
  document.querySelectorAll('.gc-tipo-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
      e.preventDefault();
      const tipo = btn.dataset.filter?.split(':')[1];
      if (!tipo) return;
      
      if (activeTipos.has(tipo)) {
        activeTipos.delete(tipo);
        btn.classList.remove('active');
      } else {
        // Máximo 2 tipos seleccionados
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

  // Búsqueda por nombre
  const searchInput = document.getElementById('search');
  if (searchInput) {
    searchInput.addEventListener('input', (e) => {
      currentSearch = e.target.value;
      render();
    });
  }

  // Búsqueda por ID
  const searchIdInput = document.getElementById('searchId');
  if (searchIdInput) {
    searchIdInput.addEventListener('input', (e) => {
      currentSearchId = e.target.value.trim();
      render();
    });
  }

  // Toggle tema
  const themeBtn = document.getElementById('themeToggle');
  if (themeBtn) {
    themeBtn.addEventListener('click', toggleTheme);
  }

  // Cerrar modal con ESC
  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') closeModal();
  });
}

// ══════════════════════════════════════════════════════
// MODAL COMPLETO
// ══════════════════════════════════════════════════════
let currentModalId = null;
let currentAudio = null;

function openModal(id) {
  const p = allPokemons.find(x => x.id === id);
  if (!p) return;
  
  currentModalId = p.id;
  const modal = document.getElementById('modal');
  if (!modal) return;

  // Reproducir grito (con manejo de errores)
  if (currentAudio) { currentAudio.pause(); currentAudio = null; }
  try {
    currentAudio = new Audio(`https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/${p.id}.ogg`);
    currentAudio.volume = 0.3;
    currentAudio.play().catch(() => {});
  } catch(_) {}

  // Rellenar datos del modal
  const perfil = PERFILES[String(p.id)] || {};
  const curiosidades = CURIOSIDADES[String(p.id)] || [];
  
  // Stats
  const stats = p.stats || {};
  const statLabels = {ps:'PS', ataque:'ATQ', defensa:'DEF', atesp:'AT.E', defesp:'DE.E', velocidad:'VEL'};
  const statsHtml = Object.entries(stats).map(([key, val]) => {
    const label = statLabels[key] || key;
    const pct = Math.min(100, (val / 200) * 100);
    const cls = val >= 120 ? 'stat-great' : val >= 90 ? 'stat-high' : val >= 60 ? 'stat-mid' : 'stat-low';
    return `
      <div class="modal-stat">
        <div class="modal-stat-row">
          <span class="modal-stat-lbl">${label}</span>
          <span class="modal-stat-num">${val}</span>
        </div>
        <div class="modal-stat-bar-wrap">
          <div class="modal-stat-bar-fill ${cls}" style="width:${pct}%"></div>
        </div>
      </div>`;
  }).join('');
  
  const totalStats = Object.values(stats).reduce((a,b) => a + (b||0), 0);

  // Tipos
  const tiposModal = (p.tipo || []).map(t => 
    `<span class="modal-tipo" style="background:${TIPO_COLORS[t]||'#888'}">${t.toUpperCase()}</span>`
  ).join('') || '<span class="modal-tipo">—</span>';

  // Ataques
  const ataquesModal = (p.ataques || []).map(a => 
    `<span class="modal-ataque">${a}</span>`
  ).join('') || '<span class="modal-ataque">—</span>';

  // Curiosidades
  const curiosidadesHtml = curiosidades.length 
    ? curiosidades.map(c => `
        <div class="modal-curiosidad-item">
          <span class="modal-curiosidad-icon">${c.icon || '💡'}</span>
          <p class="modal-curiosidad-text">${c.texto}</p>
        </div>`).join('')
    : '<p class="modal-no-curiosidades">Sin curiosidades registradas.</p>';

  // Renderizar modal
  modal.innerHTML = `
    <div class="modal-overlay open" onclick="if(event.target===this)closeModal()">
      <div class="modal" role="dialog" aria-modal="true" aria-labelledby="modal-title">
        <header class="modal-bar">
          <div class="modal-bar-left">
            <span class="modal-bar-icon"></span>
            <h2 class="modal-bar-title" id="modal-title">POKÉDEX</h2>
          </div>
          <div class="modal-bar-right">
            <span class="modal-bar-btn-label">[B] CERRAR</span>
            <button class="modal-close" onclick="closeModal()" aria-label="Cerrar">✕</button>
          </div>
        </header>
        
        <main class="modal-body">
          <aside class="modal-left">
            <div class="modal-sprite-wrap" id="modal-sprite-wrap">
              <img class="modal-sprite" id="modal-sprite" alt="${p.nombre}" src="" />
              <button class="modal-shiny-btn" id="modal-shiny-btn" onclick="toggleModalShiny()">✨ SHINY</button>
              <div class="modal-sprite-hint">Hover para ver detalle</div>
              <div class="pk-loader" id="modal-loader">
                <img class="pk-loader-ball" src="${POKEBALL_SVG}" alt="Cargando" />
                <span class="pk-loader-txt">CARGANDO...</span>
              </div>
            </div>
            <table class="modal-ficha">
              <tr class="modal-ficha-row"><td class="modal-ficha-key">ALTURA</td><td class="modal-ficha-val">${perfil.altura || '—'}</td></tr>
              <tr class="modal-ficha-row"><td class="modal-ficha-key">PESO</td><td class="modal-ficha-val">${perfil.peso || '—'}</td></tr>
              <tr class="modal-ficha-row"><td class="modal-ficha-key">ESPECIE</td><td class="modal-ficha-val">${perfil.especie || '—'}</td></tr>
              <tr class="modal-ficha-row"><td class="modal-ficha-key">TIPOS</td><td class="modal-ficha-val"><div class="modal-tipos">${tiposModal}</div></td></tr>
            </table>
          </aside>
          
          <section class="modal-right">
            <div class="modal-name-bar">
              <h3 class="modal-name" id="modal-pokemon-name">${p.nombre}</h3>
              <span class="modal-name-num">#${String(p.id).padStart(4,'0')}</span>
            </div>
            
            <div class="modal-right-grid">
              <div class="modal-stats-panel">
                <div class="modal-section-hdr">
                  <span class="modal-section-dot"></span>
                  <h4 class="modal-section-title">ESTADÍSTICAS</h4>
                </div>
                ${statsHtml}
                <div class="modal-stat-total">
                  <span class="modal-stat-total-lbl">TOTAL</span>
                  <span class="modal-stat-total-num">${totalStats}</span>
                </div>
              </div>
              
              <div class="modal-perfil-panel">
                <div class="modal-section-hdr">
                  <span class="modal-section-dot"></span>
                  <h4 class="modal-section-title">INFORMACIÓN</h4>
                </div>
                <div class="modal-perfil-fields">
                  <div class="modal-perfil-field accent">
                    <span class="modal-perfil-label">📖</span>
                    <p class="modal-perfil-value">${perfil.descripcion || 'Sin descripción disponible.'}</p>
                  </div>
                  <div class="modal-perfil-field">
                    <span class="modal-perfil-label">⚔️</span>
                    <p class="modal-perfil-value"><strong>Ataques:</strong> ${p.ataques?.join(', ') || '—'}</p>
                  </div>
                  <div class="modal-perfil-field">
                    <span class="modal-perfil-label">🌍</span>
                    <p class="modal-perfil-value"><strong>Región:</strong> ${p.region || '—'} (Gen ${p.generacion})</p>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="modal-curiosidades-section">
              <div class="modal-section-hdr">
                <span class="modal-section-dot"></span>
                <h4 class="modal-section-title">CURIOSIDADES</h4>
              </div>
              <div class="modal-curiosidades-list">
                ${curiosidadesHtml}
              </div>
            </div>
          </section>
        </main>
        
        <footer class="modal-footer">
          <span class="modal-footer-hint">✨ Click en ✨ para ver forma Shiny • [ESC] para cerrar</span>
          <div class="modal-footer-keys">
            <span class="modal-key">←→ Navegar</span>
            <span class="modal-key">S Shiny</span>
            <span class="modal-key">🔊 Sonido</span>
          </div>
        </footer>
      </div>
    </div>`;

  // Cargar sprite del modal
  const modalSprite = document.getElementById('modal-sprite');
  const loader = document.getElementById('modal-loader');
  const spriteWrap = document.getElementById('modal-sprite-wrap');
  
  if (modalSprite) {
    modalSprite.classList.add('loading');
    loader?.classList.add('visible');
    
    setSpriteAnimated(modalSprite, p.id, p.nombre, false, 170);
    
    // Ocultar loader cuando cargue
    modalSprite.onload = () => {
      loader?.classList.remove('visible');
      modalSprite.classList.remove('loading');
    };
  }

  // Navegación con teclado en modal
  document.addEventListener('keydown', onModalKeydown);
  
  // Animación de entrada
  setTimeout(() => {
    modal.querySelector('.modal')?.style.setProperty('transform', 'scale(1)');
  }, 10);
}

function closeModal() {
  const modal = document.getElementById('modal');
  if (!modal) return;
  
  // Animación de salida
  modal.querySelector('.modal')?.style.setProperty('transform', 'scale(0.94)');
  
  setTimeout(() => {
    modal.classList.remove('open');
    modal.innerHTML = '';
    if (currentAudio) { currentAudio.pause(); currentAudio = null; }
    currentModalId = null;
    document.removeEventListener('keydown', onModalKeydown);
  }, 200);
}

function onModalKeydown(e) {
  if (!currentModalId) return;
  
  switch(e.key.toLowerCase()) {
    case 'escape':
      e.preventDefault();
      closeModal();
      break;
    case 'arrowleft':
      e.preventDefault();
      navigateModal(-1);
      break;
    case 'arrowright':
      e.preventDefault();
      navigateModal(1);
      break;
    case 's':
      e.preventDefault();
      toggleModalShiny();
      break;
  }
}

function navigateModal(direction) {
  if (!currentModalId) return;
  const currentIndex = allPokemons.findIndex(p => p.id === currentModalId);
  if (currentIndex === -1) return;
  
  const newIndex = currentIndex + direction;
  if (newIndex >= 0 && newIndex < allPokemons.length) {
    openModal(allPokemons[newIndex].id);
  }
}

// ══════════════════════════════════════════════════════
// SHINY TOGGLE (CARD Y MODAL)
// ══════════════════════════════════════════════════════
function toggleShiny(id, nombre) {
  const card = document.querySelector(`.gc-card[data-pkid="${id}"]`);
  if (!card) return;
  
  const img = card.querySelector('img');
  const btn = card.querySelector('.gc-shiny-btn');
  const isShiny = btn?.classList.contains('active');
  
  if (isShiny) {
    btn?.classList.remove('active');
    card.classList.remove('shiny');
    setSpriteAnimated(img, id, nombre, false);
  } else {
    btn?.classList.add('active');
    card.classList.add('shiny');
    setSpriteAnimated(img, id, nombre, true);
  }
}

function toggleModalShiny() {
  if (!currentModalId) return;
  
  const p = allPokemons.find(x => x.id === currentModalId);
  if (!p) return;
  
  const sprite = document.getElementById('modal-sprite');
  const btn = document.getElementById('modal-shiny-btn');
  const wrap = document.getElementById('modal-sprite-wrap');
  
  if (!sprite || !btn) return;
  
  const isShiny = btn.classList.contains('active');
  
  if (isShiny) {
    btn.classList.remove('active');
    wrap?.classList.remove('shiny');
    setSpriteAnimated(sprite, p.id, p.nombre, false, 170);
  } else {
    btn.classList.add('active');
    wrap?.classList.add('shiny');
    setSpriteAnimated(sprite, p.id, p.nombre, true, 170);
  }
}

// ══════════════════════════════════════════════════════
// INICIALIZACIÓN
// ══════════════════════════════════════════════════════
// El loadPokemons() se llama desde DOMContentLoaded
