package tech.jdevmin.web.service.files;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;

@Service
@Slf4j
@Data
public class FilesServiceImpl implements FilesService {


    private File folder = new File("/home/bowzer/WORKSPACE/jdevmin-web/src/main/resources/static/images");

    private String[] files = folder.list();
    private String filePrefix = "uploads/images/";
    private ArrayList<String> filesList = new ArrayList<>();

    @Override
    public void buildFilePaths() {

        log.info("building file paths...");

        for (int i = 0; i < files.length; i++) {

            filesList.add(filePrefix + files[i]);
        }


    }

}
