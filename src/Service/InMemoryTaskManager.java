package Service;

import TaskModel.Epic;
import TaskModel.Status;
import TaskModel.Subtask;
import TaskModel.Task;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private int taskId = 0;
    private Map<Integer, Task> tasks = Map.of();
    private Map<Integer, Epic> epicTasks = Map.of();
    private Map<Integer, Subtask> subTasks = Map.of();
    private HistoryManager historyManager = Managers.getDefaultHistory();

    private int getNextId() {
        taskId++;
        return taskId;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateHistory(Task task) {
        historyManager.add(task);
    }

    //getters
    @Override
    public List<Task> getTasks() {
        return new ArrayList<Task>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<Epic>(epicTasks.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<Subtask>(subTasks.values());
    }

    //deletes
    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epicTasks.clear();
        subTasks.clear();
    }

    @Override
    public void deleteAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epicTasks.values()) {
            epic.deleteAllSubTasks();
            updateEpicStatus(epic);
        }
    }

    //get by id

    @Override
    public Task getTaskById(int taskId) {
        updateHistory(tasks.get(taskId));
        return tasks.get(taskId);
    }

    @Override
    public Epic getEpicById(int epicId) {
        updateHistory(epicTasks.get(epicId));
        return epicTasks.get(epicId);
    }

    @Override
    public Subtask getSubtaskById(int subTaskId) {
        updateHistory(subTasks.get(subTaskId));
        return subTasks.get(subTaskId);
    }

    //create
    @Override
    public void createTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(getNextId());
        epicTasks.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        if (epicTasks.containsKey(subtask.getEpicId())) {
            subtask.setId(getNextId());
            subTasks.put(subtask.getId(), subtask);
            Epic epic = epicTasks.get(subtask.getEpicId());
            epic.addSubtask(subtask);
            updateEpicStatus(epic);
        }
    }

    //deletes by id
    @Override
    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void deleteEpicById(int epicId) {
        if (epicTasks.containsKey(epicId)) {
            Epic epic = epicTasks.get(epicId);
            List<Subtask> epicSubtasks = epic.getSubTasks();
            for (Subtask epicSubtask : epicSubtasks) {
                subTasks.remove(epicSubtask.getId());
            }
            epicTasks.remove(epicId);
        }
    }

    @Override
    public void deleteSubTaskById(int subTaskId) {
        if (subTasks.containsKey(subTaskId)) {
            Subtask subtask = subTasks.get(subTaskId);
            int epicID = subtask.getEpicId();
            Epic epic = epicTasks.get(epicID);
            epic.deleteSubTaskById(subTaskId);
            subTasks.remove(subTaskId);
            updateEpicStatus(epic);
        }
    }

    //updates
    @Override
    public void updateTask(Task taskForUpdate) {
        if (tasks.containsKey(taskForUpdate.getId())) {
            tasks.put(taskForUpdate.getId(), taskForUpdate);
        }
    }

    @Override
    public void updateEpic(Epic epicForUpdate) {
        if (epicTasks.containsKey(epicForUpdate.getId())) {
            Epic epic = epicTasks.get(epicForUpdate.getId());
            epic.setName(epicForUpdate.getName());
            epic.setDescription(epicForUpdate.getDescription());
            epicTasks.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateSubtask(Subtask subTaskForUpdate) {
        if (subTasks.containsKey(subTaskForUpdate.getId())) {
            Subtask subtask = subTasks.get(subTaskForUpdate.getId());
            if (subTaskForUpdate.getEpicId() == subtask.getEpicId()) {
                subTasks.put(subtask.getId(), subtask);
                int epicId = subTaskForUpdate.getEpicId();
                Epic epic = getEpicById(epicId);
                updateEpicStatus(epic);
            }
        }
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        int newCount = 0;
        int doneCount = 0;
        List<Subtask> subTasks = epic.getSubTasks();

        for (Subtask subTask : subTasks) {
            if (subTask.getStatus() == Status.NEW) {
                newCount++;
            } else if (subTask.getStatus() == Status.DONE) {
                doneCount++;
            }
        }

        if (subTasks.isEmpty() || newCount == subTasks.size()) {
            epic.setStatus(Status.NEW);
        } else if (doneCount == subTasks.size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public List<Subtask> getSubTasksFromEpic(int epicId) {
        if (epicTasks.containsKey(epicId)) {
            Epic epic = epicTasks.get(epicId);
            return epic.getSubTasks();
        }
        return new ArrayList<Subtask>();
    }
}
