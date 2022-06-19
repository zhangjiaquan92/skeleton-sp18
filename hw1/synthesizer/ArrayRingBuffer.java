package synthesizer;


import java.util.Iterator;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {


        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        this.capacity = capacity;
        fillCount = 0;


    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */

    public void enqueue(T x) {

        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");

        } else {
            rb[last] = x;
            last = indexhelper(last);
            fillCount += 1;
        }

    }
    private int indexhelper(int index) {
        if (index < this.capacity - 1) {
            return index + 1;
        } else {
            return 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {

        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            fillCount -= 1;
            int temp = first;
            first = indexhelper(first);
            return rb[temp];
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            return rb[first];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Selfiterator();
    }


    private class Selfiterator implements Iterator<T> {

        public boolean hasNext() {
            return pt < fillCount;
        }

        public T next() {
            if (pt + first >= capacity) {
                T output = rb[pt + first - capacity];
                pt += 1;
                return output;

            } else {
                T output = rb[pt + first];
                pt += 1;
                return output;
            }


        }
        private int pt;
        Selfiterator() {
            pt = 0;

        }


    }


}
