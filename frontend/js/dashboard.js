const API_TASKS = "http://localhost:8080/api/tasks";
const currentUser = JSON.parse(localStorage.getItem('currentUser'));

// controllo di accesso
if(!currentUser) {
    window.location.href = 'login.html';
} else {
    document.getElementById('welcome-user').textContent = `Benvenuto, ${currentUser.username}`;
}

const taskModal = document.getElementById('task-modal');
const taskForm = document.getElementById('task-form');
const tasksContainer = document.getElementById('tasks-container');

// caricamento task
async function loadTasks() {
    
    try {
        //filtro per id 
        const response = await fetch(`${API_TASKS}?userId=${currentUser.id}`);
        let tasks = await response.json();

        const filterStatus = document.getElementById('filter-status').value;
        const filterPriority = document.getElementById('filter-priority').value;

        if (filterStatus !== 'ALL') tasks = tasks.filter(t => t.stato === filterStatus);
        if (filterPriority !== 'ALL') tasks = tasks.filter(t => t.priorita === filterPriority);

        renderTasks(tasks);
    } catch (error) {
        console.error("Errore caricamento task: ", error);
    }

}

function renderTasks(tasks) {
    tasksContainer.innerHTML = '';

    if (tasks.length === 0) {
        tasksContainer.innerHTML = '<p class="no-tasks">Nessuna task trovata.</p>';
        return;
    }

    // 1. Ordiniamo i task per data (dalla più recente alla più lontana o viceversa)
    tasks.sort((a, b) => new Date(a.dataTask) - new Date(b.dataTask));

    let lastDate = null;

    tasks.forEach(task => {
        // 2. Controlliamo se la data del task corrente è diversa dall'ultima processata
        const currentDate = task.dataTask || 'Senza Data';

        if (currentDate !== lastDate) {
            // Creiamo un elemento separatore per la nuova data
            const separator = document.createElement('div');
            separator.className = 'date-separator';
            separator.innerHTML = `<span>📅 Scadenza: ${formatDate(currentDate)}</span>`;
            tasksContainer.appendChild(separator);
            
            lastDate = currentDate;
        }

        // 3. Creiamo la card del task (come facevi prima)
        const card = document.createElement('div');
        card.className = `task-card ${task.priorita}`;
        card.innerHTML = `
            <h3>${task.titolo}</h3>
            <p>${task.descrizione}</p>
            <div class="task-meta">
                <span>Priorità: <strong>${task.priorita}</strong></span> | <span>Stato: ${task.stato}</span>
            </div>
            <div class="task-actions">
                <button onclick="editTask(${task.id})" class="btn-edit">Modifica</button>
                <button onclick="deleteTask(${task.id})" class="btn-delete">Elimina</button>
            </div>
        `;
        tasksContainer.appendChild(card);
    });
}
function formatDate(dateString) {
    if (dateString === 'Senza Data') return dateString;
    
    const date = new Date(dateString);
    
    // Controlla se la data è valida per evitare "Invalid Date"
    if (isNaN(date.getTime())) return dateString;

    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    return date.toLocaleDateString('it-IT', options);
}

// --- CREAZIONE / MODIFICA ---
taskForm.addEventListener('submit', async (e) => {
    
    const taskId = document.getElementById('task-id').value;
    
    const taskData = {
        titolo: document.getElementById('task-title').value,
        descrizione: document.getElementById('task-desc').value,
        dataTask: document.getElementById('task-date').value,
        priorita: document.getElementById('task-priority').value,
        stato: document.getElementById('task-status').value,
        user_id: Number(currentUser.id) // Collega il task all'utente loggato
    };

    const method = taskId ? 'PUT' : 'POST';
    const url = taskId ? `${API_TASKS}/${taskId}` : API_TASKS;

    await fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(taskData)
    });
    if(response.ok){
        await loadTasks();
    }

    closeModalFunc();
    loadTasks();
});



// --- MODIFICA ---
async function editTask(id) {
    try {
        // Recuperiamo i dati della task specifica dal backend
        const response = await fetch(`${API_TASKS}/${id}`);
        if (!response.ok) throw new Error("Impossibile recuperare la task");
        
        const task = await response.json();

        // Popoliamo i campi del form con i dati ricevuti
        document.getElementById('task-id').value = task.id; // Campo nascosto fondamentale!
        document.getElementById('task-title').value = task.titolo;
        document.getElementById('task-desc').value = task.descrizione;
        document.getElementById('task-date').value = task.dataTask;
        document.getElementById('task-priority').value = task.priorita;
        document.getElementById('task-status').value = task.stato;

        // Cambiamo il titolo della modale per chiarezza
        document.getElementById('modal-title').textContent = "Modifica Task";
        
        // Apriamo la modale
        taskModal.style.display = 'block';
    } catch (error) {
        console.error("Errore nel recupero della task:", error);
        alert("Errore nel caricamento dei dati della task.");
    }
}




// --- ELIMINAZIONE ---
async function deleteTask(id) {
    if (confirm("Sei sicuro di voler eliminare questo task?")) {
        await fetch(`${API_TASKS}/${id}`, { method: 'DELETE' });
        loadTasks();
    }
}

// --- GESTIONE MODALE ---
document.getElementById('open-modal-btn').onclick = () => {
    taskForm.reset();
    document.getElementById('task-id').value = '';
    document.getElementById('modal-title').textContent = "Nuova Task";
    taskModal.style.display = 'block';
};

function closeModalFunc() {
    taskModal.style.display = 'none';
}

document.querySelector('.close-modal').onclick = closeModalFunc;

// --- LOGOUT ---
document.getElementById('logout-btn').onclick = () => {
    localStorage.removeItem('currentUser');
    window.location.href = 'login.html';
};

// --- FILTRI ---
document.getElementById('filter-status').onchange = loadTasks;
document.getElementById('filter-priority').onchange = loadTasks;

// Inizializza
loadTasks();