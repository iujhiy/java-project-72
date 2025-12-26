package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@AllArgsConstructor
public class UrlCheck {
    int id;
    int statusCode;
    String title;
    String h1;
    StringBuilder description;
    int urlId;
    Timestamp created_at;
}
