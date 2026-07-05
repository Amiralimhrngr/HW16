package model;

public class Member {
    private Long id;
    private String fullName;
    private String phone;

    public Member(Long id, String fullName, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
    }

    public Member(String fullName, String phone) {
        this.fullName = fullName;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("""
                MEMBER
                ID: %d
                Full Name: %s
                Phone: %s
                """, getId(), getFullName(), getPhone());
    }
}
