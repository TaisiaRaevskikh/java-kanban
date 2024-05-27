import Service.InMemoryTaskManager;
import TaskModel.Epic;
import TaskModel.Status;
import TaskModel.Subtask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    InMemoryTaskManager inMemoryTaskManager;

    @BeforeEach
    void beforeEach() {
        inMemoryTaskManager = new InMemoryTaskManager();
    }


    @Test
    public void checkIfSubtaskIdEquals() {
        Epic epic = new Epic("Ремонт", "Квартира 35 м");
        inMemoryTaskManager.createEpic(epic);
        Subtask subtask = new Subtask("Найти бригаду", "Лучшую из лучших", Status.NEW, epic.getId());
        inMemoryTaskManager.createSubtask(subtask);
        assertEquals(subtask, inMemoryTaskManager.getSubtaskById(subtask.getId()));
    }

    @Test
    public void checkIfCanSubtaskSelfEpic() {
        Epic epic = new Epic("Ремонт", "Квартира 35 м");
        inMemoryTaskManager.createEpic(epic);
        Subtask subtask = new Subtask("Найти бригаду", "Лучшую из лучших", Status.NEW, epic.getId());
        inMemoryTaskManager.createSubtask(subtask);
        assertEquals(subtask.getEpicId(), epic.getId());
    }

}