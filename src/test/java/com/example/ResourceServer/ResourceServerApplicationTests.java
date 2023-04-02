package com.example.ResourceServer;

import com.example.ResourceServer.dao.ProfilesDao;
import com.example.ResourceServer.dao.UploadsDao;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.exceptions.UserNotFoundException;
import com.example.ResourceServer.security.AuthManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResourceServerApplicationTests {

	@Autowired
	ProfilesDao profilesDao;

	@Autowired
	UploadsDao uploadsDao;
	@Test
	void contextLoads() {
	}
	@Test
	void getUploadsWithAvg(){
		assert uploadsDao.getUploads("7ba149f6-1b60-4033-94cc-3022b4c13ce0", 10, 0).size() != 0;
	}

	@Test
	@Transactional
	void loadUser() throws UserNotFoundException {
		Profile profile = profilesDao.getProfileById("fd7f8157-8dbd-41eb-a44f-31e102a23e0e");
		profile.getYearList();
		profile.getUploadList();
		assert profile != null;
	}

}
