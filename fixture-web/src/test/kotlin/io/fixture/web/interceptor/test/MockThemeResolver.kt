package io.fixture.web.interceptor.test

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.web.servlet.ThemeResolver

class MockThemeResolver(val name: String?): ThemeResolver {

    public override fun resolveThemeName(request: HttpServletRequest?): String? {
        return name
    }

    public override fun setThemeName(request: HttpServletRequest?, response: HttpServletResponse?, themeName: String?) {
        throw UnsupportedOperationException()
    }

}
