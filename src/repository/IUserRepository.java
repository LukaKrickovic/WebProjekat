package repository;

import enums.Gender;

public interface IUserRepository<T, ID> {

	public T getUserByUsername(String username);
	public Iterable<T> getUsersByName(String name);
	public Iterable<T> getUsersBySurname(String surname);
	public Iterable<T> getUsersByNameAndSurname(String name, String surname);
	public Iterable<T> getUsersByGender(Gender gender);
}
