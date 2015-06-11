package workshop.microservices.weblog.core.internal;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class IdNormalizerBeanTest {

    private static final String SIMPLE_TITLE = "A simple title";
    private static final String SIMPLE_EXPECTED = "a-simple-title";

    private static final String COMPLEX_TITLE = "Ein Spaß mit Sönderzeichen; und Ümlauten?!";
    private static final String COMPLEX_EXPECTED = "ein-spass-mit-soenderzeichen-und-uemlauten";

    private IdNormalizerBean underTest = new IdNormalizerBean();

    @Test
    public void normalizeSimple() throws Exception {
        String result = underTest.normalizeTitle(SIMPLE_TITLE);
        assertThat("normalized title", result, is(SIMPLE_EXPECTED));
    }

    @Test
    public void normalizeComplex() throws Exception {
        String result = underTest.normalizeTitle(COMPLEX_TITLE);
        assertThat("normalized title", result, is(COMPLEX_EXPECTED));
    }
}