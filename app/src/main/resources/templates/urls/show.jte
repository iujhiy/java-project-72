@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes

@param UrlPage page

@template.layout.page(
    content = @`
        <h1>
            Сайт: ${page.getUrl().getName()}
        </h1>
        <h2>
            Проверки:
        </h2>
        <form action="${NamedRoutes.urlChecksPath(page.getUrl().getId())}" method="post">
            <div class="form-group">
                <button type="submit" name="submitButton">Проверить</button>
            </div>
        </form>
    `
)
