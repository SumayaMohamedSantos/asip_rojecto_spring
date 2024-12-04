// Função de controle para a página de login
function controla() {
    const usr = document.getElementById("username").value;
    const pass = document.getElementById("password").value;

    if (usr === "admin" && pass === "admin") {
        open("http://localhost:8081/admin");
        document.getElementById("username").value = "";
        document.getElementById("password").value = "";
        return false;
    }
    return true;
}

// Função para exibir alerta de boas-vindas na Form Page
document.addEventListener("DOMContentLoaded", () => {
    const usernameElement = document.querySelector("[th\\:text='${username}']");
    if (usernameElement) {
        const username = usernameElement.textContent;
        alert(`Welcome, ${username}!`);
    }
});
