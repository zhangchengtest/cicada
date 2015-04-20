package com.cicada.service.impl.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.cicada.core.model.user.User;
import com.cicada.core.model.user.UserStatusEnum;
import com.cicada.dao.user.UserDao;
import com.cicada.service.api.user.UserService;
import com.cicada.service.bean.UserLoginRequest;
import com.cicada.service.exception.RuntimeServiceException;
import com.cicada.service.exception.ServiceException;
import com.cicada.service.impl.BaseService;
import com.cicada.utils.SecurityUtils;
import com.cicada.utils.StringUtils;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserServiceImpl extends BaseService implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

//	private static final String passwordRecoveryTitle = HazeDefaultConfig.getMailProps().getProperty(
//			"mail_PasswordRecoveryTitle");
//	private static final String passwordRecoveryText1 = HazeDefaultConfig.getMailProps().getProperty(
//			"mail_PasswordRecoveryText1");
//	private static final String passwordRecoveryText2 = HazeDefaultConfig.getMailProps().getProperty(
//			"mail_PasswordRecoveryText2");
//	private static final String passwordRecoveryURL = HazeDefaultConfig.getMailProps().getProperty(
//			"mail_PasswordRecoveryURL");
//	private static final String recoveryAuthKeyValidTime = HazeDefaultConfig.getSecurityProps().getProperty(
//			"PASSWORDRECOVERYURL_VALID_TIME");

	@Autowired
	private UserDao userMapper;

//	@Autowired
//	private MailService mailService;
//	@Autowired
//	private SecurityService securityService;
//
//	@Autowired
//	private PasswordRecoveryMapper passwordRecoveryMapper;

	private static final String USER_NOT_EXIST = "the user does not exist";
	private static final String USERNAME_OR_PASSWORD_WRONG = "the username or password is wrong";
	private static final String USER_FOBIDDEN = "the user is fobidden to login";
	private static final String EMAIL_FORMAT_FAIL = "invalid email format";
	private static final String AUTH_KEY_TIMEOUT = "this URL is timeout";

	public UserServiceImpl() {
	}

	@Override
	public User login(@Validated UserLoginRequest request) throws ServiceException, RuntimeServiceException {
		User user = userMapper.selectByName(request.getUsername());
		if (user == null) {
			logger.error("the user {} login fail: {}", request.getUsername(), USER_NOT_EXIST);
			throw new RuntimeServiceException(USER_NOT_EXIST);
		}
		
		String password = SecurityUtils.SHA3(SecurityUtils.SHA3(request.getPassword()) + user.getPasswdSalt());
		if (!StringUtils.equals(password, user.getEncryptedPasswd())) {
			logger.error("the user {} login fail: {}", request.getUsername(), USERNAME_OR_PASSWORD_WRONG);
			throw new RuntimeServiceException(USERNAME_OR_PASSWORD_WRONG);
		}
		if (!UserStatusEnum.ACTIVE.equals(user.getStatus())) {
			logger.error("the user {} login fail: {}", request.getUsername(), USER_FOBIDDEN);
			throw new RuntimeServiceException(USER_FOBIDDEN);
		}

		user.setPasswdSalt("");
		user.setEncryptedPasswd("");

		return user;
	}
	
}
