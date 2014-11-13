var pageSize = 10;
var untreated = "yes";
function initTicketList(){
	untreated = "no";
	$("#data_ticket").html("<tr><td><img src='images/icon/loading2.gif'/></td></tr>");
	var page_no = $("#pageNum").text();
	var filter_name = encodeURI($("#search_filter_name").val());
	var ticket_type = encodeURI($("#search_ticket_type").val());
	$.ajax({
		type : "POST",
		url : contextPath + "/ticket_get_ticket_List.do",
		data : {stringPageNum:page_no,stringPageSize:pageSize,ticketType:ticket_type,searchValue:filter_name},
		success : function(page) {
//			var str = "";
			$("#data_ticket").empty();
			var table = document.getElementById("data_ticket");
			
			if(page != null && page.list.length > 0){
				for(var i=0;i<page.list.length;i++){
					var ticketType = "";
					var ticket_type = page.list[i].ticketType;
					if(ticket_type =="open"){
	                	ticketType = "开通工单";
	                }else if(ticket_type == "modify"){
	                	ticketType = "变更工单";
					}else if(ticket_type == "close"){
						ticketType = "撤销工单";
					}
					
					var tr = document.createElement("tr"); 
					var td1 = document.createElement("td");
					var td2 = document.createElement("td");
					var td3 = document.createElement("td");
					var td4 = document.createElement("td");
					var td5 = document.createElement("td");
					var form = document.createElement("form");
					
					var input1 = document.createElement("input");
					var input2 = document.createElement("input");
					var input3 = document.createElement("input");
					
					var span1 = document.createElement("span");
					var span2 = document.createElement("span");
					
					td1.setAttribute("style", "width:15%;text-align:center;");
					td2.setAttribute("style", "width:30%;text-align:left;padding-left:10px;");
					td3.setAttribute("style", "width:15%;text-align:center;");
					td4.setAttribute("style", "width:15%;text-align:center;");
					td5.setAttribute("style", "width:25%;text-align:center;");
					td5.className = "opera";
					form.setAttribute("name", "form" + page.list[i].customerId + "" + page.list[i].ticketId);
					form.setAttribute("id", "form" + page.list[i].customerId + "" + page.list[i].ticketId);
					form.setAttribute("method", "POST");
					form.setAttribute("action", "");
					input1.setAttribute("name", "customerId");
					input1.setAttribute("type", "hidden");
					input1.setAttribute("value", page.list[i].customerId);
					input2.setAttribute("name", "ticketId");
					input2.setAttribute("type", "hidden");
					input2.setAttribute("value", page.list[i].ticketId);
					input3.setAttribute("name", "ticketType");
					input3.setAttribute("type", "hidden");
					input3.setAttribute("value", page.list[i].ticketType);
					span1.setAttribute("onclick", "modifyTicket('" + page.list[i].customerId + "','" + page.list[i].ticketId + "','" + page.list[i].ticketType + "')");
					span2.setAttribute("onclick", "viewTicket('" + page.list[i].customerId + "','" + page.list[i].ticketId + "','" + page.list[i].ticketType + "')");
					
					span1.appendChild(document.createTextNode(" [编辑] "));
					span2.appendChild(document.createTextNode(" [查看] "));
					
					td1.appendChild(document.createTextNode(page.list[i].ticketId));
					td2.appendChild(document.createTextNode(page.list[i].customerName));
					td3.appendChild(document.createTextNode(ticketType));
					td4.appendChild(document.createTextNode(page.list[i].receiveTime));
					if(roleType == 'cfm'){
						if(page.list[i].isModify == 1){
							form.appendChild(span1);
							form.appendChild(span2);
						}else{
							form.appendChild(span2);
						}
					}else if(roleType == 'user'){
						form.appendChild(span2);
					}
					form.appendChild(input1);
					form.appendChild(input2);
					form.appendChild(input3);
					td5.appendChild(form);
					if(i%2 == 0){
						tr.setAttribute("background","#FEEFFF");
					}
					tr.appendChild(td1);
					tr.appendChild(td2);
					tr.appendChild(td3);
					tr.appendChild(td4);
					tr.appendChild(td5);
					table.appendChild(tr);
					/*
//					jQuery调用，IE9不能正常显示
					str += '<form></form><tr><td width="15%">'+page.list[i].ticketId+'</td>' +
						'<td width="30%">'+page.list[i].customerName+'</td>' +
						'<td width="15%">'+ticketType+'</td>' +
						'<td width="15%">'+page.list[i].receiveTime+'</td>' +
						'<td width="25%" class="opera">' +
						'<form action="" name="form'+page.list[i].customerId+''+page.list[i].ticketId+'" id="form'+page.list[i].customerId+''+page.list[i].ticketId+'" method="POST">' +
							'<input name="customerId" type="hidden" value="'+page.list[i].customerId+'"/>' +
							'<input name="ticketId" type="hidden" value="'+page.list[i].ticketId+'"/>' +
							'<input name="ticketType" type="hidden" value="'+page.list[i].ticketType+'"/>';
						if(roleType == 'cfm'){
							if(page.list[i].isModify == 1){
								str += '[<span onclick="modifyTicket(\''+page.list[i].customerId+'\',\''+page.list[i].ticketId+'\',\''+page.list[i].ticketType+'\');">编辑</span>]' +
									  ' [<span onclick="viewTicket(\''+page.list[i].customerId+'\',\''+page.list[i].ticketId+'\',\''+page.list[i].ticketType+'\');">查看</span>]';
							}else{
								str += '[<span onclick="viewTicket(\''+page.list[i].customerId+'\',\''+page.list[i].ticketId+'\',\''+page.list[i].ticketType+'\');">查看</span>]';
							}
						}else if(roleType == 'user'){
							str += '[<span onclick="viewTicket(\''+page.list[i].customerId+'\',\''+page.list[i].ticketId+'\',\''+page.list[i].ticketType+'\');">查看</span>]';
						}
						'</form></td></tr>';
					*/
				}
			}else{
				var tr = document.createElement("tr"); 
				var td = document.createElement("td");
				td.setAttribute("colspan", "5");
				td.setAttribute("style", "text-align:center;color:red;");
				td.appendChild(document.createTextNode(" 没有工单 "));
				tr.appendChild(td);
				table.appendChild(tr);
//				str += "<tr><td clospan='4'>没有工单</td></tr>"
			}
//			$("#data_ticket").html(str);
			
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

function viewTicket (customerId,ticketId,ticketType){
	var form = document.getElementById("form"+customerId+""+ticketId);
	form.action = "ticketView.htm";
	form.submit();
}

function modifyTicket(customerId,ticketId,ticketType){
	var form = document.getElementById("form"+customerId+""+ticketId);
	form.action = "ticketEdit.htm";
	form.submit();
}

function initUntreatedTicketList(){
	untreated = "yes";
	$("#data_ticket").html("<tr><td><img src='images/icon/loading2.gif'/></td></tr>");
	var page_no = $("#pageNum").text();
	var filter_name = encodeURI($("#search_filter_name").val());
	var ticket_type = encodeURI($("#search_ticket_type").val());
	$.ajax({
		type : "POST",
		url : contextPath + "/ticket_get_ticket_List_is_modify.do",
		data : {stringPageNum:page_no,stringPageSize:pageSize,ticketType:ticket_type,searchValue:filter_name},
		success : function(page) {
//			var str = "";
			$("#data_ticket").empty();
			var table = document.getElementById("data_ticket");
			if(page != null && page.list.length > 0){
				for(var i=0;i<page.list.length;i++){
					var ticketType = "";
					var ticket_type = page.list[i].ticketType;
					if(ticket_type =="open"){
	                	ticketType = "开通工单";
	                }else if(ticket_type == "modify"){
	                	ticketType = "变更工单";
					}else if(ticket_type == "close"){
						ticketType = "撤销工单";
					}
					var tr = document.createElement("tr"); 
					var td1 = document.createElement("td");
					var td2 = document.createElement("td");
					var td3 = document.createElement("td");
					var td4 = document.createElement("td");
					var td5 = document.createElement("td");
					var form = document.createElement("form");
					
					var input1 = document.createElement("input");
					var input2 = document.createElement("input");
					var input3 = document.createElement("input");
					
					var span1 = document.createElement("span");
					var span2 = document.createElement("span");
					
					td1.setAttribute("style", "width:15%;text-align:center;");
					td2.setAttribute("style", "width:30%;text-align:left;padding-left:10px;");
					td3.setAttribute("style", "width:15%;text-align:center;");
					td4.setAttribute("style", "width:15%;text-align:center;");
					td5.setAttribute("style", "width:25%;text-align:center;");
					td5.className = "opera";
					form.setAttribute("name", "form" + page.list[i].customerId + "" + page.list[i].ticketId);
					form.setAttribute("id", "form" + page.list[i].customerId + "" + page.list[i].ticketId);
					form.setAttribute("method", "POST");
					form.setAttribute("action", "");
					input1.setAttribute("name", "customerId");
					input1.setAttribute("type", "hidden");
					input1.setAttribute("value", page.list[i].customerId);
					input2.setAttribute("name", "ticketId");
					input2.setAttribute("type", "hidden");
					input2.setAttribute("value", page.list[i].ticketId);
					input3.setAttribute("name", "ticketType");
					input3.setAttribute("type", "hidden");
					input3.setAttribute("value", page.list[i].ticketType);
					span1.setAttribute("onclick", "modifyTicket('" + page.list[i].customerId + "','" + page.list[i].ticketId + "','" + page.list[i].ticketType + "')");
					span2.setAttribute("onclick", "viewTicket('" + page.list[i].customerId + "','" + page.list[i].ticketId + "','" + page.list[i].ticketType + "')");
					
					span1.appendChild(document.createTextNode(" [编辑] "));
					span2.appendChild(document.createTextNode(" [查看] "));
					
					td1.appendChild(document.createTextNode(page.list[i].ticketId));
					td2.appendChild(document.createTextNode(page.list[i].customerName));
					td3.appendChild(document.createTextNode(ticketType));
					td4.appendChild(document.createTextNode(page.list[i].receiveTime));
					if(roleType == 'cfm'){
						if(page.list[i].isModify == 1){
							form.appendChild(span1);
							form.appendChild(span2);
						}else{
							form.appendChild(span2);
						}
					}else if(roleType == 'user'){
						form.appendChild(span2);
					}
					form.appendChild(input1);
					form.appendChild(input2);
					form.appendChild(input3);
					td5.appendChild(form);
					if(i%2 == 0){
						tr.setAttribute("background","#FEEFFF");
					}
					tr.appendChild(td1);
					tr.appendChild(td2);
					tr.appendChild(td3);
					tr.appendChild(td4);
					tr.appendChild(td5);
					table.appendChild(tr);
				/*
//				jQuery调用，IE9不能正常显示
					str += '<form></form><tr><td width="15%">'+page.list[i].ticketId+'</td>' +
						'<td width="30%">'+page.list[i].customerName+'</td>' +
						'<td width="15%">'+ticketType+'</td>' +
						'<td width="15%">'+page.list[i].receiveTime+'</td>' +
						'<td width="25%" class="opera">' +
						'<form action="" name="form'+page.list[i].customerId+''+page.list[i].ticketId+'" id="form'+page.list[i].customerId+''+page.list[i].ticketId+'" method="POST">' +
							'<input name="customerId" type="hidden" value="'+page.list[i].customerId+'"/>' +
							'<input name="ticketId" type="hidden" value="'+page.list[i].ticketId+'"/>' +
							'<input name="ticketType" type="hidden" value="'+page.list[i].ticketType+'"/>';
						if(roleType == 'cfm'){
							if(page.list[i].isModify == 1){
								str += '[<span onclick="modifyTicket(\''+page.list[i].customerId+'\',\''+page.list[i].ticketId+'\',\''+page.list[i].ticketType+'\');">编辑</span>]' +
									  ' [<span onclick="viewTicket(\''+page.list[i].customerId+'\',\''+page.list[i].ticketId+'\',\''+page.list[i].ticketType+'\');">查看</span>]';
							}else{
								str += '[<span onclick="viewTicket(\''+page.list[i].customerId+'\',\''+page.list[i].ticketId+'\',\''+page.list[i].ticketType+'\');">查看</span>]';
							}
						}else if(roleType == 'user'){
							str += '[<span onclick="viewTicket(\''+page.list[i].customerId+'\',\''+page.list[i].ticketId+'\',\''+page.list[i].ticketType+'\');">查看</span>]';
						}
						'</form></td></tr>';
				*/
				}
			}else{
				var tr = document.createElement("tr"); 
				var td = document.createElement("td");
				td.setAttribute("colspan", "5");
				td.setAttribute("style", "text-align:center;color:red;");
				td.appendChild(document.createTextNode(" 没有未工单 "));
				tr.appendChild(td);
				table.appendChild(tr);
//				str += "<tr><td clospan='4'>没有工单</td></tr>"
			}
//			$("#data_ticket").html(str);
			
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

//上一页
function PrevPage(){
	var pageNum = $("#pageNum").text();
	if(pageNum!=1){
		$("#pageNum").text(parseInt(pageNum)-1);
		if(untreated == "yes"){
			initUntreatedTicketList();
		}else{
			initTicketList();
		}
	}
}

//下一页
function NextPage(){
	var pageNum = $("#pageNum").text();
	var totalPage = $("#totalPage").text();
	if(pageNum != totalPage){
		$("#pageNum").text(parseInt(pageNum)+1);
		if(untreated == "yes"){
			initUntreatedTicketList();
		}else{
			initTicketList();
		}
	}
}
//首页
function FirstPage(){
	$("#pageNum").text(1);
	if(untreated == "yes"){
		initUntreatedTicketList();
	}else{
		initTicketList();
	}
}
//末页
function LastPage(){
	var totalPage = $("#totalPage").text();
	$("#pageNum").text(totalPage);
	if(untreated == "yes"){
		initUntreatedTicketList();
	}else{
		initTicketList();
	}
}