public class Main {

    public static void main(String[] args) {

        TaskManager.createTask("Переезд", "Выполнить до июля");
        TaskManager.createTask("Вычесать кошку", "Ириску");
        TaskManager.createEpic("Ремонт", "Квартира 35 м");
        TaskManager.createEpic("Отпуск", "Июль");
        Epic epic1 = TaskManager.getEpicById(3);
        Epic epic2 = TaskManager.getEpicById(4);
        TaskManager.createSubtask("Найти бригаду", "Лучшую из лучших", epic1);
        TaskManager.createSubtask("Купить материалы", "Обои, плитку, краску", epic1);
        TaskManager.createSubtask("Решить куда ехать", "Россия или заграницу", epic2);

        System.out.println(TaskManager.getTasks().values());
        System.out.println(TaskManager.getEpics().values());
        System.out.println(TaskManager.getSubtasks().values());

        Task task1 = TaskManager.getTaskById(1);
        task1.setStatus(Status.DONE);
        TaskManager.updateTask(task1);
        System.out.println(task1.getName() + " " +  task1.getStatus());

        Subtask subtask1 = TaskManager.getSubtaskById(5);
        subtask1.setStatus(Status.IN_PROGRESS);
        TaskManager.updateSubtask(subtask1);
        System.out.println("Подзадача: " + subtask1.getName() + " " +  subtask1.getStatus());
        System.out.println("Эпик: " + epic1.getName() + " " +   epic1.getStatus());

        TaskManager.deleteTaskById(2);
        TaskManager.deleteEpicById(4);

        TaskManager.deleteSubTaskById(6);


        System.out.println(TaskManager.getTasks().values());
        System.out.println(TaskManager.getEpics().values());
        System.out.println(TaskManager.getSubtasks().values());

    }
}
