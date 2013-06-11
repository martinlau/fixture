package io.fixture.web.interceptor

import java.net.URLEncoder
import java.util.LinkedHashMap
import java.util.LinkedList
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.dom.addClass
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import org.springframework.web.servlet.support.RequestContextUtils

public class NextThemeInterceptor: HandlerInterceptorAdapter() {

    val themes: List<String> = arrayListOf(
            "amelia",
            "cerulean",
            "cosmo",
            "cyborg",
            "default",
            "journal",
            "readable",
            "shamrock",
            "simplex",
            "slate",
            "spacelab",
            "spruce",
            "superhero",
            "united"
    )

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
