@import hexlet.code.dto.checks.UrlChecksPage
@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@import hexlet.code.util.UrlStringUtils

@param UrlPage urlPage
@param UrlChecksPage urlChecksPage

@template.layout.page(
    page = urlChecksPage,
    content = @`
    <div class="flex-grow-1">
        <section>
            <div class="container-lg mt-5">
                <h1 class="mb-3 p-0">
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
                            <td>${UrlStringUtils.getCreatedAtAsString(urlPage.getUrl().getCreatedAt())}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <h2>
                    Проверки:
                </h2>
                <form action="${NamedRoutes.urlChecksPath(urlPage.getUrl().getId())}" method="post">
                    <div class="form-group">
                        <button type="submit" name="submitButton" class="btn btn-primary">Запустить проверку</button>
                    </div>
                </form>
                <div class="row mt-4">
                    <div class="table-responsive table table-hover">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th class="w-10">ID</th>
                                    <th class="w-10">Код ответа</th>
                                    <th class="w-20">title</th>
                                    <th class="w-10">h1</th>
                                    <th class="w-30">description</th>
                                    <th class="w-20">Дата проверки</th>
                                </tr>
                            </thead>
                            <tbody>
                                @if(urlChecksPage.getUrlChecks() != null)
                                    @for(var urlCheck: urlChecksPage.getUrlChecks())
                                        <tr>
                                            <td>${urlCheck.getId()}</td>
                                            <td>${urlCheck.getStatusCode()}</td>
                                            <td>${urlCheck.getTitle()}</td>
                                            <td>${urlCheck.getH1()}</td>
                                            <td>${urlCheck.getDescription().toString()}</td>
                                            <td>${UrlStringUtils
                                                    .getCreatedAtAsString(urlCheck.getCreatedAt())}
                                            </td>
                                        </tr>
                                    @endfor
                                @endif
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>
    </div>
    `
)
