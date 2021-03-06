package ru.ibalashov.measure;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class MeasureAspect {
    private MeasureService measureService;

    public MeasureAspect(MeasureService measureService) {
        this.measureService = measureService;
    }

    @Around("@annotation(measure)")
    public Object aroundCallAt(ProceedingJoinPoint pjp, Measure measure) throws Throwable {
        long start = System.nanoTime();
        Object retVal = pjp.proceed();
        long time = (System.nanoTime() - start) / 1000;
        boolean baseline = measure.value().equals("baseline") || measure.baseline();
        measureService.addMeasurement(measure.value(), time, measure.warmup(), baseline);
        return retVal;
    }

}
