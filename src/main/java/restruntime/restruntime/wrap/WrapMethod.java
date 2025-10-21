package restruntime.restruntime.wrap;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public class WrapMethod<T> {
    private final Method method;
    private final T object;

    public WrapMethod(Method method, T object) {
        this.method = method;
        this.object = object;
    }
   public <R> ResponseEntity<R> handle(@RequestParam  Map<String, String> queryMap) throws InvocationTargetException, IllegalAccessException {
        return (ResponseEntity<R>) ResponseEntity.ok(method.invoke(this.object, queryMap.values().stream().map(item -> new BigDecimal(item)).collect(Collectors.toList()).toArray()));
    }
}
