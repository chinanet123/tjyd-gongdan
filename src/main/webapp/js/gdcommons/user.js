function initEcloudS3Page() {
	$("#main-jqgrid").jqGrid({
		url:contextPath + "/s3_json.do",
		datatype: 'json',
		mtype:'GET',
		jsonReader:{
		root : "data", 
		page : "page",
		total: "totalPages",
		records: "recordCount",
		repeatitems: false,
		id: "0"
	  },
	    colNames:['ID','名称', 'accessKey','secretKey'],  
	    colModel :[                        
	      {name:'id', index:'id',align:'center', width:55}, 
	      {name:'name', index:'name', align:'center', width:90}, 
	      {name:'accessKey',index:'accessKey', width:150, align:'center'}, 
	      {name:'secretKey',index:'secretKey', width:80, align:'center'}
	      ],
	    pager: '#pager',
	    rowNum:10,
	    rowList:[10,20,30],
	    sortname: 'id',
	    sortorder: 'desc',
	    viewrecords: false,
	    gridview: true,
	    autowidth:true,
	    //width: 800,
	    height: 250
  });
	
	
	//添加：
	  $( "#btnAdd" ).button().click(function() {
			clearForm();
			$( "#form_operation" ).val("add");
			$( "#dialog-form" ).dialog({title:"添加S3"});
			$( "#dialog-form" ).dialog( "open" );
		});
	  
	 //修改：
	  $( "#btnModify" ).button().click(function() {
			clearForm();			
			var id = jQuery("#main-jqgrid").jqGrid('getGridParam','selrow');
			if (id)	{
				var ret = jQuery("#main-jqgrid").jqGrid('getRowData',id);
				$("#s3_id").val(ret.id);
				$("#s3_name").val(ret.name);
				$("#s3_accesskey").val(ret.accessKey);
				$("#s3_secretkey").val(ret.secretKey);	
			} else { 
				alert("请选择一行。");
				return;
			}
			
			$( "#form_operation" ).val("modify");
			$( "#dialog-form" ).dialog({title:"修改S3"});
			$( "#dialog-form" ).dialog( "open" );
			$("#main-jqgrid").jqGrid().trigger("reloadGrid");
		});
	
	
	//对话框：
	  $( "#dialog-form" ).dialog({
			autoOpen: false,
			height: 300,
			width: 400,
			modal: true,
			buttons: {
				"保存": function() {
					if($( "#form_operation" ).val() == "add") {
						createEcloudS3();
					} else {
						modifyEcloudS3();
					}
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			},
		});
	  
	  //提交添加：
		 function createEcloudS3() {
			if( validateForm() ) {
				 submitAddForm();
			   }
			}
		   
		function submitAddForm() {
			var name = $.trim($("#s3_name").val());
			var accessKey = $.trim($("#s3_accesskey").val());
			var secretKey = $.trim($("#s3_secretkey").val());
			alert(name);
				
			$.ajax({
				type: "POST",
				url:contextPath + "/s3_ajax_add.do",
				data: {name:name,accessKey:accessKey,secretKey:secretKey},
				success: function(ajaxResponse) {
					$("#main-jqgrid").jqGrid().addRowData(ajaxResponse.result.id,ajaxResponse.result,'first');
					$("#main-jqgrid").jqGrid().setSelection(ajaxResponse.result.id);
					$("#dialog-form").dialog( "close" );
						
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus.error);
						alert(errorThrown);
					},
					dataType : "json"
				});			
			}

		 //提交修改：
		  function modifyEcloudS3() {
				if( validateForm() ) {
					submitModifyForm();
				}
			}
		 
			function submitModifyForm() {
				var id =  $("#s3_id").val();
				var name =  $.trim($("#s3_name").val());
				var accessKey = $.trim($("#s3_accesskey").val());
				var secretKey = $.trim($("#s3_secretkey").val());			
				$.ajax({
					type: "POST",
					url:contextPath + "/s3_ajax_modify.do",
					data: {id:id,name:name,accessKey:accessKey,secretKey:secretKey},
					success: function(ajaxResponse) {
						//alert(ajaxResponse.result.id);
						$("#main-jqgrid").jqGrid().trigger("reloadGrid");
						$( "#dialog-form" ).dialog( "close" );
						
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus.error);
						alert(errorThrown);
					},
					dataType : "json"
				});			
			}
	  
	  
	  $( "#btnRefresh" ).button().click(function() {
			$("#main-jqgrid").jqGrid().trigger("reloadGrid");
		});
		
		$( "#btnDelete" ).click(function() {
			deleteRow();
		});
		
	  
	 function clearForm() {
			$("#dialog-form input[type='text']").each(function(index,node) {
				$(this).val("");
			});		
			$('#errorMessages').empty();
		}
	 
	 function deleteRow() {
			var id = jQuery("#main-jqgrid").jqGrid('getGridParam','selrow');			
			if (id)	{
				var ret = jQuery("#main-jqgrid").jqGrid('getRowData',id);
				var sid = ret.id;
				if( confirm("您确定要删除此S3吗？") ) {
					ajaxDeleteEcloudS3(sid);
				}
			} else { 
				alert("请选择一行。");
				return;
			}
		}
	 
		function ajaxDeleteEcloudS3(id) {
			
			$.ajax({
				type: "POST",
				url: contextPath  + "/s3_ajax_delete.do",
				data: {sid:id},
				success: function(ajaxResponse) {
					//alert(ajaxResponse.result.id);
					$("#main-jqgrid").jqGrid().trigger("reloadGrid");
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus.error);
					alert(errorThrown);
				},
				dataType : "json"
			});
		}
		
		function validateForm() {
			
	        var errorList = $('#errorMessages');
	        
			errorList.empty();
			
			var bValid = true;
			
			$("#dialog-form input[type='text']").each(function(index,node) {
				bValid = bValid && node.checkValidity();
				if(!bValid) {
					//Find the field's corresponding label
	                var label = $('label[for=' + node.id + ']');
	                //alert(label.id + label.html());
	                //Opera incorrectly does not fill the validationMessage property.
	                var message = node.validationMessage || 'Invalid value.';
	                
	                errorList.show().append('<li><span>' + label.html() + '</span> ' + message + '</li>');
	                //alert( '<li><span>' + label.html() + '</span> ' + message + '</li>' );
				}
			});		
			
			$("#dialog-form input[type='number']").each(function(index,node) {
				bValid = bValid && node.checkValidity();
				if(!bValid) {
					//Find the field's corresponding label
	                var label = $('label[for=' + node.id + ']');
	                //alert(label.id + label.html());
	                //Opera incorrectly does not fill the validationMessage property.
	                var message = node.validationMessage || 'Invalid value.';
	                
	                errorList.show().append('<li><span>' + label.html() + '</span> ' + message + '</li>');
	                //alert( '<li><span>' + label.html() + '</span> ' + message + '</li>' );
				}
			});	
			$("#dialog-form input[type='email']").each(function(index,node) {
				bValid = bValid && node.checkValidity();
				if(!bValid) {
					//Find the field's corresponding label
	                var label = $('label[for=' + node.id + ']');
	                //alert(label.id + label.html());
	                //Opera incorrectly does not fill the validationMessage property.
	                var message = node.validationMessage || 'Invalid value.';
	                
	                errorList.show().append('<li><span>' + label.html() + '</span> ' + message + '</li>');
				}
			});	
			return bValid;
		}
	

}


