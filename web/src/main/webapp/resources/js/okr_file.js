/*Sample data:
Description	Weight	KeyResultDescription	Unit	StartValue	Target	Progress
Love1	100	NotLove1	Number	10	100	10
NotLove	100	Love	Number	20	200	20
Tuan	90	Fall	Number	30	300	30
Trung	80	Winter	Number	40	100	20
My	77	Test	Number	23	50	46
Thach	120	Law	Number	70	100	50
Tuan	30	Mouse	Number	55	120	30*/

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
		const headers = data[0]; // First row contains headers
		const rows = data.slice(1); // Remaining rows contain data

		// Step 1: Remove duplicate rows from Excel data
		function createRowKey(row) {
			return row.map(cell => String(cell).trim()).join('|');
		}

		const uniqueRows = Array.from(new Set(rows.map(createRowKey)))
			.map(key => rows.find(row => createRowKey(row) === key));

		console.log("Preprocessed unique rows:", uniqueRows);

		// Step 2: Fetch existing objectives from the database
		fetch('objectives/loaddata', { method: 'GET' })
			.then(response => {
				if (!response.ok) {
					throw new Error(`HTTP error! Status: ${response.status}`);
				}
				return response.json();
			})
			.then(responseData => {
				const existingObjectives = responseData.data?.objectives || [];
				if (!Array.isArray(existingObjectives)) {
					console.error('Unexpected response format:', existingObjectives);
					alert('Error fetching existing objectives. Please check the console for details.');
					return;
				}

				// Helper function to create a unique key for existing objectives
				function createObjectiveKey(obj) {
					if (!obj.keyResults || !Array.isArray(obj.keyResults)) {
						console.error('Invalid keyResults format:', obj.keyResults);
						return '';
					}
					return `${obj.description}|${obj.weight}|${obj.keyResults.map(kr =>
						`${kr.description}|${kr.itype}|${kr.result}|${kr.target}|${kr.progress}`).join('|')}`;
				}

				function createObjectiveKeySample(obj) {
					if (!obj.keyResults || !Array.isArray(obj.keyResults)) {
						console.error('Invalid keyResults format:', obj.keyResults);
						return '';
					}
					return `${obj.description}|${obj.weight}|${obj.keyResults.map(kr =>
						`${kr.description}|${kr.itype}|${kr.startvalue}|${kr.numberTarget}|${kr.progress}`).join('|')}`;
				}

				const existingObjectivesKeys = new Set(existingObjectives.map(createObjectiveKey));
				console.log('Existing Objectives Keys:', existingObjectivesKeys);

				// Step 3: Convert unique rows to objectives and filter out existing objectives
				const objectives = uniqueRows.map(row => {
					const description = row[headers.indexOf('Description')] || 'Default Description';
					const weight = parseFloat(row[headers.indexOf('Weight')]) || 0;
					const keyResultDescription = row[headers.indexOf('KeyResultDescription')] || 'Default Key Result';
					const unit = row[headers.indexOf('Unit')] || 'Number';
					const startValue = parseFloat(row[headers.indexOf('StartValue')]) || 0;
					const target = parseFloat(row[headers.indexOf('Target')]) || 0;
					const progress = parseFloat(row[headers.indexOf('Progress')]) || 0;

					/*console.log('Processing row:');
					console.log('Description:', description);
					console.log('Weight:', weight);
					console.log('Key Result Description:', keyResultDescription);
					console.log('Unit:', unit);
					console.log('Start Value:', startValue);
					console.log('Target:', target);
					console.log('Progress:', progress);*/

					const keyResult = {
						description: keyResultDescription,
						dueDate: '',  // Assuming you have a way to get due date
						itype: unit === 'Number' ? 1 : unit === 'Yes/No' ? 2 : unit === 'Percentage' ? 3 : 0,
						progress: progress,
						weight: weight,
						numberResult: unit === 'Number' ? startValue : 0,
						numberTarget: unit === 'Number' ? target : 0,
						yesNoResult: unit === 'Yes/No' ? (startValue > 0 ? true : false) : false,
						yesNoTarget: unit === 'Yes/No' ? (target > 0 ? true : false) : false,
						percentageResult: unit === 'Percentage' ? startValue : 0,
						percentageTarget: unit === 'Percentage' ? target : 0,
						standard: 'None',
						startvalue: startValue
					};

					return {
						description: description,
						status: 'DRAFT',
						weight: weight,
						comment: '',
						keyResults: [keyResult]
					};
				});

				console.log('Processed objectives:', objectives);

				// Filter out objectives that already exist in the database
				const uniqueToAdd = objectives.filter(obj => {
					const objKey = createObjectiveKeySample(obj);
					console.log("ObjKey: " + objKey);
					return !existingObjectivesKeys.has(objKey);
				});

				if (uniqueToAdd.length === 0) {
					console.warn('No new data to update');
					return;
				}

				// Step 4: Upload new objectives to the database
				uniqueToAdd.forEach(objective => {
					const requestData = { objectives: [objective] };

					if (requestData.objectives.length === 0) {
						console.warn('No data to update');
						return;
					}

					//console.log('Sending data:', JSON.stringify(requestData));

					fetch('objectives/uploaddata', {
						method: 'POST',
						headers: {
							'Content-Type': 'application/json'
						},
						body: JSON.stringify(requestData)
					})
						.then(response => {
							if (!response.ok) {
								throw new Error(`HTTP error! Status: ${response.status}`);
							}
							return response.json();
						})
						.then(result => {
							console.log('Data successfully updated:', JSON.stringify(result));
						})
						.catch(error => {
							console.error('Error updating data:', error);
						});
				});

				alert('Data processing completed.');
			})
			.catch(error => {
				console.error('Error:', error);
				alert('Error processing data. Please check the console for details.');
			});
	}
});