<%@page import="com.chinaops.cloud.metadata.shared.Cloud"%>
<%@page import="com.chinaops.cloud.metadata.shared.CloudUser"%>
<%@page import="com.chinaops.cloud.auth.shared.Company"%>
<%@page import="com.chinaops.web.common.entity.EcloudUserDetails"%>
<%@page import="com.chinaops.cloud.auth.shared.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.chinaops.cloud.framework.CompanyWithCloudManager"%>
<%@page import="com.chinaops.cloud.framework.UserManager"%>
<%@page import="com.chinaops.cloud.framework.CompanyManager"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- <script src="http://code.jquery.com/jquery-1.9.1.js"></script> -->
<!-- <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> -->
<script type="text/javascript">
		//显示/隐藏
	var cloudType2 = 0;
	function showHideColDialog() {	
		<%
			ApplicationContext con = new ClassPathXmlApplicationContext("spring-dao.xml");
			CompanyManager companyManager2 = (CompanyManager) con.getBean("companyManager");
			UserManager userManager2 = (UserManager) con.getBean("userManager");
			CompanyWithCloudManager cloudManager2 = (CompanyWithCloudManager) con.getBean("companyWithCloudManager");
			Object principal2 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user2 = new User();
			user2.setId(((EcloudUserDetails)principal2).getId());
			user2 = userManager2.getUserById(user2.getId());
			Company company2 = companyManager2.getCompanyById(user2.getCompanyId());
			//得到云的类型：
			CloudUser cloudUser2 = company2.getCloudUser();
			int type;
			if(cloudUser2 != null){
			int cloudid = cloudUser2.getCloud().getId();
			//根据云id查询得到云信息，然后判断：
			Cloud cloud = cloudManager2.getCloudById(cloudid);
			if(cloud != null && cloud.getId() != 0){
			type = cloud.getCloudType();
			System.out.print(type+"////////////////////");
			%>
			cloudType2=<%=type%>;
			//alert("cloudType2:"+cloudType2); 
			<% }
		} 
	  %>
		var s = "<ul>";
		for(key in columns) {
			//'<li><span>' + label.html() + '</span> ' + message + '</li>'
			var prop = jQuery("#main-jqgrid").jqGrid('getColProp',columns[key].name);
			//alert(columns[key].name+"???");
			//alert(columns[key].caption);
		    if(cloudType2 == 1 ){
		    	 if(columns[key].caption != "IP地址" && columns[key].caption != "网络虚拟防火墙" ){
					 //alert("saasssaasassa/////////////");
		    		 s += "<li><input type='checkbox' value='" + columns[key].name + "' id='cbx_" + columns[key].name
						+ "'" + (prop.hidden ? "" : "checked") +" "+ (prop.label ? "disabled" : "") 
						+ " /> <label for='cbx_" + columns[key].name + "'>" + columns[key].caption + "</label></li>"; 
			   }	
		    }else{
				s += "<li><input type='checkbox' value='" + columns[key].name + "' id='cbx_" + columns[key].name
				+ "'" + (prop.hidden ? "" : "checked") +" "+ (prop.label ? "disabled" : "") 
				+ " /> <label for='cbx_" + columns[key].name + "'>" + columns[key].caption + "</label></li>";								
			}
		  }
		s += "</ul>";
		$( "#dialog-showhide-col #grid-col-list" ).html(s);
		$("#dialog-showhide-col input[type='checkbox']").change(function() {
			var count = 0;
			$("#grid-col-list ul li input[type='checkbox']").each(function(i){
				if($("#grid-col-list ul li input[type='checkbox']").get(i).checked){
					count++;
				}
			});
			width = jQuery("#main-jqgrid").jqGrid('getGridParam','width');
			if(count>0){
				if ($(this).get(0).checked) {
					jQuery("#main-jqgrid").jqGrid('showCol',$(this).val());
				} else {
					jQuery("#main-jqgrid").jqGrid('hideCol',$(this).val());
				}
			}
			jQuery("#main-jqgrid").jqGrid('setGridWidth',width);
		});
		$( "#dialog-showhide-col" ).dialog( "open" );
	}
		

	/* 显示列表项   */
	 jQuery(document).ready(function() {	
		$("#btnOperat").click(function() {
			$("#menu-button").show();
			$("#menu-button").css("right","16.75em");
			$( document ).one( "click", function() {
				$("#menu-button").hide();
			});
			return false;
		});
		 $("#select").click(function() {
			$("#menu-button").show();
			$("#menu-button").css("right","9.8em");
			$( document ).one( "click", function() {
				$("#menu-button").hide();
			});
			return false;
		});
		$("#btnOperat").attr("disabled",true);
		 
		//初始化框架底层
		//初始化显示列表项dialog
		$( "#dialog-showhide-col" ).dialog({
			autoOpen: false,
			autoHeight: true,
			autoWidth: true,
			modal: true,
			buttons: {
				"关闭": function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
			}
		});
		
		// 限制文本框最大输入字符数20
/* 		$("input").each(function(){
			if($(this).attr("type")==='text'){
				if($(this).attr("maxlength") === undefined){
					$(this).attr("maxlength",20);
				}
			}
		}); */

		
		/*  $("input").each(function(){
			if($(this).attr("type")==='text'){
				alert($(this).attr("type")+"???");
				alert($(this).val()); 
				
			}
		}); */ 
		
	 });
		// 禁用鼠标右键
		/* function norightclick(e){
			if (window.Event){
				// 判断IE版本
				if(!navigator.appVersion.match(/8./i)=='8.')	{
					if (e.which == 2 || e.which == 3)
						return false;
				}
			}else if (event.button == 2 || event.button == 3){
				event.cancelBubble = true
				event.returnValue = false;
				return false;
			}
		} 
		function nocontextmenu(event){
			event.cancelBubble = true
			event.returnValue = false;
			return false;
		} 
		document.oncontextmenu = nocontextmenu; // for IE5+
		document.onmousedown = norightclick; // for all others
	 */	
		
		
		//清空表单信息
		function clearForm(id) {
			$("#"+id+"_dialog input[type='text']").each(function(index,node) {
				$(this).val("");
			});
			$("#"+id+"_dialog textarea").each(function(index,node) {
				$(this).val("");
			});
			$("#"+id+"_dialog input[type='hidden']").each(function(index,node) {
				$(this).val("");
			});
			$("#"+id+"_dialog input[type='password']").each(function(index,node) {
				$(this).val("");
			});
			$("#"+id+"_msg").empty();
			$(".error-msg").empty();
		}
		//显示异常信息
		function showErrorMsg(err,content){
			 var errorList = $('#'+err);
			 errorList.empty();
			 if(content!="")
				 errorList.show().append(content);
		}
		/**
		 * 取得Radio的值
		 */
		function getRadioValue(radioname){
			return $("input[name="+radioname+"]:checked").val();
		}
		/**
		*在删除带分页数据时调用
		*
		*/
		function delRefrush(){
			$(".south-title-myclass").children("span").text("");
			$(".south-title-myclass").children("span").hide();
			$("#btnOperat").attr("disabled",true);
			var rowcount=jQuery("#main-jqgrid").jqGrid('getGridParam','reccount');//当前页有多少条记录
			var pagesize=jQuery("#main-jqgrid").jqGrid('getGridParam','lastpage');//总共有多少页
			var rowNums=jQuery("#main-jqgrid").jqGrid('getGridParam','rowNum');//一页显示多少条 
			if (rowcount==1) {
				$("#main-jqgrid").jqGrid("setGridParam",{rowNum:rowNums,page:pagesize-1}).trigger("reloadGrid");
			}else{
				$("#main-jqgrid").jqGrid().trigger("reloadGrid");
			}
		}
		
		function getCookies(key,value){
			if(value!=null){
				setCookie(key,value);			
			}else{
				value =  getCookie(key);
			}
			if(value==null || value=="null"){
				return "";
			}else{
				return value;
			}
		}
		/*开始： cookies*/
		//设置COOKIE
		function setCookie(name,value,expires,path,domain,secure) {
			if(expires==null){
				expires = 36500;
			}
			var expDays = expires*24*60*60*1000;
			var expDate = new Date();
			expDate.setTime(expDate.getTime()+expDays);
			var expString = ((expires==null) ? "" : (";expires="+expDate.toGMTString()));
			var pathString = ((path==null) ? "" : (";path="+path));
			var domainString = ((domain==null) ? "" : (";domain="+domain));
			var secureString = ((secure==true) ? ";secure" : "" );
			document.cookie = name + "=" + escape(value) + expString + pathString + domainString + secureString;
		}
		//获取指定名称的cookie值：
		function getCookie(name) {
			var result = null;
			var myCookie = document.cookie + ";";
			var searchName = name + "=";
			var startOfCookie = myCookie.indexOf(searchName);
			var endOfCookie;
			if (startOfCookie != -1) {
				startOfCookie += searchName.length;
				endOfCookie = myCookie.indexOf(";",startOfCookie);
				result = unescape(myCookie.substring(startOfCookie, endOfCookie));
			}
			return result;
		}
		//删除指定名称的cookie：
		function delCookie(name) {
			var ThreeDays=3*24*60*60*1000;
			var expDate = new Date();
			expDate.setTime(expDate.getTime()-ThreeDays);
			document.cookie=name+"=;expires="+expDate.toGMTString();
		}
		/*结束： cookies*/
		
	
	/**
	 *判断输入是否只是数字
	 **/
	function checkNum(value){
		var patrn=/^[0-9]*$/;   ///^[1-9]\\d*|0$/; 
		if (!patrn.exec(value)) 
			return true ;
		return false ;
	}	
	
	function htmlEncode(value){
	//	alert(value);		
	//	var str=escape(value);
		var str;
	 	if(value.contains("<") || value.contains(">") || value.contains('"')){
	 		str = value.replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll('"',"&quot;"); 			
	 		return str;
	 	}else if(value.contains("&lt;") || value.contains("&gt;") || value.contains("&quot;")) {
	 		str = value.replaceAll("&lt;","&amp;lt;").replaceAll("&gt;","&amp;gt;").replaceAll("&quot;","&amp;quot;"); 			
	 		return str;
	 	}else{
	 		return value;	
	 	}
		//alert(str);
		//alert(encodeURI(value));
		//alert(encodeURIComponent(value));
		
	}
	//只能输入字母、数字、下划线、中划线、中文。
	function checkInput(value){
		var reg = /^[A-Za-z0-9_-\u554A-\u9C52]+$/; //\u4e00-\u9fa5 中文。
		if (!reg.test(value)){
			return true;
		}
		return false;
	}
		
</script>
