package org.telegram.tomatophile.canuaffordtosmoke.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegram.tomatophile.canuaffordtosmoke.entity.Cigarette;

import java.util.List;

@Repository
public interface CigaretteRepo extends JpaRepository<Cigarette, Integer> {
    public List<Cigarette> findAllByPriceBetween(float minPrice, float maxPrice);
    public Cigarette findByName(String name);
}
