package org.telegram.tomatophile.canuaffordtosmoke.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.tomatophile.canuaffordtosmoke.entity.Cigarette;
import org.telegram.tomatophile.canuaffordtosmoke.repo.CigaretteRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CigaretteService {

    private final CigaretteRepo cigaretteRepo;

    public List<Cigarette> findAllInRange(int minPrice, int maxPrice){
        return cigaretteRepo.findAllByPriceBetween(minPrice, maxPrice);
    }

    public Cigarette findByName(String name){
        return cigaretteRepo.findByName(name);
    }

    public void save(Cigarette cigarette){
        cigaretteRepo.save(cigarette);
    }
}
