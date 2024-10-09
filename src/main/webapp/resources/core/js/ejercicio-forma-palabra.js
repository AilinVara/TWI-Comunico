console.log("Archivo JS cargado correctamente.");

// Array para almacenar las letras que han sido movidas a la drop-area
let letrasSeleccionadas = [];

// Función para manejar el arrastre de las letras
function drag(event) {
    event.dataTransfer.setData("text/plain", event.target.innerText);
}

// Evento cuando el área de drop permite que se le arrastren elementos
document.getElementById("dropArea").addEventListener("dragover", function(event) {
    event.preventDefault();
});

// Evento cuando se suelta una letra en el área de drop
document.getElementById("dropArea").addEventListener("drop", function(event) {
    event.preventDefault();
    const data = event.dataTransfer.getData("text/plain");

    // Crear un nuevo div para mostrar la letra soltada
    const droppedLetterDiv = document.createElement("div");
    droppedLetterDiv.classList.add("dropped-letter");
    droppedLetterDiv.innerText = data;

    document.getElementById("droppedLetters").appendChild(droppedLetterDiv);

    // Agregar la letra al array de letras seleccionadas
    letrasSeleccionadas.push(data);

    // Actualizar el campo oculto del formulario con las letras seleccionadas
    const listaLetras = document.getElementById("listaLetras");
    listaLetras.value = letrasSeleccionadas.join(""); // Convertir el array en una cadena
});