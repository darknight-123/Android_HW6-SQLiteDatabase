package ttu.mis.todolist;

public class MyFriend {
    private String name;
    private String sex;
    private String addr;

    public MyFriend(String name, String sex, String addr) {
        this.name = name;
        this.sex = sex;
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
