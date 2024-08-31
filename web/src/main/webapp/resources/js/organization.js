document.addEventListener("DOMContentLoaded", () => {
	// Function to create a table row for organizations
	function createRow(org) {
		const row = document.createElement('tr');

		// Organization details
		const nameCell = document.createElement('td');
		nameCell.textContent = org.name;
		row.appendChild(nameCell);

		const orgIdCell = document.createElement('td');
		orgIdCell.textContent = org.orgId;
		row.appendChild(orgIdCell);

		const descriptionCell = document.createElement('td');
		descriptionCell.textContent = org.description;
		row.appendChild(descriptionCell);

		// User details (if any)
		const usersCell = document.createElement('td');
		if (org.users && org.users.length > 0) {
			const usersList = document.createElement('ul');
			org.users.forEach(user => {
				const userItem = document.createElement('li');
				userItem.textContent = `${user.name} (${user.email})`;
				usersList.appendChild(userItem);
			});
			usersCell.appendChild(usersList);
		} else {
			usersCell.textContent = 'No users';
		}
		row.appendChild(usersCell);

		return row;
	}

	// Function to populate the dropdown with organizations
	function populateDropdown(data) {
		const dropdown = document.querySelector('#orgSelect');

		function createOption(org, level) {
			const option = document.createElement('option');
			option.value = org.orgId;
			option.textContent = ' '.repeat(level * 4) + org.name; // Indentation based on hierarchy level
			return option;
		}

		function addOptions(orgs, parentOption, level) {
			orgs.forEach(org => {
				const option = createOption(org, level);
				dropdown.appendChild(option);
				if (org.orgs && org.orgs.length > 0) {
					addOptions(org.orgs, option, level + 1); // Recursive call for child organizations
				}
			});
		}

		addOptions(data, null, 0);
	}


	// Function to fetch and display users for the selected organization
	function fetchUsers(orgId) {
		fetch(`organization/users/${orgId}`)
			.then(response => response.json())
			.then(data => {
				const userTableBody = document.querySelector('#userTableBody');
				userTableBody.innerHTML = ''; // Clear previous users

				if (data && data.data && data.data.length > 0) {
					data.data.forEach(user => {
						const row = document.createElement('tr');

						const nameCell = document.createElement('td');
						nameCell.textContent = user.name;
						row.appendChild(nameCell);

						const emailCell = document.createElement('td');
						emailCell.textContent = user.email;
						row.appendChild(emailCell);

						userTableBody.appendChild(row);
					});
				} else {
					const row = document.createElement('tr');
					const cell = document.createElement('td');
					cell.colSpan = 2;
					cell.textContent = 'No users found';
					row.appendChild(cell);
					userTableBody.appendChild(row);
				}
			})
			.catch(error => console.error('Error fetching users:', error));
	}


	// Recursive function to process hierarchical organizations
	function processHierarchy(orgs, parentRow) {
		orgs.forEach(org => {
			const row = createRow(org);
			parentRow.appendChild(row);

			// Recursively process child organizations
			if (org.orgs && org.orgs.length > 0) {
				const childTable = document.createElement('table');
				childTable.classList.add('child-table');
				const childTableBody = document.createElement('tbody');
				childTable.appendChild(childTableBody);

				// Add the child table to the current row
				const childTableCell = document.createElement('td');
				childTableCell.colSpan = 4; // Adjust based on your table column count
				childTableCell.appendChild(childTable);
				row.appendChild(childTableCell);

				// Process child organizations
				processHierarchy(org.orgs, childTableBody);
			}
		});
	}

	// Function to populate the table with organization data
	function populateTable(data) {
		const tableBody = document.querySelector('#orgTable tbody');
		processHierarchy(data, tableBody);
	}

	// Fetch and populate the organization data
	fetch('organization/loadalldata')
		.then(response => {
			if (!response.ok) {
				throw new Error(`Network response was not ok: ${response.statusText}`);
			}
			return response.text(); // Read as text first
		})
		.then(text => {
			try {
				const data = JSON.parse(text); // Parse the text as JSON
				if (data && data.data) {
					populateDropdown(data.data);
					populateTable(data.data);
				} else {
					console.error("Unexpected data format:", data);
				}
			} catch (e) {
				console.error('Error parsing JSON:', e);
			}
		})
		.catch(error => console.error('Error fetching organization data:', error));

	function createOrganization() {
		const name = document.querySelector('#orgName').value;
		const description = document.querySelector('#orgDescription').value;
		const parentId = document.querySelector('#orgSelect').value;
		const setAsRoot = !parentId; // If there's no parentId, setAsRoot is true

		const requestData = {
			name: name,
			description: description,
			parentId: parentId,
			setAsRoot: setAsRoot
		};
		console.log(requestData);
		fetch('organization/create', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			
			body: JSON.stringify(requestData)
		})
			.then(response => response.json())
			.then(data => {
				if (data && data['message:'] === "Succeed") {
					alert("Organization created successfully!");
					// Optionally, refresh the dropdown or table to show the new organization
					// You can re-fetch and repopulate the dropdown here
				} else {
					console.error("Error creating organization:", data);
					alert("Failed to create organization. Please try again.");
				}
			})
			.catch(error => console.error('Error creating organization:', error));
	}

	// Event listener for create organization button
	document.querySelector('#createOrgButton').addEventListener('click', createOrganization);
	// Event listener for dropdown change
	document.querySelector('#orgSelect').addEventListener('change', (event) => {
		const orgId = event.target.value;
		if (orgId) {
			fetchUsers(orgId);
		}
	});
});
