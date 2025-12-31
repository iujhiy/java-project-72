package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter @Setter
public class Url {
    int id;
    String name;
    Timestamp createdAt;

    public Url(String name) {
        this.name = name;
    }

    public Url() { }

    public String getCreatedAtAsString() {
        if (createdAt == null) {
            return "";
        }
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(createdAt);
    }
}
