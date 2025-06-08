document.addEventListener("DOMContentLoaded", () => {
	flatpickr("#menstruationDate", {
		dateFormat: "Y-m-d"
	});

	const sections = document.querySelectorAll(".section");
	let current = [...sections].findIndex(sec => sec.classList.contains("active"));

	function showSection(index) {
		sections.forEach((sec, i) => {
			sec.classList.remove("active");
		});
		sections[index].classList.add("active");
		current = index; // Atualiza a seção atual corretamente
	}

	document.querySelectorAll(".proximo").forEach(btn => {
		btn.addEventListener("click", () => {
			if (current < sections.length - 1) {
				showSection(current + 1);
			}
		});
	});

	document.querySelectorAll(".volta").forEach(btn => {
		btn.addEventListener("click", () => {
			if (current > 0) {
				showSection(current - 1);
			}
		});
	});
});