package ru.ibalashov.measure;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

@RestController
public class MeasurementsController {
    private MeasureService measureService;

    public MeasurementsController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @GetMapping("measurements")
    public Map<String, Measurement> getMeasurementsBaseline() {
        Measurement baseline = measureService.getBaseline();
        Map<String, Measurement> measurements = measureService.measurements;
        if (baseline !=null && baseline.getLatency() != 0) {
            for (Measurement measurement : measurements.values()) {
                double d = Math.round(10000d * measurement.getLatency() / baseline.getLatency());
                measurement.setPercent(d/100);

                NumberFormat nf = NumberFormat.getInstance(Locale.US);
                nf.setMaximumFractionDigits(2);
                nf.setGroupingUsed(false);
                double b = 1d * baseline.getLatency() / measurement.getLatency();
                System.out.println(b+ ":"+ nf.format(b));
                measurement.setBoost(nf.format(b));
            }
        }
        return measurements;
    }

    @GetMapping("measurements/clear")
    public String getMeasurements() {
        measureService.measurements.clear();
        return "succeed";
    }

    @GetMapping("measurements/{name}")
    public Measurement getMeasurements(@PathVariable String name) {
        return measureService.measurements.get(name);
    }
}