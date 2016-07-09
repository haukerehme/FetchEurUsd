package de.hrs.repository;

import de.hrs.model.Eurusd;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by hrs on 21.06.16.
 */
@org.springframework.stereotype.Repository
public interface EurUsdRepository extends CrudRepository<Eurusd, Long> {
    List<Eurusd> findAll();
}
