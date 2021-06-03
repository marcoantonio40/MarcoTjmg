package com.project.marco.services;

import org.springframework.http.HttpStatus;

public interface PatternService {

    HttpStatus formatToPattern(int anoDoc, int mesDoc, int anoInicioProcesso, int mesInicioProcesso);
}
