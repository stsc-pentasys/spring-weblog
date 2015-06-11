package workshop.microservices.weblog.core.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import workshop.microservices.weblog.core.IdNormalizer;

/**
 * Implements string normalization.
 */
@Component
public class IdNormalizerBean implements IdNormalizer {

    /**
     * Replacements for german mutations.
     */
    private static final Map<Character, String> REPLACEMENTS = new HashMap<>();

    /**
     * Search for whitespace and special characters.
     */
    public static final Pattern SPECIAL_CHARS = Pattern.compile("[^a-z]+");

    // initialize map on load.
    static {
        REPLACEMENTS.put('ä', "ae");
        REPLACEMENTS.put('ö', "oe");
        REPLACEMENTS.put('ü', "ue");
        REPLACEMENTS.put('ß', "ss");
    }

    /**
     * Normalize with these rules:
     * <ul>
     *     <li>only lowercase characters</li>
     *     <li>replace mutations by equivalents (e.g. @auml; --@gt; ae)</li>
     *     <li>replace whitespace and special characters with '-'</li>
     *     <li>only one consecutive '-'</li>
     *     <li>no trailing '-'</li>
     * </ul>
     *
     * @param title the original string
     * @return the normalized title
     */
    @Override
    public String normalizeTitle(String title) {
        String lowerCase = toLowerCase(title);
        String replacedMutations = replaceMutations(lowerCase);
        return replaceSpecialCharacters(replacedMutations);
    }

    private String toLowerCase(String title) {
        return title.toLowerCase();
    }

    private String replaceMutations(String withMutations) {
        StringBuilder buffer = new StringBuilder();
        for (char c : withMutations.toCharArray()) {
            buffer.append(REPLACEMENTS.containsKey(c) ? REPLACEMENTS.get(c) : c);
        }
        return buffer.toString();
    }

    private String replaceSpecialCharacters(String withSpecialCharacters) {
        StringBuffer buffer = new StringBuffer();
        Matcher m = SPECIAL_CHARS.matcher(withSpecialCharacters);
        while (m.find()) {
            // do not append a trailing '-'
            String replaceWith = (m.hitEnd()) ? "" : "-";
            m.appendReplacement(buffer, replaceWith);
        }
        m.appendTail(buffer);
        return buffer.toString();
    }
}
