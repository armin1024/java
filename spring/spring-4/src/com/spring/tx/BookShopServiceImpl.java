package com.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {
	
	@Autowired
	private BookShopDao bookShopDao;

	//�������ע��
	//1.ʹ�� propagation ָ������Ĵ�����Ϊ, ����ǰ�����񷽷�������һ�����񷽷�����ʱ
	//	���ʹ������, Ĭ��ȡֵΪ REQUIRED, ��ʹ�õ��÷���������
	//	REQUIRES_NEW: ʹ���Լ�������, ���õ����񷽷������񱻹���.
	//2.ʹ�� isolation ָ������ĸ��뼶��, ��õ�ȡֵΪ READ_COMMITTED
	//3.Ĭ������� Spring ����������������е�����ʱ�쳣���лع�, Ҳ����ͨ����Ӧ�����Խ�������.
	//	ͨ�������Ĭ�ϼ���
	//4.ʹ�� readOnly ָ�������Ƿ�Ϊֻ��. ��ʾ�������ֻ��ȡ���ݵ�����������, 
	//	�������԰������ݿ������Ż�����. �������һ��ֻ��ȡ���ݿ�ֵ�÷���,Ӧ���� readOnly=true
	//5.ʹ�� timeout ָ��ǿ�ƻع�֮ǰ�������ռ�õ�ʱ��. 
		//	@Transactional(propagation = Propagation.REQUIRES_NEW,
		//			isolation = Isolation.READ_COMMITTED,
		//			noRollbackFor = {UserAccountException.class})
	@Transactional(propagation = Propagation.REQUIRES_NEW,
			isolation = Isolation.READ_COMMITTED,
			readOnly = false,
			timeout = 3)
	@Override
	public void purchase(String username, String isbn) {
		
		try {
			Thread.sleep(5000);
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
