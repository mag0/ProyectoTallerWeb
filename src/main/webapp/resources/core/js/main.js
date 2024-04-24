document.addEventListener("DOMContentLoaded", function() {
    const calendarioDias = document.getElementById("calendario-dias");
    const mesSelect = document.getElementById("mes");
    const anioInput = document.getElementById("anio");
    const prevMonthBtn = document.getElementById("prevMonth");
    const nextMonthBtn = document.getElementById("nextMonth");

    const meses = [
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    ];

    const fechaActual = new Date();
    let mesActual = fechaActual.getMonth();
    let anioActual = fechaActual.getFullYear();

    function generarCalendario(mes, anio) {
        calendarioDias.innerHTML = "";
        const primerDiaMes = new Date(anio, mes, 1).getDay();
        const diasEnMes = new Date(anio, mes + 1, 0).getDate();

        for (let i = 0; i < primerDiaMes; i++) {
            const espacio = document.createElement("div");
            calendarioDias.appendChild(espacio);
        }

        for (let dia = 1; dia <= diasEnMes; dia++) {
            const diaElemento = document.createElement("div");
            diaElemento.classList.add("dia");
            diaElemento.textContent = dia;
            diaElemento.addEventListener("click", () => {
                alert(`Fecha seleccionada: ${dia}/${mes + 1}/${anio}`);
            });
            calendarioDias.appendChild(diaElemento);
        }
    }

    function actualizarCalendario() {
        mesSelect.innerHTML = "";
        meses.forEach((mes, indice) => {
            const opcion = document.createElement("option");
            opcion.value = indice;
            opcion.textContent = mes;
            mesSelect.appendChild(opcion);
        });
        mesSelect.value = mesActual;
        anioInput.value = anioActual;
        generarCalendario(mesActual, anioActual);
    }

    prevMonthBtn.addEventListener("click", () => {
        if (mesActual === 0) {
            mesActual = 11;
            anioActual--;
        } else {
            mesActual--;
        }
        actualizarCalendario();
    });

    nextMonthBtn.addEventListener("click", () => {
        if (mesActual === 11) {
            mesActual = 0;
            anioActual++;
        } else {
            mesActual++;
        }
        actualizarCalendario();
    });

    mesSelect.addEventListener("change", () => {
        mesActual = parseInt(mesSelect.value);
        generarCalendario(mesActual, anioActual);
    });

    anioInput.addEventListener("change", () => {
        anioActual = parseInt(anioInput.value);
        generarCalendario(mesActual, anioActual);
    });

    actualizarCalendario();
});

