package de.clique.westwood.example.examplemultitenancy.service;

import de.clique.westwood.example.examplemultitenancy.entity.Data;
import de.clique.westwood.example.examplemultitenancy.repository.DataRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

  private DataRepository dataRepository;

  public DatabaseService(DataRepository dataRepository) {
    this.dataRepository = dataRepository;
  }

  public List<Data> getData() {
    return dataRepository.findAll();
  }

}
