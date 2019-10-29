package tech.jdevmin.web.service.files;

import java.io.File;
import java.util.ArrayList;

public interface FilesService {

    void buildFilePaths();
    ArrayList<String> getFilesList();
}
