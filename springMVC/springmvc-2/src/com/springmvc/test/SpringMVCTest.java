package com.springmvc.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.crud.dao.EmployeeDao;
import com.springmvc.crud.entities.Employee;

@Controller
public class SpringMVCTest {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@RequestMapping("/testSimpleMappingExceptionResolver")
	public String testSimpleMappingExceptionResolver(@RequestParam("i") int i) {
		String[] vals = new String[10];
		System.out.println(vals[i]);
		return "success";
	}
	
	@RequestMapping(value="/testDefaultHandlerExceptionResolver", method=RequestMethod.POST)
	public String testDefaultHandlerExceptionResolver() {
		System.out.println("testDefaultHandlerExceptionResolver...");
		return "success";
	}
	
	@ResponseStatus(reason="测试", value=HttpStatus.NOT_FOUND)
	@RequestMapping("/testResponseStatusExceptionResolver")
	public String testResponseStatusExceptionResolver(@RequestParam("id") int i) {
		if(i == 13) {
			throw new UserNameNotMatchPassword();
		}
		System.out.println("testResponseStatusExceptionResolver...");
		
		return "success";
	}
	
//	@ExceptionHandler({RuntimeException.class})
//	public ModelAndView handlerArithmeticException2(Exception ex) {
//		System.out.println("[出异常了]: "+ex);
//		ModelAndView mv = new ModelAndView("error");
//		mv.addObject("exception", ex);
//		return mv;
//	}
	
	/**
	 * 1. 在 @ExceptionHandler 方法的入参中可以加入 Exception 类型的参数, 该参数即对应发生的异常对象
	 * 2. @ExceptionHandler 方法的入参中不能传入 Map. 若希望把异常信息传导页面上, 需要使用 ModelAndView 作为返回值
	 * 3. @ExceptionHandler 方法标记的异常有优先级的问题. 
	 * 4. @ControllerAdvice: 如果在当前 Handler 中找不到 @ExceptionHandler 方法来出来当前方法出现的异常, 
	 * 则将去 @ControllerAdvice 标记的类中查找 @ExceptionHandler 标记的方法来处理异常. 
	 */
//	@ExceptionHandler({ArithmeticException.class})
//	public ModelAndView handlerArithmeticException(Exception ex) {
//		System.out.println("出异常了: "+ex);
//		ModelAndView mv = new ModelAndView("error");
//		mv.addObject("exception", ex);
//		return mv;
//	}
	
	@RequestMapping("/testExceptionHandlerExceptionResolver")
	public String testExceptionHandlerExceptionResolver(@RequestParam("id") int id) {
		System.out.println("result: "+(10/id));
		return "success";
	}
	
	/**
	 * 文件的上传
	 * 配置的  MultipartResolver 将影响 HttpMessageConverter 的 @RequestBody
	 */
	@RequestMapping("/testFileUpload")
	public String testFileUpload(@RequestParam("desc") String desc,
			@RequestParam("file") MultipartFile file) throws IOException {				
		if(!file.isEmpty()) {
			System.out.println("desc: "+desc);
			file.transferTo(new File("D:\\upload\\"+file.getOriginalFilename()));
			System.out.println("InputStream: "+file.getInputStream());
		}		
		return "success";
	}
	
	/**
	 * 效果同 <mvc:view-controller path="/i18n" view-name="i18n"/>
	 */
	@RequestMapping("/i18n")
	public String testI18n(Locale locale) {
		String val = messageSource.getMessage("i18n.user", null, locale);
		System.out.println(val);
		return "i18n";
	}
	
	/**
	 * 文件的下载
	 */
	@RequestMapping("/testResponseEntity")
	public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
		byte[] body = null;
		ServletContext servletContext = session.getServletContext();
		InputStream in = servletContext.getResourceAsStream("/files/MEN.txt");
		body = new byte[in.available()];
		in.read(body);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=xss.txt");
		
		HttpStatus statusCode = HttpStatus.OK;
		
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
		return response;
	}
	
	/**
	 * HttpMessageConverter
	 * 文件的上传(不能实现上传, 只能读取到文件内容)
	 */
	@ResponseBody
	@RequestMapping("/testHttpMessageConverter")
	public String testHttpMessageConverter(@RequestBody String body) {
		System.out.println(body);
		return "helloworld! "+new Date();
	}
	
	/**
	 * HttpMessageConverter
	 * Employee 对象转化为 JSON 返回到前端
	 */
	@ResponseBody
	@RequestMapping("/testJson")
	public Collection<Employee> testJson() {
		return employeeDao.getAll();
	}
	
	/**
	 * 实现Employee对象如: GG-gg@armin.com-0-105 的添加
	 * @RequestParam("employee") 对应 input.jsp 表单中 name="employee"
	 * 该方法依赖于 EmployeeConverter.java 以装配到 springmvc.xml 配置文件中.
	 */
	@RequestMapping("/testConversionServiceConverer")
	public String testConverter(@RequestParam("employee") Employee employee) {
		System.out.println("save: "+employee);
		employeeDao.save(employee);
		return "redirect:/emps";
	}
	
}
