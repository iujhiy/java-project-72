package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class Url {
    int id;
    String name;
    LocalDateTime createdAt;

    public Url(String name) {
        this.name = name;
    }

    public Url() { }
}
