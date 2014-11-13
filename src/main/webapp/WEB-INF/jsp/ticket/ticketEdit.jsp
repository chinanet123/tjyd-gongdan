<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page 
	import="com.chinaops.web.common.entity.SysAdminUserDetails"
	import="org.springframework.security.core.context.SecurityContextHolder"
%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
   	<meta charset="utf-8">
    <title>天津移动工单平台</title>
   	<link rel="stylesheet" type="text/css" href="<c:url value='/css/gd/base.css'/>" />
    <script src="<c:url value='/js/jquery-1.8.3.min.js'/>"></script>
	<script src="<c:url value='/js/gdcommons/clientDtail.js'/>"></script>
	<script src="<c:url value='/js/gdcommons/ticketEdit.js'/>"></script>
    <script src="<c:url value='/js/zDrag.js'/>"></script>
    <script src="<c:url value='/js/zDialog.js'/>"></script>
    <%
    	String ticketId = request.getParameter("ticketId");
	    String customerId = request.getParameter("customerId");
		String ticketType = request.getParameter("ticketType");
		
    	if((ticketId == null || ticketId.equals("")) && (customerId == null || customerId.equals("")) && (ticketType == null || ticketType.equals(""))){
    %>
    	<script type="text/javascript" charset="utf-8">  
    		window.location.href = "<%=request.getContextPath()%>/login.htm";
    	</script>
    <%
    	}
    %>
	
<%
	Object principal1 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	SysAdminUserDetails user = (SysAdminUserDetails)principal1;
%>
	<script type="text/javascript" charset="utf-8">  
		var contextPath = "<%=request.getContextPath()%>";
		var roleType = "<%=user.getRole() %>";
		var customerId = "<%=customerId%>";
		var ticketId = "<%=ticketId %>";
		jQuery(document).ready(function() {
			$("#client_header").removeClass("selected");
			$("#ticket_header").addClass("selected");
		});
	</script>
	<% 
		if(customerId != null && !customerId.equals("")){
	%>
	    <script src="<c:url value='/js/gdcommons/ticketCustomer.js'/>"></script>
		<script type="text/javascript" charset="utf-8"> 
			jQuery(document).ready(function() {
				$("#ticket_id").text(ticketId);
				$(".customerMagager").show();
				initClientDetail(customerId);
				initTicketDetail(ticketId);
			});
		</script>
	<%
		}else{
	%>
		<script src="<c:url value='/js/gdcommons/ticket.js'/>"></script>
		<script type="text/javascript" charset="utf-8"> 
			var ticketId = "<%=ticketId %>";
			jQuery(document).ready(function() {
				$("#ticket_id").text(ticketId);
				$(".customerMagager").show();
				initClientDetail(customerId);
			});
		</script>
	<%
		}
	%>
	
</head>

<body>
	<div class="mainLayout" style="">
		<jsp:include page="../header.jsp"/>
		<div class="body">
		<style type="text/css">
		</style>
			<div class="ticket-title border-all">
			<%
				if(ticketType.equals("open")){
			%>
				<div class="ticket-tit">
					<h2>业务开通【<span id="ticket_id"></span>】</h2>
				</div>
				<div class="ticket-time">
					<div id="" style="float:left;">接收时间：<span id="receiveTime">2014-08-21</span></div>
					<div style="float:right; display:none;">导出资源信息</div>
					<div class="clear"></div>
				</div>
			<%  } else if(ticketType.equals("modify")){  %>
				<div class="ticket-tit">
					<h2>业务变更【<span id="ticket_id"><%=ticketId %></span>】</h2>
				</div>
				<div class="ticket-time">
					<div id="" style="float:left;">接收时间：<span id="receiveTime">2014-08-21</span></div>
					<div style="float:right; display:none;">导出资源信息</div>
					<div class="clear"></div>
				</div>
			<%  }else if(ticketType.equals("close")){ %>
				<div class="ticket-tit">
					<h2>业务撤销【<span id="ticket_id"><%=ticketId %></span>】</h2>
				</div>
				<div class="ticket-time">
					<div id="" style="float:left;">接收时间：<span id="receiveTime">2014-08-21</span></div>
					<div style="float:right; display:none;">导出资源信息</div>
					<div class="clear"></div>
				</div>
			<%  } %>
			</div>
			<div class="data-body">
				<div class="title" style=""><img src="<c:url value='/images/arrows.png'/>"/>客户信息</div>
				<jsp:include page="../client/client_detail.jsp"/>
				<div class="ticket-orders" id="ticket-orders">
					<div class="title" style=""><img src="<c:url value='/images/arrows.png'/>"/>需求信息</div>
					<table class="orders-table">
						<tr><th width="15%">合同编号：</th><td width="20%"><span id="contractId"></span></td><th width="15%">合同起止时间：</th><td width="45%"><span id="contranctStartEndTime"></span></td></tr>
						<tr>
						<%if(ticketType.equals("open")){ %>
							<th>资源开通时间：</th>
							<td colspan="3"><span id="availableAt"></span></td>
						<%}else if(ticketType.equals("modify")){ %>
							<th>资源变更时间：</th>
							<td colspan="3"><span id="availableAt"></span></td>
						<%} %>
						</tr>
						<tr><th>产品类型：</th><td colspan="3"><span id="resourceProductType"></span></td></tr>
						<tr><th>是否做网站：</th><td colspan="3"><input type="radio" name="isWebSite" value="yes"/>是 <input type="radio" name="isWebSite" value="no"/>否</td></tr>
						<tr id="isWebSite">
							<th>开放端口号：</th>
							<td colspan="3"><span id="portsNeedToOpen"></span></td>
						</tr>
						<tr><th>工单备注：</th><td colspan="3"><span id="remark">涉及资源减少时，客户经理与客户确认客户资源哪些可以释放。在变更工单中注明！<br>客户经理信息变更；IP地址变更说明</span></td></tr>
					</table>
				</div>
				<jsp:include page="../order/orderView.jsp"/>
				<div class="configration-ticket">
					<input type="hidden" id="product_type"/>
					<input type="hidden" id="ticket_type"/>
					<input type="hidden" id="ticketId" value="<%=ticketId%>"/>
					<input type="hidden" id="customerId" value="<%=customerId%>"/>
					<div class="edit-sys" id="edit-sys">
						<div class="title" style=""><img src="<c:url value='/images/arrows.png'/>"/>系统信息</div>
						<table id="edit">
							<tr><th>客户编号：</th><td id="td_customer_id"><input type="text" value="<%=customerId%>" disabled="disabled"/></td></tr>
							<tr>
								<th>所属云平台：</th>
								<td id="td_cloud_platform">
									<select id="cloud_platform" onchange="changeCloudPlatForm();">
										<option value="">请选择云平台</option>
										<option value="xidan">西单云平台</option>
										<option value="anhua">安华云平台</option>
										<option value="yizhuang">亦庄云平台</option>
										<option value="fangzhuang">方庄云平台</option>
									</select>
								</td>
							</tr>
							<tr><th>云平台地址：</th><td id="td_cloud_url"><input type="text" id="cloud_url" disabled="disabled"/></td></tr>
							<tr><th>用户名：</th><td id="td_login_username"><input node-type="text" placeholder="请输入用户名" type="text" id="login_username"/></td></tr>
							<tr><th>密码：</th><td id="td_login_password"><input node-type="text" placeholder="请输入密码" type="text" id="login_password"/></td></tr>
							<tr class="cloud-share"><th>IP地址：</th><td valign="center"><textarea rows="2" cols="40" style="resize:none" id="ip"></textarea> &nbsp;<span style="color:red;">* 如果多个ip地址，每个ip之间必须以逗号","间隔</span></td></tr>
							<tr id="cloud-exclusive"><th>IP地址：</th><td><input type="text" id="ip1"/>--<input type="text" id="ip2"/></td></tr>
							<tr><th>已开放端口：</th><td><input type="text" id="open_port"/></td></tr>
							<tr class=""><th>可创建密钥：</th><td><input type="text" id="keypairs"/> 个</td></tr>
							<tr class="cloud-share"><th>网络虚拟防火墙：</th><td><input type="text" id="securitys"/> 个</td></tr>
						</table>
					</div>
					<div class="close-ticket" id="close-ticket">
						<div class="title" style=""><img src="<c:url value='/images/arrows.png'/>"/>需求信息</div>
						<table class="close-table">
							<tr>
								<th>是否欠费：</th>
								<td><span id="is_fees_owed">否</span></td>
							</tr>
							<tr>
								<th>撤销时间：</th>
								<td><span id="close_time">2013-12-16</span></td>
							</tr>
							<tr>
								<th>撤销原因：</th>
								<td><span id="reason">合同到期关闭</span></td>
							</tr>
							<tr>
								<th>详细原因：</th>
								<td><span id="detailed_reason">关闭原因的说明关</span></td>
							</tr>
						</table>
					</div>
					<div class="filing-info" id="filing-info">
						<div class="title" style=""><img src="<c:url value='/images/arrows.png'/>"/>备案信息</div>
						<table class="filing-table">
							<tr><th>备案IP：</th><td><span id="filing_ip"></span></td></tr>
							<tr><th>备案号：</th><td><span id="filing_no"></span></td></tr>
							<tr><th>备案域名：</th><td><span id="filing_domain"></span></td></tr>
						</table>
					</div>
					<div class="ticket-remark">
						<table class="remark-table">
							<tr><th>工单备注：</th><td><textarea rows="3" cols="60" id="ticket_remark"></textarea></td></tr>
						</table>
					</div>
					<div class="ticket-submit">
						<table class="submit-table">
							<tr>
								<td>
									<input type="button" id="openTiJiao" onclick="openTiJiao();" class="mybutton" value="提交openTiJiao"/>
									<input type="button" id="modifyTiJiao" onclick="modifyTiJiao();" class="mybutton" value="提交modifyTiJiao"/>
									
									<input type="button" id="openWebSite" onclick="openWebSite();" class="mybutton" value="提交openWebSite"/>
									<input type="button" id="modifyWebSite" onclick="modifyWebSite();" class="mybutton" value="提交modifyWebSite"/>
									
									<input type="button" id="closeTicket" onclick="closeTicket();" class="mybutton" value="提交closeTicket"/>
									
									<input type="button" disabled="disabled" id="tuidan" class="mybutton" value="退单"/>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../footer.jsp"/>
	</div>
</body>
</html>
