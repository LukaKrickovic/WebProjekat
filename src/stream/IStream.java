package stream;

public interface IStream<T, ID> {

	public T create(T entity);
	public T getById(ID id);
	public Iterable<T> getAll();
	public void update();
	public void delete();
	
}
