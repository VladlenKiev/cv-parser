package cloud.molddata.parser.cv.service.impl;

import cloud.molddata.parser.cv.dao.UserUploaderDao;
import cloud.molddata.parser.cv.model.UserSecurity;
import cloud.molddata.parser.cv.model.Users;
import cloud.molddata.parser.cv.service.UserUploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserUploaderServiceImpl implements UserUploaderService {

    //private SessionFactory sessionFactory;

    @Autowired
    private UserUploaderDao dao;


    @Override
    @Transactional(readOnly = true)
    public List<Users> getListUsersByName(String nameAuth, String sessionID) {
        return dao.getListUsersByName(nameAuth, sessionID);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Users> getListUsersAll() {
        return dao.getListUsersAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserSecurity> getListUsersAuth() {
        return dao.getListUsersAuth();
    }


    @Override
    @Transactional
    public void createAnonymousUser(String sessionID) {
        dao.createAnonymousUser(sessionID);
    }

}
