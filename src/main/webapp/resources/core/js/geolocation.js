// Función para obtener la ubicación del usuario
async function initWithLocation(destino) {
    if ('geolocation' in navigator) {
        navigator.geolocation.getCurrentPosition(async (position) => {
            const startMarker = createMarkerVehiculo([position.coords.longitude, position.coords.latitude])
            // Crea y agrega la marca de destino
            createMarkerPedido(destino)
            await renderingRoute([position.coords.longitude, position.coords.latitude], destino, startMarker);
        }, (error) => {
            console.error('Error al obtener la ubicación: ', error);
        });
    } else {
        console.error('La geolocalización no está disponible en este navegador.');
    }
}

function createMarkerVehiculo(coords){
    return new mapboxgl.Marker({ color: 'green' }).setLngLat(coords).addTo(map);
}