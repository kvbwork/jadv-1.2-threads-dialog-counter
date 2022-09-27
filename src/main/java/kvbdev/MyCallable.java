package kvbdev;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<Integer> {
    protected final String name;
    protected final int iterations;

    public MyCallable(String name, int iterations) {
        this.name = name;
        this.iterations = iterations;
    }

    @Override
    public Integer call() throws Exception {
        final String threadName = Thread.currentThread().getName();
        int count = 0;

        try {
            for (int i = 0; i < iterations; i++) {
                Thread.sleep(2500);
                ++count;
                System.out.println("Я задача " + name + ", мой поток " + threadName + ". Всем привет! (" + count + ")");
            }
            System.out.printf("%s завершена\n", name);
        } catch (InterruptedException err) {
            System.out.printf("%s прервана\n", name);
        }

        return count;
    }

    public String getName() {
        return name;
    }
}
