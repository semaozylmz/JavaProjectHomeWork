package org.project.data;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonRepository<T extends Identifiable> {
    private String filePath;
    private Class<T[]> type;

    public JsonRepository(String filePath, Class<T[]> type) {
        this.filePath = filePath;
        this.type = type;
    }

    public void save(T item) {
        List<T> items = findAll();
        if (items == null) {
            items = new ArrayList<>();
        }
        for (T item2 : items) {
            if (item2.getId().equals(item.getId())) {
                throw new IllegalArgumentException("Duplicate item id: " + item.getId());
            }
        }
        items.add(item);
        writeToFile(items);
    }

    public void update(T item) {
        List<T> items = findAll();
        if (items == null) {
            items = new ArrayList<>();
        }
        for(T item1 : items){
            if (item1.getId().equals(item.getId())) {
                items.remove(item1);
                items.add(item);
                break;
            }
        }
        writeToFile(items);
    }

    public void delete(Integer id) {
        List<T> items = findAll();
        if (items == null) {
            items = new ArrayList<>();
        }
        for (T item : items) {
            if (item.getId().equals(id)) {
                items.remove(item);
                writeToFile(items);
                return;
            }
        }
        throw new IllegalArgumentException("Item not found: " + id);

    }
    public List<T> findAll() {
        try (FileReader reader = new FileReader(filePath)) {
            T[] array = new Gson().fromJson(reader, type);
            if (array != null) {
                return new ArrayList<>(Arrays.asList(array));
            }
        } catch (EOFException e) {
            // Eğer dosya tamamen boşsa, boş bir liste döndür
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void initializeJsonFile(String filePath) {
        File file = new File(filePath);
        //eğer mecvut dosya yok ise boş bir dosya oluşturulur
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]"); // Boş bir JSON dizisi
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeToFile(List<T> items) {
        try (FileWriter writer = new FileWriter(filePath)) {
            new Gson().toJson(items.toArray(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}