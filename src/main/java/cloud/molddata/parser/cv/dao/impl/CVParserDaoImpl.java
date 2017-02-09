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
    public void saveParsedCV(List<UploadedFile> ActiveFilesInSession){
        loadParsedCV(ActiveFilesInSession);
    }


    @Transactional
    private Long getIDbySession(String sessionID){
        //System.out.println("sessionID for FIND "+sessionID);
        Query usersBySession = getEntityManager().createNamedQuery("Users.findBySessionID").setParameter("idReq",sessionID);
        Users users = (Users) usersBySession.getSingleResult();
        Long idUsersbySess = (Long)users.getId();
        //System.out.println("from getIDbySess "+idUsersbySess);
        return idUsersbySess;//in LONG -exe: 2L
    }

    @Transactional
    private void loadParsedCV(List<UploadedFile> activeFilesInSession){
        String sessionID = activeFilesInSession.get(0).getSessionID();

        SearchBoxParser.CVparser(activeFilesInSession); //ready CV in CLASS
        List<CV> cvList = SearchBoxParser.getcvList();

        for (CV cv:cvList){
            System.out.println("save to db 1");
            Contact contact = cv.getContact();
            System.out.println("save to db 2");

            cv.setContact(contact);
            contact.setCv(cv);
            System.out.println("save to db 3");

            getEntityManager().persist(contact);
            System.out.println("4.5");
            getEntityManager().persist(cv);
            System.out.println("save to db 4");

            Long tempID = getIDbySession(sessionID);
            addCV(tempID,cv);
            System.out.println("save to db 5");
        }
    }

    @Transactional
    private void addCV(Long idUSER, CV cv) {
        Users user = getEntityManager().find(Users.class,idUSER);
        user.addCV(cv);
        getEntityManager().persist(user);
    }

    @Override
    @Transactional
    public Contact saveParsedCVes(List<UploadedFile> activeFilesInSession){
        String sessionID = activeFilesInSession.get(0).getSessionID();

        SearchBoxParser.CVparser(activeFilesInSession); //ready CV in CLASS
        System.out.println("active file in sess = " + activeFilesInSession.size());
        //List<Contact> contactList = SearchBoxParser.getContactList();
        List<Contact> contactList = SearchBoxParser.getContactList();
        System.out.println("contact list = "+contactList.size());

      /*  CV cv1 = new CV("java","3 years","Ukraine","NTUU KPI 2004",new Date());
        Contact contact01 = new Contact(cv1,contactFullName,contactPhone,contactLocation);
        Contact contact02 = new Contact(cv1,contactFullName,contactPhone,contactLocation);
        Contact contact03 = new Contact(cv1,contactFullName,contactPhone,contactLocation);
        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact01);
        contactList.add(contact02);
        contactList.add(contact03);*/

        Contact contact1 = new Contact();


        for (Contact contact:contactList){
            Contact contact2 = new Contact(contact.getFullName(),contact.getPhone(),contact.getLocation());


            System.out.println("save to db 1");
            CV cv = contact.getcv();//cv def in method create
            System.out.println("save to db 2");

            cv.setContact(contact2);
            contact2.setCv(cv);
            System.out.println("save to db 3");

            getEntityManager().persist(contact2);
            System.out.println("4.5");
            getEntityManager().persist(cv);//cv def in method create
            System.out.println("save to db 4");

            Long tempID = getIDbySession(sessionID);
            addCV(tempID,cv);
            contact1 = contact2;
        }
        System.out.println("exit from for-each");
        return getEntityManager().merge(contact1);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<CV> listCVes() {
        //return getSession().createCriteria(UploadedFile.class).list();
        //getEntityManager().getTransaction().begin();
        //Query query = getEntityManager().createQuery("Select uf from UploadedFile u WHERE u.nameSession LIKE :nameSess").setParameter("nameSess",nameSession);
        Query query = getEntityManager().createQuery("Select cv from CV cv");
        List<CV> resList = query.getResultList();

        return resList;
    }
    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public Contact contInfo(String id_cont) {
        Long id_cont_long = Long.parseLong(id_cont);
        Contact contact = getEntityManager().find(Contact.class, id_cont_long);
        return contact;
    }
    @Override
    @Transactional
    public String getContactForThis(String id_cont) {
        //return (UploadedFile) getSession().get(UploadedFile.class, id);
        //getEntityManager().getTransaction().begin();
        Long id_cont_long = Long.parseLong(id_cont);
        Contact contact = getEntityManager().find(Contact.class, id_cont_long);
        return contact.toString();
    }

    @Override
    @Transactional
    public String parseStatus(UploadedFile activeFileInSession){
        return SearchBoxParser.parseStatus(activeFileInSession);
    }
}
