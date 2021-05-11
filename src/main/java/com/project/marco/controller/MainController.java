package com.project.marco.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.marco.services.TjmgService;
import io.swagger.annotations.ApiOperation;
import jxl.write.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.util.calendar.LocalGregorianCalendar;

import java.io.IOException;
import java.text.Format;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@RestController
@ApiOperation(value = "TJMG Service")
@RequestMapping("/tjmgMarco")
public class MainController {

	@Autowired
	private TjmgService tjmgService;

	@ApiOperation(value = "Upload PDF do fator de atualização monetária")
	@PostMapping(value = "/upload")
    public HttpStatus upload(@RequestParam("Arquivo de atualização monetária") MultipartFile file,
							 @RequestParam("Mês e ano do arquivo de atualização monetária")
							 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") Date data) throws IOException {
	    return tjmgService.updloadPdf(file);
    }


}
