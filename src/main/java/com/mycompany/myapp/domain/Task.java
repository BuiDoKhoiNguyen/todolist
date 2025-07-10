package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enums.TodoPriority;
import com.mycompany.myapp.domain.enums.TodoStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
public class Task extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TodoStatus status = TodoStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TodoPriority priority = TodoPriority.MEDIUM;

    @Column(name = "due_date")
    private Instant dueDate;

    // Task N:1 Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties(value = { "tasks" }, allowSetters = true)
    private Category category;

    // Task N:1 User (assigned user)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id")
    @JsonIgnoreProperties(value = { "authorities" }, allowSetters = true)
    private User assignedUser;

    // Task 1:N Subtask
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = { "task" }, allowSetters = true)
    private Set<Subtask> subtasks = new HashSet<>();

    // Task 1:N Comment
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = { "task", "user" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    // Task N:M Tag
    @ManyToMany
    @JoinTable(name = "task_tag", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIgnoreProperties(value = { "tasks" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();
}
