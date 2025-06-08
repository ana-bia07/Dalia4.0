
console.log("JS rodando");
document.addEventListener('DOMContentLoaded', () => {
    const useContraceptive = document.getElementById('useContraceptive');
    const contraceptiveType = document.getElementById('contraceptiveType');
    const labelContraceptiveType = document.getElementById('labelContraceptiveType');

    function toggleContraceptiveType() {
        if (useContraceptive.value === 'true') {
            contraceptiveType.style.display = 'inline-block';
            labelContraceptiveType.style.display = 'block';
        } else {
            contraceptiveType.style.display = 'none';
            labelContraceptiveType.style.display = 'none';
            contraceptiveType.value = ""; // limpa seleção
        }
    }

    // Inicializa no carregamento da página
    toggleContraceptiveType();

    // Atualiza quando mudar o select principal
    useContraceptive.addEventListener('change', toggleContraceptiveType);
});

function mostrarDenuncia() {
    const caixa = document.getElementById("caixaDenuncia");
    caixa.style.display = caixa.style.display === "none" ? "block" : "none";
}



