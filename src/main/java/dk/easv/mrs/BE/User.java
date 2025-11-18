package dk.easv.mrs.BE;

public class User {

    private int id = -1;
    private String name = "";

    public User(int id, String name) {
        setId(id);
        setName(name);
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }




}
