package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.service.tavolo.TavoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/gestionetavolo")
public class GestioneTavoloController {

    @Autowired
    private TavoloService tavoloService;

}
