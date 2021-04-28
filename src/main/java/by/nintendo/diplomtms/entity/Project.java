package by.nintendo.diplomtms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"tasks"})
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private long id;

    @NotBlank(message = "Пустое поле.")
    @Size(min = 6, max = 15, message = "Title: должен быть больше 6 и меньше 15.")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Пустое поле.")
    @Size(min = 4, max = 7, message = "ShortName: должен быть больше 4 и меньше 7.")
    @Column(name = "short_name")
    private String shortName;

    @NotBlank(message = "Пустое поле.")
    @Size(min = 6, max = 15, message = "Description: должен быть больше 6 и меньше 25.")
    @Column(name = "description")
    private String description;

    @Column(name = "create_time")
    private String creatTime;

    @Column(name = "end_time")
    private String endTime;

    @Enumerated(value = EnumType.STRING)
    private ProjectStatus projectStatus;

    @ManyToOne
    private User owner;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "project_users",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<Task> tasks;

}
