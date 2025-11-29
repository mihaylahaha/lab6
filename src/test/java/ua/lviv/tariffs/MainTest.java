package ua.lviv.tariffs;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MainTest {

    @Test
    void main_shouldRunAndExit_whenUserChoosesExitImmediately() {
        String input = "0\n"; // користувач одразу обирає "Вихід"
        InputStream originalIn = System.in;

        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            assertDoesNotThrow(() -> Main.main(new String[]{}));
        } finally {
            System.setIn(originalIn);
        }
    }
}
