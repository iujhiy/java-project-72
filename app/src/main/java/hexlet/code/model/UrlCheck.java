package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlCheck {
    int id;
    int statusCode = 0;
    String title;
    String h1;
    StringBuilder description;
    int urlId;
    Timestamp createdAt;

    public UrlCheck(int urlId, int statusCode) {
        this.urlId = urlId;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
        this.statusCode = statusCode;
    }

}
