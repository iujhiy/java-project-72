@import hexlet.code.dto.checks.UrlChecksPage
@import hexlet.code.dto.urls.UrlsPage
@import gg.jte.Content
@import hexlet.code.util.NamedRoutes
@import hexlet.code.util.UrlStringUtils

@param UrlsPage urlPage
@param UrlChecksPage lastChecksPage
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
                                    @if (lastChecksPage.getUrlChecks() != null)
                                        @for(var urlCheck: lastChecksPage.getUrlChecks())
                                            @if (url.getId() == urlCheck.getUrlId())
                                                <td>
                                                    ${UrlStringUtils.getCreatedAtAsString(
                                                        urlCheck.getCreatedAt()
                                                    )}
                                                </td>
                                                <td>${urlCheck.getStatusCode()}</td>
                                                break;
                                            @endif
                                        @endfor
                                    @else
                                        <td></td>
                                        <td></td>
                                    @endif
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
