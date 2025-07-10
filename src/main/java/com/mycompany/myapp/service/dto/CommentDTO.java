package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 1000)
    private String content;

    private Long taskId;

    private Long userId;

    private String userLogin;

    private Instant createdDate;
}
