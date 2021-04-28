package by.nintendo.diplomtms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @NotBlank(message = "Пустое поле.")
    @Size(min = 4, max = 10, message = "Name: должен быть больше 6 и меньше 10.")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Пустое поле.")
    @Size(min = 3, max = 10, message = "Surname: должен быть больше 3 и меньше 10.")
    @Column(name = "surname")
    private String surname;

    @NotBlank(message = "Пустое поле.")
    @Column(name = "phone")
    @Pattern(regexp = "^(\\+375)(29|44|)(\\d{3})(\\d{2})(\\d{2})$",
            message = "Не корректный телефонный номер. Формат: +375(29/44) и 5 цифр.")
    private String phone;

    @NotBlank(message = "Пустое поле.")
    @Email(message = "Не правильный формат email.")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Пустое поле.")
    @Size(min = 3, max = 10, message = "Login: должен быть больше 3 и меньше 10.")
    @Column(name = "login")
    private String login;

    @NotBlank(message = "Пустое поле.")
    @Size(min = 3, message = "Password: должен быть больше 3 ")
    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "owner")
    private List<Project> ownedProjects;

    @ManyToMany
    @JoinTable(name = "project_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

    @ManyToMany()
    @JoinTable(name = "users_task",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<Task> tasks;

    @OneToMany(mappedBy = "author")
    private List<Comments> comments;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                '}';
    }

}
