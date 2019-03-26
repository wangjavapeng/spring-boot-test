package com.yestae.springboot.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yestae.springboot.annotation.LimitAnnotation;
import com.yestae.springboot.service.RedisService;

@RestController
//不在application.yml或者application.properties中需要如下引用
@PropertySource(value = {"classpath:properties/system.properties"})
public class OrderController {
	@Autowired
	private RedisService redisService;
	
	/**
	 * 属性注入
	 */
	@Value("${user.path}")
	private String path;
	
	@RequestMapping(value = "/unionpay", method=RequestMethod.POST)
    public String unionpay(HttpServletResponse response,String a) throws IOException{
    	response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter out = response.getWriter();
		a =  "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body><form id = \"pay_form\" action=\"https://gateway.95516.com/gateway/api/frontTransReq.do\" method=\"post\"><input type=\"hidden\" name=\"bizType\" id=\"bizType\" value=\"000201\"/><input type=\"hidden\" name=\"backUrl\" id=\"backUrl\" value=\"https://rest-pay.yestae.com/notify/unionpay\"/><input type=\"hidden\" name=\"orderId\" id=\"orderId\" value=\"1017001929486929922\"/><input type=\"hidden\" name=\"txnSubType\" id=\"txnSubType\" value=\"01\"/><input type=\"hidden\" name=\"signature\" id=\"signature\" value=\"M0LJtq/sgvHOlOPm3UyQAVEU7QxBh4euYKTKPlBve++cu39+ghFQrGKkrbj1whoM8TG0P1eoMbZfnq/6sh50bS/nuPEJB7cbiFmXKpprZEQ+ZFsBEWd3woyrRXLGrTdMyuOPdqkQPKUoN00BHNI0DBhO2cWxfDVVQ0nrt/fLY1fDIIJ3n9VujzofyPXZo5OSBwlM7fGvMbWzYuNpfL2kF/3IatyB0i1i62gX8UUT26up2AcNO9dmTI0h9Bcu9WOGYXe1htEWQ7qlwAUSFYMlorPFBd+tJ9j0OnMwOvz7gI0EwyA8Ga3SWha3otWakresB8zfRZTs/784oyASlZoa4g==\"/><input type=\"hidden\" name=\"channelType\" id=\"channelType\" value=\"07\"/><input type=\"hidden\" name=\"frontUrl\" id=\"frontUrl\" value=\"https://www.yestae.com/shopping-related/wallet\"/><input type=\"hidden\" name=\"txnType\" id=\"txnType\" value=\"01\"/><input type=\"hidden\" name=\"certId\" id=\"certId\" value=\"75035313720\"/><input type=\"hidden\" name=\"encoding\" id=\"encoding\" value=\"UTF-8\"/><input type=\"hidden\" name=\"version\" id=\"version\" value=\"5.1.0\"/><input type=\"hidden\" name=\"accessType\" id=\"accessType\" value=\"0\"/><input type=\"hidden\" name=\"txnTime\" id=\"txnTime\" value=\"20180711190529\"/><input type=\"hidden\" name=\"merId\" id=\"merId\" value=\"898111948161053\"/><input type=\"hidden\" name=\"payTimeout\" id=\"payTimeout\" value=\"20180711191529\"/><input type=\"hidden\" name=\"currencyCode\" id=\"currencyCode\" value=\"156\"/><input type=\"hidden\" name=\"orderDesc\" id=\"orderDesc\" value=\"大益卡充值\"/><input type=\"hidden\" name=\"signMethod\" id=\"signMethod\" value=\"01\"/><input type=\"hidden\" name=\"txnAmt\" id=\"txnAmt\" value=\"1\"/></form></body><script type=\"text/javascript\">document.all.pay_form.submit();</script></html>";
		out.print(a);
		out.flush();
		out.close();
		
		return null;
    }
    
	@LimitAnnotation(bizType="user", limit="5", sec=60)
    @RequestMapping("/get")
    public String get(){
    	return redisService.get("act_end_time");
    } 
    
}
