package api.model;

import api.model.enums.EStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String status = EStatus.IN_PROGRESS.toString();
    private String priority;

    public Task(String title, String description, String priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    @ManyToOne
    private User author;

    @ManyToMany
    private List<User> executor = new ArrayList<>();

    @OneToMany
    private List<Comment> comment = new ArrayList<>();
}
