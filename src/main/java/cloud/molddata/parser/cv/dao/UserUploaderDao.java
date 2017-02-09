package cloud.molddata.parser.cv.dao;

import cloud.molddata.parser.cv.model.UserSecurity;
import cloud.molddata.parser.cv.model.Users;

import java.util.List;

public interface UserUploaderDao {


  List<Users> listUsers(String nameAuth, String sessionID);

  List<Users> listUsersAll();

  List<UserSecurity> listUsersAuth();

  void createUser(String sessionID);

}
