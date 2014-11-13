<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<div class="orders-resource">
	<table id="cloud">
		<tr>
			<th width="10%" style="text-align: right;padding-right:5px;">资源名称</th><th width="40%">数量</th><th width="25%">价格（元/月）</th><th width="25%" style="text-align: center;">折扣</th>
		</tr>
		<tr><td class="td">计算资源</td><td valign="middle"><div id="res_ecu"></div></td><td><div id="res_ecu_price"></div></td><td rowspan="3" class="ce"><div id="res_discount"></div></td></tr>
		<tr><td class="td">存储</td><td><div id="res_vol"></div></td><td><div id="res_vol_price"></div></td></tr>
		<tr><td class="td">快照</td><td><div id="res_snap"></div></td><td><div id="res_snap_price"></div></td></tr>
		<tr id="zzfw" style="display: none;"><td class="td">增值服务套餐</td><td><div id="res_zzfw"></div><td><div id="res_zzfw_price"></div></td><td  class="ce">无折扣</td></tr>
		<tr><td class="td">带宽</td><td><div id="res_bandwidth"></div><td><div id="res_bandwidth_price"></div></td><td  class="ce">无折扣</td></tr>
		<tr><td class="td">IP</td><td><div id="res_ip"></div></td><td><div id="res_ip_price"></div></td><td  class="ce">无折扣</td></tr>
		<tr><td class="td">主机保护</td><td><div id="res_ha"></div></td><td><div id="res_ha_price">免费</div></td><td  class="ce">无折扣</td></tr>
		<tr><td class="td">负载均衡</td><td><div id="res_elb"></div></td><td><div id="res_elb_price">免费</div></td><td  class="ce">无折扣</td></tr>
		<tr><td colspan="4" class="sum"><div id="cloud_sum_price"></div></td></tr>
	</table>
	<table id="storage">
		<tr>
			<th width="10%">资源名称</th><th width="40%">数量</th><th width="25%">价格（元）</th><th width="25%">总计（元）</th>
		</tr>
		<tr>
			<td class="td">存储</td><td><div id="stor_vol"></div></td><td><div id="stor_vol_price"></div></td><td rowspan="3" class="sum"><div id="stor_sum_price"></div></td>
		</tr>
		<tr>
			<td class="td">带宽</td><td><div id="stor_bandwidth"></div><td><div id="stor_bandwidth_price"></div></td>
		</tr>
		<tr>
			<td class="td">IP</td><td><div id="stor_ip"></div></td><td><div id="stor_ip_price"></div></td>
		</tr>
	</table>
</div>
