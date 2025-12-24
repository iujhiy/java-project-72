@import hexlet.code.dto.urls.UrlPage

@param UrlPage page

@template.layout.page(
    content = @`
        <h1>
            ${page.getUrl().getName()}
        </h1>
    `
)
