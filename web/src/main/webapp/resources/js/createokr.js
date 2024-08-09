document.addEventListener('DOMContentLoaded', function() {
	// Add Objective
	$('#addObjective').click(function() {
		var template = $('#objectiveTemplate').html();
		$('#objectivesContainer').append(template);
	});

	// Remove Objective
	$(document).on('click', '.btn-remove-objective', function() {
		$(this).closest('.objective').remove();
	});

	// Add Key Result
	$(document).on('click', '.btn-add-key-result', function() {
		var template = $('#keyResultTemplate').html();
		$(this).prev('.key-results-container').append(template);
	});

	// Remove Key Result
	$(document).on('click', '.btn-remove-key-result', function() {
		$(this).closest('.key-result').remove();
	});

	// Form Submission
	$('#okrForm').submit(function(event) {
		event.preventDefault(); // Prevent form submission

		//var period = $('#period').val();
		var objectives = [];

		$('.objective').each(function() {
			var description = $(this).find('.objective-description').val().trim();
			var status = "DRAFT";
			var weight = $(this).find('.objective-weight').val().trim();
			var comment = "chayngaydi";

			// Check if both description and weight are not empty
			if (description !== "" && weight !== "") {
				var keyResults = [];

				$(this).find('.key-result').each(function() {
					var keyResultDescription = $(this).find('.key-result-description').val().trim();
					var dueDate = $(this).find('.key-result-due-date').val().trim();
					if (keyResultDescription !== "" && dueDate !== "") {
						keyResults.push({
							description: keyResultDescription,
							dueDate: dueDate,
							itype: 1,
							weight: 10,
							numberResult: 10,
							numberTarget: 50,
							yesNoResult: false,
							yesNoTarget: false,
							percentageResult: 0,
							percentageTarget: 100,
							standard: "None",
							startvalue: 0
						});
					}
				});

				if (keyResults.length == 0) {
					objectives.push({
						description: "null",
						weight: "null"
					});
				}

				// Add objective only if it has at least one key result
				if (keyResults.length > 0) {
					objectives.push({
						description: description,
						status: status,
						weight: weight,
						comment: comment,
						keyResults: keyResults
					});
				}
			}
		});

		var data = {
			objectives: objectives
		};

		var jsonData = JSON.stringify(data);
		console.log('JSON Data:', jsonData);

		/*var jsonData1 = {
			"status": "DRAFT",
			"progress": 0.69,
			"grade": 0.69,
			"organizationId": "ba654104-9e77-47ec-8d70-52256932fce2",
			"periodId": "fdd7f22c-6dd9-43d9-9bb3-bfec6d6b0074",
			"objectives": [
				{
					"description": "objective1",
					"status": "DRAFT",
					"weight": 25,
					"comment": "comment1",
					"keyResults": [
						{
							"description": "test_ks11",
							"dueDate": "2024-07-11",
							"itype": 1,
							"weight": 10,
							"numberResult": 10,
							"numberTarget": 50,
							"yesNoResult": false,
							"yesNoTarget": false,
							"percentageResult": 0,
							"percentageTarget": 100,
							"standard": "None",
							"startvalue": 0
						},
						{
							"description": "test_ks12",
							"dueDate": "2024-07-11",
							"itype": 1,
							"weight": 15,
							"numberResult": 15,
							"numberTarget": 55,
							"yesNoResult": false,
							"yesNoTarget": false,
							"percentageResult": 0,
							"percentageTarget": 100,
							"standard": "None",
							"startvalue": 0
						},
						{
							"description": "test_ks13",
							"dueDate": "2024-07-11",
							"itype": 1,
							"weight": 20,
							"numberResult": 20,
							"numberTarget": 50,
							"yesNoResult": false,
							"yesNoTarget": false,
							"percentageResult": 0,
							"percentageTarget": 100,
							"standard": "None",
							"startvalue": 0
						}
					]
				},
				{
					"description": "objective2",
					"status": "DRAFT",
					"weight": 30,
					"comment": "comment1",
					"keyResults": [
						{
							"description": "test_ks2",
							"dueDate": "2024-07-11",
							"itype": 2,
							"weight": 20,
							"numberResult": 20,
							"numberTarget": 60,
							"yesNoResult": false,
							"yesNoTarget": true,
							"percentageResult": 0,
							"percentageTarget": 100,
							"standard": "None",
							"startvalue": 0
						}
					]
				},
				{
					"description": "objective3",
					"status": "DRAFT",
					"weight": 15,
					"comment": "comment1",
					"keyResults": [
						{
							"description": "test_ks31",
							"dueDate": "2024-07-11",
							"itype": 3,
							"weight": 25,
							"numberResult": 30,
							"numberTarget": 80,
							"yesNoResult": false,
							"yesNoTarget": false,
							"percentageResult": 0,
							"percentageTarget": 100,
							"standard": "None",
							"startvalue": 0
						},
						{
							"description": "test_ks32",
							"dueDate": "2024-07-11",
							"itype": 1,
							"weight": 10,
							"numberResult": 30,
							"numberTarget": 70,
							"yesNoResult": false,
							"yesNoTarget": false,
							"percentageResult": 0,
							"percentageTarget": 100,
							"standard": "None",
							"startvalue": 0
						}
					]
				}
			]
		}*/

		/*async function transformData(originalData) {
			const UNKNOWN_CONSTANT = "None";

			// Example API call to fetch organizationId and periodId
			const fetchIds = async () => {
				try {
					const response = await fetch('your_api_url');
					const data = await response.json();
					return {
						organizationId: data.organizationId,
						periodId: data.periodId
					};
				} catch (error) {
					console.error('Error fetching IDs:', error);
					// Return default or fallback values if API call fails
					return {
						organizationId: "a87ce450-d701-4163-8620-ebc9f64c67aa",
						periodId: "af3fcf0d-3b40-48bc-9f99-ea95147b9b35"
					};
				}
			}

			const { organizationId, periodId } = await fetchIds();

			// Initialize the target structure
			let transformedData = {
				status: "DRAFT",
				progress: 0.69,
				grade: 0.69,
				organizationId: organizationId,
				periodId: periodId,
				objectives: []
			};

			// Create a map to store objectives by their description for efficient lookup
			let objectivesMap = new Map();

			// Loop through each item in the original data
			originalData.forEach(item => {
				let [
					itype,
					objectiveDescription,
					weight,
					keyResultDescription,
					type,
					result1,
					result2,
					result3
				] = item;

				// Check if the objective is already in the objectives map
				if (!objectivesMap.has(objectiveDescription)) {
					// If not, create a new objective
					let objective = {
						description: objectiveDescription,
						status: "DRAFT",
						weight: weight,
						comment: "comment1",
						keyResults: []
					};
					objectivesMap.set(objectiveDescription, objective);
					transformedData.objectives.push(objective);
				}

				// Retrieve the objective from the map
				let objective = objectivesMap.get(objectiveDescription);

				// Determine the itype and populate key results accordingly
				let keyResult = {
					description: keyResultDescription,
					dueDate: "2024-07-11", // Assuming a fixed due date for all key results
					itype: itype,
					weight: weight * 5, // Example calculation for weight, adjust as needed
					numberResult: type === "Number" ? result1 : UNKNOWN_CONSTANT,
					numberTarget: type === "Number" ? result2 : UNKNOWN_CONSTANT,
					yesNoResult: type === "Yes/No" ? result1 : false,
					yesNoTarget: type === "Yes/No" ? result2 : false,
					percentageResult: type === "Percentage" ? result1 : UNKNOWN_CONSTANT,
					percentageTarget: type === "Percentage" ? result2 : UNKNOWN_CONSTANT,
					standard: UNKNOWN_CONSTANT,
					startvalue: result3
				};

				// Push the key result into the objective's keyResults array
				objective.keyResults.push(keyResult);
			});

			return transformedData;
		}*/

		// Example usage with the provided JSON data
		/*const originalJsonData = [
			[1, "objective3", 15, "test_ks32", "Number", 0, 70, 0],
			[1, "objective3", 15, "test_ks31", "Percentage", 0, 100, 0],
			[2, "objective2", 30, "test_ks2", "Yes/No", null, true, 10],
			[3, "objective1", 25, "test_ks11", "Number", 0, 50, 9],
			[3, "objective1", 25, "test_ks13", "Number", 0, 50, 11],
			[3, "objective1", 25, "test_ks12", "Number", 0, 55, 0]
		];

		const transformedData = JSON.stringify(transformData(originalJsonData), null, 2);
		console.log(transformedData);*/


		$.ajax({
			url: 'objectives/uploaddata',
			type: 'POST',
			contentType: 'application/json',
			data: jsonData,
			success: function(response) {
				console.log('Data saved successfully:', response);
			},
			error: function(xhr, status, error) {
				console.error('Error saving data:', {
				      status: xhr.status,
				      statusText: xhr.statusText,
				      responseText: xhr.responseText
				    });
			}
		});


		// Reset form or perform other actions as needed
		//$('#okrForm')[0].reset();
	});
});
