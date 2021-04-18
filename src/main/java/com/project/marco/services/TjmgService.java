package com.project.marco.services;

import org.springframework.http.HttpStatus;

import java.net.MalformedURLException;

public interface TjmgService {

    HttpStatus downloadPdf() throws MalformedURLException;

    HttpStatus readerPdf() ;


}
