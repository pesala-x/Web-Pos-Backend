package org.example.webposbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CustomerDTO implements Serializable {
    private String id;
    private String name;
    private String address;
    private String mobile;
}
