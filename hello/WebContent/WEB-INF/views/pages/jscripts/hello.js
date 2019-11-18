/**
 * 
 */

$(document).ready(function() {
	$("#checkBtn").click(callAjax);
	getUserList();
});

function getUserList() {
	$("#userTableBody tr").remove();
	$
			.ajax({
				url : "/hello/userList",
				type : "GET",
				dataType : "json",
				data : "",
				contentType : "application/json",
				success : function(data) {
					$("#userTableBody tr").remove();
					for (i = 0; i < data.userList.length; i++) {
						trString = "<tr><td>"
								+ data.userList[i].name
								+ "</td>"
								+ "<td>"
								+ data.userList[i].place
								+ "</td>"
								+ "<td><button value='"
								+ data.userList[i].userId
								+ "' onclick = deleteUser(this) operationType='edit'> Edit </button></td>"
								+ "<td><button value='"
								+ data.userList[i].userId
								+ "' onclick = deleteUser(this) operationType='delete'> Delete </button></td></tr>";
						$("#userTableBody").append(trString);
					}
				}
			});

};

function deleteUser(button) {
	reqUrl = "";

	if (button.getAttribute('operationtype') == 'delete') {
		reqUrl = "/hello/deleteUser";
	} else {
		reqUrl = "/hello/getUser";
	}

	$.ajax({
		url : reqUrl,
		data : button.value,
		dataType : "json",
		contentType : "application/json",
		type : "post",
		success : function(data) {

			if (button.getAttribute('operationtype') == 'edit') {
				$("#checkBtn").text("Update Record")
				$("#userName").val(data.user.name);
				$("#userPlace").val(data.user.place);
				$("#recordId").val(data.user.userId);
			}
			if (data.errors) {
				$("#contents").text(data.errors);
			}
			getUserList();
		}

	});

};

function callAjax() {

	var formData = $("#userForm").serializeArray();
	var jsonData = formData2Json(formData);
	reqUrl = "/hello/userinfo";
	if ($("#checkBtn").text() == "Update Record") {
		reqUrl = "/hello/updateUser";
	}

	$.ajax({
		url : reqUrl,
		type : "POST",
		dataType : "json",
		data : jsonData,
		contentType : "application/json",
		success : function(data) {

			if ($("#checkBtn").text() == "Update Record") {
				$("#checkBtn").text("Submit");
				$("#userName").val("");
				$("#userPlace").val("");
			}

			if (data.errors) {
				$("#contents").text(data.errors);
			}
			getUserList();
		}
	});
};

function formData2Json(formData) {

	var jsonData = "{";

	for (i = 0; i < formData.length; i++) {
		jsonObject = JSON.parse(JSON.stringify(formData[i]));
		jsonData += "\"" + jsonObject.name + "\"" + ":" + "\""
				+ jsonObject.value + "\"";

		jsonData += formData.length == (i + 1) ? "" : ",";
	}
	return jsonData + "}";
};