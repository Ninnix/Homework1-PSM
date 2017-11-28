import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMerge extends RecursiveAction {

    static final ForkJoinPool fjPool = new ForkJoinPool();

    @Override
    protected void compute() {

    }
}
