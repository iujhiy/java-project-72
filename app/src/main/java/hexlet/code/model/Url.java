package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class Url {
    int id;
    String name;
    LocalDate createdAt;

    public Url(String name, LocalDate createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
