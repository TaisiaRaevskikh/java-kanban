import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    static InMemoryTaskManager taskManager;
    static Task task1;
    static Epic epic1;
    static Subtask subtask1Epic1;

    @BeforeAll
    static void beforeAll() {
        taskManager = new InMemoryTaskManager();
        task1 = new Task("Переезд", "Выполнить до июля", Status.NEW);
        taskManager.createTask(task1);
        epic1 = new Epic("Ремонт", "Квартира 35 м");
        taskManager.createEpic(epic1);
        subtask1Epic1 = new Subtask("Найти бригаду", "Лучшую из лучших",
                Status.NEW, epic1.getId());
        taskManager.createSubtask(subtask1Epic1);
    }

    @Test
    void checkIfCanAddDiffTypes() {
        taskManager.getTaskById(task1.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getSubtaskById(subtask1Epic1.getId());
        assertEquals(taskManager.getHistory().size(), 3);

    }

    @Test
    void checkIfcanGetTaskById() {
        assertEquals(taskManager.getTaskById(task1.getId()), task1);
    }

    @Test
    void checkIfcanGetEpicById() {
        assertEquals(taskManager.getEpicById(epic1.getId()), epic1);
    }

    @Test
    void checkIfcanGetSubTaskById() {
        assertEquals(taskManager.getSubtaskById(subtask1Epic1.getId()), subtask1Epic1);
    }

    @Test
    void checkAllFieldsAreSame() {
        String[] arrayOne = new String[]{task1.getName(), task1.getDescription(), task1.getStatus().toString(),
                String.valueOf(task1.getId())};
        Task task2 = taskManager.getTaskById(task1.getId());
        String[] arrayTwo = new String[]{task2.getName(), task2.getDescription(), task2.getStatus().toString(),
                String.valueOf(task2.getId())};

        assertArrayEquals(arrayTwo, arrayOne, "задачи не равны!");
    }

    @Test
    void checkHistoryHoldPrevious() {
        ArrayList<Task> history = taskManager.getHistory();
        task1.setName("newName");
        Task oldTask = history.getFirst();
        assertNotEquals(task1, oldTask);

    }
}