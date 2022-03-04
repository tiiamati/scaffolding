package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

@Service
public class ZipService {

    public String zip(String projeto) {
        String zipDirectory = projeto.concat(".zip");
        ZipUtil.pack(new File(projeto), new File(zipDirectory));
        return zipDirectory;
    }
}
