@import hexlet.code.util.NamedRoutes

@template.layout.page(
content = @`
    <section>
        <div class="row bg-dark justify-content-center">
            <div class="col-7 text-left mt-3">
                <h1 class="display-3 text-white">Анализатор страниц</h1>
                <p class="lead text-white">Проверить сайт на SEO - пригодность</p>
                <form action="${NamedRoutes.urlsPath()}" method="post">
                    <div class="row mb-3">
                        <div class="col">
                            <div class="form-floating">
                                <input id="url-form" class="form-control w-100" type="url" name="url" required
                                       placeholder="https://example.com">
                                <label class="d-block" for="url-form">Ссылка</label>
                            </div>
                        </div>
                        <div class="col-auto">
                            <button class="btn btn-md btn-primary px-5 p-3" type="submit"
                                    name="submitButton">Проверить</button>
                        </div>
                        <p class="lead text-muted mt-2 mb-5">Пример: <span
                                class="d-inline-block user-select-all">https://example.com</span></p>
                    </div>
                </form>
            </div>
        </div>
    </section>
`
)