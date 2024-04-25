package fr.formation.app.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {
	
	List<T> getAll();
	T saveOrUpdate(T o);
	Optional<T> getById(int id);
	boolean deleteById(int id);
}
