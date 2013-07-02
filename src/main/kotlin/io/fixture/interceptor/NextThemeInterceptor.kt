package io.fixture.interceptor

import java.net.URLEncoder
import java.util.LinkedHashMap
import java.util.LinkedList
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.dom.addClass
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import org.springframework.web.servlet.support.RequestContextUtils
import org.springframework.core.io.support.ResourcePatternUtils
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.util.Collections

public class NextThemeInterceptor: HandlerInterceptorAdapter() {

    val themes: List<String>

    {
        val resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(PathMatchingResourcePatternResolver())
        val resources = resourcePatternResolver.getResources("classpath*:/META-INF/resources/webjars/bootswatch/2.3.1/**/bootstrap.min.css")
        val unsorted: MutableList<String> = resources.mapTo(LinkedList()) { it.createRelative(".").getFilename()!! }

        themes = unsorted.sort()
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        val theme = RequestContextUtils.getThemeResolver(request)?.resolveThemeName(request)
        if (theme != null) {

            val nextTheme = if (theme == themes.last) themes.first
            else themes.get(themes.indexOf(theme) + 1)

            request.setAttribute("nextTheme", nextTheme)
        }

        return true;
    }

}
