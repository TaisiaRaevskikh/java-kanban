import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public HashMap<Integer, Subtask> getSubTasks() {
        return subTasks;
    }

    public void addSubtask(Subtask subtask) {
        subTasks.put(subtask.getId(), subtask);
    }

    public void deleteSubTaskById(int subTaskId) {
        subTasks.remove(subTaskId);
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
        return "Epic{" +
                "subtasks=" + subTasks +
                "} " + super.toString();
    }

}
