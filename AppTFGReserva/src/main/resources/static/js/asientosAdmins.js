




function toggleButtonColor(button) {
    if (button.classList.contains('btn-success')) {
        button.classList.remove('btn-success');
        button.classList.add('btn-warning');
    } else if (button.classList.contains('btn-warning')) {
        button.classList.remove('btn-warning');
        button.classList.add('btn-success');
    }
}

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
        
        
        function resetDetalleAsiento() {
    const infoAsiento = document.getElementById('info-asiento');
    infoAsiento.innerHTML = '<p>Elija un asiento para ver la información.</p>';
}


function mostrarOCultarDetalleAsiento(button) {
    if (button.classList.contains('selected')) {
        resetDetalleAsiento();
        button.classList.remove('selected');
    } else {
        mostrarDetalleAsiento(button);
        document.querySelectorAll('.asiento-btn').forEach(btn => btn.classList.remove('selected'));
        button.classList.add('selected');
    }
}
            

            
 document.addEventListener('DOMContentLoaded', function () {
            console.log("DOM completamente cargado y analizado");
            const asientoBtns = document.querySelectorAll('.asiento-btn');
            console.log("Botones encontrados:", asientoBtns.length);
            asientoBtns.forEach(btn => {
                btn.addEventListener('click', function() {
                    mostrarOCultarDetalleAsiento(this);
                    toggleButtonColor(this);
                });
            });
        });
        
        
        