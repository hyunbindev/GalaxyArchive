package com.hyunbindev.api.support.resolver

import com.hyunbindev.common.auth.VisitorId
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.UUID

@Component
class VisitorIdArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(VisitorId::class.java)
    }

    private val logger = LoggerFactory.getLogger(VisitorIdArgumentResolver::class.java)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): UUID? {
        val annotation = parameter.getParameterAnnotation(VisitorId::class.java)

        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)

        val visitorId: UUID? = request?.cookies
            ?.find { it.name == "GAL_VISITOR" }
            ?.value
            ?.let{
                try {
                    UUID.fromString(it)
                }catch(e: IllegalArgumentException){
                    logger.warn("fail to parse visitorId to UUID : {}",e.message)
                    null
                }
            }

        return if(annotation?.required == true){
            visitorId?: throw IllegalArgumentException("VisitorId is required")
        } else {
            visitorId
        }
    }
}