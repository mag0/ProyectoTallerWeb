// Configurar Mapbox
mapboxgl.accessToken = 'pk.eyJ1IjoibHVjb3NhIiwiYSI6ImNseDZmcXphMDFxN2UycW9lcDQwNmFidTUifQ.H4VUeIgiGUXMaQXOB849MA';

// Definir la ubicación del origen
let origenLat = -34.6037; // Ejemplo: Latitud fija para el origen (Buenos Aires)
let origenLon = -58.3816; // Ejemplo: Longitud fija para el origen (Buenos Aires)

// Crear el mapa
const map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    zoom: 8,
    center: [origenLon, origenLat] // Centra el mapa inicialmente en el origen
});

let rutaCoordenadas = [];
let currentStep = 0;
const globalMarkers = {};

document.getElementById('avanzar').addEventListener('click', () => {
    if (rutaCoordenadas.length > 0 && currentStep < rutaCoordenadas.length - 1) {
        currentStep++;
        const [pedidoLon, pedidoLat] = rutaCoordenadas[currentStep];
        console.log({rutaCoordenadas, pedidoLon, pedidoLat})

        // Crear marcadores para origen y nuevo destino
        updateMarker( 'coord_A',[origenLon, origenLat]); // Marcador del origen con letra A

        // Calcular la distancia actualizada
        const distancia = calcularDistancia(origenLat, origenLon, pedidoLat, pedidoLon);
        document.getElementById('distancia').textContent = `Distancia: ${distancia.toFixed(2)} km`;

        // Centrar el mapa en la nueva ubicación
        map.flyTo({
            center: [pedidoLon, pedidoLat],
            essential: true
        });
    } else {
        console.log('No hay más pasos en la ruta o la ruta no está cargada.');
    }
});



// Crear marcadores
function createMarker(coords, color, point) {
    // Verificar si el número o etiqueta es válido
    if (!point) {
        point = ''; // Asignar un valor por defecto si es undefined
    }

    // Crear un elemento div que contendrá el marcador
    const el = document.createElement('div');
    el.className = 'marker';
    el.style.backgroundColor = color;
    el.style.width = '30px';
    el.style.height = '30px';
    el.style.borderRadius = '50%';
    el.style.display = 'flex';
    el.style.justifyContent = 'center';
    el.style.alignItems = 'center';
    el.id = `coord_${point}`;
    el.innerHTML = `<span style="color: white; font-size: 16px;">${point}</span>`; // Número en el marcador

    // Crear el marcador y añadirlo al mapa
    return new mapboxgl.Marker(el)
        .setLngLat(coords)
        .addTo(map);

    // Guardar el marcador en el objeto markers
   // markers[el.id] = marker;
}

// Función para actualizar la posición de un marcador por ID
function updateMarker(id, newCoords) {
    console.log(globalMarkers.dinamic.getLngLat());
    globalMarkers.dinamic.setLngLat(newCoords).update();

    map.setCenter(newCoords);
}


// Verificar coordenadas válidas
function isValidCoordinate(lat, lon) {
    return lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180;
}

// Mostrar todas las ubicaciones de los pedidos en el mapa al cargar la vista
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.card').forEach((card, index) => {
        const pedidoLat = parseFloat(card.querySelector('input[name="pedidoLat"]').value);
        const pedidoLon = parseFloat(card.querySelector('input[name="pedidoLon"]').value);
        if (isValidCoordinate(pedidoLat, pedidoLon)) {
            createMarker([pedidoLon, pedidoLat], 'red', index + 1); // Usar el índice de la tarjeta como número
        } else {
            console.error('Coordenadas inválidas para el pedido:', pedidoLat, pedidoLon);
        }
    });

    // Manejar el evento de clic en el botón "Ver ruta"
    document.querySelectorAll('.ver-ruta-btn').forEach(button => {
        button.addEventListener('click', async (e) => {
            const form = e.target.closest('.ver-ruta-form');
            const pedidoLat = parseFloat(form.querySelector('input[name="pedidoLat"]').value);
            const pedidoLon = parseFloat(form.querySelector('input[name="pedidoLon"]').value);

            if (isValidCoordinate(pedidoLat, pedidoLon)) {
                await showRoute(pedidoLat, pedidoLon);
            } else {
                console.error('Coordenadas inválidas para el pedido:', pedidoLat, pedidoLon);
            }
        });
    });
});


// Mostrar la ruta para un pedido específico
async function showRoute(pedidoLat, pedidoLon) {
    const origen = [origenLon, origenLat];
    const destino = [pedidoLon, pedidoLat];

    // Eliminar marcadores anteriores si existen
    const markers = document.querySelectorAll('.mapboxgl-marker');
    markers.forEach(marker => marker.remove());

    // Crear marcadores para origen y destino
    const dinamicMarker = createMarker(origen, 'green', 'A'); // Marcador del origen con letra A
    const staticMarker = createMarker(destino, 'red', 'B'); // Marcador del destino con letra B

    globalMarkers.dinamic = dinamicMarker
    globalMarkers.static = staticMarker

    // Generar la ruta
    await renderingRoute(origen, destino);

    // Calcular la distancia
    const distancia = calcularDistancia(origenLat, origenLon, pedidoLat, pedidoLon);
    document.getElementById('distancia').textContent = `Distancia: ${distancia.toFixed(2)} km`;

    // Centrar el mapa en la ruta
    map.fitBounds([
        origen, // Coordenadas del origen
        destino // Coordenadas del destino
    ], {
        padding: { top: 50, bottom: 50, left: 50, right: 50 } // Espaciado alrededor de los bordes del mapa
    });
}


// Función para renderizar la ruta
async function renderingRoute(partida, destino) {
    const url = `https://api.mapbox.com/directions/v5/mapbox/driving-traffic/${partida[0]},${partida[1]};${destino[0]},${destino[1]}?steps=true&geometries=geojson&access_token=${mapboxgl.accessToken}`;

    const responseMapBox = await fetch(url);
    const dataMapBox = await responseMapBox.json();

    if (dataMapBox.routes && dataMapBox.routes.length > 0) {
        const ruta = dataMapBox.routes[0].geometry;

        // Guardar las coordenadas de la ruta
        rutaCoordenadas = ruta.coordinates;
        currentStep = 0;

        // Verificar si la capa de ruta ya existe y eliminarla si es necesario
        if (map.getLayer('ruta')) {
            map.removeLayer('ruta');
            map.removeSource('ruta');
        }

        // Agregar la capa de la ruta al mapa
        map.addLayer({
            id: 'ruta',
            type: 'line',
            source: {
                type: 'geojson',
                data: {
                    type: 'Feature',
                    properties: {},
                    geometry: ruta
                }
            },
            layout: {
                'line-join': 'round',
                'line-cap': 'round'
            },
            paint: {
                'line-color': '#3887be',
                'line-width': 5,
                'line-opacity': 0.75
            }
        });

        // Mostrar información del tráfico
        const duracionConTrafico = dataMapBox.routes[0].duration / 60; // en minutos
        const distanciaConTrafico = dataMapBox.routes[0].distance / 1000; // en km

        document.getElementById('trafico').textContent = `Duración con tráfico: ${duracionConTrafico.toFixed(2)} minutos`;
        document.getElementById('distanciaConTrafico').textContent = `Distancia con tráfico: ${distanciaConTrafico.toFixed(2)} km`;

    } else {
        console.error('No se encontraron rutas.');
    }
}


// Calcular distancia entre dos coordenadas
function calcularDistancia(lat1, lon1, lat2, lon2) {
    const R = 6371; // Radio de la Tierra en km
    const dLat = toRad(lat2 - lat1);
    const dLon = toRad(lon2 - lon1);
    const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
}

function toRad(value) {
    return value * Math.PI / 180;
}

// Geocodificación inversa para obtener la dirección a partir de coordenadas
async function reverseGeocode(latitude, longitude) {
    const apiKey = mapboxgl.accessToken;
    const url = `https://api.mapbox.com/geocoding/v5/mapbox.places/${longitude},${latitude}.json?access_token=${apiKey}`;
    try {
        const response = await fetch(url);
        const data = await response.json();
        if (data.features && data.features.length > 0) {
            return data.features[0].place_name;
        } else {
            console.error('Error en la geocodificación:', data.message);
        }
    } catch (error) {
        console.error('Solicitud fallida', error);
    }
    return null;
}
