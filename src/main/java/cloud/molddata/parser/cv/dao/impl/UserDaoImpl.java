package cloud.molddata.parser.cv.dao.impl;

import cloud.molddata.parser.cv.dao.UserDao;
import cloud.molddata.parser.cv.model.UserSecurity;
import cloud.molddata.parser.cv.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
/*
	@Autowired
	private SessionFactory sessionFactory;*/

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
	public void save(UserSecurity user, String sessionID) {
		String nameUserAuth = user.getUsername();
		//System.out.println("userNameAuth="+nameUserAuth);
		//getEntityManager().persist(user); //persist?
		if(user.getUsername() == null)
			getEntityManager().persist(user);
		else {
			getEntityManager().merge(user);
			//getEntityManager().merge(user.getUserSecRole());
		}
		//System.out.println("SESS_ID in save (register)="+sessionID);
		Query usersBySession = getEntityManager().createQuery("SELECT us FROM Users us where us.sessionID LIKE :sessionID").setParameter("sessionID",sessionID);
		List<Users> usersList =  usersBySession.getResultList();
		//System.out.println("user from SEC REG="+usersList.get(0).getSessionID());

		Users users = usersList.get(0);
		//System.out.println("nameAuth before="+users.getNameAuth());
		users.setNameAuth(nameUserAuth);
		//System.out.println("insert into USERS nameAuth="+users.getNameAuth());
		//Update into Users
		getEntityManager().persist(users);
		//System.out.println("insert into USERS nameAuth="+users.getNameAuth());

		//return user;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public void authorization(String nameAuth, String sessionID) {
		//System.out.println("userNameAuth="+nameAuth);
		//getEntityManager().persist(user); //persist?
		/*if(user.getUsername() == null) {
			getEntityManager().persist(user);
			System.out.println("ROLE!!!!!!!!!!!"+user.getUserSecRole());
			//getEntityManager().persist(user.getUserSecRole());

		} else {
			getEntityManager().merge(user);
			//getEntityManager().merge(user.getUserSecRole());
		}*/
		try {
			Query querySessionID = getEntityManager().createNamedQuery("Users.findBySessionID").setParameter("idReq",sessionID);

			Users userLogin = (Users) querySessionID.getSingleResult();

			System.out.println("insert into USERS nameAuth after LOGIN=" + userLogin.getNameAuth());
			userLogin.setNameAuth(nameAuth);
			//Update into Users
			getEntityManager().merge(userLogin);
			System.out.println("update into USERS nameAuth after LOGIN=" + userLogin.getNameAuth());

		}catch (NoResultException NRE){
			System.out.println("NULL??");
			Users usersNew = new Users(sessionID,nameAuth);
			getEntityManager().persist(usersNew);
		}
	}

	@SuppressWarnings("unchecked")
	public UserSecurity findByUserName(String username) {
		//System.out.println("I WAS CALLED! findByUserName (username="+username+")");
		Query query = getEntityManager().createQuery("Select us from UserSecurity us where us.username LIKE :username").setParameter("username",username);
		List<UserSecurity> users = query.getResultList();
		//System.out.println("SIZE! users==="+users.size());

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
}