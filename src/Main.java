import Service.InMemoryTaskManager;
import TaskModel.Epic;
import TaskModel.Status;
import TaskModel.Subtask;
import TaskModel.Task;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task1 = new Task("Переезд", "Выполнить до июля", Status.NEW);
        Task task2 = new Task("Переезд", "Выполнить до июля", Status.NEW);
        Task task3 = new Task("Вычесать кошку", "Ириску", Status.NEW);

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);

        Epic epic1 = new Epic("Ремонт", "Квартира 35 м");
        Epic epic2 = new Epic("Отпуск", "Июль");

        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        Subtask subtask1Epic1 = new Subtask("Найти бригаду", "Лучшую из лучших",
                                            Status.NEW, epic1.getId());
        Subtask subtask2Epic1 = new Subtask("Купить материалы", "Обои, плитку, краску",
                                            Status.NEW, epic1.getId());
        Subtask subtask1Epic2 = new Subtask("Решить куда ехать", "Россия или заграница",
                                            Status.NEW, epic2.getId());

        taskManager.createSubtask(subtask1Epic1);
        taskManager.createSubtask(subtask2Epic1);
        taskManager.createSubtask(subtask1Epic2);

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());

        task1.setStatus(Status.DONE);
        taskManager.updateTask(task1);
        System.out.println(task1.getName() + " " + task1.getStatus());

        subtask1Epic1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtask1Epic1);
        System.out.println("Подзадача: " + subtask1Epic1.getName() + " " + subtask1Epic1.getStatus());
        System.out.println("Эпик: " + epic1.getName() + " " + epic1.getStatus());

        taskManager.deleteTaskById(task2.getId());
        taskManager.deleteEpicById(epic2.getId());

        taskManager.deleteSubTaskById(subtask1Epic2.getId());


        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());

    }
}
