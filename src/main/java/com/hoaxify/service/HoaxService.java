package com.hoaxify.service;

import com.hoaxify.entity.Hoax;
import com.hoaxify.repositories.HoaxRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HoaxService {

    HoaxRepository hoaxRepository;

    public HoaxService(HoaxRepository hoaxRepository) {
        super();
        this.hoaxRepository = hoaxRepository;
    }

    public void save(Hoax hoax){
        hoax.setTimestamp(new Date());
        hoaxRepository.save(hoax);
    }
}
