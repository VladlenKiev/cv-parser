package cloud.molddata.parser.cv.service;

import cloud.molddata.parser.cv.model.*;

import java.util.List;

public interface CVParserService {

  Contact saveParsedCVes(List<UploadedFile> activeFilesInSession);

    void saveParsedCV(List<UploadedFile> activeFilesInSession);

  String getContactForThis(String id_cont);

  List<CV> listCVes();

  Contact contInfo(String id_cont);
  //----------------------------------------
  String parseStatus(UploadedFile activeFileInSession);

}
