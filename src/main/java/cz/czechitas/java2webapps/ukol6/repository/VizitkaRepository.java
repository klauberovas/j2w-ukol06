package cz.czechitas.java2webapps.ukol6.repository;

import cz.czechitas.java2webapps.ukol6.entity.Vizitka;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VizitkaRepository extends CrudRepository<Vizitka,Integer> {
    List<Vizitka> getById(Integer id);
}
