document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".botaoLogin").addEventListener("click", async (event) => {
        event.preventDefault();

        const email = document.getElementById("exampleInputEmail1").value.trim();
        const password = document.getElementById("exampleInputPassword1").value.trim();

        if (!email || !password) {
            alert("Preencha todos os campos.");
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/Login/Login.html", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password }),
            });

            const result = await response.json();

            if (response.ok) {
                alert(result.message);
                window.location.href = "/forum/index.html"; // Redireciona ao sucesso
            } else {
                alert(result.message); // Exibe erro
            }
        } catch (error) {
            console.error("Erro ao fazer login:", error);
            alert("Erro ao conectar ao servidor.");
        }
    });
});