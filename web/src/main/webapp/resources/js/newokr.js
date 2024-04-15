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
        url : _ctx + 'newokr/load/okr',
        type : 'GET',
        dataType : 'json',
        contentType : 'application/json',
        success : function(res) {
            console.log("res=" + JSON.stringify(res));
    
            if (res) {
                tblOkrData = res;
                tblOkrColHeaders = res.colHeaders;
                tblOkrColWidths = res.colWidths;
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
  var container = document.getElementById('tblOkr');
  
  hotOkr = new Handsontable(container, {
        data: tblOkrData.data,
        colHeaders: tblOkrData.colHeaders,
        colWidths: tblOkrData.colWidths,
        hiddenColumns: {
            columns: [0],
            indicators: true,
        },
        height: 800,
        rowHeaders: true,
        minRows: 10,
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
  $('#btnSave').click(function(e) {
     $('#frmOkr').submit();
  });

  $('#frmOkr').submit(function(e) {
      e.preventDefault();
      
      var frm = $('#frmOkr');
      var frmData = new FormData(this);
      // Get header name
      var colHeaderData =  hotOkr.getColHeader();
      
      // Get data from Handsontable
      var tableData = hotOkr.getData();

      var hotTableData = {header: colHeaderData, data: tableData};

      var dataListJson = JSON.stringify(tableData);


      frmData.append("colHeaders", colHeaderData);
      frmData.append("data", dataListJson);
          
      $.ajax({
          url : _ctx + frm.attr('action'),
          type : frm.attr('method'),
          enctype : frm.attr('enctype'),
          data : frmData,
          processData : false,
          contentType : false,
          success : function(result) {
              console.log(result);
              alert("Save OK.");
          },
          error : function() {
              alert("Save failed.");
          }
      });
  });
});

