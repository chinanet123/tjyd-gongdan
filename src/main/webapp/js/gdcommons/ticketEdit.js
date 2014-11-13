var ticketTypeFlag;
function suiteShare(o){
	var suite = new Object();
	if(o == '套餐1'){
		suite.ecu = '0.5';
		suite.price = '134';
		suite.volume = '25';
	}else if(o == '套餐2'){
		suite.ecu = '1';
		suite.price = '260';
		suite.volume = '50';
	}else if(o == '套餐3'){
		suite.ecu = '1.5';
		suite.price = '378';
		suite.volume = '75';
	}else if(o == '套餐4'){
		suite.ecu = '2';
		suite.price = '489';
		suite.volume = '100';
	}else if(o == '套餐5'){
		suite.ecu = '3 ';
		suite.price = '713';
		suite.volume = '150';
	}else if(o == '套餐6'){
		suite.ecu = '4 ';
		suite.price = '923';
		suite.volume = '200';
	}else if(o == '套餐7'){
		suite.ecu = '6 ';
		suite.price = '1344';
		suite.volume = '300';
	}else if(o == '套餐8'){
		suite.ecu = '8 ';
		suite.price = '1739';
		suite.volume = '400';
	}else if(o == '套餐9'){
		suite.ecu = '12 ';
		suite.price = '2522';
		suite.volume = '600';
	}else if(o == '套餐10'){
		suite.ecu = '16 ';
		suite.price = '3279 ';
		suite.volume = '800';
	}
	return suite;
}
function suiteExclusive(o,count) {
	var suite = new Object();
	if(o == '套餐1'){
		suite.ecu = '32';
		suite.price = '6365';
		suite.volume = '1600';
	}else if(o == '套餐2'){
		suite.ecu = '64';
		suite.price = '12360';
		suite.volume = '3200';
	}else if(o == '套餐3'){
		suite.ecu = '256';
		suite.price = '49600';
		suite.volume = '12800';
	}else if(o == "ECU定制包"){
		suite.ecu = 1;
		suite.price = 260;
		suite.volume = 50;
	}else if(o == "增值服务套餐1"){
		suite.ecu = 0;
		suite.price = 0;
		suite.volume = 0;
	}else if(o == "增值服务套餐2"){
		suite.ecu = 0;
		suite.price = 0;
		suite.volume = 0;
	}else if(o == "增值服务套餐3"){
		suite.ecu = 0;
		suite.price = 0;
		suite.volume = 0;
	}
	return suite;
}

function initTicketDetail(ticketId){
	$.ajax({
		type : "POST",
		url : contextPath + "/ticket_get_ticket_detail.json",
		data : {ticketId:ticketId},
		success : function(ticket) {
			var receiveTime = ticket.receiveTime;
			var orders = ticket.order;
			$("#ticket_type").val(ticket.ticketType);
			if(ticket.ticketType == "open"){
				if(orders.forWebsite == 'yes'){
					$("#filing-info").show();
					$("#filing_no").text(orders.filingNo==null?'':orders.filingNo);
					$("#filing_ip").text(orders.filingIp==null?'':orders.filingIp);
					$("#filing_domain").text(orders.filingDomain==null?'':orders.filingDomain);
					$("#modifyWebSite").hide();
					$("#modifyTiJiao").hide();
					$("#openTiJiao").hide();
					$("#closeTicket").hide();
				}else{
					$("#filing-info").hide();
					$("#openWebSite").hide();
					$("#modifyWebSite").hide();
					$("#modifyTiJiao").hide();
					$("#closeTicket").hide();
				}
				$("#close-ticket").hide();
			}else if(ticket.ticketType == "modify"){
				ticketTypeFlag="modify";
				if(orders.forWebsite == 'yes'){
					$("#filing-info").show();
					$("#filing_no").text(orders.filingNo==null?'':orders.filingNo);
					$("#filing_ip").text(orders.filingIp==null?'':orders.filingIp);
					$("#filing_domain").text(orders.filingDomain==null?'':orders.filingDomain);
					$("#modifyTiJiao").hide();
					$("#openTiJiao").hide();
					$("#openWebSite").hide();
					$("#closeTicket").hide();
				}else{
					$("#filing-info").hide();
					$("#openWebSite").hide();
					$("#modifyWebSite").hide();
					$("#openTiJiao").hide();
					$("#closeTicket").hide();
				}
				$("#close-ticket").hide();
				$("#edit #td_customer_id").text(ticket.customerId);
				$("#edit #td_cloud_platform").text(orders.cloudPlatform);
				$("#edit #td_cloud_url").text(orders.loginUrl);
				$("#edit #td_cloud_url").parent().children("th").text("云平台访问地址：");
				$("#edit #td_login_username").text(orders.loginUsername);
				$("#edit #td_login_password").text(orders.loginPassword);
				(orders.openPorts == null || orders.openPorts == "")?$("#open_port").val(''):$("#open_port").val(orders.openPorts);
				if(orders.productType == "share"){
					(orders.ip == null || orders.ip == "")?$("#ip").val(''):$("#ip").val(orders.ip);
				}else if(orders.productType == "exclusive"){
					var ip = orders.ip;
					if(orders.ip == null || orders.ip == ""){
						$("#ip1").val('');
						$("#ip2").val('');
					}else{
						$("#ip1").val(ip.split("-")[0]);
						$("#ip2").val(ip.split("-")[1]);
					}
				}
				
				(orders.keypairs == null || orders.keypairs == "")?$("#keypairs").val(''):$("#keypairs").val(orders.keypairs);
				(orders.securitys == null || orders.securitys == "")?$("#securitys").val(''):$("#securitys").val(orders.securitys);
				(ticket.remark == null || ticket.remark == "")?$("#ticket_remark").val(''):$("#ticket_remark").val(ticket.remark);
			}else if(ticket.ticketType == "close"){
				$("#edit-sys").hide();
				$("#filing-info").hide();
				$("#ticket-orders").hide();

				$("#is_fees_owed").text((orders.isFeesOwed == null || orders.isFeesOwed =="") ? '':orders.isFeesOwed);
				$("#close_time").text((orders.closeTime == null || orders.closeTime =="") ? '':orders.closeTime);
				$("#reason").text((orders.reason == null || orders.reason =="") ? '':orders.reason);
				$("#detailed_reason").text((orders.detailedReason == null || orders.detailedReason =="") ? '':orders.detailedReason);
				(ticket.remark == null || ticket.remark == "")?$("#ticket_remark").val(''):$("#ticket_remark").val(ticket.remark);
				
				$("#openWebSite").hide();
				$("#modifyWebSite").hide();
				$("#openTiJiao").hide();
				$("#modifyTiJiao").hide();
			}
			
			$("#receiveTime").text(receiveTime.split(" ")[0]);
			var start = orders.contractSignedDate;
			var end = orders.contractExpiredDate;
			$("#contractId").text(orders.contractId);
			$("#contranctStartEndTime").text(start.split(" ")[0].replace(/-/g, "/") + " —— " + end.split(" ")[0].replace(/-/g, "/"));
			var product_type = orders.productType;
			var productType = "";
			if(product_type == 'share'){
				productType = "共享云";
				$("#storage").hide();
				$("#cloud-exclusive").hide();
				showShareResouce(orders.suites,orders);
			}else if(product_type == 'exclusive'){
				productType = "专享云";
				$("#storage").hide();
				$(".cloud-share").hide();
				showExclusiveResouce(orders.suites,orders);
			}else if(product_type == 'storage'){
				productType = "云存储";
				$("#cloud").hide();
				showStroge(orders);
			}
			$("#product_type").val(product_type);
			$("#resourceProductType").text(productType);
			$("#availableAt").text(orders.availableAt.split(" ")[0]);
			var isWebsite = orders.forWebsite;
			$("input[type=radio][value="+isWebsite+"][name='isWebSite']").attr("checked",'checked');
			$("input[type=radio][name='isWebSite']").attr("disabled","disabled"); 
//			if(isWebsite == "yes"){
//				$("#isWebSite").show();
			$("#portsNeedToOpen").text(orders.portsNeedToOpen);
			$("#remark").text(orders.memo == null ? "" : orders.memo);
//			}else{
//				$("#isWebSite").hide();
//			}
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

function changeCloudPlatForm (){
	if($("#cloud_platform").val()=="xidan"){
		$("#cloud_url").val("http://xidan.com");
	}else if($("#cloud_platform").val()=="fangzhuang"){
		$("#cloud_url").val("http://fangzhuang.com");
	}else if($("#cloud_platform").val()=="yizhuang"){
		$("#cloud_url").val("http://yizhuang.com");
	}else if($("#cloud_platform").val()=="anhua"){
		$("#cloud_url").val("http://anhua.com");
	}else if($("#cloud_platform").val()==""){
		$("#cloud_url").val('');
	}
}


function showShareResouce(suites,orders){
	var ecu = 0;
	var price = 0.0;
	var volume = 0;
	var html = "<div id='suite' style=' border:1px solid #AAA; border-radius:3px; -moz-border-radius:3px; -webkit-border-radius:3px; padding:2px 4px;'>";
	if(suites != null && suites.length >0){
		for(var i=0;i<suites.length;i++){
			ecu += parseFloat(suiteShare(suites[i].name).ecu) * suites[i].count;
			price += parseFloat(suiteShare(suites[i].name).price) * suites[i].count;
			volume += parseInt(suiteShare(suites[i].name).volume) * suites[i].count;
			html += suites[i].name+" * "+suites[i].count+"个 <br/>";
		}
		html += " </div>";
	}
	$("#res_ecu").html(ecu + " 个ECU").after(html);
	$("#res_ecu").parent().children().css("float","left");
	$("#res_ecu").css("line-height",($("#suite").height()+3)+"px");
	$("#suite").css("margin-left",(5)+"px");
	$("#res_ecu_price").text((price*parseFloat(orders.discount)/10).toFixed(2));
	var bandwidth = orders.bandwidth;
	var bandwidthPrice = "";
	$("#res_bandwidth").text(bandwidth + " Mbps");
	if(1==bandwidth || bandwidth==2 || bandwidth == 5){
		bandwidthPrice = 136;
	}else if(bandwidth ==10 || bandwidth==20 ||bandwidth ==50){
		bandwidthPrice = 132;
	}else if(bandwidth == 100){
		bandwidthPrice = 128;
	}
	$("#res_bandwidth_price").text(parseFloat(bandwidth*bandwidthPrice).toFixed(2));
	
	$("#res_ip").text(orders.ipCount + " 个");
	$("#res_ip_price").text(parseFloat(orders.ipCount*30).toFixed(2));

	var vol = orders.sizeInGB==null || orders.sizeInGB=="" ? '0':orders.sizeInGB;
	var volPrice = "";
	if(vol < 1000){
		volPrice = 5*vol/10;
	}else if(vol >= 1000){
		volPrice = 500*vol/1000;
	}
	
	$("#res_vol").html(parseInt(vol)+parseInt(volume) + " GB <span style='color:red'>[ 含购买存储："+vol+" GB]</span>");
	$("#res_vol_price").text((volPrice * parseFloat(orders.discount)/10).toFixed(2));
	
	var snap = orders.snapshot==null || orders.snapshot=="" ? '0':orders.snapshot;
	var snapPrice = "";
	if(snap < 1000){
		snapPrice = 5*snap/10;
	}else if(snap >= 1000){
		snapPrice = 500*snap/1000;
	}
	$("#res_snap").text(snap + " GB");
	$("#res_snap_price").text((snapPrice * parseFloat(orders.discount)/10).toFixed(2));
	
	$("#res_discount").text(orders.discount + " 折");
	
	var ha = orders.ha == null || orders.ha == "" ? '0' : orders.ha;
	$("#res_ha").text(ha + " 个");
	var elb = orders.elb == null || orders.elb == "" ? '0' : orders.elb;
	$("#res_elb").text(elb + " 个");
	var sumPrice = parseFloat($("#res_ecu_price").text()) + parseFloat(bandwidth*bandwidthPrice) + parseFloat(orders.ipCount*30) + parseFloat($("#res_vol_price").text()) + parseFloat($("#res_snap_price").text());
	$("#cloud_sum_price").text("总计："+parseFloat(sumPrice).toFixed(2));
}

function showExclusiveResouce(suites,orders){
	$("#zzfw").show();
	var ecu = 0;
	var price = 0.0;
	var volume = 0;
	var ePrice = 0.0;
	var eHtml = "<div>";
	var html = "<div id='suite' style='border:1px solid #AAA; border-radius:3px; -moz-border-radius:3px; -webkit-border-radius:3px; padding:2px 4px;'>";
	if(suites != null && suites.length >0){
		for(var i=0;i<suites.length;i++){
			ecu += parseFloat(suiteExclusive(suites[i].name).ecu) * suites[i].count;
			price += parseFloat(suiteExclusive(suites[i].name).price) * suites[i].count;
			if(suites[i].name == "增值服务套餐1"){
				ePrice += 100 * suites[i].count;
				eHtml += suites[i].desc+"<br/>";
			}else if(suites[i].name == "增值服务套餐2"){
				ePrice += 1000  * suites[i].count;
				eHtml += suites[i].desc+"<br/>";
			}else if(suites[i].name == "增值服务套餐3"){
				ePrice += 1000  * suites[i].count;
				eHtml += suites[i].desc+"<br/>";
			}
			volume += parseInt(suiteExclusive(suites[i].name).volume) * suites[i].count;
			html += suites[i].name+" * "+suites[i].count+"个 <br/>";
		}
		eHtml += "</div>";
		html += " </div>";
	}
	$("#res_ecu").html(ecu + " 个ECU").after(html);
	$("#res_ecu").parent().children().css("float","left");
	$("#res_ecu").css("line-height",($("#suite").height()+3)+"px");
	$("#suite").css("margin-left",(5)+"px");
	$("#res_ecu_price").text((price*parseFloat(orders.discount)/10).toFixed(2));
	var bandwidth = orders.bandwidth;
	var bandwidthPrice = "";
	$("#res_bandwidth").text(bandwidth + " Mbps");
	if(1==bandwidth || bandwidth==2 || bandwidth == 5){
		bandwidthPrice = 136;
	}else if(bandwidth ==10 || bandwidth==20 ||bandwidth ==50){
		bandwidthPrice = 132;
	}else if(bandwidth == 100){
		bandwidthPrice = 128;
	}
	$("#res_bandwidth_price").text(parseFloat(bandwidth*bandwidthPrice).toFixed(2));
	
	$("#res_ip").text(orders.ipCount + " 个");
	$("#res_ip_price").text(parseFloat(orders.ipCount*30).toFixed(2));
	
	var vol = orders.sizeInGB==null || orders.sizeInGB=="" ? '0':orders.sizeInGB;
	var volPrice = "";
	if(vol < 1000){
		volPrice = 5*vol/10;
	}else if(vol >= 1000){
		volPrice = 500*vol/1000;
	}
	
	$("#res_vol").html(parseInt(vol)+parseInt(volume) + " GB <span style='color:red'>[ 含购买存储："+vol+" GB]</span>");
	$("#res_vol_price").text((volPrice * parseFloat(orders.discount)/10).toFixed(2));
	
	var snap = orders.snapshot==null || orders.snapshot=="" ? '0':orders.snapshot;
	var snapPrice = "";
	if(snap < 1000){
		snapPrice = 5*snap/10;
	}else if(snap >= 1000){
		snapPrice = 500*snap/1000;
	}
	$("#res_snap").text(snap + " GB");
	$("#res_snap_price").text((snapPrice * parseFloat(orders.discount)/10).toFixed(2));
	
	$("#res_discount").text(orders.discount + " 折");
	$("#res_zzfw").html(eHtml);
	$("#res_zzfw_price").text(ePrice.toFixed(2));
	
	var ha = orders.ha == null || orders.ha == "" ? '0' : orders.ha;
	$("#res_ha").text(ha + " 个");
	var elb = orders.elb == null || orders.elb == "" ? '0' : orders.elb;
	$("#res_elb").text(elb + " 个");
	var sumPrice = parseFloat(ePrice) + parseFloat($("#res_ecu_price").text()) + parseFloat(bandwidth*bandwidthPrice) + parseFloat(orders.ipCount*30) + parseFloat($("#res_vol_price").text()) + parseFloat($("#res_snap_price").text());
	$("#cloud_sum_price").text("总计："+parseFloat(sumPrice).toFixed(2));
}

function showStroge(orders){
	var vol = orders.sizeInGB==null || orders.sizeInGB=="" ? '0':orders.sizeInGB;
	var volPrice = "";
	if(vol < 1000){
		volPrice = 5*vol/10;
	}else if(vol >= 1000){
		volPrice = 500*vol/1000;
	}
	$("#stor_vol").html(parseInt(vol) + " GB <span style='color:#999'>[ 规格：以10G为单位采购 ]</span>");
	$("#stor_vol_price").text(volPrice * parseFloat(orders.discount)/10);
	
	var bandwidth = orders.bandwidth;
	var bandwidthPrice = "";
	$("#stor_bandwidth").text(bandwidth + " Mbps");
	if(1==bandwidth || bandwidth==2 || bandwidth == 5){
		bandwidthPrice = 136;
	}else if(bandwidth ==10 || bandwidth==20 ||bandwidth ==50){
		bandwidthPrice = 132;
	}else if(bandwidth == 100){
		bandwidthPrice = 128;
	}
	$("#stor_bandwidth_price").text(bandwidth*bandwidthPrice);
	
	$("#stor_ip").text(orders.ipCount + " 个");
	$("#stor_ip_price").text(orders.ipCount*30);
	var sumPrice = bandwidth*bandwidthPrice + orders.ipCount*30 + volPrice;
	$("#stor_sum_price").text("总计："+sumPrice);
}

function tijiao(ticketState){
	var product  = {};
	var product_type = $("#product_type").val();
	var ticketId = $("#ticketId").val();
	var ticketType = $("#ticket_type").val();
	var customerId = $("#customerId").val();
	var cloud_platform = $("#cloud_platform").val();
	var cloud_url = $("#cloud_url").val();
	var login_username = $("#login_username").val();
	var login_password = $("#login_password").val();
	var open_port = $("#open_port").val();
	var keypairs = $("#keypairs").val();
	var securitys = $("#securitys").val();
	var remark = $("#ticket_remark").val();
	if(ticketTypeFlag == "modify"){
		cloud_platform=$("#td_cloud_platform").text();
		cloud_url=$("#td_cloud_url").text();
		login_username=$("#td_login_username").text();
		login_password=$("#td_login_password").text();
	}
	
	var ip = "";
	if(cloud_platform == ""){
		alert("请选择云平台！");
		return ;
	}
	if(login_username == ""){
		alert("用户名不能为空！");
		$("#login_username").focus();
		return ;
	}
	if(login_password == ""){
		alert("密码不能为空！");
		$("#login_password").focus();
		return ;
	}
	if(product_type == 'share') {
		ip = $("#ip").val();
		if(ip == ''){
			alert("ip不能为空！");
			return ;
		}else{
			var ip2 = ip.replace(/,/g, ";"); 
			var ips = "";
			if(ip2.indexOf(";")>0){
				ips = ip2.split(";");
				var flag = "0";
				for(var i=0;i<ips.length;i++){
					if(!ipChk(ips[i])){
						flag = "1";
						break;
					}
				}
				if(flag == 1){
					alert("ip格式不正确");
					return ;
				}
			}else if(!ipChk(ip)){
				alert("ip格式不正确");
				return ;
			}
		}
	}else if(product_type == 'exclusive'){
		var ip1 = $("#ip1").val();
		var ip2 = $("#ip2").val();
		if(ip1 == '' || ip2 == '') {
			alert("ip不能为空！");
			return ;
		}else if(!ipChk(ip1) || !ipChk(ip2)){
			alert("ip格式不正确");
			return ;
		}
		ip = ip1 + "-" + ip2;
	}
	
	product['ticketId'] = ticketId;
	product['ticketType'] = ticketType;
	product['customerId'] = customerId;
	product['ticketState'] = ticketState;
	product['loginUrl'] = cloud_url;
	product['cloudPlatform'] = cloud_platform;
	product['loginUsername'] = login_username;
	product['loginPassword'] = login_password;
	product['ip'] = ip;
	product['openPorts'] = open_port;
	product['keypairs'] = keypairs;
	product['securitys'] = securitys;
	product['remark'] = remark;
//	alert(JSON.stringify(product));
	$.ajax({
		type : "POST",
		contentType: "application/json; charset=utf-8",
		url : contextPath + "/ticket_ticketType_open.json",
		data : JSON.stringify(product),
		success : function(result) {
//			alert(result);
			if(result == 1){
				window.location.href = contextPath + "/ticket.htm";
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
function openTiJiao(){
	var ticketState = "资源已分配";
	tijiao(ticketState);
}
function modifyTiJiao(){
	var ticketState = "资源已分配";
	tijiao(ticketState);
}
function openWebSite(){
	var ticketState = "资源已分配，等候备案";
	tijiao(ticketState);
}
function modifyWebSite(){
	var ticketState = "端口已开放";
	tijiao(ticketState);
}
function closeTicket(){
	var product  = {};
	var ticketId = $("#ticketId").val();
	var customerId = $("#customerId").val();
	var remark = $("#ticket_remark").val();
	product['ticketId'] = ticketId;
	product['customerId'] = customerId;
	product['remark'] = remark;
	$.ajax({
		type : "POST",
		contentType: "application/json; charset=utf-8",
		url : contextPath + "/ticket_ticketType_close.json",
		data : JSON.stringify(product),
		success : function(result) {
			if(result == 1){
				window.location.href = contextPath + "/ticket.htm";
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
/**
 * 判断IP是否符合格式
 */
function ipChk(IPStr){
	var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    var reg = IPStr.match(exp);
    if(reg == null) {
        return false;
    } else {
        return true;
    }
}