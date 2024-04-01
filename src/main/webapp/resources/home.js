/**
 * 
 */
var data = [
	['A1', 'B1', 'C1'],
	['A2', 'B2', 'C2'],
	['A3', 'B3', 'C3']
];

var container = document.getElementById('hot');
var hot = new Handsontable(container, {
	data: data,
	rowHeaders: true,
	colHeaders: true
});