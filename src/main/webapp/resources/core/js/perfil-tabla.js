function mostrarContenido(opcion) {
    const secciones = ['amigos', 'facturas'];

    secciones.forEach(function(seccion) {
        document.getElementById(seccion).style.display = (seccion === opcion) ? 'block' : 'none';
    });
}

document.addEventListener("DOMContentLoaded", function() {
    var navLinks = document.querySelectorAll('.nav-link');

    navLinks.forEach(function(link) {
        link.addEventListener('click', function(event) {
            navLinks.forEach(function(link) {
                link.classList.remove('active');
            });
            event.target.classList.add('active');
        });
    });
});