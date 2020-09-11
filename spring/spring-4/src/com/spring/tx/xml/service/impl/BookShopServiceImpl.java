package com.spring.tx.xml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.tx.xml.BookShopDao;
import com.spring.tx.xml.service.BookShopService;

public class BookShopServiceImpl implements BookShopService {
	
	private BookShopDao bookShopDao;
	
	public void setBookShopDao(BookShopDao bookShopDao) {
		this.bookShopDao = bookShopDao;
	}

	@Override
	public void purchase(String username, String isbn) {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//1.��ȡ��ĵ���
		int price = bookShopDao.findBookPriceIsbn(isbn);
		
		//2.������Ŀ��
		bookShopDao.updateBookStock(isbn);
		
		//3.�����û����
		bookShopDao.updateUserAccount(username, price);
	}

}
