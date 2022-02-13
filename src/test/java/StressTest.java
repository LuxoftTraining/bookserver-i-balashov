import org.junit.jupiter.api.Test;
import org.loadtest4j.LoadTester;
import org.loadtest4j.Request;
import org.loadtest4j.Result;
import org.loadtest4j.factory.LoadTesterFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StressTest {
    private static final LoadTester loadTester = LoadTesterFactory.getLoadTester();

    @Test
    public void accessUrlLike() {
        List<Request> requests = Arrays.asList(Request.get("/books/search/like")
                .withHeader("Accept", "application/json")
                .withQueryParam("by", "Book164275568 AuthorSurname1674355847"));

        Result result = loadTester.run(requests);

        System.out.println("Median request time: "+
                result.getResponseTime().getMedian());
        System.out.println("Max request time: "+
                result.getResponseTime().getMax());
        System.out.println("OK %: "+
                result.getPercentOk());
        System.out.println("OK request number: "+
                result.getDiagnostics().getRequestCount().getOk());
        System.out.println("Total request number: "+
                result.getDiagnostics().getRequestCount().getTotal());
        System.out.println("Throughput (requests/sec): "+
                result.getDiagnostics().getRequestsPerSecond());
        System.out.println("Percentile 90: "+
                result.getResponseTime().getPercentile(90));
        System.out.println("Percentile 95: "+
                result.getResponseTime().getPercentile(95));
        System.out.println("Percentile 99: "+
                result.getResponseTime().getPercentile(99));

        assertThat(result.getResponseTime().getPercentile(90))
                .isLessThanOrEqualTo(Duration.ofMillis(6000));
    }

    @Test
    public void accessUrlJoin() {
        List<Request> requests = Arrays.asList(Request.get("/books/search/join")
                .withHeader("Accept", "application/json")
                .withQueryParam("by", "Book164275568 AuthorSurname1674355847"));

        Result result = loadTester.run(requests);

        System.out.println("Median request time: "+
                result.getResponseTime().getMedian());
        System.out.println("Max request time: "+
                result.getResponseTime().getMax());
        System.out.println("OK %: "+
                result.getPercentOk());
        System.out.println("OK request number: "+
                result.getDiagnostics().getRequestCount().getOk());
        System.out.println("Total request number: "+
                result.getDiagnostics().getRequestCount().getTotal());
        System.out.println("Throughput (requests/sec): "+
                result.getDiagnostics().getRequestsPerSecond());
        System.out.println("Percentile 90: "+
                result.getResponseTime().getPercentile(90));
        System.out.println("Percentile 95: "+
                result.getResponseTime().getPercentile(95));
        System.out.println("Percentile 99: "+
                result.getResponseTime().getPercentile(99));

        assertThat(result.getResponseTime().getPercentile(90))
                .isLessThanOrEqualTo(Duration.ofMillis(6000));
    }
}
