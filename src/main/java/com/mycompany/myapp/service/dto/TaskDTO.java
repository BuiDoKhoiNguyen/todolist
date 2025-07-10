package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enums.TodoPriority;
import com.mycompany.myapp.domain.enums.TodoStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    private TodoStatus status;

    private TodoPriority priority;

    private Instant dueDate;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;
}
