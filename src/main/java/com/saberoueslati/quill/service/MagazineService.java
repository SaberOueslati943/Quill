package com.saberoueslati.quill.service;

import com.saberoueslati.quill.entity.Magazine;

import java.util.List;

public interface MagazineService {
    Magazine addMagazine(Magazine magazine);
    List<Magazine> getAllMagazines();
}
