package cloud.molddata.parser.cv.dao.impl;


import cloud.molddata.parser.cv.parser.SearchBoxParser;
import cloud.molddata.parser.cv.dao.CVParserDao;
import cloud.molddata.parser.cv.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CVParserDaoImpl implements CVParserDao {

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




    @Transactional
    public void saveListParsedCV(List<UploadedFile> activeFilesInSession) {
        String sessionID = activeFilesInSession.get(0).getSessionID();

        SearchBoxParser.CVparser(activeFilesInSession); //ready CV in CLASS
        List<CV> cvList = SearchBoxParser.getcvList();

        for (CV cv : cvList) {
            Contact contact = cv.getContact();
            cv.setContact(contact);
            contact.setCv(cv);

            getEntityManager().persist(contact);
            getEntityManager().persist(cv);

            //Long tempID = getIDbySession(sessionID);
            addCV(getIDbySession(sessionID), cv);
        }
    }


    @Transactional
    private Long getIDbySession(String sessionID){
        //System.out.println("sessionID for FIND "+sessionID);
        Query usersBySession = getEntityManager().createNamedQuery("Users.findBySessionID").setParameter("idReq",sessionID);
        return ((Users) usersBySession.getSingleResult()).getId();//in LONG -exe: 2L
    }

    @Transactional
    private void addCV(Long idUSER, CV cv) {
        Users user = getEntityManager().find(Users.class,idUSER);
        user.addCV(cv);
        getEntityManager().persist(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<CV> getListCV() {
        //return getSession().createCriteria(UploadedFile.class).list();
        //getEntityManager().getTransaction().begin();
        //Query query = getEntityManager().createQuery("Select uf from UploadedFile u WHERE u.nameSession LIKE :nameSess").setParameter("nameSess",nameSession);
        //Query query = getEntityManager().createQuery("Select cv from CV cv");
        //List<CV> resList = query.getResultList();

        return getEntityManager().createQuery("Select cv from CV cv").getResultList();
    }
    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public Contact getContactInfo(String id_cont) {
        //Long id_cont_long = Long.parseLong(id_cont);
        //Contact contact = getEntityManager().find(Contact.class, id_cont_long);
        return getEntityManager().find(Contact.class, Long.parseLong(id_cont));
    }
    /*@Override
    @Transactional
    public String getContactForThis(String id_cont) {
        //return (UploadedFile) getSession().get(UploadedFile.class, id);
        //getEntityManager().getTransaction().begin();
        Long id_cont_long = Long.parseLong(id_cont);
        Contact contact = getEntityManager().find(Contact.class, id_cont_long);
        return contact.toString();
    }*/

    @Override
    @Transactional
    public String getParseStatus(UploadedFile activeFileInSession){
        return SearchBoxParser.parseStatus(activeFileInSession);
    }
}
