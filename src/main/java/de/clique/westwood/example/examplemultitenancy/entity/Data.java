package de.clique.westwood.example.examplemultitenancy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Data {

  @Id
  private UUID id;
  private String value;

  public Data(){
  }

  public Data(UUID id, String value) {
    this.id = id;
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Data data = (Data) o;
    return Objects.equals(id, data.id) && Objects.equals(value, data.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, value);
  }

  @Override
  public String toString() {
    return "Data{" +
        "id=" + id +
        ", value='" + value + '\'' +
        '}';
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
