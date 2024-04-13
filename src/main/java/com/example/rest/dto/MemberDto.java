package com.example.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MemberDto {

    @JsonProperty("name_a")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String name;

    @JsonProperty("age_b")
    @JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
    private int age;

    private String address;

    public String getAddress() {
        return address;
    }

    public int getHeight() {
        return 176;
    }

    public boolean isMan() {
        return true;
    }
}
