function mostrarDetalleAsiento(button) {
    const fila = button.getAttribute('data-fila');
    const columna = button.getAttribute('data-columna');
    const estado = button.getAttribute('data-estado');
    const asientoId = button.getAttribute('data-asiento-id');
    const fechaInicio = button.getAttribute('data-fecha-inicio');
    const fechaFin = button.getAttribute('data-fecha-fin');
    const salaId = document.querySelector('input[name="salaId"]').value;
    const salaNumero = document.querySelector('input[name="salaNumero"]').value;
    const fecha = button.getAttribute('data-fecha');
    const tipoReserva = button.getAttribute('data-tipo-reserva');
    
    if (!fecha || !tipoReserva) {
        alert("Por favor seleccione una fecha y un tipo de reserva.");
        return;
    }
    
    
    const tipoReservaTexto = tipoReserva === 'completa' ? 'Jornada Completa' :
                             tipoReserva === 'media_manana' ? 'Media Jornada Mañana' :
                             'Media Jornada Tarde';
                             
   	const horaInicio = new Date(fechaInicio).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    const horaFin = new Date(fechaFin).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
                             
    console.log("Fila:", fila, "Columna:", columna, "Estado:", estado);

    const infoAsiento = document.getElementById('info-asiento');
    infoAsiento.innerHTML = `
        <strong>Fila:</strong> ${fila}<br>
        <strong>Columna:</strong> ${columna}<br>
        <strong>Fecha de Reserva:</strong> ${fecha}<br>
        <strong>Jornada de Reserva:</strong> ${tipoReservaTexto}<br>
        <strong>Horario:</strong> ${horaInicio} - ${horaFin}<br>
        <strong>Sala:</strong> ${salaNumero}<br>
        <strong>Estado:</strong> ${estado}<br>
        <form action="/reservarAsiento" method="post">
            <input type="hidden" name="asientoId" value="${asientoId}">
            <input type="hidden" name="fechaInicio" value="${fechaInicio}">
            <input type="hidden" name="fechaFin" value="${fechaFin}">
            <input type="hidden" name="salaId" value="${salaId}">
            <button type="submit" class="btn btn-primary mt-2">Reservar</button>
        </form>
    `;
}

function resetButtonColors() {
    const buttons = document.querySelectorAll('.asiento-btn');
    buttons.forEach(btn => {
        const estado = btn.getAttribute('data-estado');
        if (estado === 'Libre') {
            btn.classList.remove('btn-warning');
            btn.classList.add('btn-success');
        }
    });
}

function mostrarOCultarDetalleAsiento(button) {
    const estado = button.getAttribute('data-estado');

    if (estado === 'Libre') {
        const currentlySelected = document.querySelector('.asiento-btn.btn-warning');
        
        if (currentlySelected && currentlySelected !== button) {
            currentlySelected.classList.remove('btn-warning');
            currentlySelected.classList.add('btn-success');
        }

        if (button.classList.contains('btn-warning')) {
            button.classList.remove('btn-warning');
            button.classList.add('btn-success');
            resetDetalleAsiento();
        } else {
            button.classList.remove('btn-success');
            button.classList.add('btn-warning');
            mostrarDetalleAsiento(button);
        }
    }
}

function resetDetalleAsiento() {
    const infoAsiento = document.getElementById('info-asiento');
    infoAsiento.innerHTML = '<p>Seleccione una fecha y un tipo de reserva para posteriormente seleccionar un asiento.</p>';
}



document.addEventListener('DOMContentLoaded', function () {
	
	// Establecer la fecha mínima en el campo de selección de fecha
    const fechaInput = document.getElementById('fecha');
    const today = new Date().toISOString().split('T')[0];
    fechaInput.setAttribute('min', today);
    
    console.log("DOM completamente cargado y analizado");
    const asientoBtns = document.querySelectorAll('.asiento-btn');
    console.log("Botones encontrados:", asientoBtns.length);
    asientoBtns.forEach(btn => {
        btn.addEventListener('click', function () {
            mostrarOCultarDetalleAsiento(this);
        });
    });
});