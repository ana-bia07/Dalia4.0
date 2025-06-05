document.addEventListener("DOMContentLoaded", function () {
    fetch("/calendar-data")
        .then(response => response.json())
        .then(data => {
            const eventos = [];

            if (data.error) {
                alert(data.error[0]);
                return;
            }

            const adicionarEventos = (lista, titulo, cor) => {
                lista.forEach(date => {
                    eventos.push({
                        title: "",
                        start: date,
                        display: 'background',
                        backgroundColor: cor
                    });
                });
            };

            adicionarEventos(data.menstruacao, "Menstruação", "#FF4D4D");
            adicionarEventos(data.fertil, "Fértil", "#4D90FE");
            adicionarEventos(data.ovulacao, "Ovulação", "#4CAF50");

            const calendarEl = document.getElementById("calendar");

            const calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: "dayGridMonth",
                height: "auto",
                contentHeight: "auto",
                aspectRatio: 1.35,
                headerToolbar: {
                    left: "prev,next today",
                    center: "title",
                    right: ""
                },
                events: eventos
            });

            calendar.render();
        });
});
