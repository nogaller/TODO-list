const PATH = "http://localhost:8080/tasks"

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

function removeTask(button) {
	const tr = button.parentNode.parentNode
	tr.remove()

	const idx = tr.children[0].innerText - 1
	const xhr = new XMLHttpRequest();
	xhr.onreadystatechange = () => {
		if (xhr.readyState === XMLHttpRequest.DONE && xhr.status !== 200)
			console.error(xhr.statusText);
	}
	xhr.open("DELETE", `${PATH}/${idx}`, true)
	xhr.send("")
}

function addTask(text) {
	const clone = taskPrototype.content.children[0].cloneNode(true)
	clone.querySelector('[type="text"]').value = text
	clone.children[0].innerText = tasks.children.length
	tasks.appendChild(clone)
	return clone
}

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

function onBlurInput(elem) {
	const tr = elem.parentNode.parentNode
	const idx = tr.children[0].innerText - 1

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

