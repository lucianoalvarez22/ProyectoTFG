function mostrarDetalleAsiento(button) {
    const fila = button.getAttribute('data-fila');
    const columna = button.getAttribute('data-columna');
    const estado = button.getAttribute('data-estado');
    const asientoId = button.getAttribute('data-asiento-id');
    console.log("Fila:", fila, "Columna:", columna, "Estado:", estado);

    const infoAsiento = document.getElementById('info-asiento');
    infoAsiento.innerHTML = `
        <strong>Fila:</strong> ${fila}<br>
        <strong>Columna:</strong> ${columna}<br>
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
    infoAsiento.innerHTML = '<p>Elija un asiento para ver la información.</p>';
}

document.addEventListener('DOMContentLoaded', function () {
    console.log("DOM completamente cargado y analizado");
    const asientoBtns = document.querySelectorAll('.asiento-btn');
    console.log("Botones encontrados:", asientoBtns.length);
    asientoBtns.forEach(btn => {
        btn.addEventListener('click', function () {
            mostrarOCultarDetalleAsiento(this);
        });
    });
});
