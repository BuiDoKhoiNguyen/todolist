package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;
}
