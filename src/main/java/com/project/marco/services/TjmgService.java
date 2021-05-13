package com.project.marco.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public interface TjmgService {

    HttpStatus updloadPdf(MultipartFile file, int anoDoc, int mesDoc, int anoInicioProcesso, int mesInicioProcesso ) throws IOException;

    HttpStatus readerPdf(String fileName, int anoDoc, int mesDoc, int anoInicioProcesso, int mesInicioProcesso) ;


}
