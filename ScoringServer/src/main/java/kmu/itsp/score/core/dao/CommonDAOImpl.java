package kmu.itsp.score.core.dao;

import javax.inject.Named;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="CommonDAO")
public abstract class CommonDAOImpl implements CommonDAO{
	@Autowired
	private SessionFactory sessionFactory;

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
	@Override
	public void persist(Object entity) {
		getSession().persist(entity);
	}
	@Override
	public void update(Object entity) {
		getSession().update(entity);
	}
	@Override
	public void delete(Object entity) {
		getSession().delete(entity);
		
	}

}
