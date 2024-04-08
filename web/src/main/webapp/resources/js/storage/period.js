import Handsontable from 'handsontable';
import 'handsontable/dist/handsontable.full.min.css';

const container = document.querySelector('#example');

		data = [
			  [
			    false,
			    "First period",
			    "01/01/2024",
			    "31/03/2024",
			    "None"
			  ],
			  [
				 false,
				 "Second period",
				 "01/04/2024",
				 "31/06/2024",
				 "None"
			],			 
				  
		];
		
		
		const hot = new Handsontable(container, {
			data,
			  height: 450,
			  width: 1000,
			  colWidths: [170, 156, 222, 130, 130, 120, 120],
			  colHeaders: [
			    "Period name",
			    "Start date",
			    "End date",
			    "Note",
			  ],
			  columns: [
			    { data: 1, type: "text" },
			    {
					data: 2,
				    type: "date",
				    allowInvalid: false,
				},
				{
					data: 3,
					type: "date",
					allowInvalid: false,
				},
			    { data: 4, type: "text" },

			  ],
		  rowHeaders: true,
		  height: 'auto',
		  autoWrapRow: true,
		  autoWrapCol: true,
		  licenseKey: 'non-commercial-and-evaluation' // for non-commercial use only