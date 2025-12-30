package hexlet.code.dto.checks;

import hexlet.code.dto.BasePage;
import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UrlCheckPage extends BasePage {
    private UrlCheck urlCheck;
}
