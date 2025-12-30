@import hexlet.code.dto.checks.UrlCheckPage
@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes

@param UrlPage urlPage
@param UrlCheckPage urlCheckPage = null

@template.layout.page(
    page = urlCheckPage,
    content = @`
        <h1>
            Сайт: ${urlPage.getUrl().getName()}
        </h1>
        <h2>
            Проверки:
        </h2>
        <form action="${NamedRoutes.urlChecksPath(urlPage.getUrl().getId())}" method="post">
            <div class="form-group">
                <button type="submit" name="submitButton">Проверить</button>
            </div>
        </form>
        @if (urlCheckPage != null)
            <p>${urlCheckPage.getUrlCheck().getId()}</p>
        @endif
    `
)
