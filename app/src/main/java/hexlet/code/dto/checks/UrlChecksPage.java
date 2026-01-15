package hexlet.code.dto.checks;

import hexlet.code.dto.BasePage;
import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class UrlChecksPage extends BasePage {
    private List<UrlCheck> urlChecks;
}
