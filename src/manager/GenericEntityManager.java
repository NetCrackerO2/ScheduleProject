package manager;


import mvc.Commands.ElementAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;


public abstract class GenericEntityManager<T extends Entity> {
    private List<T> list = new ArrayList<>();
    private int nextIndex = 0;

    protected GenericEntityManager() {
    }

    public T createObject() {
        boolean isCreated = false;
        T createdObject = null;

        while (!isCreated) {
            try {
                createdObject = createObject(nextIndex);
                isCreated = true;
            } catch (ElementAlreadyExistsException ignored) {
            }

            nextIndex++;
        }

        return createdObject;
    }

    public T createObject(int index) throws ElementAlreadyExistsException {
        if (index < 0)
            throw new IllegalArgumentException("Incorrect index");
        if (isExist(index))
            throw new IllegalArgumentException("Object already exists");

        T newObject = newObject(index);

        list.add(newObject);
        return newObject;
    }

    protected abstract T newObject(int index);

    public void removeObject(int index) {
        list.remove(
                list.stream().filter(
                        object -> object.getIndex() == index
                ).findFirst().get()
        );
    }

    public T getObject(int index) {
        return list.stream().filter(
                object -> object.getIndex() == index
        ).findFirst().get();
    }

    public ArrayList<T> getAllObjects() {
        return new ArrayList<>(list);
    }

    public boolean isExist(int index) {
        return list.stream().anyMatch(
                object -> object.getIndex() == index
        );
    }
}
