<%@page import="com.chinaops.cloud.metadata.shared.Cloud"%>
<%@page import="com.chinaops.cloud.metadata.shared.CloudUser"%>
<%@page import="com.chinaops.cloud.framework.CompanyWithCloudManager"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page 
import="java.util.*"
import="org.springframework.context.*"
import="org.springframework.context.support.*"
import="com.chinaops.cloud.framework.PrivilegeManager"
import="com.chinaops.cloud.framework.RoleManager"
import="com.chinaops.cloud.framework.CompanyManager"
import="com.chinaops.cloud.framework.UserManager"
import="com.chinaops.cloud.auth.shared.Company"
import="org.springframework.security.core.context.SecurityContextHolder"
import="com.chinaops.cloud.auth.shared.*"
import="com.chinaops.cloud.common.entity.RoleEnum"
import="com.chinaops.web.common.entity.EcloudUserDetails"
%>
<script type="text/javascript">
	function toUrl(href){
		window.location.href = href;
	}
	
	$(document).ready(function(){
		/* 选中效果 */
		var thisURL = document.URL;
		thisUPage_s = thisURL.substring(thisURL.indexOf("/",10),thisURL.length);
		$(".left-menuitem").find("a").each(function(index){
			 if ($(".left-menuitem a:eq("+index+")").attr("href") == thisUPage_s) { 
				 /* $(".left-menuitem a:eq("+index+")").parent().css("background","url('<c:url value='/images/left_menu_highlight.png'/>') no-repeat scroll center transparent"); */ 
				 $(".left-menuitem a:eq("+index+")").parent().css("background","#d3d3d3");//url('<c:url value='/images/left_menu_hover.jpg'/>') repeat-x scroll center transparent");
				 var image = $(".left-menuitem img:eq("+index+")").attr("alt")+"2.png";
				 $(".left-menuitem img:eq("+index+")").attr("src","<c:url value='/images/icon/leftmenu/"+image+"'/>");
				 return false; 
			  }
		});
		
		/* 指点击span内文字才起作用，点击横线不起作用 */
		$(".menu-group").children("span").css("cursor","pointer").click(function(){
			if($(this).nextAll(".left-menuitem").css('display') == "block"){
				$(this).nextAll(".left-menuitem").hide();
				$(this).nextAll(".left-menuitem").attr("display","none")
				$(this).prev("img").attr("src","<c:url value='/images/sq.png'/>");
				$(this).prev("img").attr("title","展开");
			}else{
				$(this).nextAll(".left-menuitem").show();
				$(this).nextAll(".left-menuitem").attr("display","block");
				$(this).prev("img").attr("src","<c:url value='/images/zk.png'/>");
				$(this).prev("img").attr("title","收起");
			}
			//$(this).nextAll(".left-menuitem").toggle();
		});
//		点击展开/收起图片时控制菜单项的显示隐藏		
		$(".menu-group").children("img").css("cursor","pointer").click(function(){
			if($(this).attr("title") === "收起" && $(this).nextAll(".left-menuitem").css('display') == "block"){
				$(this).nextAll(".left-menuitem").hide();
				$(this).nextAll(".left-menuitem").attr("display","none")
				$(this).attr("src","<c:url value='/images/sq.png'/>");
				$(this).attr("title","展开");
			}else if($(this).attr("title") === "展开" && $(this).nextAll(".left-menuitem").css('display') == "none"){
				$(this).nextAll(".left-menuitem").show();
				$(this).nextAll(".left-menuitem").attr("display","block");
				$(this).attr("src","<c:url value='/images/zk.png'/>");
				$(this).attr("title","收起");
			}
		});
	}); 
</script>
<%
ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
PrivilegeManager privilegeManager = (PrivilegeManager) context.getBean("privilegeManager");
RoleManager roleManager = (RoleManager) context.getBean("roleManager");
CompanyManager companyManager = (CompanyManager) context.getBean("companyManager");
UserManager userManager = (UserManager) context.getBean("userManager");
CompanyWithCloudManager cloudManager = (CompanyWithCloudManager) context.getBean("companyWithCloudManager");
Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
User user = new User();
user.setId(((EcloudUserDetails)principal).getId());  

user = userManager.getUserById(user.getId());
Company company = companyManager.getCompanyById(user.getCompanyId());

System.out.println(company.getId()+ "{}{}{}{}" + company.getIsopendept() + " [][][][]"+user.getRole());
//得到云的类型：
CloudUser cloudUser = company.getCloudUser();
int cloudType = 0;
if(cloudUser != null){
    int cloudid = cloudUser.getCloud().getId();
	 //根据云id查询得到云信息，然后判断：
	 Cloud cloud = cloudManager.getCloudById(cloudid);
	 if(cloud != null && cloud.getId() != 0){
		 cloudType = cloud.getCloudType();
	 }
}
System.out.println("cloudType:"+cloudType);
List<Privilege> privileges = privilegeManager.getUserPrivileges(user, PrivilegeCategory.ElasticInstance.toString());
Map<String,List<Privilege>> priviMap = new LinkedHashMap<String,List<Privilege>>();
for(Privilege priv : privileges) {
	if(StringUtils.isNotEmpty(priv.getGroup())){
		if(user.getRole().equalsIgnoreCase(RoleEnum.CompanyAdministrator.toString())){
			if(company.getIsopendept() == 1){
				if(!priviMap.containsKey(priv.getGroup())) {
		   			priviMap.put(priv.getGroup(), new ArrayList<Privilege>());
				}
				priviMap.get(priv.getGroup()).add(priv);				
			}else{
				if(!priv.getGroup().equals("部门管理")){
					if(!priv.getGroup().equals("用户管理")){
						if(!priviMap.containsKey(priv.getGroup())) {
				   			priviMap.put(priv.getGroup(), new ArrayList<Privilege>());
						}
						priviMap.get(priv.getGroup()).add(priv);
					}
				}
			}
		}else{
			if(!priviMap.containsKey(priv.getGroup())) {
			    priviMap.put(priv.getGroup(), new ArrayList<Privilege>());
			}
			priviMap.get(priv.getGroup()).add(priv);
		}
	}
}
if(user.getRole().equalsIgnoreCase(RoleEnum.CompanyAdministrator.toString())){
	%>

<script type="text/javascript">
<!--
	$("#welcome-top-second").click(function(){
		$("#menu-header-button").show();
		$( document ).one( "click", function() {
			$("#menu-header-button").hide();
		});
		return false;
	});
//-->
</script>
<%
}
for(String key : priviMap.keySet()) {
	for(Privilege privilege : priviMap.get(key)) {
		
	}
}

for(String key : priviMap.keySet()) {
	%>
		<div class="menu-group">
		<%
			if(!key.equals("控制台") && !key.equals("信息管理") && !key.equals("账户管理")){
		%>
			<div style="padding:5px;font-weight:bold;color:#707070;"><%=key %></div>
		<%		
			}
		%>
			
		<% 
			for(Privilege privilege : priviMap.get(key)) {
				
				String iconImage = "/images/icon/leftmenu/" + privilege.getToken().subSequence(1,privilege.getToken().length()) + ".png" ;
				if(!privilege.getName().equals("修改密码") && !privilege.getName().equals("产品升级公告")){
					if(cloudType == 1){
						if(!privilege.getName().equals("网络虚拟防火墙") && !privilege.getName().equals("弹性IP")){
							%>
							<div class="left-menuitem" title="<%=privilege.getTooltip()%>" onclick="toUrl('<c:url value='<%=privilege.getUrl() %>'/>')">
								<img src="<c:url value='<%=iconImage%>'/>" alt="<%=privilege.getToken().subSequence(1,privilege.getToken().length())%>"/> <a href="<c:url value='<%=privilege.getUrl() %>'/>"><%=privilege.getName() %></a>
							</div>
					  <%	 }
					}else{%>
						<div class="left-menuitem" title="<%=privilege.getTooltip()%>" onclick="toUrl('<c:url value='<%=privilege.getUrl() %>'/>')">
							<img src="<c:url value='<%=iconImage%>'/>" alt="<%=privilege.getToken().subSequence(1,privilege.getToken().length())%>"/> <a href="<c:url value='<%=privilege.getUrl() %>'/>"><%=privilege.getName() %></a>
						</div>
					<%}
		         }
			}
		%>
			</div>
	<%	
}
%>
