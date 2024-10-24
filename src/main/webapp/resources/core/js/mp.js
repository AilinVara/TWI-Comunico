const mp = new MercadoPago('APP_USR-80a01d15-9532-4611-8737-02f9243a7e0e', {locale: 'es-AR'});

const MP = async () => {
    try {
        console.log("entre en el mp script");
        const quantity = document.getElementById("cantidad").value;

        const response = await fetch(`http://localhost:8080/mp/${quantity}`, {
        method: 'POST',
            headers: {
            'Accept': 'application/json',
                'Content-Type': 'application/json'
        }
    });
    const preferenceId = await response.text();
    createCheckoutButton(preferenceId);
} catch (e) {
    alert('error: ' + e);
}
};

const createCheckoutButton = (preferenceId) => {
    const brickBuilder = mp.bricks();
    brickBuilder.create("wallet", "wallet_container", {
        initialization: {
            preferenceId: preferenceId
        }
    });
};


document.addEventListener('DOMContentLoaded', function () {
    const inputCantidad = document.getElementById('cantidad');
    const precioElement = document.getElementById('precio');

    function actualizarPrecio() {
        const cantidad = parseInt(inputCantidad.value) || 0;
        const precioTotal = cantidad * 10;
        precioElement.textContent = `Precio: $${precioTotal}`;
    }

    inputCantidad.addEventListener('input', actualizarPrecio);
});