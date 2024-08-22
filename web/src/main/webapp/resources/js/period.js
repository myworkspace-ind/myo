$(document).ready(function() {
	var trackingTable = $('#okr-table').DataTable({
			"paging": false,
			"searching": false,
			"info": false,
			"ordering": false,
		});

	
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

	function updateRowNumbers() {
		$('#okr-table tbody tr').each(function(index) {
			$(this).find('td').eq(0).text(index + 1);
		});
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
		                        </tr>`;
						trackingTable.row.add($(rowHtml)).draw();
					} else {
						console.error('Invalid row data:', row);
					}
				});

				updateRowNumbers();
			})
			.catch(error => {
				console.error('Error fetching data:', error);
			});
	}

	fetchData();
});
