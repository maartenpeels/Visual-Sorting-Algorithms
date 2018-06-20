package helpers;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.Random;

import algorithms.Algorithm;
import algorithms.BadSort;
import algorithms.InsertionSort;
import algorithms.QuickSort;
import algorithms.RadixSort;
import enums.AccessType;
import enums.Algorithms;
import enums.Color;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glViewport;

public class SortingArray {
    private Algorithm algorithm;

    private int[] show_values;
    private int[] proc_values;

    private Queue<Action> actions;

    private HashMap<Integer, Color> colors;

    private int size;

    private int swaps;
    private int comparisons;

    public SortingArray(Integer size) {
        this.size = size;

        this.show_values = new int[size];
        this.proc_values = new int[size];

        this.actions = new ArrayDeque<>();
        this.colors = new HashMap<>();

        randomize();
    }

    private void randomize() {
        for(int i = 0; i < size; i++) {
            show_values[i] = i;
        }

        shuffleArray(show_values);
        proc_values = show_values.clone();
    }

    private void shuffleArray(int[] array)
    {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }

    private void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public boolean lessThan(int i, int j) {
        return compare(i, j) < 0;
    }

    public void swap(int i, int j) {
        actions.add(new Action(AccessType.SWAP, i, j));

        swap(proc_values, i, j);
    }

    public void set(int pos, int val) {
        actions.add(new Action(AccessType.SET, pos, val));
    }

    public void mark(int pos) {
        actions.add(new Action(AccessType.MARK, pos, 0));
    }

    private int compare(int i, int j) {
        actions.add(new Action(AccessType.COMPARISON, i, j));

        return proc_values[i] - proc_values[j];
    }

    private void swap_show(int i, int j) {
        swap(show_values, i, j);
        swaps++;

        colors.put(i, Color.SWAP);
        colors.put(j, Color.SWAP);
    }

    private void compare_show(int i, int j) {
        comparisons++;

        colors.put(i, Color.COMPARISON);
        colors.put(j, Color.COMPARISON);
    }

    public void step() {
        Action action = actions.poll();

        if(action == null)
            return;

        switch (action.getType()) {
            case SWAP:
                swap_show(action.getI(), action.getJ());
                break;
            case COMPARISON:
                compare_show(action.getI(), action.getJ());
                break;
            case SET:
                colors.put(action.getI(), Color.SET);
                show_values[action.getI()] = action.getJ();
                break;
            case MARK:
                colors.put(action.getI(), Color.MARK);
                break;
            default:
                break;
        }
    }

    public Integer getSize() {
        return size;
    }

    public int[] getShow_values() {
        return show_values;
    }

    public int[] getProc_values() {
        return proc_values;
    }

    public void setProc_values(int[] arr) {
        proc_values = arr;
    }

    public int getSwaps() {
        return swaps;
    }

    public int getComparisons() {
        return comparisons;
    }

    public void setAlgorithm(Algorithms algorithm) {
        switch (algorithm) {
            case BADSORT:
                this.algorithm = new BadSort();
                break;
            case INSERTIONSORT:
                this.algorithm = new InsertionSort();
                break;
            case QUICKSORT:
                this.algorithm = new QuickSort();
                break;
            case RADIXSORT:
                this.algorithm = new RadixSort();
                break;
            default:
                this.algorithm = new BadSort();
                break;
        }

        runAlgorithm();
    }

    private void runAlgorithm() {
        if (algorithm == null)
            return;

        SortingArray arr = this.algorithm.run(this);
        proc_values =  arr.proc_values;
        actions = arr.actions;

        Logger.Log("Done with algorithm, starting visualizer");
    }

    public void draw(int width, int height) {
        for(int i = 0; i < size; i++) {
            drawRect(i, width, height);
        }

        colors.clear();
    }

    private void drawRect(int i, int w, int h) {
        int value = show_values[i];
        double height = (((double)value + 1) / (double)size) * h;
        double barWidth = (w - (size-1)) / (double)size;
        if(w <= (size - 1)) barWidth = 0.0;
        double bstep = barWidth + 1;

        Color col = colors.get(i);
        if(col != null){
            switch (col) {
                case SWAP:
                    glColor3f(1.0f, 0.0f, 0.0f);
                    break;
                case COMPARISON:
                    glColor3f(0.0f, 0.0f, 1.0f);
                    break;
                case SET:
                    glColor3f(0.7f, 0.6f, 1.0f);
                    break;
                case MARK:
                    glColor3f(0.0f, 1.0f, 0.0f);
                    break;
            }
        }else{
            glColor3f(0.9f, 0.9f, 0.9f);
        }

        glViewport((int) (i * bstep), 0, (int) Math.floor(barWidth), (int) Math.floor(height));
        glRectf(-1.f, -1.f, 1.f, 1.f);
        glViewport(0, 0, w, h);
    }

    public boolean isSorted() {
        for (int i = 0; i < proc_values.length - 1; i++) {
            if (proc_values[i] > proc_values[i + 1]) {
                return false;
            }
        }

        return true;
    }
}
