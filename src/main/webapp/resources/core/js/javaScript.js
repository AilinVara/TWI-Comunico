// Obtiene el modal
var modal = document.getElementById("modalVidas");

// Obtiene el botón o área que abrirá el modal
var btn = document.getElementById("vidaYNum");

// Obtiene el elemento que cierra el modal (la "X")
var span = document.getElementsByClassName("close")[0];

// Cuando el usuario hace clic en "vidaYNum", se abre el modal
btn.onclick = function() {
    modal.style.display = "block";
}

// Cuando el usuario hace clic en la "X", se cierra el modal
span.onclick = function() {
    modal.style.display = "none";
}

// Cuando el usuario hace clic fuera del modal, se cierra
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
