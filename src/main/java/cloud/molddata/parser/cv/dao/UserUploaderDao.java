package cloud.molddata.parser.cv.dao;

import cloud.molddata.parser.cv.model.UserSecurity;
import cloud.molddata.parser.cv.model.Users;

import java.util.List;

public interface UserUploaderDao {


  List<Users> getListUsersByName(String nameAuth, String sessionID);

  List<Users> getListUsersAll();

  List<UserSecurity> getListUsersAuth();

  void createAnonymousUser(String sessionID);

}
