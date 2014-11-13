function initClientResource(){
	$.ajax({
		type : "POST",
		url : contextPath + "/client_product_type_by_customerId.do",
		data : {customerId:customerId},
		success : function(orders) {
			if(orders != null && orders.length > 0){
				for(var i=0;i<orders.length ;i++){//'share','exclusive','storage'
					if(orders[i].productType == "share"){
						$(".abgne_tab .tabs li a[href='#tab1']").parent().show();
					}else if(orders[i].productType == "exclusive"){
						$(".abgne_tab .tabs li a[href='#tab2']").parent().show();
					}else if(orders[i].productType == "storage"){
						$(".abgne_tab .tabs li a[href='#tab3']").parent().show();
					}
				}
			}else{
				$(".abgne_tab").hide();
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

function shareResult(productType){
	$.ajax({
		type : "POST",
		url : contextPath + "/client_products_by_customerId.do",
		data : {customerId:customerId,productType:productType},
		success : function(orders) {
			if(orders != null && orders.length > 0){
				var str = "";
				for(var i = 0;i<orders.length;i++){
					var obj = orders[i];
					
					var ecu = 0;
					var storage = 0;
					if(productType == 'share'){
						ecu = showShareEcuAndVolume(obj).ecu;
						storage = parseInt((obj.sizeInGB == null || obj.sizeInGB == "") ? '0' : obj.sizeInGB) + parseInt(showShareEcuAndVolume(obj).volume);
					}else if(productType == 'exclusive'){
						ecu = showExclusiveEcuAndVolume(obj).ecu;//suiteExclusive(obj.suite).ecu == null ? 0 : suiteExclusive(obj.suite).ecu;
						storage = parseInt((obj.sizeInGB == null || obj.sizeInGB == "") ? '0' : obj.sizeInGB) + parseInt(showExclusiveEcuAndVolume(obj).volume);
					}else if(productType == 'storage'){
						storage = parseInt((obj.sizeInGB == null || obj.sizeInGB == "") ? '0' : obj.sizeInGB);
					}
					var elb = (obj.elb==null||obj.elb=="")?0:orders[i].elb;
//					var storage = obj.storage == null ? 0 : obj.storage;
					var ha = (obj.ha == null || obj.ha == "")? 0 : obj.ha;
					var snapshot = (obj.snapshot == null || obj.snapshot == "") ? 0 : obj.snapshot;
					var security = (obj.securitys == null || obj.securitys == "") ? 0 : obj.securitys;
					var blanWidth = obj.bandwidth == null ? 0 : obj.bandwidth;
					var keypairs = (obj.keypairs == null || obj.keypairs == "") ? 0 : obj.keypairs;
					var ipCount = (obj.ipCount == null || obj.ipCount == "") ? 0 : obj.ipCount;
					
					if(productType == 'share'){
						str += "<tr>"+
						"	<td style='width:12%;'><span>"+(obj.cloudPlatform==null?"　":obj.cloudPlatform)+"</span></td>"+
						"	<td style='width:18%;'><span>"+obj.availableAt+"</span></td>"+
						"	<td>"+
						"		<table class='share_detail'>"+
						"			<tr><td>ECU：<span>" + ecu + " 个</span></td><td>负载均衡：<span>" + elb + " 个</span></td></tr>"+
						"			<tr><td>存储：<span>" + storage + " GB</span> <span style='color:red;'>[赠送存储："+showShareEcuAndVolume(obj).volume+" GB]</span></td><td>主机保护：<span>" + ha +" 个</span></td></tr>"+
						"			<tr><td>快照：<span>" + snapshot + " GB</span></td><td>防火墙：<span>" + security + " 个</span></td></tr>"+
						"			<tr><td>带宽：<span>" + blanWidth + " M</span></td><td>密钥：<span>" + keypairs + " 个</span></td></tr>"+
						"			<tr><td>IP总数：<span>" + ipCount + " 个</span></td><td></td></tr>" +
						"		</table>"+
						"	</td>"+
						"</tr>";
					}else if(productType == 'exclusive'){
						str += "<tr>"+
						"			<td style='width:12%;'><span>"+(obj.cloudPlatform==null?"　":obj.cloudPlatform)+"</span></td>"+
						"			<td style='width:18%;'><span>"+obj.availableAt+"</span></td>"+
						"			<td>"+
						"				<table class='share_detail'>"+
						"					<tr><td>ECU：<span>" + ecu + " 个</span></td><td>负载均衡：<span>" + elb + " 个</span></td></tr>"+
						"					<tr><td>存储：<span>" + storage + " GB</span> <span style='color:red;'>[赠送存储："+showExclusiveEcuAndVolume(obj).volume+" GB]</span></td><td>主机保护：<span>" + ha +" 个</span></td></tr>"+
						"					<tr><td>快照：<span>" + snapshot + " GB</span></td><td>带宽：<span>" + blanWidth + " M</span></td></tr>"+
						"					<tr><td>IP总数：<span>" + ipCount + " 个</span></td><td>密钥：<span>" + keypairs + " 个</span></td></tr>"+
						"					<tr><td colspan='2' style='text-align:left;'>增值服务：<span>" + eHtml + "</span></td></tr>"+
						"				</table>"+
						"			</td>"+
						"		</tr>";
					}else if(productType == 'storage'){
						str += "<tr>"+
						"			<td style='width:12%;'><span>"+(obj.cloudPlatform==null?"　":obj.cloudPlatform)+"</span></td>"+
						"			<td style='width:18%;'><span>"+obj.availableAt+"</span></td>"+
						"			<td>"+
						"				<table class='share_detail'>"+
						"					<tr><td>存储：<span>" + storage + " GB</span></tr>"+
						"					<tr><td>带宽：<span>" + blanWidth + " M</span></td></tr>"+
						"					<tr><td>IP总数：<span>" + ipCount + " 个</span></td></tr>"+
						"				</table>"+
						"			</td>"+
						"		</tr>"
					}
				}
				$("#"+productType).html(str)
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

function showShareEcuAndVolume(orders){
	var suite = new Object();
	var suites = orders.suites;
	var ecu = 0;
	var price = 0.0;
	var volume = 0;
	if(suites != null && suites.length >0){
		for(var i=0;i<suites.length;i++){
			ecu += parseFloat(suiteShare(suites[i].name).ecu) * suites[i].count;
			price += parseFloat(suiteShare(suites[i].name).price) * suites[i].count;
			volume += parseInt(suiteShare(suites[i].name).volume) * suites[i].count;
		}
		suite.ecu = ecu;
		suite.volume = volume;
	}
	return suite;
}

var eHtml = "";
function showExclusiveEcuAndVolume(orders){
	var suite = new Object();
	var suites = orders.suites;
	var ecu = 0;
	var price = 0.0;
	var volume = 0;
	var ePrice = 0.0;
	if(suites != null && suites.length >0){
		var html = "";
		for(var i=0;i<suites.length;i++){
			ecu += parseFloat(suiteExclusive(suites[i].name).ecu) * suites[i].count;
			if(suites[i].name == "增值服务套餐1"){
				ePrice += 100 * suites[i].count;
				html += suites[i].desc + " ";
			}else if(suites[i].name == "增值服务套餐2"){
				ePrice += 1000  * suites[i].count;
				html += suites[i].desc+" ";
			}else if(suites[i].name == "增值服务套餐3"){
				ePrice += 1000  * suites[i].count;
				html += suites[i].desc+" ";
			}
			price += parseFloat(suiteExclusive(suites[i].name).price) * suites[i].count;
			volume += parseInt(suiteExclusive(suites[i].name).volume) * suites[i].count;
		}
		eHtml = html;
		suite.ecu = ecu;
		suite.volume = volume;
	}
	return suite;
}

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
function suiteExclusive(o){
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
		suite.price = 100;
		suite.volume = 0;
	}else if(o == "增值服务套餐2"){
		suite.ecu = 0;
		suite.price = 1000;
		suite.volume = 0;
	}else if(o == "增值服务套餐3"){
		suite.ecu = 0;
		suite.price = 10000;
		suite.volume = 0;
	}
	return suite;
}