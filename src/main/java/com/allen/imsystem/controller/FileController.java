package com.allen.imsystem.controller;

import com.allen.imsystem.common.exception.BusinessException;
import com.allen.imsystem.common.exception.ExceptionType;
import com.allen.imsystem.common.utils.MultipartFileUtil;
import com.allen.imsystem.model.dto.FileUploadInfo;
import com.allen.imsystem.model.dto.JSONResponse;
import com.allen.imsystem.model.dto.MultiFileResponse;
import com.allen.imsystem.model.dto.MultipartFileDTO;
import com.allen.imsystem.service.IFileService;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 文件模块，处理与文件上传相关的请求。
 */
@RequestMapping("/api/file")
@RestController
public class FileController {

    @Autowired
    private IFileService fileService;

    /**
     * 上传聊天图片。用于保存聊天中的图片到磁盘中，并返回URL
     */
    @RequestMapping(value = "/uploadMessageImage", method = RequestMethod.POST)
    public JSONResponse uploadMessageImage(@RequestParam("image") MultipartFile multipartFile) {
        try {
            String url = fileService.uploadMsgImg(multipartFile);
            return new JSONResponse(1).putData("imageUrl", url);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ExceptionType.FILE_NOT_RECEIVE, "文件保存失败");
        }
    }

    /**
     * 分块上传文件
     */
    @RequestMapping(value = "/uploadMultipartFile", method = RequestMethod.POST)
    public JSONResponse uploadMultipartFile(HttpServletRequest request) throws Exception {
        //使用 工具类解析相关参数
        MultipartFileDTO param = MultipartFileUtil.parse(request);
        MultiFileResponse responseDTO = fileService.uploadMultipartFile(param);
        return new JSONResponse(1).putAllData(new BeanMap(responseDTO));
    }

    /**
     * 通过MD5获取某个文件的上传情况
     * 1、已上传过，直接返回URL，秒传
     * 2、部分块已上传，返回未上传的块编号，断点续传
     * 3、未上传，整个文件上传
     */
    @RequestMapping(value = "/getFileUploadInfo", method = RequestMethod.GET)
    public JSONResponse getFileUploadInfo(@RequestParam("md5") String md5) {
        FileUploadInfo fileUploadInfo = fileService.getUnCompleteParts(md5);
        return new JSONResponse(1).putAllData(new BeanMap(fileUploadInfo));
    }

}
