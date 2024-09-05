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

		function addOptions(orgs, level) {
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

	function populateParentDropdown(data) {
		const dropdown = document.querySelector('#orgSelectParent');

		function createOption(org, level) {
			const option = document.createElement('option');
			option.value = org.orgId;
			option.textContent = ' '.repeat(level * 4) + org.name; // Indentation based on hierarchy level
			return option;
		}

		function addOptions(orgs, level) {
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


	function populateLeafDropdown(data) {
		const leafDropdown = document.querySelector('#leafOrgSelect');

		function createOption(org) {
			const option = document.createElement('option');
			option.value = org.orgId;
			option.textContent = org.name; // No indentation needed for leaf nodes
			return option;
		}

		function addLeafOptions(orgs) {
			orgs.forEach(org => {
				if (!org.orgs || org.orgs.length === 0) {
					// If the organization has no children, it's a leaf node
					const option = createOption(org);
					leafDropdown.appendChild(option);
				} else if (org.orgs && org.orgs.length > 0) {
					// Recursively check child organizations
					addLeafOptions(org.orgs);
				}
			});
		}

		addLeafOptions(data);
	}

	document.getElementById('deleteOrgButton').addEventListener('click', function() {
		const selectedOrgId = document.getElementById('leafOrgSelect').value;
		if (userRole === 'ADMIN') {

			if (!selectedOrgId) {
				alert('Please select an organization to delete.');
				return;
			}

			const confirmation = confirm('Are you sure you want to delete this organization? This action cannot be undone.');

			if (confirmation) {
				fetch(`organization/delete/${selectedOrgId}`, {
					method: 'DELETE',
					headers: {
						'Content-Type': 'application/json',
						// Include other necessary headers like Authorization if required
					}
				})
					.then(response => {
						if (!response.ok) {
							throw new Error('Failed to delete organization.');
						}
						return response.text().then(text => text ? JSON.parse(text) : {});
					})
					.then(data => {
						alert('Organization deleted successfully.');
						// Refresh or update the dropdown to reflect the deletion
						removeDeletedOrgFromDropdown(selectedOrgId);
						window.location.reload();
					})
					.catch(error => {
						console.error('Error:', error);
						alert('Error deleting organization: ' + error.message);
					});
			}
		} else {
			alert('You do not have permission to delete organization.');
		}
	});

	function removeDeletedOrgFromDropdown(orgId) {
		const dropdown = document.getElementById('leafOrgSelect');
		const optionToRemove = dropdown.querySelector(`option[value="${orgId}"]`);

		if (optionToRemove) {
			optionToRemove.remove();
		}
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

						// Add click event listener to the row
						row.addEventListener('click', () => {
							window.location.href = `organization.html?id=${user.userId}`; // Redirect to user profile page
						});

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

						// Add event listener to display user details on click
						row.addEventListener('click', () => {
							displayUserProfile(user);
						});

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



	function displayUserProfile(user) {
		document.getElementById('userName').textContent = user.name;
		document.getElementById('userEmail').textContent = user.email;
		document.getElementById('userAddress').textContent = user.address;

		// Show the profile section
		$('#userProfileModal').modal('show');
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
					populateParentDropdown(data.data);
					populateLeafDropdown(data.data);
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
		if (userRole === 'ADMIN') {
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
						window.location.reload();
						// Optionally, refresh the dropdown or table to show the new organization
						// You can re-fetch and repopulate the dropdown here
					} else {
						console.error("Error creating organization:", data);
						alert("Failed to create organization. Please try again.");
					}
				})
				.catch(error => console.error('Error creating organization:', error));
		} else {
			alert('You do not have permission to create organization.');
		}
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
						window.location.reload();
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

	document.getElementById('updateOrgButton').addEventListener('click', function() {
		const orgId = document.getElementById('orgSelect').value;
		const name = document.getElementById('orgNameUpdate').value;
		const description = document.getElementById('orgDescriptionUpdate').value;
		const parentId = document.getElementById('orgSelectParent').value;
		if (userRole === 'ADMIN') {
			if (!orgId) {
				alert('Please select an organization to update.');
				return;
			}

			const setAsRoot = !parentId; // Set to true if parentId is empty

			const updateData = {
				name: name,
				description: description,
				parentId: parentId || null, // Use null if parentId is empty
				setAsRoot: !parentId
			};

			console.log("Update data: " + updateData);

			fetch(`organization/update/${orgId}`, {
				method: 'PUT',
				headers: {
					'Content-Type': 'application/json',
					// Include other necessary headers like Authorization if required
				},
				body: JSON.stringify(updateData)
			})
				.then(response => {
					if (!response.ok) {
						throw new Error('Failed to update organization.');
					}
					if (response.status === 204) {
						alert('Organization updated successfully.');
						return null;
					}

					return response.json();
				})
				.then(data => {
					alert('Organization updated successfully.');
					// Optionally refresh or update the dropdown or UI
				})
				.catch(error => {
					console.error('Error:', error);
					alert('Error updating organization: ' + error.message);
				});
		} else {
			alert('You do not have permission to update organization.');
		}

	});

	document.querySelector('#addMemberButton').addEventListener('click', addMember);
});
