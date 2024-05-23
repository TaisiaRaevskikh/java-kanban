import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class  TaskManager {

    private int taskId = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epicTasks = new HashMap<>();
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();


    private int getNextId() {
        taskId++;
        return taskId;
    }

    //getters
    public ArrayList<Task> getTasks() {
        return new ArrayList<Task>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<Epic>(epicTasks.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<Subtask>(subTasks.values());
    }

    //deletes
    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epicTasks.clear();
        subTasks.clear();
    }

    public void deleteAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epicTasks.values()) {
            epic.deleteAllSubTasks();
            updateEpicStatus(epic);
        }
    }

    //get by id
    public Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    public Epic getEpicById(int epicId) {
        return epicTasks.get(epicId);
    }

    public Subtask getSubtaskById(int subTaskId) {
        return subTasks.get(subTaskId);
    }

    //create
    public void createTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(getNextId());
        epicTasks.put(epic.getId(), epic);
    }

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
    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    public void deleteEpicById(int epicId) {
        if (epicTasks.containsKey(epicId)) {
            Epic epic = epicTasks.get(epicId);
            ArrayList<Subtask> epicSubtasks = epic.getSubTasks();
            for (Subtask epicSubtask : epicSubtasks) {
                subTasks.remove(epicSubtask.getId());
            }
            epicTasks.remove(epicId);
        }
    }

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
    public void updateTask(Task taskForUpdate) {
        if (tasks.containsKey(taskForUpdate.getId())) {
            tasks.put(taskForUpdate.getId(), taskForUpdate);
        }
    }

    public void updateEpic(Epic epicForUpdate) {
        if (epicTasks.containsKey(epicForUpdate.getId())) {
            Epic epic = epicTasks.get(epicForUpdate.getId());
            epic.setName(epicForUpdate.getName());
            epic.setDescription(epicForUpdate.getDescription());
            epicTasks.put(epic.getId(), epic);
        }
    }

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

    public void updateEpicStatus(Epic epic) {
        int newCount = 0;
        int doneCount = 0;
        ArrayList<Subtask> subTasks = epic.getSubTasks();

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

    public List<Subtask> getSubTasksFromEpic (int epicId) {
        if (epicTasks.containsKey(epicId)) {
            Epic epic = epicTasks.get(epicId);
            return epic.getSubTasks();
        }
        return null;
    }
}
