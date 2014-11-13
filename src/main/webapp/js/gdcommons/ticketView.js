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
			if(ticket.ticketType == "open"){
				if(orders.forWebsite == 'yes'){
					$("#filing-info").show();
					$("#filing_no").text((orders.filingNo==null || orders.filingNo=="")?'':orders.filingNo);
					$("#filing_ip").text((orders.filingIp==null || orders.filingIp=="")?'':orders.filingIp);
					$("#filing_domain").text((orders.filingDomain==null || orders.filingDomain=="")?'':orders.filingDomain);
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
				
				if(ticket.isModify == "0"){
					$("#edit-sys").show();
					$("#edit #td_customer_id").text(ticket.customerId);
					$("#edit #td_cloud_platform").text(orders.cloudPlatform);
					$("#edit #cloud_url").text(orders.loginUrl);
					$("#edit #cloud_url").parent().children("th").text("云平台访问地址：");
					$("#edit #login_username").text(orders.loginUsername);
					$("#edit #login_password").text(orders.loginPassword);
					(orders.openPorts == null || orders.openPorts == "")?$("#open_port").text(''):$("#open_port").text(orders.openPorts);
					(orders.ip == null || orders.ip == "")?$("#ip").text(''):$("#ip").text(orders.ip);
					(orders.keypairs == null || orders.keypairs == "")?$("#keypairs").text(''):$("#keypairs").text(orders.keypairs);
					(orders.securitys == null || orders.securitys == "")?$("#securitys").text(''):$("#securitys").text(orders.securitys);
					if(orders.productType == "exclusive"){
						$("#tr_security").hide();
					}
					(ticket.remark == null || ticket.remark == "")?$("#ticket_remark").text(''):$("#ticket_remark").text(ticket.remark);
				}else{
					$(".ticket-remark").hide();
				}
			}else if(ticket.ticketType == "modify"){
				ticketTypeFlag="modify";
				if(orders.forWebsite == 'yes'){
					$("#filing-info").show();
					$("#filing_no").text((orders.filingNo==null || orders.filingNo=="")?'':orders.filingNo);
					$("#filing_ip").text((orders.filingIp==null || orders.filingIp=="")?'':orders.filingIp);
					$("#filing_domain").text((orders.filingDomain==null || orders.filingDomain=="")?'':orders.filingDomain);
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
				
//				if(ticket.isModify == "0"){
					$("#edit-sys").show();
					$("#edit #td_customer_id").text(ticket.customerId);
					$("#edit #td_cloud_platform").text(orders.cloudPlatform);
					$("#edit #cloud_url").text(orders.loginUrl);
					$("#edit #cloud_url").parent().children("th").text("云平台访问地址：");
					$("#edit #login_username").text(orders.loginUsername);
					$("#edit #login_password").text(orders.loginPassword);
					(orders.openPorts == null || orders.openPorts == "")?$("#open_port").text(''):$("#open_port").text(orders.openPorts);
					(orders.ip == null || orders.ip == "")?$("#ip").text(''):$("#ip").text(orders.ip);
					(orders.keypairs == null || orders.keypairs == "")?$("#keypairs").text(''):$("#keypairs").text(orders.keypairs);
					(orders.securitys == null || orders.securitys == "")?$("#securitys").text(''):$("#securitys").text(orders.securitys);
					if(orders.productType == "exclusive"){
						$("#tr_security").hide();
					}
//				}
				(ticket.remark == null || ticket.remark == "")?$("#ticket_remark").text(''):$("#ticket_remark").text(ticket.remark);
			}else if(ticket.ticketType == "close"){
				$("#edit-sys").hide();
				$("#filing-info").hide();
				$("#ticket-orders").hide();
				$("#close-ticket").show();

				$("#is_fees_owed").text((orders.isFeesOwed == null || orders.isFeesOwed =="") ? '':orders.isFeesOwed);
				$("#close_time").text((orders.closeTime == null || orders.closeTime =="") ? '':orders.closeTime);
				$("#reason").text((orders.reason == null || orders.reason =="") ? '':orders.reason);
				$("#detailed_reason").text((orders.detailedReason == null || orders.detailedReason =="") ? '':orders.detailedReason);
				
				$("#openWebSite").hide();
				$("#modifyWebSite").hide();
				$("#openTiJiao").hide();
				$("#modifyTiJiao").hide();
				
				if(ticket.isModify == "0"){
					$("#edit-sys").show();
					$("#edit #td_customer_id").text(ticket.customerId);
					$("#edit #td_cloud_platform").text(orders.cloudPlatform);
					$("#edit #cloud_url").text(orders.loginUrl);
					$("#edit #cloud_url").parent().children("th").text("云平台访问地址：");
					$("#edit #login_username").text(orders.loginUsername);
					$("#edit #login_password").text(orders.loginPassword);
					(orders.openPorts == null || orders.openPorts == "")?$("#open_port").text(''):$("#open_port").text(orders.openPorts);
					(orders.ip == null || orders.ip == "")?$("#ip").text(''):$("#ip").text(orders.ip);
					(orders.keypairs == null || orders.keypairs == "")?$("#keypairs").text(''):$("#keypairs").text(orders.keypairs);
					(orders.securitys == null || orders.securitys == "")?$("#securitys").text(''):$("#securitys").text(orders.securitys);
					if(orders.productType == "exclusive"){
						$("#tr_security").hide();
					}
				}
				(ticket.remark == null || ticket.remark == "")?$("#ticket_remark").text(''):$("#ticket_remark").text(ticket.remark);
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
				$("#cloud").show();
				showShareResouce(orders.suites,orders);
			}else if(product_type == 'exclusive'){
				productType = "专享云";
				$("#storage").hide();
				$("#cloud").show();
				showExclusiveResouce(orders.suites,orders);
			}else if(product_type == 'storage'){
				productType = "云存储";
				$("#storage").show();
				$("#cloud").hide();
				showStroge(orders);
			}
			$("#resourceProductType").text(productType);
			$("#resourceProductType").text(productType);
			$("#availableAt").text(orders.availableAt.split(" ")[0]);
			var isWebsite = orders.forWebsite;
			$("input[type=radio][value="+isWebsite+"][name='isWebSite']").attr("checked",'checked');
			$("input[type=radio][name='isWebSite']").attr("disabled","disabled"); 
			$("#portsNeedToOpen").text(orders.portsNeedToOpen);
			$("#remark").text(orders.memo == null ? "" : orders.memo);
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
	$("#stor_vol_price").text((volPrice * parseFloat(orders.discount)/10).toFixed(2));
	
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
	$("#stor_sum_price").text("总计："+(sumPrice).toFixed(2));
}