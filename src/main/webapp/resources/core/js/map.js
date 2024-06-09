let pedidoLat = document.getElementById("pedidoLat").value;
let pedidoLon = document.getElementById("pedidoLon").value;

mapboxgl.accessToken = 'pk.eyJ1IjoibHVjb3NhIiwiYSI6ImNseDZmcXphMDFxN2UycW9lcDQwNmFidTUifQ.H4VUeIgiGUXMaQXOB849MA';

const map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    zoom: 15,
});

function createMarkerPedido(coords){
    new mapboxgl.Marker({ color: 'red' }).setLngLat(coords).addTo(map);
}

map.on('load', async () => {
    // Crea y agrega la marca de partida
    // Llama a la función para obtener la ubicación del usuario
    initWithLocation([pedidoLon, pedidoLat]);
});

async function renderingRoute(partida, destino, startMarker) {
    const url = `https://api.mapbox.com/directions/v5/mapbox/driving/${partida[0]},${partida[1]};${destino[0]},${destino[1]}?steps=true&geometries=geojson&access_token=${mapboxgl.accessToken}`;

    const responseMapBox = await fetch(url)
    const dataMapBox = await responseMapBox.json()

            console.log({dataMapBox})
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

                // Anima el marcador a lo largo de la ruta
                animateMarkerAlongRoute(coordenadas, startMarker);
            } else {
                console.error('No se encontraron rutas.');
            }

}

function animateMarkerAlongRoute(coordinates, marker, duration=30000) {
    let index = 0;
    const totalFrames = coordinates.length;
    const timePerFrame = duration / totalFrames; // Duración en milisegundos por frame

    function animate() {
        if (index < totalFrames) {
            marker.setLngLat(coordinates[index]);
            map.setCenter(coordinates[index]);
            index++;
            setTimeout(animate, timePerFrame);
        }
    }

    // Inicia la animación
    animate();
}

//--------------------------------------------------------------------------------------------
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
// Ejemplo de uso
document.addEventListener('DOMContentLoaded', async () => {
    const latitude = pedidoLat;
    const longitude = pedidoLon;
    const address = await reverseGeocode(latitude, longitude);
    if (address) {
        document.getElementById('address').textContent = `Destino: ${address}`;
    }
    initializeMap(latitude, longitude);
});
