package de.clique.westwood.example.examplemultitenancy.repository;

import de.clique.westwood.example.examplemultitenancy.entity.Data;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<Data, UUID> {

}
