document.addEventListener('DOMContentLoaded', function() {
	var container = document.getElementById('okr-table');
	var hot;

	$('#jstree').jstree();

	var hotSettings = {
		data: [],
		colHeaders: ['No', 'Period', 'startDate', 'endDate', 'currentPeriod'],
		columns: [
			{ data: 'No' },
			{ data: 'name' },
			{ data: 'startDate' },
			{ data: 'endDate' },
			{ data: 'currentPeriod' }
		],
		stretchH: 'all',
		autoWrapRow: true,
		height: 'auto',
		licenseKey: 'non-commercial-and-evaluation'
	};

	hot = new Handsontable(container, hotSettings);

	fetch('period/loaddata')
		.then(response => response.json())
		.then(jsonData => {
			var data = [];
			var counter = 1;

			if (jsonData.data && Array.isArray(jsonData.data)) {
				for (var i = 0; i < jsonData.data.length; i++) {
					var item = jsonData.data[i];
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
			} else {
				console.error('Unexpected data structure in the JSON data');
			}

			hot.loadData(data);
		})
		.catch(error => {
			console.error('Error fetching data:', error);
		});


	var url = 'objectives/loaddata';
	var xhr = new XMLHttpRequest();
	xhr.open('GET', url, true);
	xhr.onreadystatechange = function() {
		if (xhr.readyState === XMLHttpRequest.DONE) {
			if (xhr.status === 200) {
				var jsonResponse = JSON.parse(xhr.responseText);
				processJsonResponse(jsonResponse);
			} else {
				console.error('Failed to fetch data. Status:', xhr.status);
			}
		}
	};
	xhr.send();

	function processJsonResponse(jsonResponse) {
		var objectives = jsonResponse.data.objectives;

		function getUnitFromIType(itype) {
			switch (itype) {
				case 1:
					return 'Number';
				case 2:
					return 'Yes/No';
				case 3:
					return 'Percentage';
				default:
					return '';
			}
		}

		var datalayout = [];
		objectives.forEach(function(objective, index) {
			objective.keyResults.forEach(function(keyResult) {
				var unit = getUnitFromIType(keyResult.itype); 
				datalayout.push({
					No: index + 1,
					Objectives: objective.description,
					Weigth: objective.weight,
					"Key results": keyResult.description,
					Unit: unit,
					"Start value": keyResult.startvalue,
					Target: keyResult.target,
					"More actions": ""
				});
			});
		});

		var containerlayout = document.getElementById('okr-layouttable');
		var hotlayout = new Handsontable(containerlayout, {
			data: datalayout,
			colHeaders: ['No', 'Objectives', 'Weigth', 'Key results', 'Unit', 'Start value', 'Target', 'More actions'],
			columns: [
				{ data: 'No' },
				{ data: 'Objectives' },
				{ data: 'Weigth' },
				{ data: 'Key results' },
				{ data: 'Unit' },
				{ data: 'Start value' },
				{ data: 'Target' },
				{ data: 'More actions' }
			],
			stretchH: 'all',
			autoWrapRow: true,
			height: 'auto',
			licenseKey: 'non-commercial-and-evaluation'
		});

		hotlayout.updateSettings({
			data: datalayout
		});
	}


});