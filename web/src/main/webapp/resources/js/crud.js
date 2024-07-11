document.addEventListener('DOMContentLoaded', function() {
	$('#jstree').jstree();

	fetch('/myo-web/crud/loaddata')
		.then(response => {
			if (!response.ok) {
				throw new Error('Network response was not ok ' + response.statusText);
			}
			return response.json();
		})
		.then(data => {
			const table = document.createElement('table');
			table.classList.add('table', 'table-striped');

			const thead = document.createElement('thead');
			const headers = data.colHeaders;
			const headerRow = document.createElement('tr');

			headers.forEach(header => {
				const th = document.createElement('th');
				th.textContent = header;
				headerRow.appendChild(th);
			});

			thead.appendChild(headerRow);
			table.appendChild(thead);

			const tbody = document.createElement('tbody');

			data.data.forEach(row => {
				const tr = document.createElement('tr');
				row.forEach(cell => {
					const td = document.createElement('td');
					td.textContent = cell;
					tr.appendChild(td);
				});
				tbody.appendChild(tr);
			});

			table.appendChild(tbody);
			document.getElementById('okr-table').appendChild(table);
		})
		.catch(error => console.error('Error fetching OKR data:', error));
});