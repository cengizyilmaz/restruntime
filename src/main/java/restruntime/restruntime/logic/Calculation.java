package restruntime.restruntime.logic;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public class Calculation {
    public BigDecimal add(BigDecimal left, BigDecimal right){return left.add(right);}
    public BigDecimal subtract(BigDecimal left, BigDecimal right){
        return left.subtract(right);
    }
    public BigDecimal multiply(BigDecimal left, BigDecimal right){
        return left.multiply(right);
    }
    public BigDecimal divide(BigDecimal left, BigDecimal right){
        return left.divide(right);
    }
    public ResponseEntity<BigDecimal> sum(BigDecimal left, BigDecimal right){
        return ResponseEntity.ok(add(left,right));
    }
}
