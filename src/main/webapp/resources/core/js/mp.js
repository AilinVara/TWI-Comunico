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