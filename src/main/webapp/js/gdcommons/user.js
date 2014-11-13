var page_size = 10;
function initUserList(){
	$("#data_user").html("<tr><td><img src='images/icon/loading2.gif'/></td></tr>");
	var page_no = $("#pageNum").text()
	var filter_name = $("#search_filter_name").val();
	var role_type = $("#search_role_type").val();
	$.ajax({
		type : "POST",
		url : contextPath + "/user_list.do",
		data : {pageNum:page_no,pageSize:page_size,filterName:filter_name,roleType:role_type},
		success : function(page) {
//			var str = "";
			$("#data_user").empty();
			var table = document.getElementById("data_user");
			if(page != null && page.list.length > 0){
				for(var i=0;i<page.list.length;i++){
					var roleType = "";
					var role_type = page.list[i].role_type;
					if(page.list[i].role_type =="cfm"){
	                	roleType = "资源管理员";
	                }else if(page.list[i].role_type == "user"){
	                	roleType = "业务管理员";
					}else if(page.list[i].role_type == "sys"){
						roleType = "系统管理员";
					}
					
					var tr = document.createElement("tr"); 
					var td1 = document.createElement("td");
					var td2 = document.createElement("td");
					var td3 = document.createElement("td");
					var td4 = document.createElement("td");
					var span1 = document.createElement("span");
					var span2 = document.createElement("span");
					
					td1.setAttribute("style", "width:20%;");
					td2.setAttribute("style", "width:20%;");
					td3.setAttribute("style", "width:30%;");
					td4.setAttribute("style", "width:30%;");
					td4.className = "opera";
					span1.setAttribute("onclick", "modifyUser('" + page.list[i].id + "')");
					span2.setAttribute("onclick", "deleteUser('" + page.list[i].id + "')");
					
					td1.appendChild(document.createTextNode(page.list[i].user_name));
					td2.appendChild(document.createTextNode(roleType));
					td3.appendChild(document.createTextNode(page.list[i].login_name));
					
					span1.appendChild(document.createTextNode(" [编辑] "));
					span2.appendChild(document.createTextNode(" [删除] "));
					
					if(role_type != 'sys'){
						td4.appendChild(span1);
						td4.appendChild(span2);
					}else{
						td4.appendChild(document.createTextNode(""));
					}
					
					tr.appendChild(td1);
					tr.appendChild(td2);
					tr.appendChild(td3);
					tr.appendChild(td4);
					table.appendChild(tr);
					/*
					str += "<tr><td width='20%'>"+page.list[i].user_name+"</td>" +
							"<td width='20%'>"+roleType+"</td>" +
							"<td width='30%'>"+page.list[i].login_name+"</td>" +
							"<td width='30%' class='opera'>";
							if(role_type != 'sys'){
								str += "[<span onclick='modifyUser("+page.list[i].id+")'>编辑</span>] [<span onclick='deleteUser("+page.list[i].id+")'>删除</span>]";
							}
							"</td></tr>";
					*/
				}
			}else{
				var tr = document.createElement("tr"); 
				var td = document.createElement("td");
				td.setAttribute("colspan", "4");
				td.setAttribute("style", "text-align:center;color:red;");
				td.appendChild(document.createTextNode(" 没有用户 "));
				tr.appendChild(td);
				table.appendChild(tr);
//				str += "<tr><td clospan='4'>没有用户</td></tr>"
			}
//			$("#data_user").html(str);
			
			$("#totalNumber").text(page.totalNumber);
			$("#totalPage").text(page.totalPage);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if (XMLHttpRequest.status == 403 && errorThrown == "Forbidden") {
				window.location.reload();
			} else {
				console.log(textStatus.error + "" + errorThrown);
			}
		},
		dataType : "json"
	});
}

function createrUser(){
	$("#createUserDialog #user_name").val('');
	$("#createUserDialog #login_name").val('');
	$("#createUserDialog #role_type").val('');
	$("#createUserDialog #password").val('');
	$("#createUserDialog #cfm_password").val('');
	$("#createUserDialog #span_error").text("");
	var diag = new Dialog();
	diag.Width = 460;
	diag.Height = 250;
	diag.Title = "创建用户";
	diag.InvokeElementId="createUserDialog";
	diag.OKEvent = function(){
		var username = $("#createUserDialog #user_name").val();
		var loginname = $("#createUserDialog #login_name").val();
		var roletype = $("#createUserDialog #role_type").val();
		var password = $("#createUserDialog #password").val();
		var cfmpassword = $("#createUserDialog #cfm_password").val();
		if(valid()){
			$.ajax({
				type : "POST",
				url : contextPath + "/user_check_login_name.do",
				data : {loginName:loginname},
				success : function(result) {
					if(result == 0){
						$.ajax({
							type : "POST",
							url : contextPath + "/user_add.do",
							data : {userName:username,loginName:loginname,password:password,roleType:roletype},
							success : function(result) {
								if(result == "1"){
									diag.close();
									initUserList();
								}
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
								if (XMLHttpRequest.status == 403 && errorThrown == "Forbidden") {
									window.location.reload();
								} else {
									console.log(textStatus.error + "" + errorThrown);
								}
							},
							dataType : "json"
						});
					}else{
						Dialog.alert("提示：用户名已存在！");
						return ;
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if (XMLHttpRequest.status == 403 && errorThrown == "Forbidden") {
						window.location.reload();
					} else {
						console.log(textStatus.error + "" + errorThrown);
					}
				},
				dataType : "json"
			});
		}
	};//点击确定后调用的方法
	diag.show();
}

function modifyUser(id){
	$("#modifyUserDialog #user_name").val('');
	$("#modifyUserDialog #role_type").val('');
	$("#modifyUserDialog #password").val('');
	$("#modifyUserDialog #cfm_password").val('');
	$("#modifyUserDialog #span_error").text("");
	if(id!=""){
		$.ajax({
			type : "POST",
			url : contextPath + "/user_beforeUpdate.do",
			data : {id:id},
			success : function(user) {
				if(user != null){
					$("#modifyUserDialog #user_name").val(user.user_name);
					$("#modifyUserDialog #login_name").text(user.login_name);
					$("#modifyUserDialog #role_type").get(0).value = user.role_type;
					$("#modifyUserDialog #old_password").text(user.password);
					$("#isModPassword").attr("checked",false);
					$(".isModPassword").hide();
					var diag = new Dialog();
					diag.Width = 360;
					diag.Height = 250;
					diag.Title = "修改用户";
					diag.InvokeElementId = "modifyUserDialog";
					diag.OKEvent = function(){
						var username = $("#modifyUserDialog #user_name").val();
						var roletype = $("#modifyUserDialog #role_type").val();
						var password = $("#modifyUserDialog #old_password").text();
						var password2 = $("#modifyUserDialog #password").val();
						if($("#modifyUserDialog #isModPassword").attr('checked')==undefined){
							if(valid2(0)){
								$.ajax({
									type : "POST",
									url : contextPath + "/user_update.do",
									data : {id:id,userName:username,password:password,roleType:roletype},
									success : function(result) {
										if(result == "1"){
											diag.close();
											initUserList();
										}
									},
									error : function(XMLHttpRequest, textStatus, errorThrown) {
										if (XMLHttpRequest.status == 403 && errorThrown == "Forbidden") {
											window.location.reload();
										} else {
											console.log(textStatus.error + "" + errorThrown);
										}
									},
									dataType : "json"
								});
							}
						}else{
							if(valid2(1)){
								$.ajax({
									type : "POST",
									url : contextPath + "/user_update.do",
									data : {id:id,userName:username,password:password2,roleType:roletype},
									success : function(result) {
										if(result == "1"){
											diag.close();
											initUserList();
										}
									},
									error : function(XMLHttpRequest, textStatus, errorThrown) {
										if (XMLHttpRequest.status == 403 && errorThrown == "Forbidden") {
											window.location.reload();
										} else {
											console.log(textStatus.error + "" + errorThrown);
										}
									},
									dataType : "json"
								});
							}
						}
					};//点击确定后调用的方法
					diag.show();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (XMLHttpRequest.status == 403 && errorThrown == "Forbidden") {
					window.location.reload();
				} else {
					console.log(textStatus.error + "" + errorThrown);
				}
			},
			dataType : "json"
		});
	}
	
}


function changeCheckBox(){
	if($("#modifyUserDialog #isModPassword").attr('checked')==undefined){
		$(".isModPassword").hide();
	}else{
		$(".isModPassword").show();
	}
}

function valid(){
	var username = $("#createUserDialog #user_name").val();
	var loginname = $("#createUserDialog #login_name").val();
	var roletype = $("#createUserDialog #role_type").val();
	var password = $("#createUserDialog #password").val();
	var cfmpassword = $("#createUserDialog #cfm_password").val();
	if(username == ""){
		$("#createUserDialog #span_error").text("真实姓名不能为空 !");
		return false;
	}else if(china(username)){
		$("#createUserDialog #span_error").text("真实姓名只能输入中文 !");
		return false;
	}
	if(loginname == ""){
		$("#createUserDialog #span_error").text("用户名不能为空 !");
		return false;
	}else if(checkValue(loginname)){
		$("#createUserDialog #span_error").text("用户名只能包含字母、数字、下划线、中划线 !");
		return false;
	}else{
		$("#createUserDialog #span_error").text("");
	}
	if(password == ""){
		$("#createUserDialog #span_error").text("密码不能为空 !");
		return false;
	}else if(password.length < 6 || password.length > 14){
		$("#createUserDialog #span_error").text("密码不能小于6位 不能大于14位!");
		return false;
	}else if(checkValue(password)){
		$("#createUserDialog #span_error").text("密码只能包含字母、数字、下划线、中划线!");
		return false;
	}
	if(cfmpassword == ""){
		$("#createUserDialog #span_error").text("确认密码不能为空 !");
		return false;
	}else if(cfmpassword.length < 6 || cfmpassword.length > 14){
		$("#createUserDialog #span_error").text("确认密码不能小于6位 不能大于14位!");
		return false;
	}
	if(password != cfmpassword){
		$("#createUserDialog #span_error").text("两次输入的密码不一致 !");
		$("#createUserDialog #password").val('');
		$("#createUserDialog #cfm_password").val('');
		return false;
	}
	return true;
}
function valid2(ret){
	var username = $("#modifyUserDialog #user_name").val();
	var roletype = $("#modifyUserDialog #role_type").val();
	var password = $("#modifyUserDialog #password").val();
	var cfmpassword = $("#modifyUserDialog #cfm_password").val();
	
	if(username == ""){
		$("#modifyUserDialog #span_error").text("真实姓名不能为空 !");
		return false;
	}else if(china(username)){
		$("#modifyUserDialog #span_error").text("真实姓名只能输入中文 !");
		return false;
	}
	if(ret != 0){
		if(password == ""){
			$("#modifyUserDialog #span_error").text("密码不能为空 !");
			return false;
		}else if(password.length < 6 || password.length > 14){
			$("#modifyUserDialog #span_error").text("密码不能小于6位 不能大于14位!");
			return false;
		}else if(checkValue(password)){
			$("#modifyUserDialog #span_error").text("密码只能包含字母、数字、下划线、中划线!");
			return false;
		}
		if(cfmpassword == ""){
			$("#modifyUserDialog #span_error").text("确认密码不能为空 !");
			return false;
		}else if(cfmpassword.length < 6 || cfmpassword.length > 14){
			$("#modifyUserDialog #span_error").text("确认密码不能小于6位 不能大于14位!");
			return false;
		}
		if(password != cfmpassword){
			$("#modifyUserDialog #span_error").text("两次输入的密码不一致 !");
			$("#modifyUserDialog #password").val('');
			$("#modifyUserDialog #cfm_password").val('');
			return false;
		}
	}
	return true;
}

function deleteUser(id){
	if(id!=""){
		$.ajax({
			type : "POST",
			url : contextPath + "/user_beforeUpdate.do",
			data : {id:id},
			success : function(user) {
				if(user!="" && user!=null){
					var roleType = "";
					if(user.role_type =="cfm"){
						roleType = "资源管理员";
					}else if(user.role_type == "user"){
						roleType = "业务管理员";
					}else if(user.role_type == "sys"){
						roleType = "系统管理员";
					}
					Dialog.confirm('您确认要删除'+roleType+'【'+user.user_name+'】吗？',function(){
						$.ajax({
							type : "POST",
							url : contextPath + "/user_delete.do",
							data : {id:id},
							success : function(result) {
								if(result == "1"){
									initUserList();
								}
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
								if (XMLHttpRequest.status == 403 && errorThrown == "Forbidden") {
									window.location.reload();
								} else {
									console.log(textStatus.error + "" + errorThrown);
								}
							},
							dataType : "json"
						});
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (XMLHttpRequest.status == 403 && errorThrown == "Forbidden") {
					window.location.reload();
				} else {
					console.log(textStatus.error + "" + errorThrown);
				}
			},
			dataType : "json"
		});
	}
}

//只能输入字母、数字、下划线、中划线
function checkValue(value){
	var reg = /^[A-Za-z0-9_-]+$/; 
	if (!reg.test(value)){
		return true;
	}
	return false;
}
//判读只能输入中文
function china(value){
	var reg = /^[\u4e00-\u9C52]+$/; //\u4e00-\u9fa5 中文。
	if (!reg.test(value)){
		return true;
	}
	return false;
}


//上一页
function PrevPage(){
	var pageNum = $("#pageNum").text();
	if(pageNum!=1){
		$("#pageNum").text(parseInt(pageNum)-1);
		initUserList();
	}
}
//下一页
function NextPage(){
	var pageNum = $("#pageNum").text();
	var totalPage = $("#totalPage").text();
	if(pageNum!=totalPage){
		$("#pageNum").text(parseInt(pageNum)+1);
		initUserList();
	}
}
//首页
function FirstPage(){
	$("#pageNum").text(1);
	initUserList();
}
//末页
function LastPage(){
	var totalPage = $("#totalPage").text();
	$("#pageNum").text(totalPage);
	initUserList();
}