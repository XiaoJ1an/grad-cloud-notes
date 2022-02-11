package com.university.gradcloudnotes.rest;

import com.university.gradcloudnotes.entity.response.UniversalResponse;
import com.university.gradcloudnotes.service.FileService;
import com.university.gradcloudnotes.utils.GetReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "api/cloud", produces = "application/json;charset=utf-8")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     */
    @PostMapping("/file/uploadFile")
    public UniversalResponse uploadFile(@RequestParam MultipartFile[] files) {

        for (MultipartFile file : files) {
            /**文件非空校验*/
            if(!(file != null && file.getSize() > 0)) {
                return GetReturn.getReturn("400", "请勿上传空文件！", null);
            }
            /**文件类型校验*/
            if(!("jpg".equalsIgnoreCase(file.getContentType()) || "png".equalsIgnoreCase(file.getContentType()))) {
                return GetReturn.getReturn("400", "不支持此文件格式！", null);
            }
            /**文件上传*/
            try {
                return fileService.uploadFile(file);
            } catch (Exception e) {
                logger.info("文件上传发生异常！e={}", e);
                return GetReturn.getReturn("400", "文件上传发生异常！", e);
            }
        }
        return null;
    }
}
