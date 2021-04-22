const PATH = "http://localhost:8080/tasks"

/* LOAD */
/* Load initial Tasks and show them */
function loadData() {
	const xhr = new XMLHttpRequest()
	xhr.onload = (e) => {
		if (xhr.readyState !== XMLHttpRequest.DONE)
			return

		if (xhr.status === 200)
			renderList(xhr.responseText)
		else
			console.error(xhr.statusText);
	};
	xhr.onerror = (e) => console.error(xhr.statusText)
	xhr.open("GET", PATH, true) // true for asynchronous
	xhr.send(null)
}

/* Render server list */
function renderList(text) {
	console.log("text received: " + text)
	const array = JSON.parse(text)

	for (var i = tasks.children.length - 1; i > 0; i--) {
		const tr = tasks.children[i]
		tr.remove()
	}

	for (const task of array) {
		addTask(task)
	}
}

/* clone template and insert it */
function addTask(text) {
	const clone = taskPrototype.content.children[0].cloneNode(true)
	clone.querySelector('[type="text"]').value = text
	clone.querySelector('.taskId').innerText = tasks.children.length
	tasks.appendChild(clone)
	return clone
}

/* DELETE */
/* remove item from List and server */
function removeTask(button) {
	const tr = button.parentNode.parentNode
	tr.remove()

	const idx = tr.querySelector('.taskId').innerText - 1
	const xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		if (xhr.readyState === XMLHttpRequest.DONE && xhr.status !== 200)
			console.error(xhr.statusText);
	}
	xhr.open("DELETE", `${PATH}/${idx}`, true)
	xhr.send("")
}

/* CREATE */
/* Handle click on ADD button */
function addTaskButton() {
	const clone = addTask("")

	const xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		if (xhr.readyState === XMLHttpRequest.DONE && xhr.status !== 200)
			console.error(xhr.statusText);
	}
	xhr.open("PUT", PATH, true)
	xhr.send("")
}

/* UPDATE */
/* Text modified, save immediately */
function onBlurInput(elem) {
	const tr = elem.parentNode.parentNode
	const idx = tr.querySelector('.taskId').innerText - 1

	const xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		if (xhr.readyState === XMLHttpRequest.DONE && xhr.status !== 200)
			console.error(xhr.statusText);
	}
	const newText = tr.querySelector('[type="text"]').value
	const text = encodeURIComponent(newText)
	xhr.open("POST", `${PATH}/${idx}?text=${text}`, true)
	
	xhr.setRequestHeader("Content-Type", "text/plain")
	xhr.send("")
}

