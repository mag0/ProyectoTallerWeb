function asignarPedido(id) {
    // URL a la que deseas enviar la solicitud POST
    const url = `/pedidos/${id}/asignar`;

    // Datos que deseas enviar en el cuerpo de la solicitud
    const data = {
        pedidoId: id
    };

    // Opciones de configuración de la solicitud
    const opciones = {
        method: 'POST', // Método de la solicitud
        headers: {
            'Content-Type': 'application/json' // Tipo de contenido del cuerpo de la solicitud
        },
        body: JSON.stringify(data) // Convertimos los datos a formato JSON
    };

    console.log({url, data, opciones})

    fetch(url, opciones)
        .then(response => {
            // Verificar si la solicitud fue exitosa
            if (!response.ok) {
                throw new Error('Hubo un problema con la solicitud: ' + response.status);
            }
            // Procesar la respuesta
            return response.json(); // Convertir la respuesta a JSON
        })
        .then(data => {
            // Hacer algo con los datos de la respuesta
            console.log(data);
        })
        .catch(error => {
            // Capturar y manejar errores
            console.error('Hubo un problema con la solicitud:', error);
        });
}