const API_URL = "http://localhost:8080/api/users";

document.getElementById('register-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch(`${API_URL}/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, email, password })
        });

        if (response.ok) {
            alert("Registrazione completata! Ora puoi accedere.");
            window.location.href = 'login.html';
        } else {
            alert("Errore: l'email potrebbe essere già esistente.");
        }
    } catch (error) {
        console.error("Errore durante la registrazione:", error);
    }
});