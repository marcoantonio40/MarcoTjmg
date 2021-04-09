package com.project.marco.controller;

import com.project.marco.services.PatternService;
import com.project.marco.services.TjmgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/tjmgMarco")
public class MainController {

	@Autowired
	private TjmgService tjmgService;

	@Autowired
	private PatternService patternService;
	
	@GetMapping(value = "/download")
    public HttpStatus download() throws IOException {
	    return tjmgService.downloadPdf();
    }

	@GetMapping(value = "/readerPdf")
	public HttpStatus readerPdf() {
		return tjmgService.readerPdf();
	}

	@GetMapping(value = "/formatToPattern")
	public HttpStatus formatToPattern(){
		return patternService.formatToPattern();
	}


}
