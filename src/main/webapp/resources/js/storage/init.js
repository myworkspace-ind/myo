/**
 * 
 */
var storageDataObj;
var storageHotObj;
var colHeaders;

var colWidths;
        
 
$(document).ready(function () {
	loadTableConfiguration();

	$('#storage-action-move').hide();

//	loadOKR();

});
               
function initTable() {
	var containerCustomer = document.getElementById('storageObjTable');
	storageHotObj = new Handsontable(containerCustomer, {
        data: storageDataObj,
        colHeaders: colHeaders,
        colWidths: colWidths,
        rowHeaders: true,
        minRows: 10,
        currentRowClassName: 'currentRow',
        currentColClassName: 'currentCol',
    	manualColumnResize: true,
        manualRowResize: true,
        minSpareRows : 1, //Thêm dòng sau khi tab hoặc enter dòng cuối
        contextMenu: true,
        licenseKey: 'non-commercial-and-evaluation'
	});		
}
        
// Load danh sách OKR 
function loadOKR() {
   var url = _ctx + '/obj/byemail/abc@test.com';
        
    $.ajax({
        url : url,
        type : 'GET',
        dataType : 'json',
        contentType : 'application/json',
        success : function(res) {

        	if (res && res.length > 0) {
                storageDataObj = res;
                console.log("storageDataObj: " + JSON.stringify(storageDataObj));
            }
            
            storageHotObj.loadData(storageDataObj);                  
        },
        error : function (e) {
            console.log("Error: " + e);
        }
    });
}

/**
 * Load column width, header, initTable()
 */
function loadTableConfiguration() {
        
    $.ajax({
        url : _ctx + 'meta-table/obj',
        type : 'GET',
        dataType : 'json',
        contentType : 'application/json',
        success : function(res) {
			console.log("res=" + JSON.stringify(res));
	
        	if (res) {
                colHeaders = res.colHeaders;
                colWidths = res.colWidths;
                storageDataObj = res.data;
                initTable();
            }                
        },
        error : function (e) {
            console.log("Error: " + e);
        }
    });
}
