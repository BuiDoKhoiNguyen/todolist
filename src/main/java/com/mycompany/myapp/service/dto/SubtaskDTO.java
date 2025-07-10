package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enums.TodoStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubtaskDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    private String title;

    private TodoStatus status;

    private Long taskId;
}
