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
const tasksContainer = document.getElementById('task-container');

// caricamento task
async function loadTasks() {
    
    try {
        //filtro per id 
        const response = await fetch(`${API_TASKS} ?userId = ${currentUser.id}`);
        let tasks = await response.json();

        const filterStatus = document.getElementById('filter-status').value;
        const filterPriority = document.getElementById('filter-priority').value;

        renderTasks(tasks);
    } catch (error) {
        console.error("Errore caricamento task: ", error);
    }

}

function renderTasks(tasks) {
    tasksContainer.innerHTML = '';
    tasks.forEach(task => {
        const card = document.createElement('div');
        card.className = `task-card ${task.priorità}`;
        card.innerHTML = `
            <h3>${task.titolo}</h3>
            <p>${task.descrizione}</p>
            <div class="task-meta">
                <span>📅 ${task.dataScadenza || 'N/A'}</span> | <span>  ${task.stato}</span>
            </div>
            <div class="task-actions">
                <button onclick="editTask(${task.id})" class="btn-edit">Modifica</button>
                <button onclick="deleteTask(${task.id})" class="btn-delete">Elimina</button>
            </div>
        `;
        tasksContainer.appendChild(card);
    });
}

// --- CREAZIONE / MODIFICA ---
taskForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const taskId = document.getElementById('task-id').value;
    
    const taskData = {
        titolo: document.getElementById('task-title').value,
        descrizione: document.getElementById('task-desc').value,
        dataScadenza: document.getElementById('task-date').value,
        priorità: document.getElementById('task-priority').value,
        stato: document.getElementById('task-status').value,
        user_id: currentUser.id // Collega il task all'utente loggato
    };

    const method = taskId ? 'PUT' : 'POST';
    const url = taskId ? `${API_TASKS}/${taskId}` : API_TASKS;

    await fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(taskData)
    });

    closeModalFunc();
    loadTasks();
});

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