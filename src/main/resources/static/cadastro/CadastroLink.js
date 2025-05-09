document.addEventListener("DOMContentLoaded", () => {
    document.querySelector("button").addEventListener("click", async (event) => {
        event.preventDefault();

        const name = document.getElementById("name").value.trim();
        const surname = document.getElementById("lastname").value.trim();
        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value.trim();
        const passConfirmation = document.getElementById("passconfirmation").value.trim();
        const termsChecked = document.querySelector("input[type=checkbox]").checked;

        let errors = [];

        if (!name) errors.push("Nome é obrigatório.");
        if (!surname) errors.push("Sobrenome é obrigatório.");
        if (!email.includes("@")) errors.push("E-mail inválido.");
        if (password.length < 6) errors.push("A senha deve ter pelo menos 6 caracteres.");
        if (password !== passConfirmation) errors.push("As senhas não coincidem.");
        if (!termsChecked) errors.push("Você deve aceitar os termos e condições.");

        if (errors.length > 0) {
            alert(errors.join("\n"));
            return;
        }

        const userData = { name, surname, email, password };


        try {
            const response = await fetch("http://localhost:8080/users", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(userData),
            });

            const result = response.status !== 204 ? await response.json() : {};

            if (response.ok) {
                alert(result.message || "Usuário cadastrado com sucesso!");
                window.location.href = "../LandingPage/LandingPage.html";
            } else {
                alert(result.error || "Erro ao cadastrar usuário.");
            }
        } catch (error) {
            console.error("Erro ao cadastrar:", error);
            alert("Erro ao conectar ao servidor.");
        }
    });
});
