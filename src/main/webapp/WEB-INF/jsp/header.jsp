<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ page 
import="java.util.*"
import="java.text.SimpleDateFormat"
import="org.springframework.context.*"
import="org.springframework.context.support.*"
import="com.chinaops.cloud.framework.PrivilegeManager"
import="com.chinaops.cloud.framework.AnnouncementManager"
import="org.springframework.security.core.context.SecurityContextHolder"
import="com.chinaops.cloud.auth.shared.*"
import="com.chinaops.cloud.common.entity.RoleEnum"
import="com.chinaops.web.common.entity.EcloudUserDetails"
import="com.chinaops.ecloud.s3.openservices.model.Bucket"
import="com.chinaops.ecloud.s3.openservices.EcloudS3"
import="com.chinaops.ecloud.s3.openservices.EcloudS3Client"
import="com.chinaops.cloud.metadata.shared.Announcement"
import="com.chinaops.cloud.metadata.shared.UserAnnouncement"
%>
<style type="text/css">
.main-header-chinaunicom {
    background: url(<c:url value='/images/main-header.png'/>) repeat-x scroll 0 0 transparent;
    height: 58px;
    left: 0;
    position: absolute;
    top: 0;
    width: 100%;
}
.section_links {
    color: #000;
    display: inline;
    float: right;
    font-size: 13px;
    padding-top: 6px;
    padding-right: 20px;
}
.section_links p{
	text-align: right; 
	margin:0; 
	padding:4px 15px 6px 0;
	_padding:0 10px 8px 0;
	height:0px;
}
.section_links a:link ,.section_links a:visited,.section_links a:hover,.section_links a:active{ 
	color:#000;
	color:#000;
	text-decoration: underline;
	font-weight:normal;
}
.section_links ul { list-style-type: none; padding:2px;border:1px solid blue; }
.section_links ul li { list-style-type: none; line-height: 22px; }
.section_links ul li:hover { border:1px solid #BBB; }
.section_links ul li  a:link, .section_links ul li  a:visited, .section_links ul li  a:hover, .section_links ul li  a:active { color:#000; text-decoration: none; }
.section_links ul li a:hover { text-decoration: underline; }
.welcome-02-chinaunicom {
   /*  background: url("<c:url value='/images/welcome_bg4.png'/>") no-repeat scroll 0 0 transparent; */
    float: right;
    margin:0px 2px;
    /* height: 30px;
    padding-left: 12px;
    padding-top: 5px;
    width: 72px;
    color:#000000; */
}
.exit-link {
    color: #000000;
    text-decoration: none;
}
.welcome-01-chinaunicom {
   /* background: url("<c:url value='/images/welcome_bg.png'/>") repeat-x scroll 0 0 transparent;*/
    /* color: #000000; */
    float: right;
    margin:0px 2px;
/*     font-size: 14px;
    font-weight: bold;
    height: 30px;
    line-height: 30px;
    padding-left: 5px;
    padding-right: 30px;
    text-align: right; */
}
.welcome-top-chinaunicom {
    /* background: url("<c:url value='/images/welcome_bg3.png'/>") no-repeat scroll 0 0 transparent; */
    float: right;
    margin:0px 2px;
/*     height: 30px;
    width: 11px; */
}
img { border:0px; }

 
.welcome-01-throud {
	background: url("<c:url value='/images/tuichu.png'/>") no-repeat right;
    float: right;
    height: 16px;
    width: 16px;
    line-height: 30px;
    padding-left: 5px;
    padding-right: 5px;
    padding-top: 11px;
    cursor: pointer;
}
.welcome-01-throud:hover {
	background: url("<c:url value='/images/tuichu2.png'/>") no-repeat right;
    float: right;
    height: 16px;
    width: 16px;
    line-height: 30px;
    padding-left: 5px;
    padding-right: 5px;
    padding-top: 11px;
}
.welcome-01-third {
	background: url("<c:url value='/images/help-icon.png'/>") no-repeat right;
    float: right;
    height: 16px;
    width: 16px;
    line-height: 30px;
    padding-left: 5px;
    padding-right: 5px;
    padding-top: 11px;
    cursor: pointer;
}
.welcome-01-third:hover {
	background: url("<c:url value='/images/help-icon2.png'/>") no-repeat right;
    float: right;
    height: 16px;
    width: 16px;
    line-height: 30px;
    padding-left: 5px;
    padding-right: 5px;
    padding-top: 11px;
}
.welcome-01-line {
	float: right;
	width: 2px;
	height:16px;
	background:url("<c:url value='/images/divider.jpg'/>") no-repeat;
	margin: 6px 5px 0;
}
.welcome-top-second2 {
    float: right;
    padding-right:5px;
    height: 30px;
    line-height:32px   
}
.welcome-top-second {
    background: url("<c:url value='/images/manager-icon.png'/>") no-repeat right;
    float: right;
    padding-right:30px;
    height: 30px;
    line-height:32px   
}
.welcome-top-second:hover {
    background: url("<c:url value='/images/manager-icon2.png'/>") no-repeat right;
    float: right;
    padding-right:30px;
    height: 30px;
    line-height:32px
}
.welcome-top-first {
	float: right;
    height: 30px;
/*     margin-right:20px; */
    line-height:32px;
    width:90px;
    cursor: pointer;
    /* background:url("<c:url value='/images/announcementNew.gif'/>") no-repeat scroll 90px center rgba(0, 0, 0, 0); */
}
.welcome img {
	float: right;
	margin-right: 5px;
    line-height:32px;
}
.welcome{
	margin: 15px 0 0;
	height:30px;
	overflow: hidden;
	width:400px;
}
</style>
<script type="text/javascript">
	function redirectLayout(){
		window.location.href = "<c:url value='/j_spring_security_logout'/>";
	}
	function redirectHelp(){
		window.open("<%=request.getContextPath()%>/images/jsp/faq.jsp","_blank");
	}
</script>
<div id="main-header" class="main-header-chinaunicom">
	<div class="header-table">
		<div class="header-logo">
			<img src="<c:url value='/images/header-logo.png'/>">
		</div>
		
		<div class="section_links">
			<%-- <p>
				<a href="<c:url value='/images/jsp/faq.jsp'/>" target="_blank"> <!-- 服务支持与帮助 --> </a>
			</p> --%>
			<div class="welcome">
				<div class="welcome-01-throud" onclick="redirectLayout()"> </div>
				<div class="welcome-01-third" onclick="redirectHelp()">　</div>
				<div class="welcome-01-line">　</div>
				<%
				Object principal1 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				ApplicationContext context2 = new ClassPathXmlApplicationContext("spring-dao.xml");
				AnnouncementManager announcementManager = (AnnouncementManager) context2.getBean("announcementManager");
				String username = "";
				if(null!=principal1){
					username = ((EcloudUserDetails)principal1).getUsername();
				}
				Map<Integer,UserAnnouncement> uacsMap = new HashMap<Integer,UserAnnouncement>();
				List<UserAnnouncement> uacs2 = announcementManager.searchByUserId(((EcloudUserDetails)principal1).getId());
				if(uacs2 != null && uacs2.size()>0){
		        	for(UserAnnouncement uac:uacs2){
		        		uacsMap.put(uac.getId(),uac);
		        	}
				}
				
				
				List<Announcement> allAnnouncements = announcementManager.getAllAnnouncements();
				if(allAnnouncements !=null && allAnnouncements.size() > 0){
					for(Announcement ant : allAnnouncements){
						Date sd=new Date();
						String st="2013-12-19 14:12:56";
						String et="2013-12-20 14:12:56";
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						Date std = null;
						Date etd = null;
						try {
							std=(Date)sdf.parse(ant.getStartTime());
							etd = (Date)sdf.parse(ant.getEndTime());
						} catch (Exception e) {
							e.printStackTrace();
						}
						boolean stdBeforesd=std.before(sd);
						boolean sdBeforeetd=sd.before(etd);
						if(uacsMap.get(ant.getId())!=null){
							UserAnnouncement ua = (UserAnnouncement)uacsMap.get(ant.getId());
							if(ua.getId() != ant.getId() && ua.getUserId() != ((EcloudUserDetails)principal1).getId() && stdBeforesd && sdBeforeetd){
								announcementManager.createUserAnnouncement(((EcloudUserDetails)principal1).getId(),ant.getId(),0);
							}
						}else if(stdBeforesd && sdBeforeetd){
							announcementManager.createUserAnnouncement(((EcloudUserDetails)principal1).getId(),ant.getId(),0);
						}
					}
				}
				if(((EcloudUserDetails)principal1).getRole().equalsIgnoreCase(RoleEnum.CompanyAdministrator.toString())){
					String pwdUrl=request.getContextPath()+"/updateUserPassword.htm";
					String cardUrl=request.getContextPath()+"/dynamicCard.htm";
					%>
					<div id="welcome-top-second" class="welcome-top-second"> </div>
					<div class="welcome-top-second2">
					   <%= username %>
					</div>
					<ul id="menu-header-button" style="display: none;">
						<li><a href="#" onclick="window.location.href='<%=pwdUrl%>'">修改密码</a></li>
						<%-- <li><a href="#" onclick="window.location.href='<%=cardUrl%>'">密码卡管理</a></li> --%>
					</ul>
					<!-- <div class="welcome-01-line"></div> -->
					<%	
				}else{	
				 %>
				 
				<div class="welcome-top-second2"><%= username %></div>
				
				<%} 
				List<UserAnnouncement> uacs = announcementManager.searchByUserId(((EcloudUserDetails)principal1).getId());
		        if(uacs != null && uacs.size()>0){
		        	%>
	        		<div class="welcome-01-line"></div>
	        		<%	
		        	for(UserAnnouncement uac:uacs){
		        		if(uac.getState() != 0){
		        		 
		        			continue;
		        		}else{
		        			%>
		        			<div class="imgAnnounce" style="display: ">
		        			   <img  src="<c:url value='/images/announcementNew.gif'/>" style='height: 7px; width: 18px; '>
		        			</div>
		        			<%
		        			break;
		        		}
		        	}
		        }else{
		        	
		        }				 
				%>
				<div class="welcome-top-first" onclick="showAnnouncement()">
				   [产品升级公告]
				</div>
				
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
    function showAnnouncement(){
		$(".imgAnnounce").attr("style",'display:none');
		window.open("<c:url value='/showAnnouncement.htm'/>","_blank");
	}
</script>

