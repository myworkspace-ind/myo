/*dataTest = [["1", "2024", "Period 1", "2024-01-01", "2024-03-31", "None"], ["2", "2024", "Period 2", "2024-04-01", "2024-06-30", "ok"]]
// Now you can use the data as needed, for example, with Handsontable
const container = document.getElementById('crud-table');
const hot = new Handsontable(container, {
    data: dataTest,
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
});*/

/**
 * Processing events of search OKR by emails.
 */
$(document).ready(function() {
    loadTableData();
});

/**
 * Load column width, header, initTable()
 */
function loadTableData() {
        
    $.ajax({
        url : _ctx + 'crud/loaddata',
        type : 'GET',
        dataType : 'json',
        contentType : 'application/json',
        success : function(res) {
            console.log("res=" + JSON.stringify(res));
    
            if (res) {
                tblProductData = res;
                tblProductColHeaders = res.colHeaders;
                tblProductColWidths = res.colWidths;
                initTable();
            }                
        },
        error : function (e) {
            console.log("Error: " + e);
        }
    });
}

function initTable() {
  var container = document.getElementById('crud-table');
  
  if (container == null) {
      return;
  }
  
  hotProduct = new Handsontable(container, {
        data: tblProductData.data,
        colHeaders: tblProductData.colHeaders,
        colWidths: tblProductData.colWidths,
        height: 300,
        rowHeaders: true,
        minRows: 8,
        currentRowClassName: 'currentRow',
        currentColClassName: 'currentCol',
        manualColumnResize: true,
        manualRowResize: true,
        minSpareRows : 1,
        contextMenu: true,
        licenseKey: 'non-commercial-and-evaluation'
  });
}

$(function () {
			    $('#new_model').click(function() {
			        const popUp = $('#popup');
			        const bootstrapModal = new bootstrap.Modal(popUp);
			        
			        bootstrapModal.show();
			        updateInfo();
			    });
			});