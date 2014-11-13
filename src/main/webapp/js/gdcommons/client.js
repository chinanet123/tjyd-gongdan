var pageSize = 10;
function initCustomerList(){
	$("#data_client").html("<tr><td><img src='images/icon/loading2.gif'/></td></tr>");
	var page_no = $("#pageNum").text();
	var filter_name = $("#search_filter_name").val();
	$.ajax({
		type : "POST",
		url : contextPath + "/client_list.do",
		data : {pageNum:page_no,pageSize:pageSize,filterName:filter_name},
		success : function(page) {
			$("#data_client").empty();
			var table = document.getElementById("data_client");
			
			if(page != null && page.list.length > 0){
				for(var i=0;i<page.list.length;i++){
					var tr = document.createElement("tr"); 
					
					var td1 = document.createElement("td");
					td1.setAttribute("width", "20%");
					
					var td2 = document.createElement("td");
					td2.setAttribute("style", "width:50%;text-align:left;padding-left:10px;");
					
					var td3 = document.createElement("td");
					td3.setAttribute("width", "30%");
					td3.className="opera";
					
					var form = document.createElement("form");
					form.setAttribute("name", "form" + page.list[i].customerId);
					form.setAttribute("id", "form" + page.list[i].customerId);
					form.setAttribute("method", "POST");
					form.setAttribute("action", "");
					
					td1.appendChild(document.createTextNode(page.list[i].customerId));
					td2.appendChild(document.createTextNode(page.list[i].customerName));
					
					var span1 = document.createElement("span");
					var span2 = document.createElement("span");
					var span3 = document.createElement("span");
					
					span1.appendChild(document.createTextNode(" [资源概况] "));
					span2.appendChild(document.createTextNode(" [工单列表] "));
					span3.appendChild(document.createTextNode(" [查看] "));
					span1.setAttribute("onclick", "resourceInfo('"+page.list[i].customerId+"')");
					span2.setAttribute("onclick", "businessList('"+page.list[i].customerId+"')");
					span3.setAttribute("onclick", "clientDetail('"+page.list[i].customerId+"')");
					
					var input = document.createElement("input");
					input.setAttribute("name", "customerId");
					input.setAttribute("type", "hidden");
					input.setAttribute("value", page.list[i].customerId);
					form.appendChild(span1);
					form.appendChild(span2);
					form.appendChild(span3);
					form.appendChild(input);
					
					td3.appendChild(form);
					
					if(i%2 == 0){
						tr.setAttribute("background","#FEEFFF");
					}
					tr.appendChild(td1);
					tr.appendChild(td2);
					tr.appendChild(td3);
					table.appendChild(tr);
				}
			}else{
				var tr = document.createElement("tr"); 
				var td = document.createElement("td");
				td.setAttribute("colspan", "4");
				td.setAttribute("style", "text-align:center;color:red;");
				td.appendChild(document.createTextNode(" 没有客户 "));
				tr.appendChild(td);
				table.appendChild(tr);
			}
			/*
//			jQuery调用，IE9不能正常显示
			var str = "";
			if(page != null && page.list.length > 0){
				for(var i=0;i<page.list.length;i++){
					str += '<form></form><tr><td width="20%">'+page.list[i].customerId+'</td>' +
							'<td width="50%" style="text-align:left;padding-left:10px;">'+page.list[i].customerName+'</td>' +
							'<td width="30%" class="opera">' +
								'<form action="" name="form'+page.list[i].customerId+'" id="form'+page.list[i].customerId+'" method="POST">' +
									'[<span onclick="resourceInfo(\''+page.list[i].customerId+'\');">资源概况</span>] [<span onclick="businessList(\''+page.list[i].customerId+'\');">工单列表</span>] [<span onclick="clientDetail(\''+page.list[i].customerId+'\');">查看</span>]'+
									'<input name="customerId" type="hidden" value="'+page.list[i].customerId+'"/>';
								'</form>' +
							'</td></tr>';
				}
			}else{
				str += "<tr><td clospan='4'>没有客户</td></tr>"
			}
			$("#data_client").html(str);
			*/
			
			$("#totalNumber").html(page.totalNumber);
			$("#totalPage").html(page.totalPage);
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

function resourceInfo(customer_id){
	var form = document.getElementById("form"+customer_id);
	form.action = "clientResource.htm";
	form.submit();
}
function businessList(customer_id){
	var form = document.getElementById("form"+customer_id);
	form.action = "clientTicketList.htm";
	form.submit();
}
function clientDetail(customer_id){
	var form = document.getElementById("form"+customer_id);
	form.action = "clientDetail.htm";
	form.submit();
}

//上一页
function PrevPage(){
	var pageNum = $("#pageNum").text();
	if(pageNum!=1){
		$("#pageNum").text(parseInt(pageNum)-1);
		initCustomerList();
	}
}
//下一页
function NextPage(){
	var pageNum = $("#pageNum").text();
	var totalPage = $("#totalPage").text();
//	alert(pageNum +" , "+totalPage);
	if(pageNum != totalPage){
		$("#pageNum").text(parseInt(pageNum)+1);
		initCustomerList();
	}
}
//首页
function FirstPage(){
	$("#pageNum").text(1);
	initCustomerList();
}
//末页
function LastPage(){
	var totalPage = $("#totalPage").text();
	$("#pageNum").text(totalPage);
	initCustomerList();
}