package com.cicada.dao.user;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.cicada.core.model.user.User;
import com.cicada.dao.CRUDDao;

@Component
public interface UserDao extends CRUDDao {

	/**
	 * get a user associated with the given user name.
	 * 
	 * @param userName
	 * @return
	 */
	User selectByName(@Param("userName") String userName);

	/**
	 * select the user with login name and encrypted password.<br/>
	 * it is outer service's responsibility to encrypt the password first (with salt or any other algorithm).
	 * 
	 * @param loginName
	 * @param passwdDigest
	 * @return
	 */
	User selectUserByNameAndPasswd(@Param("loginName") String loginName,
			@Param("passwdDigest") String passwdDigest);

}