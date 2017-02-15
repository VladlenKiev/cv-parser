package cloud.molddata.parser.cv.dao;

import cloud.molddata.parser.cv.model.*;
import java.util.List;

public interface FileUploadDao {

    List<UploadedFile> getListFiles();

    UploadedFile getFile(Long id);

    UploadedFile saveFile(UploadedFile uploadedFile);
}
