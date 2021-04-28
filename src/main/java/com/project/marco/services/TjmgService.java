package com.project.marco.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;

public interface TjmgService {

    HttpStatus updloadPdf(MultipartFile file) throws MalformedURLException;

    HttpStatus readerPdf() ;


}
