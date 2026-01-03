@import hexlet.code.dto.checks.UrlChecksPage
@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@import hexlet.code.util.UrlStringUtils;

@param UrlPage urlPage
@param UrlChecksPage urlChecksPage

@template.layout.page(
    page = urlChecksPage,
    content = @`
        <h1>
            Сайт: ${urlPage.getUrl().getName()}
        </h1>
    <div class="table-responsive table table-hover">
        <table class="table table-bordered">
            <tbody>
            <tr>
                <td>ID</td>
                <td>${urlPage.getUrl().getId()}</td>
            </tr>
            <tr>
                <td>Имя</td>
                <td>${urlPage.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>Дата создания</td>
                <td>${UrlStringUtils.getCreatedAtAsString(urlPage.getUrl().getCreatedAt())}</td>
            </tr>
            </tbody>
        </table>
    </div>
        <h2>
            Проверки:
        </h2>
        <form action="${NamedRoutes.urlChecksPath(urlPage.getUrl().getId())}" method="post">
            <div class="form-group">
                <button type="submit" name="submitButton">Проверить</button>
            </div>
        </form>
        @if (urlChecksPage != null)
            @for (var urlCheck: urlChecksPage.getUrlChecks())
                <p>${urlCheck.getId()}</p>
                <p>${urlCheck.getStatusCode()}</p>
            @endfor
        @endif
    `
)
