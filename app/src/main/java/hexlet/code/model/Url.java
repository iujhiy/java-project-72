package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Url {
    int id;
    String name;
    Timestamp createdAt;

    public Url(String name) {
        this.name = name;
    }

    public Url() { }
}
