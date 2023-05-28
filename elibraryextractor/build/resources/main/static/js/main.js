async function author2json() {
	let id = $('#eLibraryId').text();
	let author = await getAuthor(id);
	let data = JSON.stringify(author.articles);
	download(data, `${author.eLibraryId}.json`, 'text/plain');
}

async function article2json() {
	let id = $('#eLibraryId').text();
	let article = await getArticle(id);
	let data = JSON.stringify(article);
	download(data, `${article.eLibraryId}.json`, 'text/plain');
}

function showUpdateArticle(id) {
	window.location = `/article/update?id=${id}`;
}

async function showDeleteArticle(id) {
	let result = await deleteArticle(id)
	if (result) {
		if (window.location.toString().includes('article')) {
			let body = $(document.body)
			body.empty()
			body.append($("<h2>").text('Публикация удалена'));
		} else if (window.location.toString().includes('author')) {
			$(`#${id}`).remove()
		}
	}
}

async function processUpdateArticle() {
	let sources = []
	$('#sources').find('input').each(function () {
		let value = $(this).val()
		if (value != "")
			sources.push(value)
	})
	console.log(sources)
	let data = {
		id: $('#id').text(),
		eLibraryId: $('#eLibraryId').text(),
		fullName: $('#fullName').val(),
		publishingHouse: $('#publishingHouse').val(),
		publishingType: $('#publishingType').val(),
		publishingDate: $('#publishingDate').val(),
		pages: $('#pages').val(),
		conference: $('#conference').val(),
		indexation: $('#indexation').val(),
		sources: sources
	}
	let result = await updateArticle(data)

	// $('#id').text(result.id)
	// $('#eLibraryId').text(result.eLibraryId)
	// $('#fullName').val(result.fullName)
	// $('#publishingHouse').val(result.publishingHouse)
	// $('#publishingType').val(result.publishingType)
	// $('#publishingDate').val(result.publishingDate)
	// $('#pages').val(result.pages)
	// $('#conference').val(result.conference)
	// $('#indexation').val(result.indexation)
}

function addSource() {
	$('#sources').append($('<input/>'))
}

function download(data, filename, type) {
	var file = new Blob([data], { type: type });
	if (window.navigator.msSaveOrOpenBlob) // IE10+
		window.navigator.msSaveOrOpenBlob(file, filename);
	else { // Others
		var a = document.createElement("a"), url = URL.createObjectURL(file);
		a.href = url;
		a.download = filename;
		document.body.appendChild(a);
		a.click();
		setTimeout(function () {
			document.body.removeChild(a);
			window.URL.revokeObjectURL(url);
		}, 0);
	}
}

function search() {
	var id = $('#ftext').val();
	window.location = "/author?id=" + id;
}

async function getAuthor(id) {
	const author = await fetch("/api/author?id=" + id).then((res) => res.status == 200 ? res.json() : null);
	return author;
}

async function getArticle(id) {
	const article = await fetch("/api/article?id=" + id).then((res) => res.status == 200 ? res.json() : null);
	return article;
}

async function updateArticle(data) {
	const requestOptions = {
		method: 'PUT',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(data)
	};
	const article = await fetch("/api/article?id=" + data.eLibraryId, requestOptions)
		.then((res) => res.status == 200 ? res.json() : null);
	return article;
}

async function deleteArticle(id) {
	const requestOptions = {
		method: 'DELETE',
		headers: { 'Content-Type': 'application/json' }
	};
	const response = await fetch("/api/article?id=" + id, requestOptions).then((res) => res.status == 200 ? res.text() : null);
	return response;
}

function getNewArticle(article) {
	return $("<tr/>").append($('<td/>')
		.append($("<a>", { href: `/article?id=${article.eLibraryId}` }).text(article.fullName)));;
}

async function loadArticle(row) {
	let id = $(row).attr("id");
	$(row).parent().parent().parent().append($("<td>").append($("<div>", { class: "boxLoading" })));
	const article = await getArticle(id);
	if (article != null) {
		$(row).parent().parent().parent().remove();
		let newArticle = getNewArticle(article);
		$('#loaded').append(newArticle);
	} else console.log("unable to get article id: " + id)
}


async function loadArticles() {
	const timer = ms => new Promise(res => setTimeout(res, ms))
	let unloaded = $('#unloaded');
	if (unloaded) {
		for (const row of unloaded.find("tr > td > div > a")) {
			await loadArticle(row);
			$('#loading').show();
			await timer(66565);
			$('#loading').hide();
		}
	}
}