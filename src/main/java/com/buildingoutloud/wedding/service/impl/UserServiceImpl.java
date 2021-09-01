package com.buildingoutloud.wedding.service.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.buildingoutloud.wedding.entity.User;
import com.buildingoutloud.wedding.pojo.UserResponse;
import com.buildingoutloud.wedding.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService {

	private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	
	protected UserServiceImpl() {
		super(User.class);
	}

	@Override
	public User registration(User user) {
		
		LOGGER.info("Enter");
		if(!Objects.isNull(user.getPartnerUserId())) {
			user.setPartnerUserId(findById(user.getPartnerUserId().getId()));
		}
		if(Objects.isNull(user.getFullName())) {
			user.setFullName(user.getFirstName()+" "+user.getLastName());
		}
		user.setActive(true);
		user.setContractGenerated(false);
		user.setDocumentsApproved(false);
		LOGGER.info("Exit");
		return save(user);
	}

	@Override
	public UserResponse generateUserResponse(User user) {
		LOGGER.info("Enter");
		UserResponse userResponse = new UserResponse();
		if(Objects.isNull(user)) {
			return null;
		}else {
			BeanUtils.copyProperties(user, userResponse);
		}
		userResponse.setId(String.valueOf(user.getId()));
		userResponse.setDocumentAadhar(Base64Utils.encodeToString(user.getDocumentAadhar()));
		userResponse.setDocumentMariagePicture( Base64Utils.encodeToString(user.getDocumentMariagePicture()));
		LOGGER.info("Exit");
		return userResponse;
	}

}
