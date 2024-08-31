document.addEventListener("DOMContentLoaded", () => {
	let userRole = ''; // Global variable to store the user role

	function fetchUserRole() {
		fetch('userRole/load') // This endpoint should return the user profile including the role
			.then(response => response.text())
			.then(data => {
				userRole = data; // Assuming the user role is in this path
				console.log('User Role:', userRole);
			})
			.catch(error => console.error('Error fetching user role:', error));
	}

	// Call fetchUserRole to set the global userRole variable
	fetchUserRole();
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
			    // Create a new child table
			    const childTable = document.createElement('table');
			    childTable.classList.add('child-table');
			    
			    // Create and append the header row
			    const childTableHead = document.createElement('thead');
			    const headerRow = document.createElement('tr');

			    const nameHeader = document.createElement('th');
			    nameHeader.textContent = 'Name';
			    headerRow.appendChild(nameHeader);

			    const orgIdHeader = document.createElement('th');
			    orgIdHeader.textContent = 'Organization ID';
			    headerRow.appendChild(orgIdHeader);

			    const descriptionHeader = document.createElement('th');
			    descriptionHeader.textContent = 'Description';
			    headerRow.appendChild(descriptionHeader);

			    const usersHeader = document.createElement('th');
			    usersHeader.textContent = 'Users';
			    headerRow.appendChild(usersHeader);

			    // Append the header row to the table head
			    childTableHead.appendChild(headerRow);
			    childTable.appendChild(childTableHead);

			    // Create the table body
			    const childTableBody = document.createElement('tbody');
			    childTable.appendChild(childTableBody);

			    // Add the child table to the current row
			    const childTableCell = document.createElement('td');
			    childTableCell.colSpan = 4; // Adjust based on your table column count
			    childTableCell.appendChild(childTable);
			    row.appendChild(childTableCell);

			    // Process child organizations recursively
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

	function populateUserDropdown() {
		fetch('userprofile/admin/getListUserProfile')
			.then(response => response.json())
			.then(data => {
				console.log(data);
				const userDropdown = document.querySelector('#userDropdown');
				userDropdown.innerHTML = ''; // Clear previous options

				// Iterate through the 'content' array within the 'data' object
				data.data.content.forEach(user => {
					// Only consider users who are part of the 'unassigned' organization
					const isUnassigned = user.orgs.some(org => org.name === 'unassigned');

					if (isUnassigned) {
						const option = document.createElement('option');
						option.value = user.userId;
						option.textContent = `${user.name} (${user.email})`;
						userDropdown.appendChild(option);
					}
				});
			})
			.catch(error => console.error('Error fetching users:', error));
	}
	populateUserDropdown();

	// Add member to organization
	function addMember() {
		const orgId = document.querySelector('#orgSelect').value;
		const startDate = document.querySelector('#startDate').value;
		const endDate = document.querySelector('#endDate').value;
		const userId = document.querySelector('#userDropdown').value;

		if (userRole === 'ADMIN') { // Check if the user is an admin
			const payload = {
				startDate,
				endDate,
				orgId,
				userId
			};

			fetch('organization/addMember', { // Update the URL if necessary
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},

				body: JSON.stringify(payload)
			})
				.then(response => response.json())
				.then(data => {
					if (data && data.message && data.message === 'Succeed') {
						alert('Member added successfully!');
					} else {
						console.error('Error adding member:', data);
						alert('Failed to add member. Please try again.');
					}
				})
				.catch(error => console.error('Error:', error));
		} else {
			alert('You do not have permission to add members.');
		}
	}


	document.querySelector('#addMemberButton').addEventListener('click', addMember);
});
