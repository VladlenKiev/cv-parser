package cloud.molddata.parser.cv.dao;

import cloud.molddata.parser.cv.model.*;

import java.util.List;

public interface CVParserDao {

  Contact saveParsedCVes(List<UploadedFile> activeFilesInSession);

   void saveParsedCV(List<UploadedFile> activeFilesInSession);

  String getContactForThis(String id_cont);

  Contact contInfo(String id_cont);

  List<CV> listCVes();

  //-----------------------
  String parseStatus(UploadedFile activeFileInSession);

}
