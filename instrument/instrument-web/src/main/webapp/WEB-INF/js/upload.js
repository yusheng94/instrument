function doUpload(formData) {  
	    
	    $.ajax({  
	         url: '/instrument-web/upload' ,  
	         type: 'POST',  
	         data: formData,  
	         async: false,  
	         cache: false,  
	         contentType: false,  
	         processData: false,  
	         success: function () {  
	        	 submitForm();
	         },  
	         error: function () {  
	             alert("操作失败");  
	         }  
	    });  
	}
	
	function submitForm(){
		 $.ajax({
				url : "/instrument-web/generate",
				async :false,
				type : "POST",
				contentType :"application/json;charset=UTF-8",
				data :JSON.stringify({"tbl_service_wx_config":{
					"MCHT_NO":$("#MCHT_NO").val(),
					"MCH_ID":$("#MCH_ID").val(),
					"APP_ID":$("#APP_ID").val(),  
					"SUB_MCH_ID":$("#SUB_MCH_ID").val(), 
					"APP_KEY":$("#APP_KEY").val(), 
					"STATUS":$("#STATUS").val(),
					"REMARK":$("#REMARK").val(), 
					"CRT_USER":$("#CRT_USER").val(), 
					"CRT_TIME":"", 
					"UPDATE_TIME":""	
				}}),
				dataType : "json",
				success:function(){
					alert("操作成功");
				}
			});
		
	}