package TaskModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
    }

    public ArrayList<Subtask> getSubTasks() {
        return new ArrayList<Subtask>(subTasks.values());
    }

    public void addSubtask(Subtask subtask) {
        subTasks.put(subtask.getId(), subtask);
    }

    public void deleteSubTaskById(int subTaskId) {
        subTasks.remove(subTaskId);
    }

    public void deleteAllSubTasks() {
        subTasks.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!super.equals(o)) return false;
        if (getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTasks, epic.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }

    @Override
    public String toString() {
        return "TaskModel.Epic{" +
                "subtasks=" + subTasks +
                "} " + super.toString();
    }

}
