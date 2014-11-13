/**
 * 
 */
package com.chinaops.web.ydgd.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinaops.web.ydgd.entity.Customer;
import com.chinaops.web.ydgd.entity.Order;
import com.chinaops.web.ydgd.entity.Suite;
import com.chinaops.web.ydgd.entity.Ticket;
import com.chinaops.web.ydgd.service.CustomerService;
import com.chinaops.web.ydgd.service.OrderService;
import com.chinaops.web.ydgd.service.SuiteService;
import com.chinaops.web.ydgd.service.TicketService;

/**
 * @author 张立强 
 * @Email  liqiang.zhang@china-ops.com
 * 2014年9月1日 上午9:34:38
 */
public class ExportExcelServlet extends HttpServlet {
	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(ExportExcelServlet.class);
	
	ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
    TicketService ticketService = (TicketService) context.getBean("ticketService") ;
    CustomerService customerService = (CustomerService) context.getBean("customerService") ;
    OrderService orderService = (OrderService) context.getBean("orderService");
    SuiteService suiteService = (SuiteService) context.getBean("suiteService");
    
//    String targetfile = "D:/资源.xls";// 输出的excel文件名
	String[] worksheet = {"共享云","专享云","云存储"};// 输出的excel文件工作表名
	String[] title = {"云平台", "开通时间", "资源详情"};// excel工作表的标题
	WritableWorkbook workbook;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customerId = request.getParameter("customerId");
		try {
			Customer customer = customerService.getCustomerBycustomerId(customerId);
//			String osSys = System.getProperty("os.name");
			// web绝对路径
		    String path = request.getSession().getServletContext().getRealPath("/");
		    String savePath = path + "excel";

			String filename = savePath + "/" + customer.getCustomerId() + "-资源.xls";
			log.debug("******************************************************************************************************************");
			log.debug("filename:"+filename);
			log.debug("******************************************************************************************************************");
//			filename = URLEncoder.encode(filename, "UTF8");
			OutputStream os = new FileOutputStream(filename);
			workbook = Workbook.createWorkbook(os);
			
			List<Ticket> ticketList = ticketService.getOpenTicketByCustomerId(customerId);
			String  ticketIdS= new String();
			if (ticketList.size()>0) {
				for (Ticket ticket : ticketList) {
					ticketIdS+="'"+ticket.getTicketId()+"',";
				}
			}
			String ticketIds = "";
			if(ticketIdS.indexOf(",")>0){
				ticketIds = ticketIdS.substring(0, ticketIdS.length()-1);
			}
			List<Order> productTypeList = new ArrayList<Order>(); 
			List<Order> productList = new ArrayList<Order>(); 
			if (!ticketIds.equals("") && ticketIds != null){
				productTypeList = orderService.getProductTypes(ticketIds);
			}
			if(productTypeList!= null && productTypeList.size()>0){
				for(int i=0;i<productTypeList.size();i++){
					WritableSheet sheet = workbook.createSheet(worksheet[i], i);
					WritableFont wftitle = new WritableFont(WritableFont.TIMES, 25, WritableFont.BOLD,  false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
					WritableCellFormat wcfFtitle = new WritableCellFormat(wftitle);
					wcfFtitle.setBackground(Colour.GRAY_25);
					wcfFtitle.setAlignment(Alignment.LEFT);
					sheet.mergeCells(0, 0, 2, 0);
					sheet.setRowView(0,600);
					Label labeltitle = new Label(0, 0, "客户信息",wcfFtitle); 
					sheet.addCell(labeltitle);
					WritableFont wfbase = new WritableFont(WritableFont.TIMES, 16, WritableFont.BOLD,  false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
					WritableCellFormat wcfFbase = new WritableCellFormat(wfbase);
					wcfFbase.setAlignment(Alignment.LEFT);
					sheet.mergeCells(0, 1, 2, 1);
					sheet.setRowView(1,500);
					Label labelbase = new Label(0, 1, "基本信息",wcfFbase); 
					sheet.addCell(labelbase);
					
					WritableFont wfright = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.NO_BOLD,  false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
					WritableCellFormat wcfFright = new WritableCellFormat(wfright);
					wcfFright.setAlignment(Alignment.RIGHT);
					Label labelcustomerName = new Label(0, 2, "客户名称：",wcfFright); 
					sheet.addCell(labelcustomerName);
					Label hangye = new Label(0, 3, "所属行业：",wcfFright); 
					sheet.addCell(hangye);
					Label dizhi = new Label(0, 4, "地址：",wcfFright); 
					sheet.addCell(dizhi);
					Label youbian = new Label(0, 5, "邮编：",wcfFright);
					sheet.addCell(youbian);
					
					WritableFont wfleft = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.NO_BOLD,  false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
					WritableCellFormat wcfleft = new WritableCellFormat(wfleft);
					wcfleft.setAlignment(Alignment.LEFT);
					sheet.mergeCells(1, 2, 2, 2);
					Label labelcustomerName1 = new Label(1, 2, customer.getCustomerName(),wcfleft); 
					sheet.addCell(labelcustomerName1);
					sheet.mergeCells(1, 3, 2, 3);
					Label hangye1 = new Label(1, 3, customer.getCategory(),wcfleft); 
					sheet.addCell(hangye1);
					sheet.mergeCells(1, 4, 2, 4);
					Label dizhi1 = new Label(1, 4, customer.getCustomerAddress(),wcfleft); 
					sheet.addCell(dizhi1);
					sheet.mergeCells(1, 5, 2, 5);
					Label youbian1 = new Label(1, 5, customer.getPostalCode(),wcfleft); 
					sheet.addCell(youbian1);
					
					WritableFont wfcontact = new WritableFont(WritableFont.TIMES, 16, WritableFont.BOLD,  false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
					WritableCellFormat wcfFcontact = new WritableCellFormat(wfcontact);
					wcfFcontact.setAlignment(Alignment.LEFT);
					sheet.mergeCells(0, 6, 2, 6);
					sheet.setRowView(6,500);
					Label labelcontact = new Label(0, 6, "联系人",wcfFcontact); 
					sheet.addCell(labelcontact);
					
					Label yewulianxiren = new Label(0, 7, "业务联系人：",wcfFright); 
					sheet.addCell(yewulianxiren);
					Label yewuPhone = new Label(0, 8, "电话：",wcfFright); 
					sheet.addCell(yewuPhone);
					Label yewuEmail = new Label(0, 9, "邮箱：",wcfFright); 
					sheet.addCell(yewuEmail);
					Label jishulianxiren = new Label(0, 10, "技术联系人：",wcfFright); 
					sheet.addCell(jishulianxiren);
					Label jishuPhone = new Label(0, 11, "电话：",wcfFright); 
					sheet.addCell(jishuPhone);
					Label jishuEmail = new Label(0, 12, "邮箱：",wcfFright); 
					sheet.addCell(jishuEmail);
					
					sheet.mergeCells(1, 7, 2, 7);
					Label yewu = new Label(1, 7, customer.getB_contact(),wcfleft); 
					sheet.addCell(yewu);
					sheet.mergeCells(1, 8, 2, 8);
					Label ywPhone = new Label(1, 8, customer.getB_phone(),wcfleft); 
					sheet.addCell(ywPhone);
					sheet.mergeCells(1, 9, 2, 9);
					Label ywEmail = new Label(1, 9, customer.getB_email(),wcfleft); 
					sheet.addCell(ywEmail);
					sheet.mergeCells(1, 10, 2, 10);
					Label jishu = new Label(1, 10, customer.getT_contact(),wcfleft); 
					sheet.addCell(jishu);
					sheet.mergeCells(1, 11, 2, 11);
					Label jsPhone = new Label(1, 11, customer.getT_phone(),wcfleft); 
					sheet.addCell(jsPhone);
					sheet.mergeCells(1, 12, 2, 12);
					Label jsEmail = new Label(1, 12, customer.getT_email(),wcfleft); 
					sheet.addCell(jsEmail);
					
					sheet.setRowView(2,400);
					sheet.setRowView(3,400);
					sheet.setRowView(4,400);
					sheet.setRowView(5,400);
					sheet.setRowView(7,400);
					sheet.setRowView(8,400);
					sheet.setRowView(9,400);
					sheet.setRowView(10,400);
					sheet.setRowView(11,400);
					sheet.setRowView(12,400);
					
					for (int j = 0; j < title.length; j++) {
						WritableFont wf = new WritableFont(WritableFont.TIMES, 16, WritableFont.BOLD,  false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
						WritableCellFormat wcfF = new WritableCellFormat(wf);
						wcfF.setBackground(Colour.GRAY_25);
						wcfF.setAlignment(Alignment.CENTRE);
						// Label(列号,行号 ,内容 )
						Label label = new Label(j, 14, title[j],wcfF); // put the title in
						sheet.addCell(label);
					}
					sheet.setColumnView(0,15);
					sheet.setColumnView(1,20);
					sheet.setColumnView(2,50);
					sheet.setRowView(14,600);
					
					productList = orderService.getOrderByProductType(ticketIds, productTypeList.get(i).getProductType());
					if(productList!=null && productList.size()>0){
						int s = 5;
						for(int k=0;k<productList.size();k++){
							Order order = productList.get(k);
							List<Suite> suitesList = suiteService.getSuiteListByTicketId(order.getTicketId(), order.getId());
							order.setSuites(suitesList);
							
							WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.GREEN);
							jxl.write.WritableCellFormat wchB = new jxl.write.WritableCellFormat(wfc);
							wchB.setAlignment(jxl.format.Alignment.CENTRE);
							wchB.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
							wchB.setBorder(Border.ALL, BorderLineStyle.THIN,jxl.format.Colour.GRAY_25);
							Label CloudPlatform = new jxl.write.Label(0,s*k+14+1, order.getCloudPlatform(), wchB);
							sheet.addCell(CloudPlatform);
							Label OpenTime = new jxl.write.Label(1,s*k+14+1, order.getAvailableAt(), wchB);
							sheet.addCell(OpenTime);
							
							WritableFont wfbleft = new jxl.write.WritableFont(WritableFont.TAHOMA, 10, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE);
							jxl.write.WritableCellFormat wchleft = new jxl.write.WritableCellFormat(wfbleft);
							wchleft.setAlignment(jxl.format.Alignment.LEFT);
							wchleft.setBorder(Border.ALL, BorderLineStyle.THIN,jxl.format.Colour.GRAY_25);
							float ecu = 0.0f;
							int volume = 0;
							String eString = "";
							if(order.getProductType().equals("share")){
								sheet.setRowView(s*k+14+1,500);
								sheet.setRowView(s*k+14+2,500);
								sheet.setRowView(s*k+14+3,500);
								sheet.setRowView(s*k+14+4,500);
								sheet.setRowView(s*k+14+5,500);
								sheet.mergeCells(0, s*k+14+1, 0, s*k+14+5);
								sheet.mergeCells(1, s*k+14+1, 1, s*k+14+5);
								if(order.getSuites() != null && order.getSuites().size()>0){
									List<Suite> suites = order.getSuites();
									for(int n=0;n<suites.size();n++){
										ecu += Float.parseFloat(Suite.suiteShare(suites.get(n))[0]) * Integer.parseInt(suites.get(n).getCount());
										volume += Integer.parseInt(Suite.suiteShare(suites.get(n))[2]) * Integer.parseInt(suites.get(n).getCount());
									}
								}
								// ECU:2个	负载均衡：0个
								Label ecuLabel = new jxl.write.Label(2,s*k+14+1, "ECU:" + ecu + "个 \t 负载均衡："+((order.getElb() != null && !order.getElb().equals(""))? order.getElb() : 0)+"个", wchleft);
								sheet.addCell(ecuLabel);
								//存储：200 GB	主机保护：0 个
								Label volumeLabel = new jxl.write.Label(2,s*k+14+2, "存储:"+(Integer.parseInt(order.getSizeInGB())+volume)+"GB [含赠送存储："+volume+"GB] \t 主机保护："+((!order.getHa().equals("") || order.getHa() != null)? order.getHa() : 0)+"个", wchleft);
								sheet.addCell(volumeLabel);
								//快照：200 GB	防火墙：0 个
								Label snapshot = new jxl.write.Label(2,s*k+14+3, "快照:"+(order.getSnapshot())+"GB \t 防火墙："+((order.getSecuritys() != null && !order.getSecuritys().equals(""))? order.getSecuritys() : 0)+"个", wchleft);
								sheet.addCell(snapshot);
								//带宽：0 M	秘钥：0 个
								Label bankwidth = new jxl.write.Label(2,s*k+14+4, "带宽:"+(order.getBandwidth())+"M  \t 密钥："+((order.getKeypairs() != null && !order.getKeypairs().equals(""))? order.getKeypairs() : 0)+"个", wchleft);
								sheet.addCell(bankwidth);
								//IP总数：0 个
								Label ipCount = new jxl.write.Label(2,s*k+14+5, "IP:"+(order.getIpCount())+"个 \t", wchleft);
								sheet.addCell(ipCount);
							}else if(order.getProductType().equals("exclusive")){
								sheet.setRowView(s*k+14+1,500);
								sheet.setRowView(s*k+14+2,500);
								sheet.setRowView(s*k+14+3,500);
								sheet.setRowView(s*k+14+4,500);
								sheet.setRowView(s*k+14+5,500);
								sheet.mergeCells(0, s*k+14+1, 0, s*k+14+5);
								sheet.mergeCells(1, s*k+14+1, 1, s*k+14+5);
								String eHtml = "";
								if(order.getSuites() != null && order.getSuites().size()>0){
									List<Suite> suites = order.getSuites();
									for(int n=0;n<suites.size();n++){
										ecu += Float.parseFloat(Suite.suiteExclusive(suites.get(n))[0]) * Integer.parseInt(suites.get(n).getCount());
										volume += Integer.parseInt(Suite.suiteExclusive(suites.get(n))[2]) * Integer.parseInt(suites.get(n).getCount());
										if(suites.get(n).getName().equals("增值服务套餐1")){
											eHtml += suites.get(n).getDesc()+"\n";
										}else if(suites.get(n).getName().equals("增值服务套餐2")){
											eHtml += suites.get(n).getDesc()+"\n";
										}else if(suites.get(n).getName().equals("增值服务套餐3")){
											eHtml += suites.get(n).getDesc()+"\n";
										}
									}
									eString = eHtml;
								}
								Label ecuLabel = new jxl.write.Label(2,s*k+14+1, "ECU:" + ecu + "个 \t 负载均衡："+((order.getElb() != null && !order.getElb().equals(""))? order.getElb() : 0)+"个", wchleft);
								sheet.addCell(ecuLabel);
								//存储：200 GB	主机保护：0 个
								Label volumeLabel = new jxl.write.Label(2,s*k+14+2, "存储:"+(Integer.parseInt(order.getSizeInGB())+volume)+"GB [含赠送存储："+volume+"GB]\t 主机保护："+((!order.getHa().equals("") || order.getHa() != null)? order.getHa() : 0)+"个", wchleft);
								sheet.addCell(volumeLabel);
								//快照：200 GB	防火墙：0 个
								Label snapshot = new jxl.write.Label(2,s*k+14+3, "快照:"+(order.getSnapshot())+"GB \t 带宽:"+(order.getBandwidth())+"M", wchleft);
								sheet.addCell(snapshot);
								//带宽：0 M	秘钥：0 个
								Label bankwidth = new jxl.write.Label(2,s*k+14+4, "IP:"+(order.getIpCount())+"个 \t 密钥："+((order.getKeypairs() != null && !order.getKeypairs().equals(""))? order.getKeypairs() : 0)+"个", wchleft);
								sheet.addCell(bankwidth);
								//增值业务：VPN,防火墙
								Label zzfw = new jxl.write.Label(2,s*k+14+5, "增值业务:"+(eString)+" ", wchleft);
								sheet.addCell(zzfw);
							}else if(order.getProductType().equals("storage")){
								sheet.setRowView(s*k+14+1,500);
								sheet.setRowView(s*k+14+2,500);
								sheet.setRowView(s*k+14+3,500);
								sheet.mergeCells(0, s*k+14+1, 0, s*k+14+3);
								sheet.mergeCells(1, s*k+14+1, 1, s*k+14+3);
								volume = Integer.parseInt(order.getSizeInGB());
								//存储：500 GB
								Label volumeLabel = new jxl.write.Label(2,s*k+14+1, "存储:"+(Integer.parseInt(order.getSizeInGB())+volume)+" GB", wchleft);
								sheet.addCell(volumeLabel);
								//带宽：0 M
								Label bankwidth = new jxl.write.Label(2,s*k+14+2, "带宽:"+(order.getBandwidth())+"M ", wchleft);
								sheet.addCell(bankwidth);
								//IP总数：0 个
								Label ipCount = new jxl.write.Label(2,s*k+14+3, "IP:"+(order.getIpCount())+"个 ", wchleft);
								sheet.addCell(ipCount);
							}
						}
					}
					
				}
				workbook.write();
				workbook.close();
			}else{
				WritableSheet sheet = workbook.createSheet("客户详细信息", 0);
				WritableFont wftitle = new WritableFont(WritableFont.TIMES, 25, WritableFont.BOLD,  false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
				WritableCellFormat wcfFtitle = new WritableCellFormat(wftitle);
				wcfFtitle.setBackground(Colour.GRAY_25);
				wcfFtitle.setAlignment(Alignment.LEFT);
				sheet.mergeCells(0, 0, 2, 0);
				sheet.setRowView(0,600);
				Label labeltitle = new Label(0, 0, "客户信息",wcfFtitle); 
				sheet.addCell(labeltitle);
				WritableFont wfbase = new WritableFont(WritableFont.TIMES, 16, WritableFont.BOLD,  false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
				WritableCellFormat wcfFbase = new WritableCellFormat(wfbase);
				wcfFbase.setAlignment(Alignment.LEFT);
				sheet.mergeCells(0, 1, 2, 1);
				sheet.setRowView(1,500);
				Label labelbase = new Label(0, 1, "基本信息",wcfFbase); 
				sheet.addCell(labelbase);
				
				WritableFont wfright = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.NO_BOLD,  false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
				WritableCellFormat wcfFright = new WritableCellFormat(wfright);
				wcfFright.setAlignment(Alignment.RIGHT);
				Label labelcustomerName = new Label(0, 2, "客户名称：",wcfFright); 
				sheet.addCell(labelcustomerName);
				Label hangye = new Label(0, 3, "所属行业：",wcfFright); 
				sheet.addCell(hangye);
				Label dizhi = new Label(0, 4, "地址：",wcfFright); 
				sheet.addCell(dizhi);
				Label youbian = new Label(0, 5, "邮编：",wcfFright);
				sheet.addCell(youbian);
				
				WritableFont wfleft = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.NO_BOLD,  false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
				WritableCellFormat wcfleft = new WritableCellFormat(wfleft);
				wcfleft.setAlignment(Alignment.LEFT);
				sheet.mergeCells(1, 2, 2, 2);
				Label labelcustomerName1 = new Label(1, 2, customer.getCustomerName(),wcfleft); 
				sheet.addCell(labelcustomerName1);
				sheet.mergeCells(1, 3, 2, 3);
				Label hangye1 = new Label(1, 3, customer.getCategory(),wcfleft); 
				sheet.addCell(hangye1);
				sheet.mergeCells(1, 4, 2, 4);
				Label dizhi1 = new Label(1, 4, customer.getCustomerAddress(),wcfleft); 
				sheet.addCell(dizhi1);
				sheet.mergeCells(1, 5, 2, 5);
				Label youbian1 = new Label(1, 5, customer.getPostalCode(),wcfleft); 
				sheet.addCell(youbian1);
				
				WritableFont wfcontact = new WritableFont(WritableFont.TIMES, 16, WritableFont.BOLD,  false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
				WritableCellFormat wcfFcontact = new WritableCellFormat(wfcontact);
				wcfFcontact.setAlignment(Alignment.LEFT);
				sheet.mergeCells(0, 6, 2, 6);
				sheet.setRowView(6,500);
				Label labelcontact = new Label(0, 6, "联系人",wcfFcontact); 
				sheet.addCell(labelcontact);
				
				Label yewulianxiren = new Label(0, 7, "业务联系人：",wcfFright); 
				sheet.addCell(yewulianxiren);
				Label yewuPhone = new Label(0, 8, "电话：",wcfFright); 
				sheet.addCell(yewuPhone);
				Label yewuEmail = new Label(0, 9, "邮箱：",wcfFright); 
				sheet.addCell(yewuEmail);
				Label jishulianxiren = new Label(0, 10, "技术联系人：",wcfFright); 
				sheet.addCell(jishulianxiren);
				Label jishuPhone = new Label(0, 11, "电话：",wcfFright); 
				sheet.addCell(jishuPhone);
				Label jishuEmail = new Label(0, 12, "邮箱：",wcfFright); 
				sheet.addCell(jishuEmail);
				
				sheet.mergeCells(1, 7, 2, 7);
				Label yewu = new Label(1, 7, customer.getB_contact(),wcfleft); 
				sheet.addCell(yewu);
				sheet.mergeCells(1, 8, 2, 8);
				Label ywPhone = new Label(1, 8, customer.getB_phone(),wcfleft); 
				sheet.addCell(ywPhone);
				sheet.mergeCells(1, 9, 2, 9);
				Label ywEmail = new Label(1, 9, customer.getB_email(),wcfleft); 
				sheet.addCell(ywEmail);
				sheet.mergeCells(1, 10, 2, 10);
				Label jishu = new Label(1, 10, customer.getT_contact(),wcfleft); 
				sheet.addCell(jishu);
				sheet.mergeCells(1, 11, 2, 11);
				Label jsPhone = new Label(1, 11, customer.getT_phone(),wcfleft); 
				sheet.addCell(jsPhone);
				sheet.mergeCells(1, 12, 2, 12);
				Label jsEmail = new Label(1, 12, customer.getT_email(),wcfleft); 
				sheet.addCell(jsEmail);
				
				sheet.setRowView(2,400);
				sheet.setRowView(3,400);
				sheet.setRowView(4,400);
				sheet.setRowView(5,400);
				sheet.setRowView(7,400);
				sheet.setRowView(8,400);
				sheet.setRowView(9,400);
				sheet.setRowView(10,400);
				sheet.setRowView(11,400);
				sheet.setRowView(12,400);
				sheet.setColumnView(0,15);
				sheet.setColumnView(1,20);
				sheet.setColumnView(2,50);
				workbook.write();
				workbook.close();
			}
			
			//先建立一个文件读取流去读取这个临时excel文件
	        FileInputStream fs = null;
	        try {
	            fs = new FileInputStream(filename);
	        } catch (FileNotFoundException e) {
	            log.error("生成excel错误! " + filename + " 不存在!",e);
	            return;
	        }
	        // 设置响应头和保存文件名
	        //这个一定要设定，告诉浏览器这次请求是一个下载的数据流
	        final String userAgent = request.getHeader("USER-AGENT");
	        
	        response.setContentType("application/vnd.ms-excel");
	        String excelName = "";
	        try {
	        	//这边的 "客户名称-资源.xls" 替换成你自己要显示给用户的文件名
	            String fileName2 = customer.getCustomerName()+"-资源.xls";
	            if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
	            	excelName = URLEncoder.encode(fileName2, "UTF8");
	            }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
	            	excelName = new String(fileName2.getBytes(), "ISO8859-1");
	            }else{
	            	excelName = URLEncoder.encode(fileName2, "UTF8");//其他浏览器
	            }
	        } catch (UnsupportedEncodingException e1) {
	            log.error("转换excel名称编码错误!",e1);
	        }
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + excelName + "\"");
	        log.debug(excelName);
	        // 写出流信息
	        int b = 0;
	        try {
	            //这里的 response 就是你 servlet 的那个传参进来的 response
	            PrintWriter out = response.getWriter();
	            while ((b = fs.read()) != -1) {
	                out.write(b);
	            }
	            fs.close();
	            out.close();
	            log.debug(excelName+" - " + new Date().toString() + "文件下载完毕.");
	        } catch (Exception e) {
	            log.error(excelName+" - " + new Date().toString() + " 下载文件失败!.",e);
	        }
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
	}
    
}
