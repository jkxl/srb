package com.atguigu.srb.oss.service;

import java.io.InputStream;

/**
 * @author jiankai
 * @description:
 * @date 2021-11-10 15:14
 */
public interface FileService {

    /**
     * 文件上传至阿里云
     */
    String upload(InputStream inputStream, String module, String fileName);
    /**
     * 根据路径删除文件
     * @author jiankai
     * @date 2021/11/10 15:45
     * @param url 
     */
    void removeFile(String url);
}
