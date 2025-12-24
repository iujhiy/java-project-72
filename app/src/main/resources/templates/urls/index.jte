@import hexlet.code.dto.urls.UrlsPage
@import gg.jte.Content
@import hexlet.code.util.NamedRoutes

@param UrlsPage page
@param Content content

@template.layout.page(
    page = page, 
    content = @`
        @if (page.getUrls() == null)
            <p>Список URL пуст</p>
        @else
            @for(var url: page.getUrls())
                <a href=${NamedRoutes.urlPath(url.getId())}>
                    <h2>
                        ${url.getId() + ": " + url.getName()}
                    </h2>
                </a>
            @endfor
        @endif
    `
)
