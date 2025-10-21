package restruntime.restruntime.valuehandler;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;


public class GenericReturnValueHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return BigDecimal.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception
    {
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        ServletServerHttpResponse outputResponse = new ServletServerHttpResponse(response);
        byte[] out = ((BigDecimal)returnValue).toString().getBytes(StandardCharsets.UTF_8);
        StreamUtils.copy(out,outputResponse.getBody());
        outputResponse.flush();
    }
}
