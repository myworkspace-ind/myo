  /**
   * Processing events of search OKR by emails.
   */
  $(document).ready(function() {

      $('#frmStorageSearch').submit(function(e) {
		  // Simple validate
		  var emails = $('#emails').val();
		  if (emails == "") {
			  alert("Input emails (Separated by comma) please!");
			  return false;
		  }
		  console.log('frmStorageSearch.Processing...');
          e.preventDefault();

          var frm = $('#frmStorageSearch');

          var frmData = new FormData(this);

		  $('#spinner').show();
          $.ajax({
              url : frm.attr('action'),
              type : frm.attr('method'),
              enctype : frm.attr('enctype'),
              data : frmData,
              processData : false,
              contentType : false,
              success : function(result) {
				  storageDataObj = result;
                  console.log("storageDataObj:" + storageDataObj);

                  storageHotObj.loadData(storageDataObj);
                  
                  $('#spinner').hide();
                  $('#storage-action-move').show();
              },
              error : function() {
                  console.log("Error!");
              }
          });
      });

  });
  
  
