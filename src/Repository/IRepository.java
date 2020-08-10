package Repository;

public interface IRepository<T, ID> {

	public T Create(T entity);
	public T GetById(ID id);
	public Iterable<T> GetAll();
	public void Update();
	public void Delete();
	
}
