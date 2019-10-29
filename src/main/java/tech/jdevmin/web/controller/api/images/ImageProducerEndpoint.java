package tech.jdevmin.web.controller.api.images;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.jdevmin.web.service.files.FilesService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequestMapping("uploads")
@Slf4j
public class ImageProducerEndpoint {


    @Autowired
    Environment environment;


    @Autowired
    FilesService filePathBuilder;










    @GetMapping
    public String getPictures(Model model) {
        filePathBuilder.buildFilePaths();

        log.info("files list");
        model.addAttribute("imgs", filePathBuilder.getFilesList());




        return "pictures";
    }

    @GetMapping(value = "/images/{filepath}",produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] localImageFileServerEndpoint(@PathVariable("filepath") String filepath, @ModelAttribute String filename) throws IOException {
        log.info("image loader is called");




//        String fullPath = "/home/bowzer/WORKSPACE/jdevmin-web/src/main/resources/static/images/" + filepath;

        String fullPath = environment.getProperty("local.image-path") + filepath;


        File file = new File(fullPath);


        return Files.readAllBytes(file.toPath());



    }


}
