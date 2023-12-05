package api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String status;
    private String priority;

    @ManyToOne
    private User author;

    @ManyToMany
    private List<User> executor = new ArrayList<>();

    @OneToMany
    private List<Comment> comment = new ArrayList<>();
}
