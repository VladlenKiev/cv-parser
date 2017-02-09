package cloud.molddata.parser.cv.service.impl;

import cloud.molddata.parser.cv.dao.CVParserDao;
import cloud.molddata.parser.cv.model.*;
import cloud.molddata.parser.cv.service.CVParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CVParserServiceImpl implements CVParserService {

  //private SessionFactory sessionFactory;

  @Autowired
  private CVParserDao dao;

 /* public FileUploadServiceImpl(){}
  public FileUploadServiceImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;

  }*/

      @Override
    @Transactional
    public void saveParsedCV(List<UploadedFile> activeFilesInSession){
        dao.saveParsedCV(activeFilesInSession);
    }
    @Override
    @Transactional
    public Contact saveParsedCVes(List<UploadedFile> activeFilesInSession){
        return dao.saveParsedCVes(activeFilesInSession);
    }

    @Override
    @Transactional
    public String parseStatus(UploadedFile activeFileInSession){
        return dao.parseStatus(activeFileInSession);
    }

    @Override
    @Transactional
    public String getContactForThis(String id_cont){
        return dao.getContactForThis(id_cont);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CV> listCVes() {

        return dao.listCVes();
    }

    @Override
    @Transactional(readOnly = true)
    public Contact contInfo(String id_cont) {

        return dao.contInfo(id_cont);
    }

}
