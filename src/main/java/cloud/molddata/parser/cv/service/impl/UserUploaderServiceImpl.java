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

 /* public FileUploadServiceImpl(){}
  public FileUploadServiceImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;

  }*/


    @Override
    @Transactional(readOnly = true)
    public List<Users> listUsers(String nameAuth, String sessionID) {

        return dao.listUsers(nameAuth, sessionID);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Users> listUsersAll() {

        return dao.listUsersAll();
    }
    @Override
        @Transactional(readOnly = true)
        public List<UserSecurity> listUsersAuth() {

            return dao.listUsersAuth();
        }


    @Override
    @Transactional
    public void createUser(String sessionID) {
        dao.createUser(sessionID);
    }

}
