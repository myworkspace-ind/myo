const container = document.querySelector('#example1');
    const data = [
    	  ['', 'Tesla', 'Nissan', 'Toyota', 'Honda', 'Mazda', 'Ford'],
    	  ['2017', 10, 11, 12, 13, 15, 16],
    	  ['2018', 10, 11, 12, 13, 15, 16],
    	  ['2019', 10, 11, 12, 13, 15, 16],
    	  ['2020', 10, 11, 12, 13, 15, 16],
    	  ['2021', 10, 11, 12, 13, 15, 16]
    	];

    	const hot = new Handsontable(container, {
    	  data,
    	  startRows: 5,
    	  startCols: 5,
    	  height: 'auto',
    	  width: 'auto',
    	  colHeaders: true,
    	  minSpareRows: 1,
    	  autoWrapRow: true,
    	  autoWrapCol: true,
    	  licenseKey: 'non-commercial-and-evaluation'
    	});