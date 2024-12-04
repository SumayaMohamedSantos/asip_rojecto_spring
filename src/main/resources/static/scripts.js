// Função de controle para a página de login
function controla() {
    const usr = document.getElementById("username").value;
    const pass = document.getElementById("password").value;

    if (usr === "admin" && pass === "admin") {
        // Redireciona para a página de admin
        window.location.href = "/admin";
        return false; // Impede o envio do formulário
    }
    return true; // Continua o envio do formulário para usuários comuns
}

// Função para exibir alerta de boas-vindas na Form Page
document.addEventListener("DOMContentLoaded", () => {
    const usernameElement = document.querySelector("[th\\:text='${username}']");
    if (usernameElement) {
        const username = usernameElement.textContent;
        alert(`Welcome, ${username}!`);
    }
});
