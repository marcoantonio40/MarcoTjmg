package com.project.marco.controller;

import com.project.marco.services.TjmgService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@ApiOperation(value = "TJMG Service")
@RequestMapping("/tjmgMarco")
public class MainController {

	@Autowired
	private TjmgService tjmgService;

	@ApiOperation(value = "Upload PDF do fator de atualização monetária")
	@PostMapping(value = "/upload")
    public HttpStatus upload(@RequestParam("Arquivo de atualização monetária") MultipartFile file) throws IOException {
	    return tjmgService.updloadPdf(file);
    }


}
