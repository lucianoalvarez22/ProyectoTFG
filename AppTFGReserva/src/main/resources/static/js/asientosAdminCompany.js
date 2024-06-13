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
                             tipoReserva === 'media_tarde' ? 'Media Jornada Tarde' :
                             'Por Horas';
                             
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
    infoAsiento.innerHTML = '<p>Seleccione una fecha y un tipo de reserva para ver los asientos disponibles.</p>';
}

function buscarAsientos() {
    const fecha = document.getElementById('fecha').value;
    const tipoReserva = document.getElementById('tipoReserva').value;

    if (!fecha || !tipoReserva) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Por favor seleccione una fecha y un tipo de reserva.',
            confirmButtonText: 'OK',
            confirmButtonColor: '#D94C09' 
        });
        return;
    }

    const salaId = document.querySelector('input[name="salaId"]').value;
    const salaNumero = document.querySelector('input[name="salaNumero"]').value;
    const companyApertura = document.getElementById('horaApertura').value;
    const companyCierre = document.getElementById('horaCierre').value;
    let fechaInicio, fechaFin;
    let horaInicio, horaFin;

    switch (tipoReserva) {
        case 'completa':
            fechaInicio = `${fecha}T${companyApertura}`;
            fechaFin = `${fecha}T${companyCierre}`;
            break;
        case 'media_manana':
            fechaInicio = `${fecha}T${companyApertura}`;
            fechaFin = new Date(new Date(fechaInicio).getTime() + 4 * 60 * 60 * 1000).toISOString().substring(0, 16);
            break;
        case 'media_tarde':
            fechaFin = `${fecha}T${companyCierre}`;
            fechaInicio = new Date(new Date(fechaFin).getTime() - 4 * 60 * 60 * 1000).toISOString().substring(0, 16);
            break;
        case 'por_horas':
            [horaInicio, horaFin] = document.getElementById('horaReserva').value.split(' - ');
            fechaInicio = `${fecha}T${horaInicio}`;
            fechaFin = `${fecha}T${horaFin}`;
            document.getElementById('horaInicioParam').value = horaInicio;
            document.getElementById('horaFinParam').value = horaFin;
            break;
        default:
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Tipo de reserva no válido.',
                confirmButtonText: 'OK',
                confirmButtonColor: '#D94C09' 
            });
            return;
    }

    const tipoReservaTexto = tipoReserva === 'completa' ? 'Jornada Completa' :
                             tipoReserva === 'media_manana' ? 'Media Jornada Mañana' :
                             tipoReserva === 'media_tarde' ? 'Media Jornada Tarde' :
                             'Por Horas';

    // Formatear la fecha seleccionada
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    const fechaFormateada = new Date(fecha).toLocaleDateString('es-ES', options);

    // Pasar la información al formulario
    document.getElementById('fechaFormateada').value = fechaFormateada;
    document.getElementById('tipoReservaTexto').value = tipoReservaTexto;

    // Enviar el formulario
    const form = document.getElementById('filterForm');
    form.action = `/buscarAsientosAdmin?fecha=${fecha}&tipoReserva=${tipoReserva}&salaId=${salaId}&salaNumero=${salaNumero}`;
    form.submit();
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

    // Mostrar/ocultar el select de hora según el tipo de reserva
    const tipoReservaSelect = document.getElementById('tipoReserva');
    const horaReservaDiv = document.getElementById('horaReservaDiv');
    const horaReservaSelect = document.getElementById('horaReserva');

    tipoReservaSelect.addEventListener('change', function () {
        if (this.value === 'por_horas') {
            horaReservaDiv.style.display = 'block';
            populateHoraReservaSelect();
        } else {
            horaReservaDiv.style.display = 'none';
            horaReservaSelect.innerHTML = '';
        }
    });

    function populateHoraReservaSelect() {
        const companyApertura = document.getElementById('horaApertura').value;
        const companyCierre = document.getElementById('horaCierre').value;
        const [startHour, startMinute] = companyApertura.split(':');
        const [endHour, endMinute] = companyCierre.split(':');

        let currentHour = parseInt(startHour);
        let options = '';

        while (currentHour < parseInt(endHour)) {
            const horaInicio = `${currentHour.toString().padStart(2, '0')}:${startMinute}`;
            const horaFin = `${(currentHour + 1).toString().padStart(2, '0')}:${startMinute}`;
            options += `<option value="${horaInicio} - ${horaFin}">${horaInicio} - ${horaFin}</option>`;
            currentHour++;
        }

        horaReservaSelect.innerHTML = options;
    }
});