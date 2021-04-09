package com.project.marco.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ConfigProperties {

    @Value("${file.atualizacao.pdf}")
    private String filePdf;

    @Value("${file.atualizacao.txt}")
    private String fileTxt;


}
