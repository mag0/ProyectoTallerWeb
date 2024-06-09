let pedidoLat = document.getElementById("pedidoLat").value;
let pedidoLon = document.getElementById("pedidoLon").value;

// Definir la ubicación del origen
let origenLat = -34.6037; // Ejemplo: Latitud fija para el origen
let origenLon = -58.3816; // Ejemplo: Longitud fija para el origen

mapboxgl.accessToken = 'pk.eyJ1IjoibHVjb3NhIiwiYSI6ImNseDZmcXphMDFxN2UycW9lcDQwNmFidTUifQ.H4VUeIgiGUXMaQXOB849MA';

const map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    zoom: 15,
    center: [pedidoLon, pedidoLat] // Centra el mapa en la ubicación del pedido
});

function createMarkerPedido(coords) {
    new mapboxgl.Marker({ color: 'red' }).setLngLat(coords).addTo(map);
}

function createMarkerOrigen(coords) {
    new mapboxgl.Marker({ color: 'green' }).setLngLat(coords).addTo(map);
}

map.on('load', async () => {
    // Crear y agregar la marca del destino
    createMarkerPedido([pedidoLon, pedidoLat]);

    // Crear y agregar la marca del origen
    createMarkerOrigen([origenLon, origenLat]);

    // Renderiza la ruta entre el origen fijo y el destino
    renderingRoute([origenLon, origenLat], [pedidoLon, pedidoLat]);
});

async function renderingRoute(partida, destino) {
    const url = `https://api.mapbox.com/directions/v5/mapbox/driving/${partida[0]},${partida[1]};${destino[0]},${destino[1]}?steps=true&geometries=geojson&access_token=${mapboxgl.accessToken}`;

    const responseMapBox = await fetch(url);
    const dataMapBox = await responseMapBox.json();

    if (dataMapBox.routes && dataMapBox.routes.length > 0) {
        const ruta = dataMapBox.routes[0].geometry;
        const detailedSteps = dataMapBox.routes[0].legs[0].steps;
        const coordenadas = ruta.coordinates;

        // Verifica si la capa de ruta ya existe y elimínala si es necesario
        if (map.getLayer('ruta')) {
            map.removeLayer('ruta');
            map.removeSource('ruta');
        }
        map.setCenter(partida);

        // Agrega la capa de la ruta al mapa
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
    } else {
        console.error('No se encontraron rutas.');
    }
}

async function reverseGeocode(latitude, longitude) {
    const apiKey = mapboxgl.accessToken;
    const url = `https://api.mapbox.com/geocoding/v5/mapbox.places/${longitude},${latitude}.json?access_token=${apiKey}`;
    try {
        const response = await fetch(url);
        const data = await response.json();
        if (data.features && data.features.length > 0) {
            return data.features[0].place_name;
        } else {
            console.error('Error in geocoding:', data.message);
        }
    } catch (error) {
        console.error('Request failed', error);
    }
    return null;
}

document.addEventListener('DOMContentLoaded', async () => {
    const latitude = pedidoLat;
    const longitude = pedidoLon;
    const address = await reverseGeocode(latitude, longitude);
    if (address) {
        document.getElementById('address').textContent = `Destino: ${address}`;
    }
});
