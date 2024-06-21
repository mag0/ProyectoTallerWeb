// Definir la ubicación del origen
let origenLat = -34.6037; // Ejemplo: Latitud fija para el origen (Buenos Aires)
let origenLon = -58.3816; // Ejemplo: Longitud fija para el origen (Buenos Aires)

// Verificar coordenadas válidas
function isValidCoordinate(lat, lon) {
    return lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180;
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

// Mostrar las distancias al cargar la vista
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.card').forEach((card, index) => {
        const pedidoLat = parseFloat(card.querySelector('input[name="pedidoLat"]').value);
        const pedidoLon = parseFloat(card.querySelector('input[name="pedidoLon"]').value);
        if (isValidCoordinate(pedidoLat, pedidoLon)) {
            const distancia = calcularDistancia(origenLat, origenLon, pedidoLat, pedidoLon);
            const distanciaConTrafico = distancia * 1.1; // Ejemplo: Aumentar la distancia en un 10% por el tráfico
            const tiempoConTrafico = distanciaConTrafico / (50 / 60); // Convertir la velocidad a km por minuto

            // Asignar los valores a los campos hidden
            card.querySelector('#distancia').value = distancia.toFixed(2);
            card.querySelector('#distanciaConTrafico').value = distanciaConTrafico.toFixed(2);
            card.querySelector('#tiempoConTrafico').value = tiempoConTrafico.toFixed(2);

            console.log(`Pedido ${index + 1}: Distancia ${distancia.toFixed(2)} km, Distancia con Tráfico ${distanciaConTrafico.toFixed(2)} km, Tiempo con Tráfico ${tiempoConTrafico.toFixed(2)} minutos`);
        } else {
            console.error('Coordenadas inválidas para el pedido:', pedidoLat, pedidoLon);
        }
    });
});




