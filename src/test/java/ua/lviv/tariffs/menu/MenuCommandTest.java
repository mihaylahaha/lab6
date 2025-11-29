package ua.lviv.tariffs.menu;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuCommandTest {

    @Test
    void execute_shouldRunLambdaBody() {
        AtomicBoolean executed = new AtomicBoolean(false);

        MenuCommand command = () -> executed.set(true);

        command.execute();

        assertTrue(executed.get());
    }
}
