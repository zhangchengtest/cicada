package com.cicada.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cicada.core.model.Model;

/**
 * <p>
 * interface for all CRUD mappers. <br />
 * some basic CRUD operation already defined, sub-classes and sub-interfaces may choose to implement part or all of
 * them.
 * </p>
 * 
 * @author hermano
 *
 */
public interface CRUDDao extends Dao {

	/**
	 * create a new entity in database.
	 * 
	 * @param entity
	 * @return rows affected
	 * @
	 */
	public <T extends Model> int insert(T entity) ;

	/**
	 * get an entity from database by the primary key.
	 * 
	 * @param id
	 *            the primary key
	 * @return
	 * @
	 */
	public <T extends Model> T selectOne(String id) ;

	/**
	 * <p>
	 * search the entities with the given filter.<br />
	 * taken all non-null properties of the <code>filterEntity</code> parameter as the filter criteria.<br/>
	 * it is up to the concrete mapper to decide how each property will be used (equals, contains, startWith, etc).
	 * </p>
	 * 
	 * @param filterEntity
	 * @param startIndex
	 * @param fetchRows
	 * @return
	 * @
	 */
	public <T extends Model> List<T> selectBy(@Param("filterEntity") T filterEntity,
			@Param("startIndex") int startIndex, @Param("fetchRows") int fetchRows) ;

	/**
	 * retrieve all entities from the database.
	 * 
	 * @return
	 * @
	 */
	public <T extends Model> List<T> selectAll() ;

	/**
	 * get total counter of entities that are filtered by the given criteria. <br/>
	 * check {@link #selectBy} for more detail.
	 * 
	 * @param filterEntity
	 * @return
	 * @
	 */
	public <T extends Model> int countBy(@Param("filterEntity") T filterEntity) ;

	/**
	 * get total counter of all entities in database.
	 * 
	 * @return
	 * @
	 */
	public <T extends Model> int countAll() ;

	/**
	 * update an entity.
	 * 
	 * @param entity
	 * @return row affected
	 * @
	 */
	public <T extends Model> int update(T entity) ;

	/**
	 * remove an entity from the database.
	 * 
	 * @param id
	 * @return row affected
	 * @
	 */
	public <T extends Model> int delete(@Param("ID") String id) ;
}
