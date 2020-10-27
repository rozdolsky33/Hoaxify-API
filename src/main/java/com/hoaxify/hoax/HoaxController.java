package com.hoaxify.hoax;

import com.hoaxify.entity.Hoax;
import com.hoaxify.service.HoaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HoaxController {

    @Autowired
    HoaxService hoaxService;

    @PostMapping("/hoaxes")
    void createHoax(@RequestBody Hoax hoax){
        hoaxService.save(hoax);
    }
}
