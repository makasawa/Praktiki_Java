package storage;

import java.util.Arrays;

public class EntityStorage {
    private Object[] data = new Object[10];
    private int size;

    public void add(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Нельзя добавить null");
        }
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
        data[size++] = value;
    }

    public Object get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        return data[i];
    }

    public int size() {
        return size;
    }

    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    public void removeAt(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        for (int j = i; j < size - 1; j++) {
            data[j] = data[j + 1];
        }
        data[--size] = null;
    }
}
