package cloud.molddata.parser.cv.service;

import cloud.molddata.parser.cv.model.*;

import java.util.List;

public interface CVParserService {

    //Contact saveParsedCVes(List<UploadedFile> activeFilesInSession);

    void saveListParsedCV(List<UploadedFile> activeFilesInSession);

    //String getContactForThis(String id_cont);

    List<CV> getListCV();

    Contact getContactInfo(String id_cont);

    String getParseStatus(UploadedFile activeFileInSession);

}
