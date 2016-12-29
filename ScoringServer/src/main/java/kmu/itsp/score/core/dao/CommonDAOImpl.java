package kmu.itsp.score.core.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 모든 Database Access Object에 사용된다.
 * 기본적인 CRUD를 구현하고있다.
 * @author WJ
 *  
 */

@Repository
public abstract class CommonDAOImpl implements CommonDAO{
	
	
	/**
	 *  hibernate의 sessionFactory
	 *    
	 *  @see #getSession()
	 */
	@Autowired
	private SessionFactory sessionFactory;

	
	/**
	 *  sessionFactory를 이용하여 현재 DB와 연결되는 session을 반환한다.
	 *  session이 없다면 새로 생성하여 반환한다.
	 *  
	 *  @see #sessionFactory
	 *  @return {@link Session}
	 */
	
	@Override
	public Session getSession() {
		Session session;
		try {
			session=sessionFactory.getCurrentSession();			
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			session = sessionFactory.openSession();
		}
		
		return session;
	}
	
	/**
	 * DB insert 메소드
	 * 
	 * @param entity DB안의 값에 해당하는 오브젝트
	 * 
	 */
	
	@Override
	public void persist(Object entity) {
		getSession().persist(entity);
	}
	
	/**
	 * DB update 메소드
	 * 
	 * @param entity DB안의 값에 해당하는 오브젝트
	 * 
	 */

	@Override
	public void update(Object entity) {
		getSession().saveOrUpdate(entity);
	}
	
	/**
	 * DB update 메소드
	 * 
	 * @param entity DB안의 값에 해당하는 오브젝트
	 * 
	 */
	
	@Override
	public void delete(Object entity) {
		getSession().delete(entity);
		
	}

}
