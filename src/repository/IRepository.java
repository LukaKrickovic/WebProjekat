package repository;

import model.Id;

public interface IRepository<T, ID> {

	public T create(T entity);
	public T getById(ID id);
	public Iterable<T> getAll();
	public void update(T entity);
	public void delete(T entity);
	public Id findHighestId();
	
}
