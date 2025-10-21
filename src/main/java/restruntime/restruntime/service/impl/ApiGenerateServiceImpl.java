package restruntime.restruntime.service.impl;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import restruntime.restruntime.logic.Calculation;
import restruntime.restruntime.service.ApiGenerateService;
import restruntime.restruntime.valuehandler.GenericReturnValueHandler;
import restruntime.restruntime.wrap.WrapMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ApiGenerateServiceImpl implements ApiGenerateService {
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final RequestMappingHandlerAdapter handlerAdapter;

    private final Calculation calculation;
    public ApiGenerateServiceImpl(RequestMappingHandlerMapping requestMappingHandlerMapping, RequestMappingHandlerAdapter handlerAdapter) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.handlerAdapter = handlerAdapter;
        calculation = new Calculation();
        List<HandlerMethodReturnValueHandler> handlerReturnValueList = handlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> newHandlerValueList = new ArrayList<>(handlerReturnValueList);
        newHandlerValueList.add(new GenericReturnValueHandler());
        handlerAdapter.setReturnValueHandlers(newHandlerValueList);

    }

    @Override
    public String generate() {
        Class<Calculation> clazz = Calculation.class;
        for (Method method : clazz.getDeclaredMethods()) {
            String path =
                    "%s/%s/**".formatted(clazz.getSimpleName().toLowerCase(), method.getName());
            RequestMappingInfo.Builder requestMappingInfoBuilder =
                    RequestMappingInfo
                            .paths(path)
                            .methods(RequestMethod.POST)
                            .mappingName(method.getName())
                            .produces(MediaType.APPLICATION_JSON.toString());


            requestMappingHandlerMapping.registerMapping(
                    requestMappingInfoBuilder.build(), calculation,  method);
        }

        return "OK";
    }

    @Override
    public String wrap() throws NoSuchMethodException {
        Class<Calculation> clazz = Calculation.class;
        Method wrapMethod = WrapMethod.class.getMethod("handle", Map.class);
        for (Method method : clazz.getDeclaredMethods()) {
            String path =
                    "%s/%s/%s/**".formatted("wrap",clazz.getSimpleName().toLowerCase(), method.getName());
            RequestMappingInfo.Builder requestMappingInfoBuilder =
                    RequestMappingInfo
                            .paths(path)
                            .methods(RequestMethod.POST)
                            .mappingName(method.getName())
                            .produces(MediaType.APPLICATION_JSON.toString());

            WrapMethod<Calculation> wrapObject = new WrapMethod<>(method,calculation);
            requestMappingHandlerMapping.registerMapping(
                    requestMappingInfoBuilder.build(), wrapObject,  wrapMethod);
        }

        return "OK";
    }
}
