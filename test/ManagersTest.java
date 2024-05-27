import Service.HistoryManager;
import Service.Managers;
import Service.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    TaskManager taskManager;
    HistoryManager historyManager;

    @Test
    void checkIfInitializedManagers() {
        taskManager = Managers.getDefault();
        assertNotNull(taskManager);
    }

    @Test
    void checkIfInitializedHistManagers() {
        historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager);
    }
}