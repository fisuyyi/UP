package com.example.springmodels.repos;

import com.example.springmodels.models.*;
import org.springframework.data.repository.CrudRepository;

public interface InstrumentRepository extends CrudRepository<Instrument, Long> {
}