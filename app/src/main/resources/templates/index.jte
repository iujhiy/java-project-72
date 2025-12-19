@import hexlet.code.util.NamedRoutes

@template.layout.page(
    content = @`
        <form action="${NamedRoutes.urlsPath()}" method="post">
            <div class="form-group">
                <label>
                    <input type="url" name="url" required placeholder="https://example.com">
                </label>
                <button type="submit" name="submitButton">Добавить</button>
            </div>
        </form>
    `
)