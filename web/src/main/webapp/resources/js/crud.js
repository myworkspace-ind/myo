document.addEventListener('DOMContentLoaded', function() {

	$('#jstree').jstree();
	var container = document.getElementById('okr-table');
	var hot;
	const dropdownMenu = document.getElementById('period-dropdown');
	var currentData = [];

	var hotSettings = {
		data: [],
		colHeaders: ['No', 'Period', 'Start date', 'End date', 'Current period'],
		columns: [
			{ data: 'No', readOnly: true },
			{ data: 'name' },
			{ data: 'startDate' },
			{ data: 'endDate' },
			{ data: 'currentPeriod' }
		],
		stretchH: 'all',
		autoWrapRow: true,
		height: 'auto',
		licenseKey: 'non-commercial-and-evaluation',
		afterChange: function(changes, source) {
			if (source === 'loadData') return;
			currentData = hot.getData();
		}
	};

	hot = new Handsontable(container, hotSettings);

	function addRow() {
		var newRow = {
			No: hot.countRows() + 1,
			name: '',
			startDate: '',
			endDate: '',
			currentPeriod: ''
		};

		hot.alter('insert_row_below', hot.countRows(), 1);
		var rowIndex = hot.countRows() - 1;

		hot.setDataAtRowProp(rowIndex, 'No', newRow.No);
		hot.setDataAtRowProp(rowIndex, 'name', newRow.name);
		hot.setDataAtRowProp(rowIndex, 'startDate', newRow.startDate);
		hot.setDataAtRowProp(rowIndex, 'endDate', newRow.endDate);
		hot.setDataAtRowProp(rowIndex, 'currentPeriod', newRow.currentPeriod);

		hot.updateSettings({
			cells: function(row, col) {
				var cellProperties = {};
				if (row === rowIndex) {
					cellProperties.readOnly = false;
				}
				return cellProperties;
			}
		});
	}

	fetch('period/loaddata')
		.then(response => {
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			return response.json();
		})
		.then(jsonData => {
			var data = [];
			var counter = 1;

			if (jsonData.data && Array.isArray(jsonData.data)) {
				for (var i = 0; i < jsonData.data.length; i++) {
					var item = jsonData.data[i];
					var itemData = {
						No: counter++,
						name: item.name,
						startDate: item.startDate,
						endDate: item.endDate,
						currentPeriod: item.currentPeriod
					};
					data.push(itemData);

					if (item.childs && Array.isArray(item.childs)) {
						for (var j = 0; j < item.childs.length; j++) {
							var childItem = item.childs[j];
							var childData = {
								No: counter++,
								name: childItem.name,
								startDate: childItem.startDate,
								endDate: childItem.endDate,
								currentPeriod: childItem.currentPeriod
							};
							data.push(childData);
						}
					}
				}

				const testData = [
					{ name: "Period 1" },
					{ name: "Period 2" },
					{ name: "Period 3" }
				];
				console.log("never gonna give you up");
				dropdownMenu.innerHTML = testData.map(period =>
					`<li><a class="dropdown-item" href="#">${period.name}</a></li>`
				).join('');
				// Assuming hot is a Handsontable instance and needs data
				hot.loadData(data);
				currentData = data;
			} else {
				console.error('Unexpected data structure in the JSON data');
			}
		})
		.catch(error => {
			console.error('Error fetching data:', error);
		});

	document.getElementById('MKSOLUpdateperiod').addEventListener('click', function() {
		if (currentData.length === 0) {
			console.warn('No data to update');
			return;
		}
		const formattedData = currentData.map(item => {
			console.log('Mapping item:', item);
			return {
				name: item[1] || "defaultName",
				startDate: item[2] || "defaultStartDate",
				endDate: item[3] || "defaultEndDate",
				parentId: null,
				setAsRoot: true,
				currentPeriod: item[4] === true || item[4] === "true"  // Convert item[4] to boolean
			};
		});
		const lastItem = formattedData[formattedData.length - 1];
		console.log(JSON.stringify(formattedData));
		console.log(JSON.stringify(lastItem));

		fetch('period/uploaddata', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},

			body: JSON.stringify(lastItem),
		})
			.then(response => response.json())
			.then(result => {
				console.log('Data successfully updated:', result);
				console.log(lastItem);
			})
			.catch(error => {
				console.error('Error updating data:', error);
			});
	});

	document.getElementById('MKSOLAddRow').addEventListener('click', function() {
		addRow();
	});

	var containerLayout = document.getElementById('okr-layouttable');
	var hotLayout = new Handsontable(containerLayout, {
		data: [],
		colHeaders: ['No', 'Objectives', 'Weight', 'Key results', 'Unit', 'Start value', 'Target', 'Delete'],
		columns: [
			{ data: 'No' },
			{ data: 'Objectives' },
			{ data: 'Weight' },
			{ data: 'Key results' },
			{ data: 'Unit' },
			{ data: 'Start value' },
			{ data: 'Target' },
			{
				data: 'Delete',
				renderer: function(instance, TD, row, col, prop, value, cellProperties) {
					var button = document.createElement('button');
					button.innerHTML = 'Delete';

					button.style.border = '1px solid white';
					button.style.backgroundColor = 'white';
					button.style.boxShadow = 'none';
					button.style.padding = '5px 10px';
					button.style.cursor = 'pointer';
					button.style.borderRadius = '3px';

					button.onclick = function() {
						var selectedRow = row;
						var name = hotLayout.getDataAtRowProp(selectedRow, 'Objectives');
						hotLayout.alter('remove_row', selectedRow, 1);
						adjustMergeCells(selectedRow);
						deleteRowFromDatabase(
							name,
							'objectives/deletedata',
							function(data) {
								console.log('Row successfully deleted: ', data);
							},
							function(error) {
								console.error('Failed to delete row: ', error);
							}
						);
					};

					Handsontable.dom.empty(TD);
					TD.appendChild(button);
				}
			}
		],
		stretchH: 'all',
		autoWrapRow: true,
		height: 'auto',
		licenseKey: 'non-commercial-and-evaluation',
		mergeCells: []
	});

	function fetchDataLayout() {
		var urlLayout = 'objectives/loaddata';
		var xhrLayout = new XMLHttpRequest();
		xhrLayout.open('GET', urlLayout, true);
		xhrLayout.onreadystatechange = function() {
			if (xhrLayout.readyState === XMLHttpRequest.DONE) {
				if (xhrLayout.status === 200) {
					var jsonResponseLayout = JSON.parse(xhrLayout.responseText);
					var objectivesLayout = jsonResponseLayout.data.objectives;

					objectivesLayout.sort((a, b) => {
						if (a.description < b.description) return -1;
						if (a.description > b.description) return 1;
						if (a.keyResults[0].description < b.keyResults[0].description) return -1;
						if (a.keyResults[0].description > b.keyResults[0].description) return 1;
						return 0;
					});

					var dataLayout = [];
					var mergeRangesLayout = [];
					var objectiveCounterLayout = 1;

					objectivesLayout.forEach(function(objective) {
						var firstRowLayout = dataLayout.length;

						objective.keyResults.forEach(function(keyResult, krIndex) {
							var unitLayout;
							switch (keyResult.itype) {
								case 1:
									unitLayout = 'Number';
									break;
								case 2:
									unitLayout = 'Yes/No';
									break;
								case 3:
									unitLayout = 'Percentage';
									break;
								default:
									unitLayout = '';
									break;
							}

							dataLayout.push({
								No: krIndex === 0 ? objectiveCounterLayout : '',
								Objectives: objective.description,
								Weight: objective.weight,
								"Key results": keyResult.description,
								Unit: unitLayout,
								"Start value": keyResult.startvalue,
								Target: keyResult.target,
								Delete: ''
							});
						});

						objectiveCounterLayout++;

						if (objective.keyResults.length > 1) {
							mergeRangesLayout.push({
								row: firstRowLayout,
								col: 0,
								rowspan: objective.keyResults.length,
								colspan: 1
							});
							mergeRangesLayout.push({
								row: firstRowLayout,
								col: 1,
								rowspan: objective.keyResults.length,
								colspan: 1
							});
							mergeRangesLayout.push({
								row: firstRowLayout,
								col: 2,
								rowspan: objective.keyResults.length,
								colspan: 1
							});
						}
					});

					hotLayout.loadData(dataLayout);
					hotLayout.updateSettings({
						mergeCells: mergeRangesLayout
					});

				} else {
					console.error('Failed to fetch data. Status:', xhrLayout.status);
				}
			}
		};
		xhrLayout.send();
	}

	function adjustMergeCells(rowToRemove) {
		var mergeCells = hotLayout.getSettings().mergeCells;

		mergeCells.forEach((merge) => {
			if (rowToRemove >= merge.row && rowToRemove < merge.row + merge.rowspan) {
				if (rowToRemove === merge.row) {
					merge.rowspan--;
				} else if (rowToRemove === merge.row + merge.rowspan - 1) {
					merge.rowspan--;
				}
			}
		});

		hotLayout.updateSettings({
			mergeCells: mergeCells
		});
	}

	function submitDataLayout() {
		var urlLayout = 'objectives/uploaddata';
		var dataLayout = hotLayout.getData();
		var mergeCellsLayout = hotLayout.getSettings().mergeCells;

		var formattedDataLayout = [];
		var rowMapLayout = new Map();

		function getRowEntryLayout(rowIndex) {
			if (!rowMapLayout.has(rowIndex)) {
				rowMapLayout.set(rowIndex, {
					No: '',
					Objectives: '',
					Weight: '',
					'Key results': '',
					Unit: '',
					'Start value': '',
					Target: '',
					'Delete': ''
				});
			}
			return rowMapLayout.get(rowIndex);
		}

		dataLayout.forEach((row, index) => {
			var rowEntryLayout = getRowEntryLayout(index);
			rowEntryLayout.No = row[0];
			rowEntryLayout.Objectives = row[1];
			rowEntryLayout.Weight = row[2];
			rowEntryLayout['Key results'] = row[3];
			rowEntryLayout.Unit = row[4];
			rowEntryLayout['Start value'] = row[5];
			rowEntryLayout.Target = row[6];
			rowEntryLayout['Delete'] = row[7];
		});

		mergeCellsLayout.forEach(merge => {
			const { row, col, rowspan, colspan } = merge;
			const topLeftCellValue = dataLayout[row][col];

			for (let r = row; r < row + rowspan; r++) {
				for (let c = col; c < col + colspan; c++) {
					var rowEntryLayout = getRowEntryLayout(r);
					switch (c) {
						case 0:
							rowEntryLayout.No = topLeftCellValue;
							break;
						case 1:
							rowEntryLayout.Objectives = topLeftCellValue;
							break;
						case 2:
							rowEntryLayout.Weight = topLeftCellValue;
							break;
						case 3:
							rowEntryLayout['Key results'] = topLeftCellValue;
							break;
						case 4:
							rowEntryLayout.Unit = topLeftCellValue;
							break;
						case 5:
							rowEntryLayout['Start value'] = topLeftCellValue;
							break;
						case 6:
							rowEntryLayout.Target = topLeftCellValue;
							break;
						case 7:
							rowEntryLayout['Delete'] = topLeftCellValue;
							break;
					}
				}
			}
		});

		formattedDataLayout = Array.from(rowMapLayout.values());

		var xhrLayout = new XMLHttpRequest();
		xhrLayout.open('POST', urlLayout, true);
		xhrLayout.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
		xhrLayout.onreadystatechange = function() {
			if (xhrLayout.readyState === XMLHttpRequest.DONE) {
				if (xhrLayout.status === 200) {
					console.log('Data successfully submitted.', JSON.stringify(formattedDataLayout));
				} else {
					console.error('Failed to submit data. Status:', xhrLayout.status);
				}
			}
		};
		xhrLayout.send(JSON.stringify(formattedDataLayout));
	}

	function addRowLayout() {
		var newRowLayout = {
			No: hotLayout.countRows() + 1,
			Objectives: '',
			Weight: '',
			"Key results": '',
			Unit: '',
			"Start value": '',
			Target: '',
			Delete: ''
		};

		hotLayout.alter('insert_row_below', hotLayout.countRows(), 1);

		var rowIndexLayout = hotLayout.countRows() - 1;

		hotLayout.setDataAtRowProp(rowIndexLayout, 'No', newRowLayout.No);
		hotLayout.setDataAtRowProp(rowIndexLayout, 'Objectives', newRowLayout.Objectives);
		hotLayout.setDataAtRowProp(rowIndexLayout, 'Weight', newRowLayout.Weight);
		hotLayout.setDataAtRowProp(rowIndexLayout, 'Key results', newRowLayout["Key results"]);
		hotLayout.setDataAtRowProp(rowIndexLayout, 'Unit', newRowLayout.Unit);
		hotLayout.setDataAtRowProp(rowIndexLayout, 'Start value', newRowLayout["Start value"]);
		hotLayout.setDataAtRowProp(rowIndexLayout, 'Target', newRowLayout.Target);
		hotLayout.setDataAtRowProp(rowIndexLayout, 'Delete', newRowLayout["Delete"]);

		hotLayout.updateSettings({
			cells: function(row, col, prop) {
				var cellProperties = {};
				if (row === rowIndexLayout) {
					cellProperties.readOnly = false;
				}
				return cellProperties;
			}
		});
	}

	fetchDataLayout();

	function updateOkrDashboardFromUrl(url) {
		fetch(url)
			.then(response => {
				if (!response.ok) {
					throw new Error('Network response was not ok');
				}
				return response.json();
			})
			.then(jsonResponse => {
				var data = jsonResponse.data;
				var objectives = data.objectives;

				var contentContainer = document.querySelector('.content');
				contentContainer.innerHTML = '';

				objectives.forEach(function(objective) {
					var card = document.createElement('div');
					card.classList.add('card');

					var cardBody = document.createElement('div');
					cardBody.classList.add('card-body');

					var htmlContent = `
                        <h5 class="card-title">${objective.description}</h5>
                        <p class="card-text">Weight: ${objective.weight}%, Progress: ${objective.progress.toFixed(2)}%</p>
                        <p class="card-text">Status: ${objective.status}</p>
                    `;
					cardBody.innerHTML = htmlContent;

					objective.keyResults.forEach(function(keyResult) {
						var keyResultCard = document.createElement('div');
						keyResultCard.classList.add('card');
						keyResultCard.classList.add('mt-3');

						var keyResultCardBody = document.createElement('div');
						keyResultCardBody.classList.add('card-body');

						var keyResultHtmlContent = `
                            <h6 class="card-title">${keyResult.description}</h6>
                            <p class="card-text">Progress: ${keyResult.progress.toFixed(2)}%, Target: ${keyResult.target}%</p>
                            <p class="card-text">Due Date: ${keyResult.dueDate}</p>
                        `;
						keyResultCardBody.innerHTML = keyResultHtmlContent;

						keyResultCard.appendChild(keyResultCardBody);
						cardBody.appendChild(keyResultCard);
					});
					card.appendChild(cardBody);
					contentContainer.appendChild(card);
				});
			})
			.catch(error => {
				console.error('Error fetching data:', error);
			});
	}

	function deleteRowFromDatabase(name, url, onSuccess, onError) {
		var completeUrl = `${url}/${name}`;
		console.log('Request URL:', completeUrl); // Log the complete URL

		fetch(completeUrl, {
			method: 'DELETE', // Ensure the method is DELETE
			headers: {
				'Content-Type': 'application/json' // Optional
			}
		})
			.then(response => {
				if (!response.ok) {
					console.log('Response error:', response.status);
					throw new Error('Network response was not ok');
				}
				return response.text(); // Adjust based on expected response
			})
			.then(data => {
				if (onSuccess) {
					onSuccess(data); // Handle response data
				}
			})
			.catch((error) => {
				console.error('Error:', error);
				if (onError) {
					onError(error);
				}
			});
	}

	var apiUrl = 'objectives/loaddata';
	updateOkrDashboardFromUrl(apiUrl);
	submitDataLayout()
});