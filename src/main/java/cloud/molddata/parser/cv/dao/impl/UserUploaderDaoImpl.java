package cloud.molddata.parser.cv.dao.impl;


import cloud.molddata.parser.cv.dao.UserUploaderDao;
import cloud.molddata.parser.cv.model.UserSecurity;
import cloud.molddata.parser.cv.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserUploaderDaoImpl implements UserUploaderDao {

  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  public EntityManager getEntityManager(){
    return entityManager;
  }

  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }



    @SuppressWarnings("unchecked")
    @Transactional
    public List<Users> listUsers(String nameAuth, String sessionID) {
        List<Users> resList = null;
        //System.out.println("income nameAUTH="+nameAuth);
        if (("000").equals(sessionID)&&("anonymousUser").equals(nameAuth)){
            Query query = getEntityManager().createQuery("Select us from Users us WHERE us.nameAuth like :nameAuth").setParameter("nameAuth","anonymousUser");
            resList = query.getResultList();
            return resList;
        }
        if (("anonymousUser").equals(nameAuth)) {
            Query querySessionID = getEntityManager().createNamedQuery("Users.findBySessionID").setParameter("idReq",sessionID);
            resList = querySessionID.getResultList();
            return resList;
        }
        Query query = getEntityManager().createQuery("Select us from Users us WHERE us.nameAuth like :nameAuth").setParameter("nameAuth",nameAuth);
        resList = query.getResultList();
        return resList;
    }
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Users> listUsersAll() {
        Query queryUsersAll = getEntityManager().createNamedQuery("Users.findAll");
        List<Users> resList = queryUsersAll.getResultList();
        return resList;
    }
    @SuppressWarnings("unchecked")
        @Transactional
        public List<UserSecurity> listUsersAuth() {
            Query queryUsersAll = getEntityManager().createNamedQuery("UserSecurity.findAll");
            List<UserSecurity> resList = queryUsersAll.getResultList();
            return resList;
        }



    @Transactional
    public void createUser(String sessionID){
        Query usersBySession = getEntityManager().createNamedQuery("Users.findBySessionID").setParameter("idReq",sessionID);
        List<Users> usersList =  usersBySession.getResultList();
        if (usersList.size() == 0){
            Users users = new Users(sessionID, "anonymousUser");
            getEntityManager().persist(users);
            return;
        }
        System.out.println("User DIDN'T create! BEACUSE! user is exist");
    }




}
