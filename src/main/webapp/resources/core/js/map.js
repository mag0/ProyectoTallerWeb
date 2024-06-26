let pedidoLat = document.getElementById("pedidoLat").value;
let pedidoLon = document.getElementById("pedidoLon").value;
let pedidoStatus = document.getElementById("pedidoStatus").value;

if(pedidoStatus !== 'RECIBIDO'){
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
}



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
                animateMarkerAlongRoute(detailedSteps, startMarker,map);
            } else {
                console.error('No se encontraron rutas.');
            }

}

function animateMarkerAlongRouteold(coordinates, marker, detailedSteps,duration=30000) {
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

function animateMarkerAlongRoute(route, marker, map) {
    let stepIndex = 0;

    function animateStep(timestamp, start) {
        if (!start) start = timestamp;
        const step = route[stepIndex];
        const stepDuration = step.duration * 1000; // Convert duration to milliseconds
        const progress = Math.min((timestamp - start) / stepDuration, 1); // Progress between 0 and 1
        const coordinates = step.geometry.coordinates;
        const [startLng, startLat] = coordinates[0];
        const [endLng, endLat] = coordinates[1];

        // Interpolación lineal entre el punto de inicio y el punto final
        const currentLng = startLng + (endLng - startLng) * progress;
        const currentLat = startLat + (endLat - startLat) * progress;

        marker.setLngLat([currentLng, currentLat]);
        map.setCenter([currentLng, currentLat]);

        if (progress < 1) {
            requestAnimationFrame((timestamp) => animateStep(timestamp, start));
        } else {
            stepIndex++;
            if (stepIndex < route.length) {
                requestAnimationFrame(animateStep);
            }
        }
    }

    requestAnimationFrame(animateStep);
}
