/*import React from 'react';
import { Flex, Progress } from 'antd';
const App = () => (
  <Flex gap="small" vertical>
	<Progress percent={30} />
	<Progress percent={50} status="active" />
	<Progress percent={70} status="exception" />
	<Progress percent={100} />
	<Progress percent={50} showInfo={false} />
  </Flex>
);
export default App;*/

$(document).ready(function() {

	var trackingTable = $('#okr-table').DataTable({
		"paging": true,
		"searching": true,
		"info": false,
		"ordering": false,
	});

	var layoutTable = $('#okr-layouttable').DataTable({
		"paging": true,
		"searching": true,
		"info": false,
		"ordering": false,
	});

	var newRows = [];
	var newLayoutRows = [];


	function addRow() {
		var newRowIndex = $('#okr-table tbody tr').length + 1;
		var newRow =
			`<tr class="draggable new-row">
                <td>${newRowIndex}</td>
                <td contenteditable="false" tabindex="0"></td>
                <td contenteditable="false" tabindex="0"></td>
                <td contenteditable="false" tabindex="0"></td>
                <td contenteditable="false" tabindex="0"></td>
                <td class="non-editable">
                    <span class="editTracking-btn"><i class="fas fa-edit"></i> Edit</span>
                    <span class="deleteTracking-btn"><i class="fas fa-trash"></i> Delete</span>
                </td>
            </tr>`;
		var row = $(newRow);
		trackingTable.row.add(row).draw();
		newRows.push(row);
		updateRowNumbers();
		makeTableSortable('#okr-table');
	}

	function addRowLayout() {
		var newRowIndex = $('#okr-layouttable tbody tr').length + 1;
		var newRow =
			`<tr data-new="true">
            <td>${newRowIndex}</td>
            <td contenteditable="false"></td>
            <td contenteditable="false"></td>
            <td contenteditable="false"></td>
            <td>
                <select class="unit-select">
                    <option value="">Select unit</option>
                    <option value="Number">Number</option>
                    <option value="Yes/No">Yes/No</option>
                    <option value="Percentage">Percentage</option>
                </select>
            </td>
            <td contenteditable="false"></td>
            <td contenteditable="false"></td>
            <td contenteditable="false"></td>
            <td>
                <span class="editLayout-btn"><i class="fas fa-edit"></i> Edit</span>
                <span class="deleteLayout-btn" data-id="${newRowIndex}"><i class="fas fa-trash"></i> Delete</span>
            </td>
        </tr>`;
		var row = $(newRow);
		layoutTable.row.add(row).draw();
		newLayoutRows.push(row);
		updateRowNumbersLayout();
		makeTableSortable('#okr-layouttable');
	}


	function flattenData(data) {
		var result = [];

		data.forEach(function(item) {
			result.push({
				No: result.length + 1,
				name: item.name || '',
				startDate: item.startDate || '',
				endDate: item.endDate || '',
				currentPeriod: item.currentPeriod || ''
			});

			if (item.childs && Array.isArray(item.childs)) {
				item.childs.forEach(function(child) {
					result.push({
						No: result.length + 1,
						name: child.name || '',
						startDate: child.startDate || '',
						endDate: child.endDate || '',
						currentPeriod: child.currentPeriod || ''
					});
				});
			}
		});

		return result;
	}

	function fetchData() {
		fetch('period/loaddata')
			.then(response => response.json())
			.then(jsonData => {
				let data = [];

				if (jsonData.data && Array.isArray(jsonData.data)) {
					data = flattenData(jsonData.data);
				} else {
					console.error('Unexpected data structure in the JSON data');
					return;
				}

				console.log("Raw JSON Data: ", jsonData.data);
				console.log("Processed Data: ", data);

				data.forEach((item, index) => {
					console.log(`Item ${index}:`, item, 'Type:', typeof item);
					if (Array.isArray(item)) {
						console.log(`Item ${index} startDate: ${item.startDate}, Type: ${typeof item.startDate}`);
					}
				});

				data.sort((a, b) => {
					const startDateA = new Date(a.startDate);
					const startDateB = new Date(b.startDate);
					const endDateA = new Date(a.endDate);
					const endDateB = new Date(b.endDate);

					// Compare start dates
					if (startDateA.getTime() !== startDateB.getTime()) {
						return startDateA - startDateB;
					}

					// If start dates are the same, compare end dates (later dates come first)
					return endDateB - endDateA;
				});

				console.log("Data after sorting: ", data);

				trackingTable.clear().draw();
				data.forEach(row => {
					if (row) {
						let rowHtml = `
	                        <tr class="draggable">
	                            <td>${row.No || ''}</td>
	                            <td>${row.name || ''}</td>
	                            <td>${row.startDate || ''}</td>
	                            <td>${row.endDate || ''}</td>
	                            <td>${row.currentPeriod || ''}</td>
	                            <td class="non-editable">
	                                <span class="editTracking-btn"><i class="fas fa-edit"></i> Edit</span>
	                                <span class="deleteTracking-btn"><i class="fas fa-trash"></i> Delete</span>
	                            </td>
	                        </tr>`;
						trackingTable.row.add($(rowHtml)).draw();
					} else {
						console.error('Invalid row data:', row);
					}
				});

				updateRowNumbers();
				makeTableSortable('#okr-table');
			})
			.catch(error => {
				console.error('Error fetching data:', error);
			});
	}

	function fetchDataLayout() {
		fetch('objectives/loaddata')
			.then(response => response.json())
			.then(jsonData => {
				if (jsonData.data.objectives && Array.isArray(jsonData.data.objectives)) {
					var data = [];
					var counter = 0;
					console.log("Fetch data");
					jsonData.data.objectives.forEach(item => {
						if (item.keyResults && Array.isArray(item.keyResults)) {
							item.keyResults.forEach(keyResult => {
								let unitType;
								switch (keyResult.itype) {
									case 1:
										unitType = 'Number';
										break;
									case 2:
										unitType = 'Yes/No';
										break;
									case 3:
										unitType = 'Percentage';
										break;
									default:
										unitType = 'N/A';
										break;
								}

								var childData = [
									counter++,
									item.description,
									item.weight,
									keyResult.description,
									unitType,
									keyResult.result,
									keyResult.target,
									item.progress,
									// Add data-id attribute for deletion
									`<span class="editLayout-btn"><i class="fas fa-edit"></i> Edit</span> 
	                                 <span class="deleteLayout-btn" data-id="${item.description}"><i class="fas fa-trash"></i> Delete</span>`
								];
								data.push(childData);
								console.log("Child: " + childData);
							});
						}
					});
					console.log("Data before sorting: ", data);
					data.sort((a, b) => a[1].localeCompare(b[1]));

					// Log data to check sorting
					console.log("Data after description's sorting: ", data);

					// Clear and redraw the table
					layoutTable.clear();
					layoutTable.rows.add(data).draw();
					makeTableSortable('#okr-layouttable');
					updateRowNumbersLayout();

					mergeObjectivesColumn();

					document.querySelectorAll('.deleteLayout-btn').forEach(button => {
						button.addEventListener('click', handleDelete);
					});

				} else {
					console.error('Unexpected data structure in the JSON data');
				}
			})
			.catch(error => {
				console.error('Error fetching layout data:', error);
			});
	}

	function fetchPeriods() {
		fetch('period/loaddata')
			.then(response => response.json())
			.then(jsonData => {
				console.log('Fetched JSON data:', jsonData);
				if (jsonData.data && Array.isArray(jsonData.data)) {
					const dropdown = document.getElementById('periodDropdown');

					dropdown.innerHTML = '';

					const placeholderOption = document.createElement('option');
					placeholderOption.value = '';
					placeholderOption.textContent = 'Select a Period';
					placeholderOption.disabled = true;
					placeholderOption.hidden = true;
					dropdown.appendChild(placeholderOption);

					let selectedPeriodId = '';

					jsonData.data.forEach(period => {
						if (period.currentPeriod) {
							selectedPeriodId = period.periodId;
						}

						// Create and append top-level period option
						const option = document.createElement('option');
						option.value = period.periodId;
						option.textContent = period.name;
						dropdown.appendChild(option);

						// Create and append child period options
						period.childs.forEach(child => {
							const childOption = document.createElement('option');
							childOption.value = child.periodId;
							childOption.textContent = `-- ${child.name}`;
							dropdown.appendChild(childOption);
						});
					});

					// Set the current period as selected if available
					if (selectedPeriodId) {
						dropdown.value = selectedPeriodId;
					}
				} else {
					console.error('Unexpected data structure in the JSON data');
				}
			})
			.catch(error => {
				console.error('Error fetching period data:', error);
			});
	}




	fetchPeriods();

	function handleDelete(event) {
		const id = event.target.closest('.deleteLayout-btn').getAttribute('data-id');
		if (!id) {
			console.error('No ID found for deletion');
			return;
		}

		function onSuccess(result) {
			console.log('Row successfully deleted:', result);
			fetchDataLayout();
		}

		function onError(error) {
			console.error('Failed to delete row:', error);
		}

		// Call deleteRowFromDatabase with appropriate parameters
		deleteRowFromDatabase(
			id,
			'objectives/deletedata',
			function(data) {
				console.log('Row successfully deleted: ', data);
			},
			function(error) {
				console.error('Failed to delete row: ', error);
			}
		);
	}

	function mergeObjectivesColumn() {
		var rows = $('#okr-layouttable tbody tr');
		var lastObjective = '';
		var startIndex = -1;

		rows.each(function(index) {
			var currentObjective = $(this).find('td').eq(1).text().trim();

			if (currentObjective === lastObjective) {
				$(this).find('td').eq(1).hide();
				rows.eq(startIndex).find('td').eq(1).attr('rowspan', (index - startIndex + 1));
			} else {
				lastObjective = currentObjective;
				startIndex = index;
			}
		});
	}
	function updateData() {
		var rowsData = [];
		var parentIdMap = {};

		$('#okr-table tbody tr').each(function() {
			var row = $(this);
			var endDate = row.find('td').eq(3).text();
			var endDateYear = new Date(endDate).getFullYear();

			if (!parentIdMap[endDateYear]) {
				parentIdMap[endDateYear] = null;
			}

			parentIdMap[endDateYear] = null;
		});

		newRows.forEach(function(row) {
			var name = row.find('td').eq(1).text();
			var startDate = row.find('td').eq(2).text();
			var endDate = row.find('td').eq(3).text();
			var currentPeriod = row.find('td').eq(4).text() === 'true';

			var endDateYear = new Date(endDate).getFullYear();
			var parentId = parentIdMap[endDateYear];

			var rowData = {
				name: name,
				startDate: startDate,
				endDate: endDate,
				parentId: parentId,
				setAsRoot: parentId === null,
				currentPeriod: currentPeriod
			};

			rowsData.push(rowData);
		});

		newRows = [];

		if (rowsData.length === 0) {
			console.warn('No new data to update');
			return;
		}

		rowsData.forEach(function(data) {
			fetch('period/uploaddata', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(data)
			})
				.then(response => response.json())
				.then(result => {
					console.log('Data successfully updated:', result);
				})
				.catch(error => {
					console.error('Error updating data:', error);
				});
		});
		window.location.reload();
	}


	function updateDataLayout() {
		$('#okr-layouttable tbody tr[data-new="true"]').each(function() {
			var row = $(this);
			var description = row.find('td').eq(1).text().trim();
			var weight = row.find('td').eq(2).text().trim();
			var keyResultDescription = row.find('td').eq(3).text().trim();
			var unit = row.find('.unit-select').val(); // Get the selected value
			var startValue = row.find('td').eq(5).text().trim();
			var target = row.find('td').eq(6).text().trim();
			var progress = row.find('td').eq(7).text().trim();

			if (!description || !weight || !keyResultDescription || !unit || !startValue || !target || !progress) {
				console.warn('One or more fields are empty in the row.');
				return;
			}

			var itype;
			switch (unit) {
				case 'Number':
					itype = 1;
					break;
				case 'Yes/No':
					itype = 2;
					break;
				case 'Percentage':
					itype = 3;
					break;
				default:
					itype = 0;
					break;
			}

			var objective = {
				description: description,
				status: 'DRAFT',
				weight: weight,
				comment: '',
				keyResults: []
			};

			var keyResult = {
				description: keyResultDescription,
				dueDate: '',
				itype: itype,
				progress: progress,
				weight: weight,
				numberResult: startValue,
				numberTarget: target,
				yesNoResult: false,
				yesNoTarget: false,
				percentageResult: startValue,
				percentageTarget: target,
				standard: 'None',
				startvalue: startValue,
			};

			objective.keyResults.push(keyResult);

			var requestData = { objectives: [objective] };

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
				.then(response => response.json())
				.then(result => {
					console.log('Data successfully updated:', JSON.stringify(result));
					row.removeAttr('data-new');
				})
				.catch(error => {
					console.error('Error updating data:', error);
				});
		});
		window.location.reload();
	}

	/* Draft Save OKR by nhbthach __START */
	function getOrgId() {
		return $.ajax({
			url: 'organization/loaddata',
			method: 'GET',
			dataType: 'text'
		}).then(function(data) {
			console.log("orgId:", data);
			return data;
		}).catch(function(error) {
			console.error('Error fetching orgId:', error);
			return null; // Return default value if occurs error
		});
	}

	function getPeriodId() {
		return $.ajax({
			url: 'period/currentPeriodId',
			method: 'GET',
			dataType: 'text'
		}).then(function(data) {
			console.log("periodId:", data);
			return data;
		}).catch(function(error) {
			console.error('Error fetching periodId:', error);
			return null; // Return default value if occurs error
		});
	}

	function draftSaveOkr() {

		var table = $('#okr-layouttable').DataTable();

		var data = table.rows().data().toArray();

		$.when(getOrgId(), getPeriodId()).done(function(orgId, periodId) {

			// Initial OKR JSON object for updating
			var jsonData = {
				"status": "DRAFT",
				"organizationId": orgId,
				"periodId": periodId,
				"objectives": []
			};

			$('#okr-layouttable tbody tr').each(function() {

				var row = $(this);
				var objDesc = row.find('td').eq(1).text().trim();
				var objWeight = row.find('td').eq(2).text().trim();
				var ksDesc = row.find('td').eq(3).text().trim();
				var unit = row.find('td').eq(4).text().trim();
				var startValue = row.find('td').eq(5).text().trim();
				var target = row.find('td').eq(6).text().trim();
				var progress = row.find('td').eq(7).text().trim();


				// Check if exists 'objective' by compare description
				var objective = jsonData.objectives.find(obj => obj.description === objDesc);

				// If not exists, generate new objective
				if (!objective) {
					objective = {
						"description": objDesc,
						"status": "DRAFT",
						"weight": objWeight || 0,
						"comment": "test_comment",
						"keyResults": []
					};
					jsonData.objectives.push(objective);
				}

				// Add KeyResult into corresponding Objective
				var itype = (unit === 'Number' ? 1 : unit === 'Yes/No' ? 2 : unit === 'Percentage' ? 3 : 0);

				var keyResult = {
					"description": ksDesc,
					"standard": 'None',
					"startvalue": itype === 2 ? 0 : startValue,
					"itype": itype
				};

				if (itype === 1) { // Number
					keyResult.numberResult = parseFloat(startValue) || 0;
					keyResult.numberTarget = parseFloat(target) || 0;
				} else if (itype === 2) { // Yes/No
					keyResult.yesNoResult = startValue; // assuming startValue is a string
					keyResult.yesNoTarget = target; // assuming target is a string
				} else if (itype === 3) { // Percentage
					keyResult.percentageResult = parseFloat(startValue) || 0;
					keyResult.percentageTarget = parseFloat(target) || 0;
				}

				objective.keyResults.push(keyResult);
			})

			// Call API
			var payload = JSON.stringify(jsonData);
			console.log(payload);
			var url = "objectives/updateOkr"

			$.ajax({
				url: url,
				type: 'POST',
				contentType: 'application/json',
				data: payload,
				success: function(response) {
					console.log('Data saved successfully:', response);
					showNotiDraftSave('Update successfully!', 'success');
				},
				error: function(xhr, status, error) {
					console.error('Error saving data:', {
						status: xhr.status,
						statusText: xhr.statusText,
						responseText: xhr.responseText
					});
					showNotiDraftSave('Failed to update! Try again, please', 'error');
				}
			});

		})
	}

	function showNotiDraftSave(message, type) {

		$('#notification-message').text(message);
		if (type === 'success') {
			$('#notiDraftSave').addClass('alert-success').removeClass('alert-danger');
		} else if (type === 'error') {
			$('#notiDraftSave').addClass('alert-danger').removeClass('alert-success');
		}

		$('#notiDraftSave').show();
		$('#notiDraftSave').fadeIn();

		// Hide notificaiton after 3s
		setTimeout(function() {
			$('#notiDraftSave').fadeOut();
		}, 5000);
	}
	/* DraftSave OKR by nhbthach __ END */

	function updateRowNumbers() {
		$('#okr-table tbody tr').each(function(index) {
			$(this).find('td').eq(0).text(index + 1);
		});
	}

	function updateRowNumbersLayout() {
		$('#okr-layouttable tbody tr').each(function(index) {
			$(this).find('td').eq(0).text(index + 1);
		});
	}

	function makeTableSortable(selector) {
		$(selector).sortable({
			items: 'tbody tr',
			cursor: 'move',
			axis: 'y',
			update: function(event, ui) {
				updateRowNumbers();
				updateRowNumbersLayout();
			}
		});
	}

	function enableCellEditTracking(row) {
		$(row).find('td').not('.non-editable').each(function() {
			$(this).attr('contenteditable', 'true').addClass('cell-editable');
		});
		$(row).find('.editTracking-btn').html('<i class="fas fa-save"></i> Save');
		$("#okr-table").sortable("disable");
	}

	function enableCellEditLayout(row) {
		$(row).find('td').not('.non-editable').each(function() {
			var cell = $(this);
			if (cell.find('select').length) {
				cell.find('select').prop('disabled', false);
			} else {
				cell.attr('contenteditable', 'true').addClass('cell-editable');
			}
		});
		$(row).find('.editLayout-btn').html('<i class="fas fa-save"></i> Save');
		$("#okr-layouttable").sortable("disable");
	}

	function disableCellEditTracking(row) {
		$(row).find('td').not('.non-editable').each(function() {
			$(this).attr('contenteditable', 'false').removeClass('cell-editable');
		});
		$(row).find('.editTracking-btn').html('<i class="fas fa-edit"></i> Edit');
		$("#okr-table").sortable("enable");
	}

	function disableCellEditLayout(row) {
		$(row).find('td').not('.non-editable').each(function() {
			var cell = $(this);
			if (cell.find('select').length) {
				cell.find('select').prop('disabled', true);
			} else {
				cell.attr('contenteditable', 'false').removeClass('cell-editable');
			}
		});
		$(row).find('.editLayout-btn').html('<i class="fas fa-edit"></i> Edit');
		$("#okr-layouttable").sortable("enable");
	}

	$('#MKSOLAddRow').click(addRow);
	$('#MKSOLAddRowLayout').click(addRowLayout);

	$('#MKSOLUpdateperiod').click(updateData);
	$('#submit-layout').click(updateDataLayout);

	// Draft Save handle event btn
	$('#draft-save').click(draftSaveOkr);

	$('#okr-table').on('click', '.editTracking-btn', function() {
		var row = $(this).closest('tr');
		if ($(this).find('i').hasClass('fa-edit')) {
			enableCellEditTracking(row);
		} else {
			disableCellEditTracking(row);
		}
	});

	$('#okr-layouttable').on('click', '.editLayout-btn', function() {
		var row = $(this).closest('tr');
		if ($(this).find('i').hasClass('fa-edit')) {
			enableCellEditLayout(row);
		} else {
			disableCellEditLayout(row);
		}
	});

	$('#okr-table').on('click', '.deleteTracking-btn', function() {
		var row = $(this).closest('tr');
		trackingTable.row(row).remove().draw();
		updateRowNumbers();
	});

	$('#okr-layouttable').on('click', '.deleteLayout-btn', function() {
		var row = $(this).closest('tr');
		layoutTable.row(row).remove().draw();
		updateRowNumbersLayout();
	});

	fetchData();
	console.log("loaded");
	fetchDataLayout();


	function loadOKRs() {
		fetch('objectives/loaddata')
			.then(response => response.json())
			.then(data => {
				if (data.data && data.data.objectives && Array.isArray(data.data.objectives)) {
					const okrCardsContainer = $('#okr-cards');
					okrCardsContainer.empty();

					data.data.objectives.forEach(objective => {
						let okrContent = '';
						if (objective.keyResults && Array.isArray(objective.keyResults)) {
							objective.keyResults.forEach(keyResult => {
								const startValue = keyResult.startvalue;
								const targetValue = keyResult.target;
								const progress = keyResult.progress;
								console.log('progress:', progress);


								if (!isNaN(progress) && isFinite(progress)) {
									okrContent += `
                                        <p>${keyResult.description}</p>
                                        <div class="progress">
                                            <div class="progress-bar" role="progressbar" style="width: ${progress}%;">${progress.toFixed(2)}%</div>
                                        </div>
                                    `;
								}
							});
						}

						const cardHtml = `
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">${objective.description}</h5>
                                        <p class="text-muted">Weight: ${objective.weight}</p>
                                        <div class="okr-content">
                                            ${okrContent}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        `;

						okrCardsContainer.append(cardHtml);
					});
				} else {
					console.error('Unexpected data structure in the JSON data');
				}
			})
			.catch(error => {
				console.error('Error fetching OKR data:', error);
			});
	}

	loadOKRs();


	document.getElementById('export-btn').addEventListener('click', function() {
		const data = [
			["Objective", "Key Result", "Progress"],
		];

		$('#okr-cards .card').each(function() {
			const objective = $(this).find('.card-title').text();
			$(this).find('.okr-content p').each(function(index, element) {
				const keyResult = $(element).text();
				const progress = $(element).next('.progress').find('.progress-bar').text();
				data.push([objective, keyResult, progress]);
			});
		});

		const ws = XLSX.utils.aoa_to_sheet(data);
		const wb = XLSX.utils.book_new();
		XLSX.utils.book_append_sheet(wb, ws, "OKRs");
		XLSX.writeFile(wb, "OKR_Dashboard.xlsx");
	});

	function deleteRowFromDatabase(name, url, onSuccess, onError) {
		var completeUrl = `${url}/${name}`;
		console.log('Request URL:', completeUrl);

		fetch(completeUrl, {
			method: 'DELETE',
			headers: {
				'Content-Type': 'application/json'
			}
		})
			.then(response => {
				if (!response.ok) {
					console.log('Response error:', response.status);
					throw new Error('Network response was not ok');
				}
				return response.text();
			})
			.then(data => {
				if (onSuccess) {
					onSuccess(data);
				}
			})
			.catch((error) => {
				console.error('Error:', error);
				if (onError) {
					onError(error);
				}
			});
	}

});
