package kmu.itsp.score.core.dao;

import org.hibernate.Session;

public interface CommonDAO {
	public Session getSession();
	
	public void persist(Object entity);
	public void update(Object entity);
	public void delete(Object entity);
}
