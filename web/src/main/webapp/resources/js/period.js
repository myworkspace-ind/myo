// Access the embedded period data
console.log(period);
dataTest = [["2024", "Period 1", "2024-01-01", "2024-03-31", "None"], ["2024", "Period 2", "2024-04-01", "2024-06-30", "ok"]]
// Now you can use the data as needed, for example, with Handsontable
const container = document.getElementById('periods-table');
const hot = new Handsontable(container, {
    data: period,
    rowHeaders: true,
    colHeaders: ['Year', 'Name', 'Start Date', 'End Date', 'Note'],
    columns: [
			    { data: 1, type: "text" },
			    { data: 2, type: "text" },
			    {
					data: 3,
				    type: "date",
				    allowInvalid: false,
				},
				{
					data: 4,
					type: "date",
					allowInvalid: false,
				},
			    { data: 5, type: "text" },

			  ],
				rowHeaders: true,
		  height: 'auto',
		  autoWrapRow: true,
		  autoWrapCol: true,

    licenseKey: 'non-commercial-and-evaluation'
});
