package com.project.marco.controller;

import com.project.marco.services.CreateSpreadsheetService;
import com.project.marco.services.PatternService;
import com.project.marco.services.TjmgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@ApiOperation(value = "TJMG Service")
@RequestMapping("/tjmgMarco")
public class MainController {

	@Autowired
	private TjmgService tjmgService;

	@Autowired
	private PatternService patternService;

	@Autowired
	private CreateSpreadsheetService createSpreadsheetService;


	@ApiOperation(value = "Upload PDF do fator de atualização monetária")
	@PostMapping(value = "/upload")
    public HttpStatus upload(@RequestParam("Arquivo de atualização monetária") MultipartFile file) throws IOException {
	    return tjmgService.updloadPdf(file);
    }

	@GetMapping(value = "/readerPdf")
	public HttpStatus readerPdf() {
		return tjmgService.readerPdf();
	}

	@GetMapping(value = "/formatToPattern")
	public HttpStatus formatToPattern(){
		return patternService.formatToPattern();
	}

	@GetMapping(value = "/createSpreadsheet")
	public HttpStatus createSpreadsheet() {
		return createSpreadsheetService.createSpreadsheet();
	}




}
