package com.itomelet.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadVideo(MultipartFile file);

    void deleteVideo(String id);
}
