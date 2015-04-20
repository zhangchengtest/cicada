package com.cicada.service.api.user;

import com.cicada.core.model.user.User;
import com.cicada.service.api.Service;
import com.cicada.service.bean.UserLoginRequest;
import com.cicada.service.exception.RuntimeServiceException;
import com.cicada.service.exception.ServiceException;

public interface UserService extends Service {

	public User login(UserLoginRequest request) throws ServiceException, RuntimeServiceException;

}
