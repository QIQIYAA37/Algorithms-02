package oy.tol.tra;

/**
 * An implementation of the StackInterface.
 */
public class StackImplementation<E> implements StackInterface<E> {

   private Object [] itemArray;
   private int capacity;
   private int currentIndex = -1;
   private static final int DEFAULT_STACK_SIZE = 10;

   public StackImplementation() throws StackAllocationException {
      this(DEFAULT_STACK_SIZE);
   }

   public StackImplementation(int capacity) throws StackAllocationException {
      if (capacity < 2) {
         throw new StackAllocationException("Capacity must be at least 2");
      }
      try {
         this.capacity = capacity;
         this.itemArray = new Object[capacity];
         this.currentIndex = -1;
      } catch (Exception e) {
         throw new StackAllocationException("Cannot allocate memory for stack");
      }
   }

   @Override
   public int capacity() {
      return capacity;
   }

   @Override
   public void push(E element) throws StackAllocationException, NullPointerException {
      if (element == null) {
         throw new NullPointerException("Element cannot be null");
      }
      if (currentIndex + 1 >= capacity) {
         reallocateInternalArray();
      }
      itemArray[++currentIndex] = element;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E pop() throws StackIsEmptyException {
      if (currentIndex < 0) {
         throw new StackIsEmptyException("Stack is empty");
      }
      E element = (E) itemArray[currentIndex];
      itemArray[currentIndex] = null;
      currentIndex--;
      return element;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E peek() throws StackIsEmptyException {
      if (currentIndex < 0) {
         throw new StackIsEmptyException("Stack is empty");
      }
      return (E) itemArray[currentIndex];
   }

   @Override
   public int size() {
      return currentIndex + 1;
   }

   @Override
   public void clear() {
      currentIndex = -1;
   }

   @Override
   public boolean isEmpty() {
      return currentIndex < 0;
   }

   private void reallocateInternalArray() throws RuntimeException {
      try {
         Object[] newArray = new Object[capacity * 2];
         System.arraycopy(itemArray, 0, newArray, 0, capacity);
         itemArray = newArray;
         capacity *= 2;
      } catch (Exception e) {
         throw new StackAllocationException("Cannot reallocate memory for stack");
      }
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder("[");
      for (var index = 0; index <= currentIndex; index++) {
         builder.append(itemArray[index].toString());
         if (index < currentIndex) {
            builder.append(", ");
         }
      }
      builder.append("]");
      return builder.toString();
   }
}