$(document).ready(function() {
    loadTableData();
});

/**
 * Load column width, header, initTable()
 */
function loadTableData() {
        
    $.ajax({
        url : _ctx + 'period/loaddata',
        type : 'GET',
        dataType : 'json',
        contentType : 'application/json',
        success : function(res) {
            console.log("res=" + JSON.stringify(res));
    
            if (res) {
                tblProductData = res;
                tblProductColHeaders = res.colHeaders;
                tblProductColWidths = res.colWidths;
                okrData = res.data;
                initTable();
            }                
        },
        error : function (e) {
            console.log("Error: " + e);
        }
    });
}

function initTable() {
  var container = document.getElementById('periods-table');
  
  if (container == null) {
        return;
    }
  
  hotProduct = new Handsontable(container, {
        data: tblProductData.data,
        colHeaders: tblProductData.colHeaders,
        colWidths: tblProductData.colWidths,
        height: 300,
        rowHeaders: true,
        minRows: 5,
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
			    $('#new_period').click(function() {
			        const popUp = $('#popup');
			        const bootstrapModal = new bootstrap.Modal(popUp);
			        
			        bootstrapModal.show();
			        updateInfo();
			    });
			});