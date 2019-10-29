package tech.jdevmin.web.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {


    @GetMapping
    public String uploader() {
        return "upload";
    }
}
