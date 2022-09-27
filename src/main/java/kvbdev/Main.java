package kvbdev;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    final static int MAX_THREADS = 4;
    final static int MAX_TASKS = 10;
    final static int TASK_ITERATIONS = 5;

    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);

        List<MyCallable> taskList = IntStream
                .range(0, MAX_TASKS)
                .mapToObj(i -> new MyCallable("MyCallable" + i, TASK_ITERATIONS))
                .collect(Collectors.toList());

        System.out.println(".submit() example:");
        submitExample(threadPool, taskList);

        System.out.println(".invokeAny() example:");
        invokeAnyExample(threadPool, taskList);

        threadPool.shutdown();
    }

    public static void submitExample(ExecutorService threadPool, List<MyCallable> taskList) throws Exception {
        System.out.println("Создаю потоки задач...");
        Map<MyCallable, Future<Integer>> map = new HashMap<>();
        taskList.forEach(myCallable -> map.put(myCallable, threadPool.submit(myCallable)));

        System.out.println("Результаты выполнения задач:");
        for (Map.Entry<MyCallable, Future<Integer>> entry : map.entrySet()) {
            System.out.printf("Задача %s, результат %d%n",
                    entry.getKey().getName(), entry.getValue().get());
        }
    }

    public static void invokeAnyExample(ExecutorService threadPool, List<MyCallable> taskList) throws Exception {
        System.out.println("Создаю потоки задач...");

        Integer result = threadPool.invokeAny(taskList);

        System.out.println("Результат invokeAny: " + result);
    }

}
