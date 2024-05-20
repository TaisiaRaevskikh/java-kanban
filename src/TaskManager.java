import java.util.HashMap;

public class TaskManager {

    private static int taskId = 0;
    private static HashMap<Integer, Task> tasks = new HashMap<>();
    private static HashMap<Integer, Epic> epicTasks = new HashMap<>();
    private static HashMap<Integer, Subtask> subTasks = new HashMap<>();


    public static int getNextId() {
        taskId++;
        return taskId;
    }

    //getters
    public static HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public static HashMap<Integer, Epic> getEpics() {
        return epicTasks;
    }

    public static HashMap<Integer, Subtask> getSubtasks() {
        return subTasks;
    }

    //deletes
    public static void deleteAllTasks() {
        tasks.clear();
    }

    public static void deleteAllEpics() {
        epicTasks.clear();
    }

    public static void deleteAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epicTasks.values()) {
            deleteSubtasksByEpicId(epic.getId());
        }
    }

    public static void deleteSubtasksByEpicId(int epicId) {
        Epic epic = getEpicById(epicId);
        HashMap<Integer, Subtask> epicSubtasks = epic.getSubTasks();
        for (Subtask subtask : epicSubtasks.values()) {
            deleteSubTaskById(subtask.getId());
        }
        updateEpicStatus(epic);
    }

    //get by id
    public static Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    public static Epic getEpicById(int epicId) {
        return epicTasks.get(epicId);
    }

    public static Subtask getSubtaskById(int subTaskId) {
        return subTasks.get(subTaskId);
    }

    //create
    public static void createTask(String name, String description) {
        Task newTask = new Task(name, description);
        tasks.put(newTask.getId(), newTask);
    }

    public static void createEpic(String name, String description) {
        Epic newEpic = new Epic(name, description);
        epicTasks.put(newEpic.getId(), newEpic);
    }

    public static void createSubtask(String name, String description, Epic epic) {
        Subtask newSubtask = new Subtask(name, description, epic.getId());
        subTasks.put(newSubtask.getId(), newSubtask);
        epic.addSubtask(newSubtask);
        updateEpicStatus(epic);
    }

    //deletes by id
    public static void deleteTaskById(int taskId) {
        tasks.remove(taskId);
    }

    public static void deleteEpicById(int epicId) {
        deleteSubtasksByEpicId(epicId);
        epicTasks.remove(epicId);
    }

    public static void deleteSubTaskById(int subTaskId) {
        Subtask subtask = getSubtaskById(subTaskId);
        int epicID = subtask.getEpicId();
        Epic epic = getEpicById(epicID);
        epic.deleteSubTaskById(subTaskId);
        subTasks.remove(subTaskId);
        updateEpicStatus(epic);
    }

    //updates
    public static void updateTask(Task taskForUpdate) {
        tasks.remove(taskForUpdate.getId());
        tasks.put(taskForUpdate.getId(), taskForUpdate);
    }

    public static void updateEpic(Epic epicForUpdate) {
        epicTasks.remove(epicForUpdate.getId());
        epicTasks.put(epicForUpdate.getId(), epicForUpdate);
    }

    public static void updateSubtask(Subtask subTaskForUpdate) {
        subTasks.remove(subTaskForUpdate.getId());
        subTasks.put(subTaskForUpdate.getId(), subTaskForUpdate);
        int epicId = subTaskForUpdate.getEpicId();
        Epic epic = getEpicById(epicId);
        updateEpicStatus(epic);

    }
    public static void updateEpicStatus(Epic epic) {
        if (getSubTasksFromEpic(epic).isEmpty() || checkEpicSubtasksForStatuses(epic, Status.NEW)) {
            epic.setStatus(Status.NEW);
        } else if (checkEpicSubtasksForStatuses(epic, Status.DONE)) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
        updateEpic(epic);
    }

    public static HashMap<Integer, Subtask> getSubTasksFromEpic(Epic epic) {
        return epic.getSubTasks();
    }

    public static boolean checkEpicSubtasksForStatuses(Epic epic, Status status) {
        for (Subtask subTask : getSubTasksFromEpic(epic).values()) {
            if (subTask.getStatus() != status) {
                return false;
            }
        }
        return true;
    }


}
