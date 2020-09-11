package com.spring.tx.xml;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.tx.xml.service.BookShopService;
import com.spring.tx.xml.service.Cashier;

class SpringTransactionTest {
	
	private ApplicationContext ctx = null;
	private BookShopDao bookShopDao = null;
	private BookShopService bookShopService = null;
	private Cashier cashier = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext-tx-xml.xml");
		bookShopDao = (BookShopDao) ctx.getBean("bookShopDao");
		bookShopService = (BookShopService) ctx.getBean("bookShopService");
		cashier = (Cashier) ctx.getBean("cashier");
	}
	
	@Test
	public void testTransactionlPropagation() {
		cashier.checkout("AA", Arrays.asList("1001", "1002"));
	}
	
	@Test
	public void testBookShopService() {
		bookShopService.purchase("AA", "1001");
	}
	
	@Test
	public void testBookshopDaoUpdateUserAccount() {
		bookShopDao.updateUserAccount("AA", 200);
	}
	
	@Test
	public void testBookShopDaoUpdateBookStock() {
		bookShopDao.updateBookStock("1001");
	}

	@Test
	public void testBookShopDaoFindPriceByIsbn() {
		System.out.println(bookShopDao.findBookPriceIsbn("1001"));
	}

}
