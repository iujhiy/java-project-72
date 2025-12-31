package hexlet.code.dto.checks;

import hexlet.code.dto.BasePage;
import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
@AllArgsConstructor
public class UrlChecksPage extends BasePage {
    private ArrayList<UrlCheck> urlChecks;
}
