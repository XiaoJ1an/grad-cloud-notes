package com.university.gradcloudnotes.service;

import com.university.gradcloudnotes.entity.response.UniversalResponse;
import com.university.gradcloudnotes.jpa.CnPicture;
import com.university.gradcloudnotes.repository.CnPictureRepository;
import com.university.gradcloudnotes.utils.GetReturn;
import com.university.gradcloudnotes.utils.PubFun;
import com.university.gradcloudnotes.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileService {

    @Value("${file-path-upload}")
    private String uploadPath;
    @Autowired
    private CnPictureRepository cnPictureRepository;

    public UniversalResponse uploadFile(MultipartFile file, String type) throws Exception {
        /**创建服务器目录*/
        String realPath = mkdir(uploadPath);
        /**重命名文件*/
        String originalFilename = file.getOriginalFilename();
        String target = realPath + "/" + UUIDUtil.getUUID() + "." +
                originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        /**文件上传*/
        file.transferTo(new File(target));
        /**保存cn_picture表信息*/
        CnPicture cnPicture = new CnPicture();
        cnPicture.setId(UUIDUtil.getUUID());
        cnPicture.setState("1");/**默认是新增正常的*/
        cnPicture.setType(type);
        cnPicture.setUrl(target);
        cnPicture.setMakeDate(PubFun.getCurrentDate());
        cnPicture.setMakeTime(PubFun.getCurrentTime());
        cnPictureRepository.save(cnPicture);
        return GetReturn.getReturn("200", "", target);
    }

    private String mkdir(String uploadPath) {
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        return uploadPath;
    }
}
