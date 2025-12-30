package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlCheck {
    int id;
    int statusCode;
    String title;
    String h1;
    StringBuilder description;
    int urlId;
    Timestamp createdAt;

    public UrlCheck(int urlId, Timestamp createdAt) {
        this.urlId = urlId;
        this.createdAt = createdAt;
    }

}
