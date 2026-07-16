import com.google.gson.Gson;
import models.akita;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class JsonParsingTest {
    private static final Gson gson = new Gson();
    private final ClassLoader cl = JsonParsingTest.class.getClassLoader();

    @Test
    void jsonFileParsingImprovedTest() throws Exception {
        try (Reader reader = new InputStreamReader(
                Objects.requireNonNull(cl.getResourceAsStream("akita.json"))
        )) {
            akita actual = gson.fromJson(reader, akita.class);


            Assertions.assertEquals("Article", actual.type);
            Assertions.assertEquals("Американская акита", actual.headline);
            Assertions.assertEquals("ZEN", actual.name);
            Assertions.assertEquals("Американская акита или большая японская собака", actual.description);
            Assertions.assertEquals(9, actual.age.years);
            Assertions.assertEquals(3, actual.age.months);
            Assertions.assertEquals("рыжий", actual.color.primary);
            Assertions.assertEquals("черный", actual.color.secondary);
            Assertions.assertEquals(50, actual.weight.kg);
            Assertions.assertEquals("2026-01-01", actual.weight.lastMeasured);
            Assertions.assertEquals(3, actual.character.temperament.size());
            Assertions.assertEquals("дружелюбный", actual.character.temperament.get(0));
            Assertions.assertEquals("игривый", actual.character.temperament.get(1));
            Assertions.assertEquals("независимый", actual.character.temperament.get(2));
            Assertions.assertEquals("MEDIUM", actual.character.energyLevel);
            Assertions.assertEquals(8, actual.character.affectionLevel);
            Assertions.assertTrue(actual.character.childFriendly);
            Assertions.assertFalse(actual.character.catFriendly);
            Assertions.assertEquals("CAUTIOUS", actual.character.strangerFriendly);
        }
    }
}



