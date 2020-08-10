package Repository;

public interface IEagerRepository<T, ID> {

	public T GetAllEager();
	public T GetEagerById(ID id);
}
