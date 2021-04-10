package com.project.marco.services;

import jxl.write.WriteException;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public interface CreateSpreadsheetService {
    HttpStatus createSpreadsheet() throws IOException, WriteException;
}
