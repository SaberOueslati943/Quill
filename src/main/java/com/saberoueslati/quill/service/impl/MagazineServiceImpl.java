package com.saberoueslati.quill.service.impl;

import com.saberoueslati.quill.entity.Magazine;
import com.saberoueslati.quill.repository.MagazineRepository;
import com.saberoueslati.quill.service.MagazineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagazineServiceImpl implements MagazineService {

    private final MagazineRepository magazineRepository;

    public MagazineServiceImpl(MagazineRepository magazineRepository) {
        this.magazineRepository = magazineRepository;
    }

    @Override
    public Magazine addMagazine(Magazine magazine) {
        return magazineRepository.save(magazine);
    }

    @Override
    public List<Magazine> addMagazines(List<Magazine> magazines) {
        return magazineRepository.saveAll(magazines);
    }

    @Override
    public List<Magazine> getAllMagazines() {
        return magazineRepository.findAll();
    }
}
