import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    public void checkIfTaskIdEquals() {
        Task task1 = new Task("Переезд", "Выполнить до июля", Status.NEW);
        inMemoryTaskManager.createTask(task1);
        assertEquals(task1, inMemoryTaskManager.getTaskById(task1.getId()));
    }
}