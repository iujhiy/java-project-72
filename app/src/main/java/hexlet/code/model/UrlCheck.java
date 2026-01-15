package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlCheck {
    int id;
    int statusCode = 0;
    String title = "";
    String h1 = "";
    StringBuilder description = new StringBuilder();
    int urlId;
    LocalDateTime createdAt;

    public UrlCheck(int urlId, int statusCode) {
        this.urlId = urlId;
        this.createdAt = LocalDateTime.now();
        this.statusCode = statusCode;
    }

    public UrlCheck(int urlId) {
        this.urlId = urlId;
    }

}
