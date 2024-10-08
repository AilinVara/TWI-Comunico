document.addEventListener('DOMContentLoaded', (event) => {
    const letras = document.querySelectorAll('.letra');
    const palabraObjetivo = document.querySelector('h2').innerText;
    const comprobarBtn = document.getElementById('comprobarBtn');
    const siguienteBtn = document.getElementById('siguienteBtn');

    letras.forEach(letra => {
        letra.addEventListener('dragstart', dragStart);
    });

    function dragStart(event) {
        event.dataTransfer.setData('text', event.target.innerText);
    }

    comprobarBtn.addEventListener('click', () => {
        const respuestaUsuario = "";
        const respuestaCorrecta = palabraObjetivo;
        if (respuestaUsuario === respuestaCorrecta) {
            alert("Respuesta correcta");
            siguienteBtn.style.display = "block";
        } else {
            alert("Respuesta incorrecta");
        }
    });
    
    siguienteBtn.addEventListener('click', () => {
        window.location.href = "/leccion?indice=" + (parseInt(indiceEjercicio) + 1);
    });
});
