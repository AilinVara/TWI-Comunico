// Definir la función drag en el ámbito global
console.log("ejercicio-forma-palabra.js cargado correctamente");


function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.innerText);
}

// Esperar a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', () => {
    const dropArea = document.getElementById("dropArea");
    const letters = document.querySelectorAll('.letter'); // Seleccionar todas las letras

    // Agregar el evento dragstart a cada letra
    letters.forEach(letter => {
        letter.addEventListener('dragstart', drag);
    });

    dropArea.addEventListener("dragover", (ev) => {
        ev.preventDefault(); // Necesario para permitir la caída
    });

    dropArea.addEventListener("drop", (ev) => {
        ev.preventDefault();
        const data = ev.dataTransfer.getData("text");

        // Crear un nuevo div para la letra arrastrada
        const letterDiv = document.createElement("div");
        letterDiv.innerText = data;
        letterDiv.classList.add("letter");
        dropArea.appendChild(letterDiv);

        // Actualizar el campo oculto de respuesta
        updateRespuestaUsuario();
    });
});

// Función para actualizar el valor del campo de respuesta
function updateRespuestaUsuario() {
    const letters = document.getElementById("dropArea").getElementsByClassName("letter");
    let respuesta = '';
    for (let i = 0; i < letters.length; i++) {
        respuesta += letters[i].innerText;
    }
    document.getElementById("respuestaUsuario").value = respuesta; // Actualizar el campo oculto
}
