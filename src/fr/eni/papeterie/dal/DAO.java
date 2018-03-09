package fr.eni.papeterie.dal;

import java.util.List;

public interface DAO<T> {

	void insert(T o) throws DALException;

	T selectById(int id) throws DALException;

	List<T> selectAll() throws DALException;

	void update(T o) throws DALException;

	void delete(T o) throws DALException;
}
