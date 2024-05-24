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
    async function cargarPedidos() {
        try {
            const response = await fetch('/spring/pedidos');
            if (!response.ok) throw new Error('Error al cargar pedidos');
            const pedidos = await response.json();
            actualizarVistaPedidos(pedidos);
        } catch (error) {
            console.error('Error:', error);
            mostrarMensaje('Error al cargar pedidos.', 'danger');
        }
    }

    function actualizarVistaPedidos(pedidos) {
        const seccionPedidos = document.querySelector('section.row');
        seccionPedidos.innerHTML = '';
        pedidos.forEach((pedido, index) => {
            seccionPedidos.innerHTML += `
        <div class="col-6">
            <div class="card my-2">
                <div class="card-header">
                    <h4 class="card-title">${pedido.nombre}</h4>
                </div>
                <div class="card-body d-flex align-items-center">
                    <img src="${pedido.imagen}" alt="Pedido" width="130px" height="130px"/>
                    <div class="ml-2 card-text">
                        <p>Tipo de pedido: ${pedido.tipo}</p>
                        <p>Codigo: ${pedido.codigo}</p>
                        <p>Tamaño: ${pedido.tamanio}</p>
                        <p>Peso: ${pedido.peso}kg</p>
                        <p class="fecha-entrega">Entrega: ${new Date(pedido.fecha).toLocaleDateString()}</p>
                    </div>
                </div>
                <div class="card-footer">
                    <div class="col flex-column">
                        <form action="/pedidos/cancelar/${pedido.id}" method="get">
                            <button class="btn btn-danger" type="submit">Eliminar</button>
                        </form>
                        <a href="/pedidos/${pedido.id}/asignar" class="btn btn-primary">Despachar</a>
                        <a href="/pedidos/${pedido.id}/reprogramar-pedido" class="btn btn-secondary">Reprogramar</a>
                    </div>
                </div>
            </div>
        </div>`;
        });
    }

    document.addEventListener('DOMContentLoaded', cargarPedidos);

}