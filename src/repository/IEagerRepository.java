package repository;

public interface IEagerRepository<T, ID> {

	public T getAllEager();
	public T getEagerById(ID id);
}
