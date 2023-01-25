package com.blog.controller.file;

import com.blog.domain.file.FileUploadRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class FileUploadController {

    // 1. 파일 업로드를 수행하는 Post 컨트롤러를 구현하고, 파일을 업로드 받아서 저장한다.
    @PostMapping("/upload")
    public void uploadFileController(@RequestBody FileUploadRequest fileUploadRequest) {
        

    }
    // 2. 파일을 업로드 받아서 저장하는 기능을 Service로 분리한다.
    // 3. 파일을 저장할때 파일명을 변경해서 저장한다.
    // 4. 파일을 저장할때 파일 타입을 체크해서 mp4, img, pdf 파일을 각각 분리해서 저장한다
    // 5. 파일을 저장할때 파일 장보를 DB에 저장한다.
    // 6. 파일을 업로드 받아서 저장하는 기능을 Repository로 분리한다.


}
