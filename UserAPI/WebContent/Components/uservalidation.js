$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateUserForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidUserIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "UserService",   
			type : type,  
			data : $("#formUser").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onUserSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onUserSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divUserGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidUserIDSave").val("");  
	$("#formUser")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidUserIDSave").val($(this).closest("tr").find('#hidUserIDUpdate').val());     
	$("#UserFirstName").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#UserLastName").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#UserAddress").val($(this).closest("tr").find('td:eq(2)').text());
	$("#UserAccountNo").val($(this).closest("tr").find('td:eq(3)').text());   
	$("#UserContactNo").val($(this).closest("tr").find('td:eq(4)').text()); 
	$("#UserEmail").val($(this).closest("tr").find('td:eq(5)').text());   
	$("#UserNIC").val($(this).closest("tr").find('td:eq(6)').text());
}); 




//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "UserService",   
		type : "DELETE",   
		data : "UID=" + $(this).data("userid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onUserDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onUserDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divUserGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validateUserForm() 
{  
	// NAME-----------------------
	if ($("#UserFirstName").val().trim() == "")  
	{   
		return "Insert Name.";  
	} 
	
	// UserLastName---------------------------  
	if ($("#UserLastName").val().trim() == "")  
	{   
		return "Insert UserLastName.";  
	}
	
	// UserAddress------------------------------
	if ($("#UserAddress").val().trim() == "")  
	{   
		return "Insert UserAddress.";  
	}
	
	// UserAccountNo-------------------------------
	if ($("#UserAccountNo").val().trim() == "")  
	{   
		return "Insert UserAccountNo.";  
	}
	
	// UserContactNo---------------------------  
	 var tmpMobile = $("#UserContactNo").val().trim();
		if (!$.isNumeric(tmpMobile)) 
		{
		return "Insert UserContactNo.";
		}
	// UserEmail-----------------------
	if ($("#UserEmail").val().trim() == "")  
	{   
		return "Insert UserEmail.";  
	} 
	
	// UserNIC---------------------------  
	if ($("#UserNIC").val().trim() == "")  
	{   
		return "Insert UserNIC.";  
	}
		
	return true; 
}