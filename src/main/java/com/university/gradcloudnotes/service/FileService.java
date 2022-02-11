package com.university.gradcloudnotes.service;

import com.university.gradcloudnotes.entity.response.UniversalResponse;
import com.university.gradcloudnotes.utils.GetReturn;
import com.university.gradcloudnotes.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileService {

    @Value("${file-path-upload}")
    private String uploadPath;

    public UniversalResponse uploadFile(MultipartFile file) throws Exception {
        /**创建服务器目录*/
        String realPath = mkdir(uploadPath);
        /**重命名文件*/
        String originalFilename = file.getOriginalFilename();
        String target = realPath + "/" + UUIDUtil.getUUID() + "." +
                originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        /**文件上传*/
        file.transferTo(new File(target));
        return GetReturn.getReturn("200", "", target);
    }

    private String mkdir(String uploadPath) {
        /**根据当前日期创建目录*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateFormat = sdf.format(new Date());
        /**创建绝对路径*/
        String realPath = uploadPath + "/" + dateFormat;
        File uploadDir = new File(realPath);
        if(!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        return realPath;
    }
}
