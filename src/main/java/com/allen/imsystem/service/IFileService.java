package com.allen.imsystem.service;

import com.allen.imsystem.model.dto.FileUploadInfo;
import com.allen.imsystem.model.dto.MultiFileResponse;
import com.allen.imsystem.model.dto.MultipartFileDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传相关的业务逻辑接口
 */
@Service
public interface IFileService {

    /**
     * 上传用户头像
     */
    String uploadAvatar(MultipartFile multipartFile, String uid);


    /**
     * 上传聊天图片
     * @param multipartFile
     * @return
     */
    String uploadMsgImg(MultipartFile multipartFile) throws IOException;

    MultiFileResponse uploadMultipartFile(MultipartFileDTO param) throws IOException;

    FileUploadInfo getUnCompleteParts(String md5);

    String getMd5FromUrl(String url);

    byte[] compressImage(byte[] bytes,int width,int height,long destSize,double quality) throws IOException;

}
