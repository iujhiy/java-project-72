@import hexlet.code.dto.checks.UrlChecksPage
@import hexlet.code.dto.urls.UrlsPage
@import gg.jte.Content
@import hexlet.code.util.NamedRoutes

@param UrlsPage urlPage
@param UrlChecksPage urlChecksPage
@param Content content

@template.layout.page(
page = urlPage,
content = @`
    <div class="section">
        <div class="container">
            <h1 class="mt-4 mb-2">Сайты</h1>
            <div class="row">
                <div class="table-responsive table table-hover">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th scope="col" class="col-1">ID</th>
                            <th scope="col">Имя</th>
                            <th scope="col" class="col-2">Последняя проверка</th>
                            <th scope="col" class="col-1">Код ответа</th>
                        </tr>
                        </thead>
                        <tbody>
                        @if (urlPage.getUrls() != null)
                            @for(var url: urlPage.getUrls())
                                <tr>
                                    <td>${url.getId()}</td>
                                    <td>
                                        <a href="${NamedRoutes.urlPath(url.getId())}">
                                            ${url.getName()}
                                        </a>
                                    </td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            @endfor
                        @endif
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

`
)
