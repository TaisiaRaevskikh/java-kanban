import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    public void checkIfEpicIdEquals() {
        Epic epic = new Epic("Ремонт", "Квартира 35 м");
        inMemoryTaskManager.createEpic(epic);
        assertEquals(epic, inMemoryTaskManager.getEpicById(epic.getId()));
    }
}