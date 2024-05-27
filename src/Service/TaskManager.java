package Service;

import TaskModel.Epic;
import TaskModel.Subtask;
import TaskModel.Task;

import java.util.List;

public interface TaskManager {

    List<Task> getHistory();

    List<Task> getTasks();

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubTasks();

    Task getTaskById(int taskId);

    Epic getEpicById(int epicId);

    Subtask getSubtaskById(int subTaskId);

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    void deleteTaskById(int taskId);

    void deleteEpicById(int epicId);

    void deleteSubTaskById(int subTaskId);

    void updateTask(Task taskForUpdate);

    void updateEpic(Epic epicForUpdate);

    void updateSubtask(Subtask subTaskForUpdate);

    void updateEpicStatus(Epic epic);

    List<Subtask> getSubTasksFromEpic(int epicId);

}
