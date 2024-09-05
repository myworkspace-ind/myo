
$(document).ready(function() {
	document.getElementById('preview-button').addEventListener('click', function() {
		const fileInput = document.getElementById('file-input');
		const file = fileInput.files[0];

		if (!file) {
			alert('Please select a file.');
			return;
		}

		const reader = new FileReader();

		reader.onload = function(event) {
			const data = new Uint8Array(event.target.result);
			const workbook = XLSX.read(data, { type: 'array' });
			const sheetName = workbook.SheetNames[0]; // Get the first sheet
			const worksheet = workbook.Sheets[sheetName];
			const json = XLSX.utils.sheet_to_json(worksheet, { header: 1 });

			previewExcelData(json);
		};

		reader.readAsArrayBuffer(file);
	});


	function previewExcelData(data) {
		const headers = data[0]; // Assuming the first row contains headers
		const rows = data.slice(1);

		let html = '<h3>Preview Data</h3><table class="table table-bordered"><thead><tr>';

		// Create table headers
		headers.forEach(header => {
			html += `<th style="color: red;">${header}</th>`;
		});

		html += '</tr></thead><tbody>';

		// Create table rows
		rows.forEach(row => {
			html += '<tr>';
			row.forEach(cell => {
				html += `<td style="color: red;">${cell}</td>`;
			});
			html += '</tr>';
		});

		html += '</tbody></table>';

		document.getElementById('preview-container').innerHTML = html;
	}

	document.getElementById('upload-button').addEventListener('click', function() {
		const fileInput = document.getElementById('file-input');
		const file = fileInput.files[0];

		if (!file) {
			alert('Please select a file.');
			return;
		}

		const reader = new FileReader();

		reader.onload = function(event) {
			const data = new Uint8Array(event.target.result);
			const workbook = XLSX.read(data, { type: 'array' });
			const sheetName = workbook.SheetNames[0]; // Get the first sheet
			const worksheet = workbook.Sheets[sheetName];
			const json = XLSX.utils.sheet_to_json(worksheet, { header: 1 });

			processExcelData(json);
		};

		reader.readAsArrayBuffer(file);
	});

	function processExcelData(data) {
		const headers = data[0]; // Assuming the first row contains headers
		const rows = data.slice(1);

		const objectives = rows.map(row => {
			const description = row[headers.indexOf('Description')] || 'Default Description';
			const weight = row[headers.indexOf('Weight')] || '0';
			const keyResultDescription = row[headers.indexOf('KeyResultDescription')] || 'Default Key Result';
			const unit = row[headers.indexOf('Unit')] || 'Number';
			const startValue = row[headers.indexOf('StartValue')] || '0';
			const target = row[headers.indexOf('Target')] || '0';
			const progress = row[headers.indexOf('Progress')] || '0';

			console.log('Description:', description);
			console.log('Weight:', weight);
			console.log('KeyResultDescription:', keyResultDescription);
			console.log('Unit:', unit);
			console.log('StartValue:', startValue);
			console.log('Target:', target);
			console.log('Progress:', progress);

			const objective = {
				description: description,
				status: 'DRAFT',
				weight: weight,
				comment: '',
				keyResults: []
			};

			const keyResult = {
				description: keyResultDescription,
				dueDate: '',
				itype: unit === 'Number' ? 1 : unit === 'Yes/No' ? 2 : unit === 'Percentage' ? 3 : 0,
				progress: progress,
				weight: weight,
				numberResult: startValue,
				numberTarget: target,
				yesNoResult: false,
				yesNoTarget: false,
				percentageResult: startValue,
				percentageTarget: target,
				standard: 'None',
				startvalue: startValue
			};

			console.log('KeyResult Object:', keyResult);

			objective.keyResults.push(keyResult);

			return objective; // Return the created objective
		});

		const requestData = { objectives: objectives };

		if (requestData.objectives.length === 0) {
			console.warn('No data to update');
			return;
		}

		console.log('Sending data:', JSON.stringify(requestData));

		fetch('objectives/uploaddata', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(requestData)
		})
			.then(response => response.text())  // Use text() to log the raw response
			.then(text => {
				console.log('Raw response:', text);
				return JSON.parse(text);  // Try parsing the response manually
			})
			.then(result => {
				console.log('Data successfully updated:', JSON.stringify(result));
				alert('Data successfully updated.');
			})
			.catch(error => {
				console.error('Error updating data:', error.message || error);
				alert('Error updating data. Please check the console for details.');
			});
	}

});
