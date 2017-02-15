package cloud.molddata.parser.cv.dao.impl;

import java.util.List;

import cloud.molddata.parser.cv.model.*;
import cloud.molddata.parser.cv.dao.FileUploadDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FileUploadDaoImpl implements FileUploadDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<UploadedFile> getListFiles() {
        return getEntityManager().createQuery("Select uf from UploadedFile uf").getResultList();
    }

    @Override
    @Transactional
    public UploadedFile getFile(Long id) {
        return getEntityManager().find(UploadedFile.class, id);
    }

    @Override
    @Transactional
    public UploadedFile saveFile(UploadedFile uploadedFile) {
        return getEntityManager().merge(uploadedFile);
    }


}
